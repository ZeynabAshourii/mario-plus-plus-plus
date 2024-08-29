package org.example.client.view.main_panels.user_starter_panels;

import org.example.client.controller.Client;
import org.example.order.Notification;
import org.example.order.Order;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.LinkedList;

public class NotificationsPanel extends JPanel implements ActionListener {
    private Client client;
    private JButton back;
    private LinkedList<Notification> notifications = new LinkedList<>();
    public NotificationsPanel(Client client) {
        this.client = client;
        client.setNotificationsPanel(this);
        client.send(new Order(Order.OrderType.RESET_NOTIFICATION , client.getUsername()));

        this.setSize(1080, 771);
        this.setLayout(null);
        this.setFocusable(true);
        this.requestFocus();
        this.requestFocusInWindow();

        back = new JButton("Back");
        this.add(back);
        back.setBounds(470 , 660 , 140 , 60);
        back.setFocusable(false);
        back.addActionListener(this);

        mouseInput();
    }
    public void mouseInput(){
        this.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent mouseEvent) {
                int whichNotification = whichNotification(mouseEvent.getX() , mouseEvent.getY());
                if(mouseEvent.getButton() == MouseEvent.BUTTON1){
                    if(whichNotification < notifications.size()) {
                        Notification notification = notifications.get(whichNotification);
                        if (notification.getType().equals(Notification.NotificationType.NEW_MESSAGE)) {
                            client.getClientFrame().setContentPane(new PvPanel(client , notification.getTopic()));
                            client.send(new Order(Order.OrderType.PV_PANEL ,client.getUsername() , notification.getTopic()));
                        } else if (notification.getType().equals(Notification.NotificationType.FRIEND_REQUEST)) {

                        } else if (notification.getType().equals(Notification.NotificationType.GAME_REQUEST)) {

                        } else if (notification.getType().equals(Notification.NotificationType.EXPULSION)) {

                        }
                    }
                }
                if(mouseEvent.getButton() == MouseEvent.BUTTON3){
                    if(whichNotification < notifications.size()) {
                        client.send(notifications.get(whichNotification));
                        repaint();
                    }
                }
            }

            @Override
            public void mousePressed(MouseEvent mouseEvent) {

            }

            @Override
            public void mouseReleased(MouseEvent mouseEvent) {

            }

            @Override
            public void mouseEntered(MouseEvent mouseEvent) {

            }

            @Override
            public void mouseExited(MouseEvent mouseEvent) {

            }
        });
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        for(int i = 0; i < notifications.size(); i++){
            Notification notification = notifications.get(i);
            g.setColor(Color.PINK);
            g.fillRect(820 , 30 + i*85, 220 , 80);
            String displayText = "";
            if(notification.getText().length() <= 40){
                displayText = notification.getText();
            }else {
                displayText = notification.getText().substring(0 , 40);
            }
            g.setColor(Color.LIGHT_GRAY);
            g.fillRect(15 , 30 + i*85, 800 , 80);
            g.setColor(Color.BLACK);
            g.drawString(notification.getTopic() , 830 , 60 + i * 80);
            g.drawString(displayText , 30 , 60 + i * 80);
        }
    }
    public int whichNotification(int x , int y){
        if(x >= 15 && x <= 1040){
            return (y-30)/85;
        }
        return -1;
    }


    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        if(actionEvent.getSource().equals(back)){
            client.getClientFrame().setContentPane(new UserStarterPanel(client));
        }
    }

    public LinkedList<Notification> getNotifications() {
        return notifications;
    }

    public void setNotifications(LinkedList<Notification> notifications) {
        this.notifications = notifications;
    }
}
