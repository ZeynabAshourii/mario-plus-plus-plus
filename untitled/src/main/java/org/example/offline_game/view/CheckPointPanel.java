package org.example.offline_game.view;


import org.example.offline_game.controller.CheckPoint;
import org.example.offline_game.controller.HandlerOfflineGame;
import org.example.offline_game.model.enums.Id;
import org.example.offline_game.model.tile.Gate;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
public class CheckPointPanel extends JPanel implements ActionListener {
    public HandlerOfflineGame handler;
    public Gate gate;
    private JButton yes;
    private JButton no;
    public CheckPointPanel(HandlerOfflineGame handler, Gate gate){
        this.handler = handler;
        this.gate = gate;

        this.setSize(1080, 771);
        this.setLayout(null);
        this.setFocusable(true);
        this.requestFocus();
        this.requestFocusInWindow();

        yes = new JButton("yes");
        this.add(yes);
        yes.setBounds(460 , 320 , 160 , 80);
        yes.setFocusable(false);
        yes.addActionListener(this);
        no = new JButton("no");
        this.add(no);
        no.setBounds(460 , 440 , 160 , 80);
        no.setFocusable(false);
        no.addActionListener(this);
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource().equals(yes)){
            new CheckPoint(handler);
            handler.getGame().setCoin(handler.getGame().getCoin() - handler.getCheckPoint().getPR());
            resumeGame(handler.getGame());
        }
        else if(e.getSource().equals(no)){
            gate.die();
            handler.getGame().setCoin(handler.getGame().getCoin() + PR()/4);
            resumeGame(handler.getGame());
        }
    }
    public int dx(){
        int dx = 0;
        for(int i = 0; i < handler.getGame().getSection(); i++){
            dx += handler.getCurrentLevel().sections.get(i).length;
        }
        for (int i = 0; i < handler.getEntities().size(); i++){
            if(handler.getEntities().get(i).getId() == Id.player){
                dx += (handler.getEntities().get(i).getX()-64);
            }
        }
        return dx;
    }
    public int PR(){
         return handler.getGame().getCoin()*dx()/handler.getCurrentLevel().length();
    }
    public void resumeGame(OfflineGame game){
        game.setPaused(false);
        handler.getGame().getClient().getClientFrame().setContentPane(game);
        game.setFocusable(true);
        game.requestFocus();
        game.requestFocusInWindow();
    }
}

