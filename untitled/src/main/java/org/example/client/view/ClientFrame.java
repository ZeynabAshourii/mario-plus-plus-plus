package org.example.client.view;

import org.example.client.controller.Client;
import org.example.client.view.main_panels.ConnectToServerPanel;
import org.example.resources.Resources;
import javax.swing.*;
public class ClientFrame extends JFrame {
    private Client client;
    private ConnectToServerPanel connectToServerPanel;
    public ClientFrame() {
        Resources.init();
        this.setSize(1080, 771);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);
        this.setLayout(null);
        client = new Client(this);
        connectToServerPanel = new ConnectToServerPanel(client);
        this.setContentPane(connectToServerPanel);
        this.setVisible(true);
    }

}


