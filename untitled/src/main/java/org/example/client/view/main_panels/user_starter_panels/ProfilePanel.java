package org.example.client.view.main_panels.user_starter_panels;

import org.example.client.controller.Client;
import org.example.local_data.User;
import javax.swing.*;
import java.awt.event.ActionListener;
import java.awt.*;
import java.awt.event.ActionEvent;
public class ProfilePanel extends JPanel implements ActionListener {
    private Client client;
    private JButton back;
    public ProfilePanel(Client client) {
        this.client = client;

        this.setSize(1080, 771);
        this.setLayout(null);
        this.setFocusable(true);
        this.requestFocus();
        this.requestFocusInWindow();

        back = new JButton("Back");
        this.add(back);
        back.setBounds(400 , 600 , 240 , 80);
        back.setFocusable(false);
        back.addActionListener(this);
    }
    @Override
    public void paint(Graphics g) {
        super.paint(g);
        g.setColor(Color.black);
        g.setFont(new Font("Courier", Font.BOLD, 25));
        g.drawString("Username : " + client.getUsername(),  400 , 400);
        g.drawString("Coins : " + client.getCoin(), 400 , 500);
        g.drawString("Coins : " + client.getDiamond(), 400 , 600);

    }
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource().equals(back)) {
            client.getClientFrame().setContentPane(new UserStarterPanel(client));
        }
    }
}

