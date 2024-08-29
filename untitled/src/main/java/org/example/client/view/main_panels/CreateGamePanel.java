package org.example.client.view.main_panels;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.example.client.controller.Client;
import org.example.local_data.ManageUser;
import org.example.offline_game.view.OfflineGame;
import org.example.config_loader.Level;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.LinkedList;

public class CreateGamePanel extends JPanel implements ActionListener {
    private int whichGame;
    private Client client;
    private int lives;
    private TextField textFieldLives;
    private JButton buttonSaveLives;
    private boolean saveLives = false;
    private int marioType;
    private TextField textFieldMarioType;
    private JButton buttonSaveMarioType;
    private boolean saveMarioType = false;
    private JButton loadLevel;
    private JButton back;
    private JButton start;
    private LinkedList<Level> levels = new LinkedList<>();

    public CreateGamePanel(int whichGame, Client client) {
        this.whichGame = whichGame;
        this.client = client;

        this.setSize(1080, 771);
        this.setLayout(null);
        this.setFocusable(true);
        this.requestFocus();
        this.requestFocusInWindow();

        textFieldLives = new TextField("Lives :");
        this.add(textFieldLives);
        textFieldLives.setBounds(370, 100, 500, 60);

        buttonSaveLives = new JButton("save");
        this.add(buttonSaveLives);
        buttonSaveLives.setBounds(200, 100, 100, 60);
        buttonSaveLives.addActionListener(this);

        textFieldMarioType = new TextField("Mario Type :");
        this.add(textFieldMarioType);
        textFieldMarioType.setBounds(370, 200, 500, 60);

        buttonSaveMarioType = new JButton("save");
        this.add(buttonSaveMarioType);
        buttonSaveMarioType.setBounds(200, 200, 100, 60);
        buttonSaveMarioType.addActionListener(this);

        loadLevel = new JButton("Upload Level +");
        this.add(loadLevel);
        loadLevel.setBounds(440, 300, 200, 100);
        loadLevel.addActionListener(this);

        back = new JButton("Back");
        this.add(back);
        back.setBounds(460, 480, 160, 80);
        back.addActionListener(this);

        start = new JButton(" Start !");
        this.add(start);
        start.setBounds(460, 590, 160, 80);
        start.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource().equals(buttonSaveLives)) {
            lives = Integer.parseInt(textFieldLives.getText());
            saveLives = true;
        } else if (e.getSource().equals(buttonSaveMarioType)) {
            marioType = Integer.parseInt(textFieldMarioType.getText());
            saveMarioType = true;
        } else if (e.getSource().equals(loadLevel)) {
            uploadLevel();
        }
        else if (e.getSource().equals(start)) {
            startButton();
        }
        else if (e.getSource().equals(back)) {
            client.getClientFrame().setContentPane(new StartNewGamePanel(client));
        }
    }
    public void startButton(){
        if (!saveLives) {
            JOptionPane.showMessageDialog(this, "click on save for Lives");
        } else if (!saveMarioType) {
            JOptionPane.showMessageDialog(this, "click on save for Mario Type");
        } else if (levels.size() == 0) {
            JOptionPane.showMessageDialog(this, "upload at least one level ");
        } else {
            startGame();
        }
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        g.setColor(Color.red);
        g.drawString(levels.size() + " levels have been uploaded" , 400 , 20 );
    }
    public void startGame(){
        OfflineGame game = new OfflineGame(client , levels , lives , marioType , whichGame);
        ManageUser.getInstance().handelGame(client.getUsername() , whichGame-1 , game);
        client.getClientFrame().setContentPane(game);
        game.setFocusable(true);
        game.requestFocus();
        game.requestFocusInWindow();
    }
    public void uploadLevel(){
        JFileChooser jFileChooser = new JFileChooser();
        int res = jFileChooser.showOpenDialog(null);
        if (res == JFileChooser.APPROVE_OPTION) {
            File filePath = new File(jFileChooser.getSelectedFile().getAbsolutePath());
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
            try {
                Level level = objectMapper.readValue(new File(String.valueOf(filePath)), Level.class);
                levels.add(level);
                repaint();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "invalid format");
            }
        }
    }

}
