package org.example.client.view.main_panels;

import org.example.client.controller.Client;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ConnectToServerPanel extends JPanel implements ActionListener , Runnable {
    private Client client;
    private boolean tryingToConnect;
    private volatile boolean shouldRun = true;
    private JButton reconnectToServer;
    private JButton goToNextPanel;
    private String status;
    public ConnectToServerPanel(Client client){
        this.client = client;

        this.setSize(1080, 771);
        this.setLayout(null);
        this.setFocusable(true);
        this.requestFocus();
        this.requestFocusInWindow();

        tryingToConnect = true;
        status = "Connecting to server ...";

        Thread thread = new Thread(this::run);
        thread.start();
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        if (actionEvent.getSource().equals(reconnectToServer)){
            tryingToConnect = true;
            status = "Connecting to server ...";
            this.remove(reconnectToServer);
            this.remove(goToNextPanel);
            repaint();
        } else if (actionEvent.getSource().equals(goToNextPanel)) {
            shouldRun = false;
            client.getClientFrame().setContentPane(new StartPanel(client));
        }
    }
    @Override
    public void paint(Graphics g) {
        super.paint(g);
        g.setFont(new Font("Courier", Font.BOLD, 30));
        g.setColor(Color.BLACK);
        g.drawString(status , 100 , 100);
    }

    @Override
    public void run() {
        while (shouldRun) {
            if (tryingToConnect) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }

                client.Connect("127.0.0.1", 5090);

                if (client.isOnline()) {
                    status = "Connection was successful";
                } else {
                    status = "Connection failed";

                    reconnectToServer = new JButton("Reconnect");
                    this.add(reconnectToServer);
                    reconnectToServer.setBounds(450, 500, 160, 80);
                    reconnectToServer.setFocusable(false);
                    reconnectToServer.addActionListener(this);
                }
                nextPanel();

                tryingToConnect = false;
                repaint();
            }
        }
    }
    public void nextPanel(){
        goToNextPanel = new JButton("Start");
        this.add(goToNextPanel);
        goToNextPanel.setBounds(450, 400, 160, 80);
        goToNextPanel.setFocusable(false);
        goToNextPanel.addActionListener(this);
    }
}
