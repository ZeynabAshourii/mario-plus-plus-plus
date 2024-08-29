package org.example.client.view.main_panels;

import org.example.client.controller.Client;
import org.example.local_data.ManageUser;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
public class StartPanel extends JPanel implements ActionListener {
    private Client client;
    private JButton signUp;
    private JButton signIn;
    private JButton exit;
    public StartPanel(Client client){
        this.client = client;

        this.setSize(1080,771);
        this.setLayout(null);
        this.setFocusable(true);
        this.requestFocus();
        this.requestFocusInWindow();

        signUp = new JButton("Sign up");
        this.add(signUp);
        signUp.setBounds(460,200,160, 80);
        signUp.setFocusable(false);
        signUp.addActionListener(this);
        signIn = new JButton("Sign in");
        this.add(signIn);
        signIn.setBounds(460 , 320 , 160 , 80);
        signIn.setFocusable(false);
        signIn.addActionListener(this);
        exit = new JButton("Exit");
        this.add(exit);
        exit.setBounds(460 , 440 , 160 , 80);
        exit.setFocusable(false);
        exit.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource().equals(signUp)){
            client.getClientFrame().setContentPane(new SignUpPanel(client));
        } else if (e.getSource().equals(signIn)) {
            client.getClientFrame().setContentPane(new SignInPanel(client));
        } else if (e.getSource().equals(exit)) {
            try {
                ManageUser.getInstance().resetUser();
                client.getClientFrame().dispose();
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
            client.getClientFrame().dispose();
            System.exit(0);
        }
    }
}
