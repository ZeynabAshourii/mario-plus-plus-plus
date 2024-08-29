package org.example.client.view.main_panels.online_game;

import org.example.client.controller.Client;
import org.example.order.Order;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Timer;
import java.util.TimerTask;

public class WaitingPanel extends JPanel implements ActionListener {
    private Client client;
    private JButton back;
    private int whichBag;
    private boolean startGame = false;
    private String status = "Connecting ...";
    private GameMode gameMode;
    public WaitingPanel(Client client , GameMode gameMode , int whichBag) {
        this.client = client;
        this.gameMode = gameMode;
        this.whichBag = whichBag;
        client.setWaitingPanel(this);
        client.send(new Order(Order.OrderType.REQUEST_GAME_MARIO, gameMode ,whichBag));

        this.setSize(1080, 771);
        this.setLayout(null);
        this.setFocusable(true);
        this.requestFocus();
        this.requestFocusInWindow();

        back = new JButton("Back");
        this.add(back);
        back.setBounds( 420 , 570 , 200 , 100);
        back.setFocusable(false);
        back.addActionListener(this);

        sendOrder();
    }
    public void sendOrder(){
        Timer timer = new Timer();
        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                if(!startGame){
                    status = "failed";
                    repaint();
                    client.send(new Order(Order.OrderType.END_WAITING_PANEL));
                }
            }
        };
        timer.schedule(timerTask , 10000);
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        if (actionEvent.getSource().equals(back)){
            client.send(new Order(Order.OrderType.END_WAITING_PANEL));
            client.getClientFrame().setContentPane(new OnlineGamePanel(client));
        }
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        if (startGame){
            status = "you connected !";
        }
        g.setFont(new Font("Courier", Font.BOLD, 30));
        g.drawString(status , 100 , 100);
    }
    public void setStartGame(boolean startGame) {
        this.startGame = startGame;
    }
}
