package org.example.client.view.main_panels.user_starter_panels;

import org.example.client.controller.Client;
import org.example.order.Message;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.LinkedList;

public class PvPanel extends JPanel implements ActionListener {
    private Client client;
    private String otherSideUsername;
    private JButton send;
    private JButton back;
    private TextField textField;
    private LinkedList<Message> messages = new LinkedList<>();
    public PvPanel(Client client , String otherSideUsername){

        this.client = client;
        this.otherSideUsername = otherSideUsername;
        client.setPvPanel(this);

        this.setSize(1080, 771);
        this.setLayout(null);
        this.setFocusable(true);
        this.requestFocus();
        this.requestFocusInWindow();

        textField = new TextField("Message");
        this.add(textField);
        textField.setBounds(130 , 640 ,910 , 60);

        send = new JButton("Send");
        this.add(send);
        send.setBounds(25 , 640 , 100 , 60);
        send.setFocusable(false);
        send.addActionListener(this);

        back = new JButton("Back");
        this.add(back);
        back.setBounds(920 , 20 , 100 , 60);
        back.setFocusable(false);
        back.addActionListener(this);

    }
    @Override
    public void paint(Graphics g) {
        super.paint(g);
        for (int i = 0; i < messages.size(); i++){
            Message message = messages.get(i);
            if(message.getSenderUsername().equals(otherSideUsername)){
                g.setColor(Color.CYAN);
                g.fillRect(25 ,75*i + 80 ,60 , 60);
                g.setColor(Color.LIGHT_GRAY);
                g.fillRect(90 , 75*i + 80 , 800 , 60);
                g.setColor(Color.BLACK);
                g.drawString(message.getSenderUsername(), 30 , 75*i + 95);
                g.drawString(message.getText() , 95 , 75*i + 95);
            }else {
                g.setColor(Color.PINK);
                g.fillRect(950 ,75*i + 80 ,60 , 60);
                g.setColor(Color.LIGHT_GRAY);
                g.fillRect(145 , 75*i + 80 , 800 , 60);
                g.setColor(Color.BLACK);
                g.drawString(message.getSenderUsername() , 955 , 75*i + 95);
                g.drawString(message.getText() , 150 , 75*i + 95);
            }
        }
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        if (actionEvent.getSource().equals(back)){
            client.getClientFrame().setContentPane(new ChatRoomPanel(client));
        } else if (actionEvent.getSource().equals(send)) {
            if(client.isOnline()) {
                Message message = new Message(client.getUsername(), otherSideUsername, textField.getText());
                client.send(message);
                repaint();
            }else {
                JOptionPane.showMessageDialog(this, "you are offline");
            }
        }
    }

    public LinkedList<Message> getMessages() {
        return messages;
    }

    public void setMessages(LinkedList<Message> messages) {
        this.messages = messages;
    }
}
