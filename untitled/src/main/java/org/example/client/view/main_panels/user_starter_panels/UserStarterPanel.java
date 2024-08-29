package org.example.client.view.main_panels.user_starter_panels;

import org.example.client.controller.Client;
import org.example.client.view.main_panels.StartNewGamePanel;
import org.example.client.view.main_panels.StartPanel;
import org.example.client.view.main_panels.online_game.OnlineGamePanel;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class UserStarterPanel extends JPanel implements ActionListener {
    private JButton startNewGame;
    private JButton continuationPreviousGame;
    private JButton notifications;
    private JButton chatRoom;
    private JButton highestResults;
    private JButton profile;
    private JButton store;
    private JButton back;
    private Client client;
    public UserStarterPanel(Client client){
        this.client = client;

        this.setSize(1080, 771);
        this.setLayout(null);
        this.setFocusable(true);
        this.requestFocus();
        this.requestFocusInWindow();

        startNewGame = new JButton("Start New game");
        this.add(startNewGame);
        startNewGame.setBounds(400,60,240, 60);
        startNewGame.setFocusable(false);
        startNewGame.addActionListener(this);

        continuationPreviousGame = new JButton("Continuation of the Previous OfflineGame");
        this.add(continuationPreviousGame);
        continuationPreviousGame.setBounds(400,130,240, 60);
        continuationPreviousGame.setFocusable(false);
        continuationPreviousGame.addActionListener(this);

        notifications = new JButton("Notifications");
        this.add(notifications);
        notifications.setBounds(400,200,240, 60);
        notifications.setFocusable(false);
        notifications.addActionListener(this);

        chatRoom = new JButton("Chat room");
        this.add(chatRoom);
        chatRoom.setBounds(400 , 270 , 240 , 60);
        chatRoom.setFocusable(false);
        chatRoom.addActionListener(this);

        highestResults = new JButton("Highest Results");
        this.add(highestResults);
        highestResults.setBounds(400 , 340 , 240 , 60);
        highestResults.setFocusable(false);
        highestResults.addActionListener(this);

        profile = new JButton("Profile");
        this.add(profile);
        profile.setBounds(400 , 410 , 240 , 60);
        profile.setFocusable(false);
        profile.addActionListener(this);

        store = new JButton("Store");
        this.add(store);
        store.setBounds(400 , 480 , 240 , 60);
        store.setFocusable(false);
        store.addActionListener(this);

        back = new JButton("Back");
        this.add(back);
        back.setBounds(400 , 550 , 240 , 60);
        back.setFocusable(false);
        back.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource().equals(startNewGame)){
            startGame();
        }
        else if(e.getSource().equals(continuationPreviousGame)){
            client.getClientFrame().setContentPane(new ContinuationPreviousGamePanel(client));
        }
//        else if(e.getSource().equals(highestResults)){
//            MainFrame.getInstance().setContentPane(new HighestResultsPanel(user));
//        }
        else if (e.getSource().equals(profile)) {
            client.getClientFrame().setContentPane(new ProfilePanel(client));
        }
        else if (e.getSource().equals(back)) {
            client.getClientFrame().setContentPane(new StartPanel(client));
        } else if (e.getSource().equals(notifications)){
            client.getClientFrame().setContentPane(new NotificationsPanel(client));
        } else if (e.getSource().equals(chatRoom)) {
            client.getClientFrame().setContentPane(new ChatRoomPanel(client));
        } else if (e.getSource().equals(store)) {
            client.getClientFrame().setContentPane(new StorePanel(client));
        }
    }
    public void startGame(){
        Object[] options = {"OFFLINE", "ONLINE"};
        int option = JOptionPane.showOptionDialog(null,
                "Choose type of game",
                "Game mode",
                JOptionPane.YES_NO_CANCEL_OPTION,
                JOptionPane.DEFAULT_OPTION,
                null,
                options,
                options[1]);
        if( option == 0 ) {
            client.getClientFrame().setContentPane(new StartNewGamePanel(client));
        }else if ( option == 1){
            if(client.isOnline()){
                client.getClientFrame().setContentPane(new OnlineGamePanel(client));
            }else {
                JOptionPane.showMessageDialog(this, "you are offline , you can't play online");
            }
        }
    }


}

