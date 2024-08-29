package org.example.client.view.main_panels.online_game;

import org.example.client.controller.Client;
import org.example.client.view.main_panels.user_starter_panels.UserStarterPanel;
import org.example.order.Order;
import org.example.server.model.Item;
import org.example.server.model.SpecialBag;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class OnlineGamePanel extends JPanel implements ActionListener {
    private Client client;
    private SpecialBag[] specialBags = new SpecialBag[3];
    private JButton startNewGame;
    private JButton createPrivateRoom;
    private JButton enterPrivateRoom;
    private JButton back;
    private JButton chooseFirstBag;
    private JButton chooseSecondBag;
    private JButton chooseThirdBag;
    private JButton editFistBag;
    private JButton editSecondBag;
    private JButton editThirdBag;
    private int level;
    private int chosenSpecialBag = 0;

    public OnlineGamePanel(Client client) {
        this.client = client;
        client.setOnlineGamePanel(this);
        client.send(new Order(Order.OrderType.OPEN_ONLINE_GAME_PANEL, client.getUsername()));

        this.setSize(1080, 771);
        this.setLayout(null);
        this.setFocusable(true);
        this.requestFocus();
        this.requestFocusInWindow();

        chooseFirstBag = new JButton("choose");
        this.add(chooseFirstBag);
        chooseFirstBag.setBounds(170 , 240 , 80 , 40);
        chooseFirstBag.setFocusable(false);
        chooseFirstBag.addActionListener(this);

        editFistBag = new JButton("edit");
        this.add(editFistBag);
        editFistBag.setBounds(270 , 240 , 80 , 40);
        editFistBag.setFocusable(false);
        editFistBag.addActionListener(this);

        chooseSecondBag = new JButton("choose");
        this.add(chooseSecondBag);
        chooseSecondBag.setBounds(450 , 240 , 80 , 40);
        chooseSecondBag.setFocusable(false);
        chooseSecondBag.addActionListener(this);

        editSecondBag = new JButton("edit");
        this.add(editSecondBag);
        editSecondBag.setBounds(550 , 240 , 80 , 40);
        editSecondBag.setFocusable(false);
        editSecondBag.addActionListener(this);

        chooseThirdBag = new JButton("choose");
        this.add(chooseThirdBag);
        chooseThirdBag.setBounds(730 , 240 , 80 , 40);
        chooseThirdBag.setFocusable(false);
        chooseThirdBag.addActionListener(this);

        editThirdBag = new JButton("edit");
        this.add(editThirdBag);
        editThirdBag.setBounds(830 , 240 , 80 , 40);
        editThirdBag.setFocusable(false);
        editThirdBag.addActionListener(this);


        startNewGame = new JButton("Start new game");
        this.add(startNewGame);
        startNewGame.setBounds(440 , 370 , 200 , 80);
        startNewGame.setFocusable(false);
        startNewGame.addActionListener(this);

        createPrivateRoom = new JButton("Create private room");
        this.add(createPrivateRoom);
        createPrivateRoom.setBounds(440 , 470 , 200 , 80);
        createPrivateRoom.setFocusable(false);
        createPrivateRoom.addActionListener(this);

        enterPrivateRoom = new JButton("Enter private room");
        this.add(enterPrivateRoom);
        enterPrivateRoom.setBounds(440 , 570 , 200 , 80);
        enterPrivateRoom.setFocusable(false);
        enterPrivateRoom.addActionListener(this);

        back = new JButton("Back");
        this.add(back);
        back.setBounds(440 , 670 , 200 , 60);
        back.setFocusable(false);
        back.addActionListener(this);

    }
    @Override
    public void paint(Graphics g){
        super.paint(g);

        for(int i = 0; i < specialBags.length; i++){
            g.setColor(Color.BLUE);
            g.drawRect(160 + i*280 , 20 , 200 , 200);
            SpecialBag specialBag = specialBags[i];
            if(specialBag != null) {
                for (int j = 0; j < specialBag.getItems().length; j++) {
                    Item item = specialBag.getItems()[j];
                    if (item != null) {
                        g.setColor(Color.PINK);
                        g.drawString(String.valueOf(item.getItemType()), 170 + i * 280, 40 + 40 * j);
                    }
                }
            }
        }
        g.drawString("Chosen bag : " + chosenSpecialBag , 500 , 300 );

    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        if (actionEvent.getSource().equals(startNewGame)){
            startGameButton();
        } else if (actionEvent.getSource().equals(createPrivateRoom)) {

        } else if (actionEvent.getSource().equals(enterPrivateRoom)) {

        } else if (actionEvent.getSource().equals(chooseFirstBag)) {
            chosenSpecialBag = 1;
            repaint();
        } else if (actionEvent.getSource().equals(editFistBag)) {
            editFirstBagButton();
        } else if (actionEvent.getSource().equals(chooseSecondBag)) {
            chosenSpecialBag = 2;
            repaint();
        } else if (actionEvent.getSource().equals(editSecondBag)) {
            editSecondBagButton();
        } else if (actionEvent.getSource().equals(chooseThirdBag)) {
            chosenSpecialBag = 3;
            repaint();
        } else if (actionEvent.getSource().equals(editThirdBag)) {
            editThirdBagButton();
        } else if (actionEvent.getSource().equals(back)) {
            client.getClientFrame().setContentPane(new UserStarterPanel(client));
        }
    }
    public void editThirdBagButton(){
        if (level < 2){
            JOptionPane.showMessageDialog(this, "Your level is low");
        }else {
            if(isEmptyBag(specialBags[2])){
                Item[] items = new Item[5];
                specialBags[2] = new SpecialBag(items);
            }
            client.getClientFrame().setContentPane(new EditBagPanel(client, specialBags[2] , 2));

        }
    }
    public void editSecondBagButton(){
        if(level < 2){
            JOptionPane.showMessageDialog(this, "Your level is low");
        }else {
            if(isEmptyBag(specialBags[1])){
                Item[] items = new Item[5];
                specialBags[1] = new SpecialBag(items);
            }
            client.getClientFrame().setContentPane(new EditBagPanel(client, specialBags[1] , 1));
        }
    }
    public void editFirstBagButton(){
        if(isEmptyBag(specialBags[0])){
            Item[] items = new Item[5];
            specialBags[0] = new SpecialBag(items);
        }
        client.getClientFrame().setContentPane(new EditBagPanel(client, specialBags[0] , 0));
    }
    public void startGameButton(){
        if(chosenSpecialBag == 0){
            startGame();
        } else {
            if (rightBag(specialBags[chosenSpecialBag-1])){
                startGame();
            }else {
                JOptionPane.showMessageDialog(this, "Edit your chosen bag");
            }
        }
    }
    public boolean isEmptyBag(SpecialBag specialBag){
        if (specialBag == null){
            return true;
        } else {
            for (int j = 0; j < specialBag.getItems().length; j++) {
                Item item = specialBag.getItems()[j];
                if (!isEmptyItem(item)) {
                    return false;
                }
            }
        }
        return true;
    }
    public boolean isEmptyItem(Item item){
        if(item == null){
            return true;
        }else if (item.getItemType() == null){
            return true;
        }
        return false;
    }
    public boolean rightBag(SpecialBag specialBag){
        Item[] items = specialBag.getItems();
        if(items == null){
            return true;
        }
        if(emptyItems(items)){
            return true;
        }
        for(int i = 0; i < items.length ; i++){
            Item item = items[i];
            if(isEmptyItem(item)){
                return false;
            }
        }
        return true;
    }
    public boolean emptyItems(Item[] items){
        for(int i = 0; i < items.length; i++){
            Item item = items[i];
            if(!isEmptyItem(item)){
                return false;
            }
        }
        return true;
    }
    public void startGame(){
        Object[] options = {"Team game" , "Mario marathon" , "Team mario survival" , "Solo mario survival"};
        int option = JOptionPane.showOptionDialog(null,
                "Choose type of game",
                "Game mode",
                JOptionPane.YES_NO_CANCEL_OPTION,
                JOptionPane.DEFAULT_OPTION,
                null,
                options,
                options[1]);
        if (option == 0){
            client.getClientFrame().setContentPane(new WaitingPanel(client , GameMode.TEAM_GAME , chosenSpecialBag));
        } else if (option == 1) {
            client.getClientFrame().setContentPane(new WaitingPanel(client , GameMode.MARIO_MARATHON , chosenSpecialBag));
        } else if (option == 2) {
            client.getClientFrame().setContentPane(new WaitingPanel(client , GameMode.TEAM_MARIO_SURVIVAL , chosenSpecialBag));
        } else if (option == 3) {
            client.getClientFrame().setContentPane(new WaitingPanel(client , GameMode.SOLO_MARIO_SURVIVAL , chosenSpecialBag));
        }
    }
    public void setSpecialBags(SpecialBag[] specialBags) {
        this.specialBags = specialBags;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }
}
