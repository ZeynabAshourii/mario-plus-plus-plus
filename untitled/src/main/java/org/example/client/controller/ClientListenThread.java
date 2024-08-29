package org.example.client.controller;

import org.example.client.view.main_panels.online_game.MarathonMarioPanel;
import org.example.client.view.main_panels.online_game.OnlineGamePanel;
import org.example.client.view.main_panels.online_game.MarioSurvivalPanel;
import org.example.client.view.main_panels.online_game.TeamGamePanel;
import org.example.order.Message;
import org.example.order.Notification;
import org.example.client.view.main_panels.user_starter_panels.UserStarterPanel;
import org.example.local_data.ChatPV;
import org.example.order.Information;
import org.example.order.Order;
import org.example.server.model.Item;
import org.example.server.model.SpecialBag;

import javax.swing.*;
import java.io.IOException;
import java.util.LinkedList;
import java.util.Timer;
import java.util.TimerTask;

public class ClientListenThread extends Thread{
    private Client client;
    public ClientListenThread(Client client){
        this.client = client;
    }
    @Override
    public void run() {
        while (true) {
            try {
                Object object = client.getsInput().readObject();
                if (object instanceof Information<?>) {
                    receiveInformation((Information) object);
                } else if (object instanceof Order) {
                    receiveOrder((Order) object);
                } else if (object instanceof SpecialBag) {
                     SpecialBag specialBag = (SpecialBag) object;
                     if(client.getEditBagPanel() != null){
                         client.getEditBagPanel().setSpecialBag(specialBag);
                         client.getEditBagPanel().repaint();
                     }
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        }
    }
    public void handelNotification(LinkedList<Notification> notifications){
        if(client.getNotificationsPanel() != null){
            client.getNotificationsPanel().setNotifications(notifications);
            client.getNotificationsPanel().repaint();
        }
    }
    public void handelMessage(LinkedList<Message> messages){
        if(client.getPvPanel() != null) {
            client.getPvPanel().setMessages(messages);
            client.getPvPanel().repaint();
        }
    }
    public void handelChat(LinkedList<ChatPV> chatPVs){
        if(client.getChatRoomPanel() != null) {
            client.getChatRoomPanel().setChatPVs(chatPVs);
            client.getChatRoomPanel().repaint();
        }
    }
    public void handelItems(LinkedList<Item> items){
        if(client.getStorePanel() != null){
            client.getStorePanel().setItems(items);
            client.getStorePanel().repaint();
        }
        if(client.getEditBagPanel() != null){
            client.getEditBagPanel().setItems(items);
            client.getEditBagPanel().repaint();
        }
    }
    public void handelSpecialBag(Information information){
        SpecialBag[] specialBags = (SpecialBag[]) information.getArray();
        if(client.getOnlineGamePanel() != null){
            client.getOnlineGamePanel().setSpecialBags(specialBags);
            client.getOnlineGamePanel().setLevel(information.getLevel());
            client.getOnlineGamePanel().repaint();
        }
    }
    public void startSurvival(Information information){
        if (client.getWaitingPanel() != null){
            client.getWaitingPanel().setStartGame(true);
            client.getWaitingPanel().repaint();
            java.util.Timer timer = new Timer();
            TimerTask timerTask = new TimerTask() {
                @Override
                public void run() {
                    SurvivalClient soloSurvivalClient = new SurvivalClient(client.getUsername() , information.getPort());
                    MarioSurvivalPanel soloMarioSurvivalPanel = new MarioSurvivalPanel(soloSurvivalClient);
                    soloMarioSurvivalPanel.setItems(information.getLinkedList());
                    soloSurvivalClient.setGamePanel(soloMarioSurvivalPanel);
                    client.getClientFrame().setContentPane(soloMarioSurvivalPanel);
                }
            };
            timer.schedule(timerTask , 2000);
        }
    }
    public void startMarathon(Information information){
        if (client.getWaitingPanel() != null){
            client.getWaitingPanel().setStartGame(true);
            client.getWaitingPanel().repaint();
            java.util.Timer timer = new Timer();
            TimerTask timerTask = new TimerTask() {
                @Override
                public void run() {
                    MarathonClient udpClient = new MarathonClient(client.getUsername() , information.getPort());
                    MarathonMarioPanel marathonMarioPanel = new MarathonMarioPanel(udpClient);
                    marathonMarioPanel.setItems(information.getLinkedList());
                    udpClient.setGamePanel(marathonMarioPanel);
                    client.getClientFrame().setContentPane(marathonMarioPanel);
                }
            };
            timer.schedule(timerTask , 2000);
        }
    }
    public void startTeamGame(Information information){
        if (client.getWaitingPanel() != null){
            client.getWaitingPanel().setStartGame(true);
            client.getWaitingPanel().repaint();
            java.util.Timer timer = new Timer();
            TimerTask timerTask = new TimerTask() {
                @Override
                public void run() {
                    TeamGameClient teamGameClient = new TeamGameClient(client , information.getPort());
                    TeamGamePanel teamGamePanel = new TeamGamePanel(teamGameClient);
                    teamGamePanel.setItems(information.getLinkedList());
                    teamGameClient.setGamePanel(teamGamePanel);
                    client.getClientFrame().setContentPane(teamGamePanel);
                }
            };
            timer.schedule(timerTask , 2000);
        }
    }
    public void receiveInformation(Information information){
        if(information.getInformationType().equals(Information.InformationType.NOTIFICATION)){
            handelNotification(information.getLinkedList());
        } else if (information.getInformationType().equals(Information.InformationType.MESSAGE)) {
            handelMessage(information.getLinkedList());
        } else if (information.getInformationType().equals(Information.InformationType.CHAT_PV)) {
            handelChat(information.getLinkedList());
        } else if (information.getInformationType().equals(Information.InformationType.ITEMS)) {
            handelItems(information.getLinkedList());
        } else if (information.getInformationType().equals(Information.InformationType.SPECIAL_BAGS)) {
            handelSpecialBag(information);
        } else if (information.getInformationType().equals(Information.InformationType.START_SOLO_MARIO_SURVIVAL)) {
            startSurvival(information);
        } else if (information.getInformationType().equals(Information.InformationType.START_MARATHON_MARIO)) {
            startMarathon(information);
        } else if (information.getInformationType().equals(Information.InformationType.START_TEAM_GAME)) {
            startTeamGame(information);
        }
    }


    public void receiveOrder(Order order){
        if(order.getOrderType().equals(Order.OrderType.NEW_USER)){
            handelUser(order);
        } else if (order.getOrderType().equals(Order.OrderType.EXCHANGE)) {
            handelExchange(order);
        } else if (order.getOrderType().equals(Order.OrderType.INSUFFICIENT_INVENTORY)) {
            JOptionPane.showMessageDialog(client.getClientFrame(), "Insufficient inventory , choose another one");
        } else if (order.getOrderType().equals(Order.OrderType.END_MARATHON_MARIO)) {
            endMarathon(order);
        } else if (order.getOrderType().equals(Order.OrderType.END_SOLO_MARIO_SURVIVAL)) {
            endSurvival(order);
        }
    }
    public void handelUser(Order order){
        if(order.getCoin() == -1){
            JOptionPane.showMessageDialog(client.getClientFrame(), "the username is duplicated , choose another username");
        } else if (order.getCoin() == -2) {
            JOptionPane.showMessageDialog(client.getClientFrame(), "username or password is wrong");
        }else {
            client.setCoin(order.getCoin());
            client.setDiamond(order.getDiamond());
            client.setUsername(order.getUsername());
            client.getClientFrame().setContentPane(new UserStarterPanel(client));
        }
    }
    public void handelExchange(Order order){
        if(order.getCoin() >= 0){
            client.setCoin(order.getCoin());
            client.setDiamond(order.getDiamond());
            client.getStorePanel().repaint();
        }else if (order.getCoin() == -2) {
            JOptionPane.showMessageDialog(client.getClientFrame(), "You don't have enough coin , use diamond or exchange the coins");
        } else if (order.getCoin() == -3) {
            JOptionPane.showMessageDialog(client.getClientFrame(), "You don't have enough diamond");
        } else if (order.getCoin() == -4) {
            JOptionPane.showMessageDialog(client.getClientFrame(), "You can only buy this item with diamonds");
        } else if (order.getCoin() == -5) {
            JOptionPane.showMessageDialog(client.getClientFrame(), "You can only buy this item with coins");
        } else if (order.getCoin() == -6) {
            JOptionPane.showMessageDialog(client.getClientFrame(), "Your level is low");
        } else if (order.getCoin() == -7) {
            JOptionPane.showMessageDialog(client.getClientFrame(), "Server number limit");
        } else if (order.getCoin() == -8) {
            JOptionPane.showMessageDialog(client.getClientFrame(), "item number limit");
        }
    }
    public void endMarathon(Order order){
        Timer timer = new Timer();
        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                client.getClientFrame().setContentPane(new OnlineGamePanel(client));
            }
        };
        timer.schedule(timerTask , 2000);
    }
    public void endSurvival(Order order){
        Timer timer = new Timer();
        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                client.getClientFrame().setContentPane(new OnlineGamePanel(client));
            }
        };
        timer.schedule(timerTask , 2000);
    }
}
