package org.example.offline_game.view;

import org.example.local_data.ManageUser;
import org.example.resources.Sound;
import org.example.client.view.main_panels.user_starter_panels.UserStarterPanel;
import org.example.resources.Resources;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
public class StopPanel extends JPanel implements ActionListener {
    private OfflineGame game;
    private JButton mute;
    private JButton unMute;
    private JButton resume;
    private JButton exit;
    public StopPanel(OfflineGame game){
        this.game = game;

        this.setSize(1080, 771);
        this.setLayout(null);
        this.setFocusable(true);
        this.requestFocus();
        this.requestFocusInWindow();

        mute = new JButton("MUTE");
        this.add(mute);
        mute.setBounds(440 , 200 , 160 , 80);
        mute.setFocusable(false);
        mute.addActionListener(this);

        unMute = new JButton("UN MUTE");
        this.add(unMute);
        unMute.setBounds(440 , 290 , 160 , 80);
        unMute.setFocusable(false);
        unMute.addActionListener(this);

        resume = new JButton("RESUME");
        this.add(resume);
        resume.setBounds(440 , 380 , 160 , 80);
        resume.setFocusable(false);
        resume.addActionListener(this);

        exit = new JButton("EXIT");
        this.add(exit);
        exit.setBounds(440 , 470 , 160 , 80);
        exit.setFocusable(false);
        exit.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource().equals(mute)){
            game.setMute(true);
            for(int i = 0; i < 6; i++){
                Sound sound =  Resources.getSounds()[i];
                if(sound.getClip().isRunning()){
                    sound.setMute(true);
                    sound.stop();
                }
            }
        } else if (e.getSource().equals(unMute)) {
            game.setMute(false);
            for(int i = 0; i < 6; i++){
                Sound sound =  Resources.getSounds()[i];
                if(sound.isMute()){
                    sound.setMute(false);
                    sound.play();
                }
            }
        }else if (e.getSource().equals(resume)){
            game.setPaused(false);
            game.getClient().getClientFrame().setContentPane(game);
            game.setFocusable(true);
            game.requestFocus();
            game.requestFocusInWindow();
        } else if (e.getSource().equals(exit)){
            game.setPaused(true);
            Resources.stopSounds();
            try {
                ManageUser.getInstance().resetUser();
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
            game.getClient().getClientFrame().setContentPane(new UserStarterPanel(game.getClient()));
        }
    }
}
