package org.example.client.view.main_panels.user_starter_panels;

import org.example.client.controller.Client;
import org.example.local_data.ManageUser;
import org.example.offline_game.view.OfflineGame;
import org.example.resources.Resources;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
public class ContinuationPreviousGamePanel extends JPanel implements ActionListener {
    private JButton game1;
    private JButton game2;
    private JButton game3;
    private JButton back;
    public Client client;
    public ContinuationPreviousGamePanel(Client client){
        this.client = client;

        this.setSize(1080, 771);
        this.setLayout(null);
        this.setFocusable(true);
        this.requestFocus();
        this.requestFocusInWindow();

        game1 = new JButton("OfflineGame 1");
        this.add(game1);
        game1.setBounds(450,100,160, 80);
        game1.setFocusable(false);
        game1.addActionListener(this);
        game2 = new JButton("OfflineGame 2");
        this.add(game2);
        game2.setBounds(450 , 240 , 160 , 80);
        game2.setFocusable(false);
        game2.addActionListener(this);
        game3 = new JButton("OfflineGame 3");
        this.add(game3);
        game3.setBounds(450 , 380 , 160 , 80);
        game3.setFocusable(false);
        game3.addActionListener(this);
        back = new JButton("Back");
        this.add(back);
        back.setBounds(450 , 550 , 160 , 80);
        back.setFocusable(false);
        back.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource().equals(game1)){
            if(ManageUser.getInstance().getGame(client.getUsername() , 0) == null){
                JOptionPane.showMessageDialog(this, "this game isn't exist");
            }
            else {
                OfflineGame game = ManageUser.getInstance().getGame(client.getUsername() , 0);
                game.setClient(client);
                startGame(game);
            }
        }
        else if(e.getSource().equals(game2)){
            if(ManageUser.getInstance().getGame(client.getUsername() , 1) == null){
                JOptionPane.showMessageDialog(this, "this game isn't exist");
            }else{
                OfflineGame game = ManageUser.getInstance().getGame(client.getUsername() , 1);
                game.setClient(client);
                startGame(game);
            }
        }
        else if(e.getSource().equals(game3)){
            if(ManageUser.getInstance().getGame(client.getUsername() , 2) == null){
                JOptionPane.showMessageDialog(this, "this game isn't exist");
            }else{
                OfflineGame game = ManageUser.getInstance().getGame(client.getUsername() , 2);
                game.setClient(client);
                startGame(game);
            }
        } else if (e.getSource().equals(back)) {
            client.getClientFrame().setContentPane(new UserStarterPanel(client));
        }
    }
    public void startGame(OfflineGame game){
        game.setRunning(false);
        game.setPaused(false);
        game.setContinuation(true);
        game.setSigmaTime(game.getElapsedTime());
        game.setStartTime(System.nanoTime()/1000000000L);
        game.start();
        client.getClientFrame().setContentPane(game);
        game.setFocusable(true);
        game.requestFocus();
        game.requestFocusInWindow();
        if(game.isBossFight()){
            Resources.getBossFight().play();
        }
        else {
            Resources.getThemeSong().play();
        }
    }
}
