package org.example.client.view.main_panels.user_starter_panels;

import org.example.client.controller.Client;
import org.example.local_data.ChatPV;
import org.example.order.Order;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.LinkedList;

public class ChatRoomPanel extends JPanel implements ActionListener {
    private Client client;
    private JButton back;
    private TextField usernameSearch;
    private JButton search;
    private JButton cancel;
    private LinkedList<ChatPV> chatPVs = new LinkedList<>();
    public ChatRoomPanel(Client client){
        this.client = client;
        client.setChatRoomPanel(this);
        Order order = new Order(Order.OrderType.RESET_PV);
        client.send(order);

        this.setSize(1080, 771);
        this.setLayout(null);
        this.setFocusable(true);
        this.requestFocus();
        this.requestFocusInWindow();

        usernameSearch = new TextField("username");
        this.add(usernameSearch);
        usernameSearch.setBounds(95,20,800,40);

        search = new JButton("search");
        this.add(search);
        search.setBounds(10,20,80,40);
        search.addActionListener(this);

        cancel = new JButton("cancel");
        this.add(cancel);
        cancel.setBounds(900 , 20 , 80 , 40);
        cancel.addActionListener(this);

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
                int whichPV = whichPV(mouseEvent.getX() , mouseEvent.getY());
                if(whichPV < chatPVs.size()){
                    if (chatPVs.get(whichPV).getUsername1().equals(client.getUsername())) {
                        client.getClientFrame().setContentPane(new PvPanel(client ,chatPVs.get(whichPV).getUsername2()));
                    }else {
                        client.getClientFrame().setContentPane(new PvPanel(client ,chatPVs.get(whichPV).getUsername1()));
                    }
                    client.send(new Order(Order.OrderType.PV_PANEL , chatPVs.get(whichPV).getUsername1() , chatPVs.get(whichPV).getUsername2()));

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
    public void actionPerformed(ActionEvent actionEvent) {
        if(actionEvent.getSource().equals(back)){
            client.getClientFrame().setContentPane(new UserStarterPanel(client));
        } else if (actionEvent.getSource().equals(search)) {
            if(client.isOnline()) {
                chatPVs.clear();
                client.send(new Order(Order.OrderType.SEARCH_PV , usernameSearch.getText()));
                repaint();
            }else {
                JOptionPane.showMessageDialog(this, "you are offline");
            }
        } else if (actionEvent.getSource().equals(cancel)) {
            chatPVs.clear();
            client.send(new Order(Order.OrderType.RESET_PV));
            repaint();
        }
    }
    @Override
    public void paint(Graphics g) {
        super.paint(g);
        chatPVs = sortChatPV();
        for(int i = 0; i < chatPVs.size(); i++){
            g.setColor(Color.CYAN);
            g.fillRect(820 , 70 + i*75, 220 , 70);
            g.setColor(Color.LIGHT_GRAY);
            g.fillRect(15 , 70 + i*75, 800 , 70);
            g.setColor(Color.BLACK);
            if (chatPVs.get(i).getUsername1().equals(client.getUsername())) {
                g.drawString(chatPVs.get(i).getUsername2(), 830, 90 + i * 75);
            }else {
                g.drawString(chatPVs.get(i).getUsername1(), 830, 90 + i * 75);
            }
            g.drawString(String.valueOf(chatPVs.get(i).getLastTimeMessage()), 30, 90 + i * 75);
        }
    }
    public int whichPV(int x , int y){
        if(x >= 15 && x <= 1040){
            return (y-70)/75;
        }
        return -1;
    }

    public LinkedList<ChatPV> sortChatPV(){
        LinkedList<ChatPV> sortChatPV = new LinkedList<>();
        for(int i = 0; i < chatPVs.size(); i++){
            sortChatPV.add(chatPVs.get(i));
        }
        for(int i = 0; i < chatPVs.size(); i++){
            for (int j = i+1 ; j < chatPVs.size(); j++) {
                if (sortChatPV.get(i).getLastTimeMessage() < sortChatPV.get(j).getLastTimeMessage()) {
                    ChatPV chatPV = sortChatPV.get(i);
                    sortChatPV.set(i , sortChatPV.get(j));
                    sortChatPV.set(j , chatPV);
                }
            }
        }
        return sortChatPV;
    }
    public void setChatPVs(LinkedList<ChatPV> chatPVs) {
        this.chatPVs = chatPVs;
    }

}
