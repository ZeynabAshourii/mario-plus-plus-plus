package org.example.server.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.example.client.view.main_panels.online_game.GameMode;
import org.example.config_loader.Level;
import org.example.local_data.ManageUser;
import org.example.marathon_mario.controller.HandlerMarathon;
import org.example.marathon_mario.model.PlayerMarathon;
import org.example.order.*;
import org.example.local_data.ChatPV;
import org.example.local_data.User;
import org.example.server.model.Item;
import org.example.server.model.SpecialBag;
import org.example.mario_survival.controller.HandlerSurvival;
import org.example.mario_survival.model.MarioTeam;
import org.example.mario_survival.model.PlayerSurvival;
import org.example.team_game.controller.HandlerTeamGame;
import org.example.team_game.model.PlayerTeamGame;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.DatagramSocket;
import java.util.LinkedList;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public class ClientListenThread extends Thread{
    private ServerSideClient client;
    private Server server;
    private boolean firstTime = false;

    public ClientListenThread(ServerSideClient client) {
        this.client = client;
        this.server = client.getServer();
    }

    @Override
    public void run() {
        while (true) {
            try {
                Object object = client.getcInput().readObject();
                if(object instanceof Order) {
                    handelOrder((Order) object);
                } else if (object instanceof Message) {
                    handelMessage((Message) object);
                } else if (object instanceof Notification) {
                    handelNotification((Notification) object);
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        }
    }
    public void handelPvPanel(Order order){
        LinkedList<Message> messages = ChatPV.findPV(order.getUsername() ,order.getOtherSideUsername()).getMessages();
        LinkedList<Message> newMessages = new LinkedList<>();
        for(int i = 0; i < messages.size(); i++){
            Message newMessage = new Message (messages.get(i).getSenderUsername() , messages.get(i).getReceiverUsername() , messages.get(i).getText());
            newMessage.setTime(messages.get(i).getTime());
            newMessages.add(newMessage);
        }
        client.send(new Information<Message>(newMessages , Information.InformationType.MESSAGE));
        String otherUsername = "";
        if(client.getUser().getUsername().equals(order.getUsername())){
            otherUsername = order.getOtherSideUsername();
        }else {
            otherUsername = order.getUsername();
        }
        ServerSideClient otherClient = otherClient(otherUsername);
        if(otherClient != null) {
            otherClient.send(new Information<Message>(newMessages , Information.InformationType.MESSAGE));
        }
    }
    public void handelExchange(){
        int coin = client.getUser().getCoin();
        int diamond = client.getUser().getDiamond();
        int exchange = client.getServer().getServerSetting().getShopDiamondConversionRate();
        if(diamond >= 1) {
            client.getUser().setCoin(coin + exchange);
            client.getUser().setDiamond(diamond - 1);
            client.send(new Order(Order.OrderType.EXCHANGE , client.getUser().getCoin() , client.getUser().getDiamond()));
        }else {
            client.send(new Order(Order.OrderType.EXCHANGE, -3 , 0));
        }
    }
    public void handelStartGame(Order order){
        client.getUser().setChosenSpecialBag(order.getWhichBag());
        if (order.getGameMode().equals(GameMode.TEAM_GAME)){
            client.setRequestTeamGame(true);
            startTeamGame();
        } else if (order.getGameMode().equals(GameMode.MARIO_MARATHON)) {
            client.setRequestMarathonMario(true);
            startMarathon();
        } else if (order.getGameMode().equals(GameMode.TEAM_MARIO_SURVIVAL)) {
            if (client.getUser().getDiamond() >= 1) {
                client.setRequestTeamMarioSurvival(true);
                startTeamSurvival();
            }else {
                client.send(new Order(Order.OrderType.EXCHANGE, -3 , 0));
            }
        } else if (order.getGameMode().equals(GameMode.SOLO_MARIO_SURVIVAL)) {
            if (client.getUser().getDiamond() >= 1) {
                client.setRequestSoloMarioSurvival(true);
                startSoloSurvival();
            }else {
                client.send(new Order(Order.OrderType.EXCHANGE, -3 , 0));
            }
        }
    }
    public void buyItemByCoin(Item item){
        if (!item.isCoin()) {
            client.send(new Order(Order.OrderType.EXCHANGE, -4, 0));
        } else {
            int coin = client.getUser().getCoin();
            int exchange = client.getServer().getServerSetting().getShopDiamondConversionRate();
            if (coin >= exchange) {
                if (item.buy()) {
                    if(client.getServer().getStore().buy(item.getItemType())){
                        client.getUser().setCoin(coin - exchange);
                        sendItem(client, client.getUser().getItems());
                        client.send(new Order(Order.OrderType.EXCHANGE , client.getUser().getCoin() , client.getUser().getDiamond()));
                    }else {
                        item.back();
                        client.send(new Order(Order.OrderType.EXCHANGE, -7, 0));
                    }
                }else {
                    client.send(new Order(Order.OrderType.EXCHANGE, -8, 0));
                }
            } else {
                client.send(new Order(Order.OrderType.EXCHANGE, -2, 0));
            }
        }
    }
    public void buyItemWithDiamond(Item item){
        if (!item.isDiamond()) {
            client.send(new Order(Order.OrderType.EXCHANGE, -5, 0));
        } else {
            int diamond = client.getUser().getDiamond();
            if (diamond >= 1) {
                if (item.buy()) {
                    if(client.getServer().getStore().buy(item.getItemType())){
                        client.getUser().setDiamond(diamond - 1);
                        sendItem(client, client.getUser().getItems());
                        client.send(new Order(Order.OrderType.EXCHANGE , client.getUser().getCoin() , client.getUser().getDiamond()));
                    }else {
                        item.back();
                        client.send(new Order(Order.OrderType.EXCHANGE, -7, 0));
                    }
                }else {
                    client.send(new Order(Order.OrderType.EXCHANGE, -8, 0));
                }
            } else {
                client.send(new Order(Order.OrderType.EXCHANGE, -3, 0));
            }
        }
    }
    public void buyItem(Order order , Item item){
        if(item.getLevel() <= client.getUser().getLevel()) {
            if (order.getOption() == 0) {
                buyItemByCoin(item);
            } else if (order.getOption() == 1) {
                buyItemWithDiamond(item);
            }
        }else {
            client.send(new Order(Order.OrderType.EXCHANGE, -6, 0));
        }
    }
    public void editBag0(Order order){
        if(client.getUser().getItems().get(0).getNumberAvailable() > 0) {
            client.getUser().getSpecialBags()[order.getWhichBag()].getItems()[order.getWhichItem()] = new Item(Item.ItemType.INVISIBLE, client.getUser().getUsername());
            client.getUser().getItems().get(0).setNumberAvailable(client.getUser().getItems().get(0).getNumberAvailable() - 1);
        }else {
            client.send(new Order(Order.OrderType.INSUFFICIENT_INVENTORY));
        }
    }
    public void editBag1(Order order){
        if(client.getUser().getItems().get(1).getNumberAvailable() > 0){
            client.getUser().getSpecialBags()[order.getWhichBag()].getItems()[order.getWhichItem()] = new Item(Item.ItemType.SPEED , client.getUser().getUsername());
            client.getUser().getItems().get(1).setNumberAvailable(client.getUser().getItems().get(1).getNumberAvailable() - 1);
        }else {
            client.send(new Order(Order.OrderType.INSUFFICIENT_INVENTORY));
        }
    }
    public void editBag2(Order order){
        if (client.getUser().getItems().get(2).getNumberAvailable() > 0) {
            client.getUser().getSpecialBags()[order.getWhichBag()].getItems()[order.getWhichItem()] = new Item(Item.ItemType.HEAL, client.getUser().getUsername());
            client.getUser().getItems().get(2).setNumberAvailable(client.getUser().getItems().get(2).getNumberAvailable() - 1);
        }else {
            client.send(new Order(Order.OrderType.INSUFFICIENT_INVENTORY));
        }
    }
    public void editBag3(Order order){
        if (client.getUser().getItems().get(3).getNumberAvailable() > 0) {
            client.getUser().getSpecialBags()[order.getWhichBag()].getItems()[order.getWhichItem()] = new Item(Item.ItemType.EXPLOSIVE_BOMB, client.getUser().getUsername());
            client.getUser().getItems().get(3).setNumberAvailable(client.getUser().getItems().get(3).getNumberAvailable() - 1);
        }else {
            client.send(new Order(Order.OrderType.INSUFFICIENT_INVENTORY));
        }
    }
    public void editBag4(Order order){
        if ( client.getUser().getItems().get(4).getNumberAvailable() > 0) {
            client.getUser().getSpecialBags()[order.getWhichBag()].getItems()[order.getWhichItem()] = new Item(Item.ItemType.SPEED_BOMB, client.getUser().getUsername());
            client.getUser().getItems().get(4).setNumberAvailable(client.getUser().getItems().get(4).getNumberAvailable() - 1);
        }else {
            client.send(new Order(Order.OrderType.INSUFFICIENT_INVENTORY));
        }
    }
    public void editBag5(Order order){
        if (client.getUser().getItems().get(5).getNumberAvailable() > 0) {
            client.getUser().getSpecialBags()[order.getWhichBag()].getItems()[order.getWhichItem()] = new Item(Item.ItemType.HAMMER, client.getUser().getUsername());
            client.getUser().getItems().get(5).setNumberAvailable(client.getUser().getItems().get(5).getNumberAvailable() - 1);
        }else {
            client.send(new Order(Order.OrderType.INSUFFICIENT_INVENTORY));
        }
    }
    public void editBag(Order order){
        int option = order.getOption();
        if(!isEmptyItem(client.getUser().getSpecialBags()[order.getWhichBag()].getItems()[order.getWhichItem()])){
            client.getUser().getItems().get(numberOfItem(client.getUser().getSpecialBags()[order.getWhichBag()].getItems()[order.getWhichItem()])).setNumberAvailable(client.getUser().getItems().get(numberOfItem(client.getUser().getSpecialBags()[order.getWhichBag()].getItems()[order.getWhichItem()])).getNumberAvailable() + 1);
        }
        if(option == 0 ){
            editBag0(order);
        } else if (option == 1) {
            editBag1(order);
        } else if (option == 2) {
            editBag2(order);
        } else if (option == 3) {
            editBag3(order);
        } else if (option == 4) {
            editBag4(order);
        } else if (option == 5) {
            editBag5(order);
        } else if (option == 6) {
            client.getUser().getSpecialBags()[order.getWhichBag()].getItems()[order.getWhichItem()] = new Item(client.getUser().getUsername());
        }
        sendItem(client , client.getUser().getItems());
        client.send(createSpecialBag(client.getUser().getSpecialBags()[order.getWhichBag()]));
    }
    public void handelOrder(Order order){
        if(order.getOrderType().equals(Order.OrderType.NEW_USER)){
            newUser(order.getUsername() , order.getPassword());
        }
        else if (order.getOrderType().equals(Order.OrderType.SEARCH_PV)){
            searchPv(order.getUsername());
        }
        else if (order.getOrderType().equals(Order.OrderType.RESET_PV)) {
            sendChatPV(client , ChatPV.userPV(client.getUser().getUsername()));
        } else if (order.getOrderType().equals(Order.OrderType.PV_PANEL)) {
            handelPvPanel(order);
        } else if (order.getOrderType().equals(Order.OrderType.RESET_NOTIFICATION)) {
            sendNotification(client , client.getUser().getNotifications());
        } else if (order.getOrderType().equals(Order.OrderType.SIGN_IN)) {
            signInUser(order.getUsername(), order.getPassword());
        } else if (order.getOrderType().equals(Order.OrderType.RESET_ITEM)) {
            sendItem(client , client.getUser().getItems());
        } else if (order.getOrderType().equals(Order.OrderType.EXCHANGE)) {
            handelExchange();
        } else if (order.getOrderType().equals(Order.OrderType.BUY_ITEM)) {
            buyItem(order , searchItem(order.getItem()));
        } else if (order.getOrderType().equals(Order.OrderType.OPEN_ONLINE_GAME_PANEL)) {
            sendSpecialBags(client , client.getUser().getSpecialBags());
        } else if (order.getOrderType().equals(Order.OrderType.OPEN_BAG)){
            sendItem(client , client.getUser().getItems());
        } else if (order.getOrderType().equals(Order.OrderType.EDIT_BAG)){
            editBag(order);
        } else if (order.getOrderType().equals(Order.OrderType.REQUEST_GAME_MARIO)) {
            handelStartGame(order);
        } else if (order.getOrderType().equals(Order.OrderType.END_WAITING_PANEL)) {
            handelEndWaitingPanel(order);
        }
    }
    public void handelMessage(Message message){
        ChatPV.findPV(message.getSenderUsername() , message.getReceiverUsername()).setLastTimeMessage(message.getTime());
        ChatPV.findPV(message.getSenderUsername() , message.getReceiverUsername()).getMessages().add(message);
        LinkedList<Message> messages = ChatPV.findPV(message.getSenderUsername() , message.getReceiverUsername()).getMessages();
        LinkedList<Message> newMessages = new LinkedList<>();
        for(int i = 0; i < messages.size(); i++){
            Message newMessage = new Message (messages.get(i).getSenderUsername() , messages.get(i).getReceiverUsername() , messages.get(i).getText());
            newMessage.setTime(messages.get(i).getTime());
            newMessages.add(newMessage);
        }
        client.send(new Information<Message>(newMessages , Information.InformationType.MESSAGE));

        String otherUsername = "";
        if(client.getUser().getUsername().equals(message.getSenderUsername())){
            otherUsername = message.getReceiverUsername();
        }else {
            otherUsername = message.getSenderUsername();
        }
        ServerSideClient otherClient = otherClient(otherUsername);
        if(otherClient != null) {
            otherClient.send(new Information<Message>(newMessages , Information.InformationType.MESSAGE));
            sendChatPV(otherClient , ChatPV.userPV(otherUsername));
            sendChatPV(client , ChatPV.userPV(client.getUser().getUsername()));
        }

        Notification notification = new Notification(message.getSenderUsername() , message.getText() , Notification.NotificationType.NEW_MESSAGE);
        User otherUser = ManageUser.getInstance().findUser(otherUsername);
        if(otherUser != null){
            otherUser.getNotifications().add(notification);
            if(otherClient != null){
                sendNotification(otherClient , otherUser.getNotifications());
            }
        }
    }
    public void handelNotification(Notification notification){
        for(int i = 0 ; i < client.getUser().getNotifications().size(); i++){
            Notification oldNotification = client.getUser().getNotifications().get(i);
            if(oldNotification.getType().equals(notification.getType()) && oldNotification.getText().equals(notification.getText()) && oldNotification.getTopic().equals(notification.getTopic())){
                client.getUser().getNotifications().remove(oldNotification);
                break;
            }
        }
        sendNotification(client , client.getUser().getNotifications());
    }
    public void handelEndWaitingPanel(Order order){
        if (order.getGameMode().equals(GameMode.TEAM_GAME)){
            client.setRequestTeamGame(false);
        } else if (order.getGameMode().equals(GameMode.MARIO_MARATHON)) {
            client.setRequestMarathonMario(false);
        } else if (order.getGameMode().equals(GameMode.TEAM_MARIO_SURVIVAL)) {
            client.setRequestTeamMarioSurvival(false);
        } else if (order.getGameMode().equals(GameMode.SOLO_MARIO_SURVIVAL)) {
            client.setRequestSoloMarioSurvival(false);
        }
    }

    public void startTeamGame() {
        Timer timer = new Timer();
        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                LinkedList<ServerSideClient> nearScore = client.getNearScoreClient(GameMode.TEAM_GAME);
                if (nearScore.size() >= 2) {
                    int port = port();

                    LinkedList<PlayerTeamGame> playerTeamGames = new LinkedList<>();
                    for (int i = 0; i < nearScore.size(); i++) {
                        ServerSideClient serverSideClient = nearScore.get(i);
                        PlayerTeamGame playerTeamGame;
                        int specialBag = serverSideClient.getUser().getChosenSpecialBag();
                        if (specialBag == 0) {
                            playerTeamGame = new PlayerTeamGame(serverSideClient.getUser().getUsername(), null , swordItem(serverSideClient) , new Random().nextInt(3));
                        } else {
                            playerTeamGame = new PlayerTeamGame(serverSideClient.getUser().getUsername(), serverSideClient.getUser().getSpecialBags()[specialBag - 1] , swordItem(serverSideClient) , new Random().nextInt(3));
                        }
                        playerTeamGames.add(playerTeamGame);

                        serverSideClient.send(new Information<Item>(playerTeamGame.getAllItems() , Information.InformationType.START_TEAM_GAME , port));
                        serverSideClient.setRequestMarathonMario(false);
                    }
                    File filePath = new File("C:\\Users\\ASUS\\OneDrive\\Desktop\\untitled12final\\src\\main\\java\\org\\example\\resources\\config\\config1-ap.json");
                    ObjectMapper objectMapper = new ObjectMapper();
                    objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
                    Level level;
                    try {
                        level = objectMapper.readValue(new File(String.valueOf(filePath)), Level.class);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                    HandlerTeamGame handlerTeamGame = new HandlerTeamGame(level.sections , playerTeamGames , server.getServerSetting().getSpeedPotionMultiplier(),
                            server.getServerSetting().getSpeedPotionPeriod(), server.getServerSetting().getInvisibilityPotionPeriod(),
                            server.getServerSetting().getHealthPotionHPPercent(), server.getServerSetting().getDamageBombBlockArea(),
                            server.getServerSetting().getDamageBombPercent(), server.getServerSetting().getSpeedBombBlockArea(),
                            server.getServerSetting().getSpeedBombMultiplier(), server.getServerSetting().getHammerStunPeriod(),
                            server.getServerSetting().getSwordBlockRange(), server.getServerSetting().getSwordPercentDamage(),
                            server.getServerSetting().getSwordPushBackBlock(), server.getServerSetting().getSwordCoolDown());
                    for (int i = 0; i < nearScore.size(); i++) {
                        ServerSideClient serverSideClient = nearScore.get(i);
                        serverSideClient.setHandlerTeamGame(handlerTeamGame);
                    }
                    TeamGameServer teamGameServer = new TeamGameServer(port , handlerTeamGame);

                }
            }
        };
        timer.schedule(timerTask ,8000 );
    }
    public int numberOfItem(Item item){
        LinkedList<Item> items = client.getUser().getItems();
        for(int i = 0; i < items.size(); i++){
            Item oldItem = items.get(i);
            if(oldItem.getItemType().equals(item.getItemType())){
                return i;
            }
        }
        return -1;
    }
    public void newUser(String username , String password){
        try {
            if(ManageUser.getInstance().findUsers(username) == null) {
                User user = new User(username, password);
                ManageUser.getInstance().writeUser(user);
                client.setUser(user);
                client.send(new Order(Order.OrderType.NEW_USER , username , user.getCoin() , user.getDiamond()));
            }else {
                client.send(new Order(Order.OrderType.NEW_USER , "" , -1 , 0));
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public void signInUser(String username , String password){
        boolean find = false;
        for(int i = 0; i < ManageUser.getInstance().getUsers().size(); i++){
            User user = ManageUser.getInstance().getUsers().get(i);
            if(user.getUsername().equals(username) && user.getPassword().equals(password)){
                client.setUser(user);
                client.send(new Order(Order.OrderType.NEW_USER , username , user.getCoin() , user.getDiamond()));
                find = true;
            }
        }
        if(!find){
            client.send(new Order(Order.OrderType.NEW_USER , "" , -2 , 0));
        }
    }

    public void searchPv(String username){
        LinkedList<ChatPV> chatPVs = new LinkedList<>();
        for(int i = 0; i < ManageUser.getInstance().getUsers().size(); i++){
            if(ManageUser.getInstance().getUsers().get(i).getUsername().contains(username) && !ManageUser.getInstance().getUsers().get(i).getUsername().equals(client.getUser().getUsername())){
                ChatPV chatPV = ChatPV.findPV(client.getUser().getUsername() , ManageUser.getInstance().getUsers().get(i).getUsername());
                ChatPV newChatPV = new ChatPV(chatPV.getUsername1() , chatPV.getUsername2());
                newChatPV.setLastTimeMessage(chatPV.getLastTimeMessage());
                chatPVs.add(newChatPV);
            }
        }
        client.send(new Information<ChatPV>(chatPVs , Information.InformationType.CHAT_PV));
    }
    public ServerSideClient otherClient(String username){
        for(int i = 0; i < Server.getClients().size(); i++){
            if(Server.getClients().get(i).getUser() != null) {
                if (Server.getClients().get(i).getUser().getUsername().equals(username)) {
                    return Server.getClients().get(i);
                }
            }
        }
        return null;
    }
    public void sendChatPV(ServerSideClient senderClient , LinkedList<ChatPV> chatPVs){
        LinkedList<ChatPV> newChatPVs = new LinkedList<>();
        for(int i = 0; i < chatPVs.size(); i++){
            ChatPV chatPV = chatPVs.get(i);
            ChatPV newChatPV = new ChatPV(chatPV.getUsername1() , chatPV.getUsername2());
            newChatPV.setLastTimeMessage(chatPV.getLastTimeMessage());
            newChatPVs.add(newChatPV);
        }
        senderClient.send(new Information<ChatPV>(newChatPVs , Information.InformationType.CHAT_PV));
    }
    public void sendNotification(ServerSideClient senderClient , LinkedList<Notification> notifications){
        LinkedList<Notification> newNotifications = new LinkedList<>();
        for(int i = 0; i < notifications.size(); i++){
            Notification notification = notifications.get(i);
            Notification newNotification = new Notification(notification.getTopic(), notification.getText() , notification.getType());
            newNotifications.add(newNotification);
        }
        senderClient.send(new Information<Notification>(newNotifications , Information.InformationType.NOTIFICATION));
    }
    public void sendItem(ServerSideClient senderClient , LinkedList<Item> items){
        LinkedList<Item> newItems = new LinkedList<>();
        for(int i = 0; i < items.size(); i++){
            Item item = items.get(i);
            newItems.add(createNewItem(item));
        }
        senderClient.send(new Information<Item>(newItems , Information.InformationType.ITEMS));
    }
    public Item searchItem(Item item){
        LinkedList<Item> items = client.getUser().getItems();
        for(int i = 0; i < items.size(); i++){
            Item oldItem = items.get(i);
            if(oldItem.getItemType().equals(item.getItemType())){
                return oldItem;
            }
        }
        return null;
    }
    public Item createNewItem(Item item){
        if (item.getItemType() == null){
            Item newItem = new Item(item.getUsername());
            return newItem;
        }
        Item newItem = new Item(item.getItemType() , item.getUsername());
        newItem.setCoin(item.isCoin());
        newItem.setDiamond(item.isDiamond());
        newItem.setLevel(item.getLevel());
        newItem.setStartDate(item.getStartDate());
        newItem.setEndDate(item.getEndDate());
        newItem.setNumberSoldPerPlayer(item.getNumberSoldPerPlayer());
        newItem.setNumberAvailable(item.getNumberAvailable());
        return newItem;
    }
    public SpecialBag createSpecialBag(SpecialBag specialBag){
        if (specialBag == null){
            return null;
        }
        Item[] items = specialBag.getItems();
        Item[] newItems = new Item[items.length];
        for(int i = 0; i < items.length; i++){
            newItems[i] = createNewItem(items[i]);
        }
        SpecialBag newSpecialBag = new SpecialBag(newItems);
        return newSpecialBag;
    }
    public void sendSpecialBags(ServerSideClient sender , SpecialBag[]specialBags){
        SpecialBag[] newSpecialBags = new SpecialBag[specialBags.length];
        for(int i = 0; i < specialBags.length; i++){
            newSpecialBags[i] = createSpecialBag(specialBags[i]);
        }
        sender.send(new Information<SpecialBag>(client.getUser().getLevel() , Information.InformationType.SPECIAL_BAGS , newSpecialBags));
    }
    public boolean isEmptyItem(Item item){
        if(item == null){
            return true;
        }else if (item.getItemType() == null){
            return true;
        }
        return false;
    }
    public void startTeamSurvival(){
        Timer timer = new Timer();
        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                LinkedList<ServerSideClient> nearScore = client.getNearScoreClient(GameMode.TEAM_MARIO_SURVIVAL);
                int length = nearScore.size();
                if (length >= 2) {
                    int port = port();

                    LinkedList<PlayerSurvival> soloSurvivals = new LinkedList<>();
                    LinkedList<MarioTeam> teams = new LinkedList<>();
                    int numberOfTeams = 2;
                    if (length >= 6) {
                        numberOfTeams = new Random().nextInt((length - 2) / 2) + 2;
                    }
                    int numberOfMembers = length/numberOfTeams;
                    LinkedList<PlayerSurvival> linkedList = new LinkedList<>();
                    for (int i = 0; i < length; i++) {
                        ServerSideClient serverSideClient = nearScore.get(i);
                        PlayerSurvival soloSurvival;
                        int specialBag = serverSideClient.getUser().getChosenSpecialBag();
                        if (specialBag == 0) {
                            soloSurvival = new PlayerSurvival(serverSideClient.getUser().getUsername(), null , swordItem(serverSideClient));
                        } else {
                            soloSurvival = new PlayerSurvival(serverSideClient.getUser().getUsername(), serverSideClient.getUser().getSpecialBags()[specialBag - 1] , swordItem(serverSideClient));
                        }

                        linkedList.add(soloSurvival);

                        if (linkedList.size() >= numberOfMembers){
                            if (teams.size() < numberOfTeams-1) {
                                teams.add(new MarioTeam(linkedList));
                                linkedList = new LinkedList<>();
                            }else if(i == length-1) {
                                teams.add(new MarioTeam(linkedList));
                            }                        }
                        soloSurvivals.add(soloSurvival);

                        serverSideClient.send(new Information<Item>(soloSurvival.getAllItems() , Information.InformationType.START_SOLO_MARIO_SURVIVAL , port));
                        serverSideClient.setRequestSoloMarioSurvival(false);
                    }
                    HandlerSurvival handlerSoloSurvival = new HandlerSurvival(soloSurvivals , teams , server.getServerSetting().getSpeedPotionMultiplier(),
                            server.getServerSetting().getSpeedPotionPeriod(), server.getServerSetting().getInvisibilityPotionPeriod(),
                            server.getServerSetting().getHealthPotionHPPercent(), server.getServerSetting().getDamageBombBlockArea(),
                            server.getServerSetting().getDamageBombPercent(), server.getServerSetting().getSpeedBombBlockArea(),
                            server.getServerSetting().getSpeedBombMultiplier(), server.getServerSetting().getHammerStunPeriod(),
                            server.getServerSetting().getSwordBlockRange(), server.getServerSetting().getSwordPercentDamage(),
                            server.getServerSetting().getSwordPushBackBlock(), server.getServerSetting().getSwordCoolDown(), server.getServerSetting().getSurvivalDamageMultiplier() , server.getServerSetting().getSurvivalEquipmentMultiplier());
                    for (int i = 0; i < nearScore.size(); i++) {
                        ServerSideClient serverSideClient = nearScore.get(i);
                        serverSideClient.setHandlerSurvival(handlerSoloSurvival);
                        serverSideClient.getUser().setDiamond(serverSideClient.getUser().getDiamond() - 1);
                    }
                    SurvivalServer soloSurvivalServer = new SurvivalServer(port , handlerSoloSurvival );

                }
            }
        };
        timer.schedule(timerTask ,8000 );
    }
    public void startSoloSurvival(){
        Timer timer = new Timer();
        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                LinkedList<ServerSideClient> nearScore = client.getNearScoreClient(GameMode.SOLO_MARIO_SURVIVAL);
                if (nearScore.size() >= 2) {
                    int port = port();

                    LinkedList<PlayerSurvival> soloSurvivals = new LinkedList<>();
                    LinkedList<MarioTeam> teams = new LinkedList<>();
                    for (int i = 0; i < nearScore.size(); i++) {
                        ServerSideClient serverSideClient = nearScore.get(i);
                        PlayerSurvival soloSurvival;
                        int specialBag = serverSideClient.getUser().getChosenSpecialBag();
                        if (specialBag == 0) {
                            soloSurvival = new PlayerSurvival(serverSideClient.getUser().getUsername(), null , swordItem(serverSideClient));
                        } else {
                            soloSurvival = new PlayerSurvival(serverSideClient.getUser().getUsername(), serverSideClient.getUser().getSpecialBags()[specialBag - 1] , swordItem(serverSideClient));
                        }
                        LinkedList<PlayerSurvival> linkedList = new LinkedList<>();
                        linkedList.add(soloSurvival);
                        teams.add(new MarioTeam(linkedList));
                        soloSurvivals.add(soloSurvival);

                        serverSideClient.send(new Information<Item>(soloSurvival.getAllItems() , Information.InformationType.START_SOLO_MARIO_SURVIVAL , port));
                        serverSideClient.setRequestSoloMarioSurvival(false);
                    }
                    HandlerSurvival handlerSoloSurvival = new HandlerSurvival(soloSurvivals , teams , server.getServerSetting().getSpeedPotionMultiplier(),
                            server.getServerSetting().getSpeedPotionPeriod(), server.getServerSetting().getInvisibilityPotionPeriod(),
                            server.getServerSetting().getHealthPotionHPPercent(), server.getServerSetting().getDamageBombBlockArea(),
                            server.getServerSetting().getDamageBombPercent(), server.getServerSetting().getSpeedBombBlockArea(),
                            server.getServerSetting().getSpeedBombMultiplier(), server.getServerSetting().getHammerStunPeriod(),
                            server.getServerSetting().getSwordBlockRange(), server.getServerSetting().getSwordPercentDamage(),
                            server.getServerSetting().getSwordPushBackBlock(), server.getServerSetting().getSwordCoolDown(), server.getServerSetting().getSurvivalDamageMultiplier() , server.getServerSetting().getSurvivalEquipmentMultiplier());
                    for (int i = 0; i < nearScore.size(); i++) {
                        ServerSideClient serverSideClient = nearScore.get(i);
                        serverSideClient.setHandlerSurvival(handlerSoloSurvival);
                        serverSideClient.getUser().setDiamond(serverSideClient.getUser().getDiamond() - 1);
                    }
                    SurvivalServer soloSurvivalServer = new SurvivalServer(port , handlerSoloSurvival );

                }
            }
        };
        timer.schedule(timerTask ,8000 );
    }
    public void startMarathon(){
        Timer timer = new Timer();
        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                LinkedList<ServerSideClient> nearScore = client.getNearScoreClient(GameMode.MARIO_MARATHON);
                if (nearScore.size() >= 2) {
                    int port = port();

                    LinkedList<PlayerMarathon> playerMarathons = new LinkedList<>();
                    for (int i = 0; i < nearScore.size(); i++) {
                        ServerSideClient serverSideClient = nearScore.get(i);
                        PlayerMarathon playerMarathon;
                        int specialBag = serverSideClient.getUser().getChosenSpecialBag();
                        if (specialBag == 0) {
                            playerMarathon = new PlayerMarathon(serverSideClient.getUser().getUsername(), null , swordItem(serverSideClient));
                        } else {
                            playerMarathon = new PlayerMarathon(serverSideClient.getUser().getUsername(), serverSideClient.getUser().getSpecialBags()[specialBag - 1] , swordItem(serverSideClient));
                        }
                        playerMarathons.add(playerMarathon);

                        serverSideClient.send(new Information<Item>(playerMarathon.getAllItems() , Information.InformationType.START_MARATHON_MARIO , port));
                        serverSideClient.setRequestMarathonMario(false);
                    }
                    HandlerMarathon handlerMarathon = new HandlerMarathon(playerMarathons , server.getServerSetting().getSpeedPotionMultiplier(),
                            server.getServerSetting().getSpeedPotionPeriod(), server.getServerSetting().getInvisibilityPotionPeriod(),
                            server.getServerSetting().getHealthPotionHPPercent(), server.getServerSetting().getDamageBombBlockArea(),
                            server.getServerSetting().getDamageBombPercent(), server.getServerSetting().getSpeedBombBlockArea(),
                            server.getServerSetting().getSpeedBombMultiplier(), server.getServerSetting().getHammerStunPeriod(),
                            server.getServerSetting().getSwordBlockRange(), server.getServerSetting().getSwordPercentDamage(),
                            server.getServerSetting().getSwordPushBackBlock(), server.getServerSetting().getSwordCoolDown(),
                            server.getServerSetting().getMarathonMultiplierSpeed(), server.getServerSetting().getMarathonMultiplierSlowDown(),
                            server.getServerSetting().getMarathonPeriodSlowDown(), server.getServerSetting().getMarathonLifeTimeMultiplier(),
                            server.getServerSetting().getMarathonDistanceMultiplier(), server.getServerSetting().getMarathonMinLifeTime(),
                            server.getServerSetting().getMarathonMinDistance());
                    for (int i = 0; i < nearScore.size(); i++) {
                        ServerSideClient serverSideClient = nearScore.get(i);
                        serverSideClient.setHandlerMarathon(handlerMarathon);
                    }
                    MarathonServer udpServer = new MarathonServer(port , handlerMarathon);
                }
            }
        };
        timer.schedule(timerTask ,8000 );

    }
    public boolean swordItem(ServerSideClient serverSideClient){
        LinkedList<Item> items = serverSideClient.getUser().getItems();
        if (items.get(6).getNumberAvailable() > 0){
            return true;
        }
        return false;
    }
    public boolean available(int port){
        DatagramSocket datagramSocket = null;
        try {
            datagramSocket = new DatagramSocket(port);
            datagramSocket.setReuseAddress(true);
            return true;
        } catch (IOException e){
        } finally {
            if (datagramSocket != null){
                datagramSocket.close();
            }
        }
        return false;
    }
    public int port(){
        boolean correct = false;
        int port = 0;
        while (!correct){
            port = new Random().nextInt(9999);
            if (available(port)){
                correct = true;
            }
        }
        return port;
    }


}
