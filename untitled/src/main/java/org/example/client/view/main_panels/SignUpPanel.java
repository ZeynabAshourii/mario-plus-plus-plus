package org.example.client.view.main_panels;

import org.example.client.controller.Client;
import org.example.order.Order;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SignUpPanel extends JPanel implements ActionListener , Runnable {
    private Client client;
    private TextField textFieldUsername;
    private JButton buttonSaveUsername;
    private boolean saveUsername = false;
    private TextField textFieldPassword;
    private JButton buttonSavePassword;
    private boolean savePassword = false;
    private JButton start;
    private JButton back;
    private JButton reconnectToServer;
    private String username;
    private String password;
    private boolean tryingToConnect;
    private volatile boolean shouldRun = true;
    private String status = "";

    public SignUpPanel(Client client){
        this.client = client;

        this.setSize(1080, 771);
        this.setLayout(null);
        this.setFocusable(true);
        this.requestFocus();
        this.requestFocusInWindow();
        if(client.isOnline()) {
            textFieldUsername = new TextField("username");
        }else {
            textFieldUsername = new TextField("you are offline");
        }
        this.add(textFieldUsername);
        textFieldUsername.setBounds(370,100,500,60);
        buttonSaveUsername = new JButton("save");
        this.add(buttonSaveUsername);
        buttonSaveUsername.setBounds(200,100,100,60);
        buttonSaveUsername.addActionListener(this);

        if(client.isOnline()) {
            textFieldPassword = new TextField("password");
        }else {
            textFieldPassword = new TextField("you are offline");
        }
        this.add(textFieldPassword);
        textFieldPassword.setBounds(370,200,500,60);
        buttonSavePassword = new JButton("save");
        this.add(buttonSavePassword);
        buttonSavePassword.setBounds(200,200,100,60);
        buttonSavePassword.addActionListener(this);

        back = new JButton("Back");
        this.add(back);
        back.setBounds(460 , 380 , 160 , 80);
        back.addActionListener(this);

        start = new JButton(" Start !");
        this.add(start);
        start.setBounds(460 , 490 , 160 , 80);
        start.addActionListener(this);

        if(!client.isOnline()) {
            reconnectToServer = new JButton("Reconnect");
            this.add(reconnectToServer);
            reconnectToServer.setBounds(460, 600, 160, 80);
            reconnectToServer.setFocusable(false);
            reconnectToServer.addActionListener(this);
        }

        Thread thread = new Thread(this::run);
        thread.start();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource().equals(buttonSaveUsername)){
            username = textFieldUsername.getText();
            saveUsername = true;
        }
        else if(e.getSource().equals(buttonSavePassword)){
            password = textFieldPassword.getText();
            savePassword = true;
        }
        else if(e.getSource().equals(start)){
            if(!saveUsername){
                JOptionPane.showMessageDialog(this, "click on save for username");
            }
            else if(!savePassword){
                JOptionPane.showMessageDialog(this, "click on save for password");
            }
            else {
                if(client.isOnline()) {
                    client.send(new Order(Order.OrderType.NEW_USER , username , password));
                }else {
                    JOptionPane.showMessageDialog(this, "you are offline , please reconnect");
                }
            }
        }
        else if(e.getSource().equals(back)){;
            client.getClientFrame().setContentPane(new StartPanel(client));
            shouldRun = false;
        } else if (e.getSource().equals(reconnectToServer)) {
            tryingToConnect = true;
            this.remove(reconnectToServer);
            repaint();
        }
    }
    @Override
    public void paint(Graphics g) {
        super.paint(g);
        g.setFont(new Font("Courier", Font.BOLD, 20));
        g.setColor(Color.BLACK);
        g.drawString(status , 450 , 300);
    }

    @Override
    public void run() {
        while (shouldRun && !client.isOnline()){
            if(tryingToConnect){
                status = "Connecting to server ...";
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                client.Connect("127.0.0.1", 5090);

                reconnectToServerButton();

                tryingToConnect = false;
                repaint();
            }
        }
    }
    public void reconnectToServerButton(){
        if (client.isOnline()) {
            status = "Connection was successful";
        } else {
            status = "Connection failed";

            reconnectToServer = new JButton("Reconnect");
            this.add(reconnectToServer);
            reconnectToServer.setBounds(460, 600, 160, 80);
            reconnectToServer.setFocusable(false);
            reconnectToServer.addActionListener(this);
        }
    }

}
