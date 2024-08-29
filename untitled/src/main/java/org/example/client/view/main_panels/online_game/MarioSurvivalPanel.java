package org.example.client.view.main_panels.online_game;

import org.example.client.controller.SurvivalClient;
import org.example.marathon_mario.model.enums.Id;
import org.example.send_object.entities.PaintEntity;
import org.example.send_object.entities.PaintMario;
import org.example.send_object.entities.PaintReaction;
import org.example.send_object.entities.PaintSpectator;
import org.example.send_object.tile.PaintEmptyBlock;
import org.example.send_object.tile.PaintTile;
import org.example.server.model.Item;
import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.LinkedList;
public class MarioSurvivalPanel extends JPanel implements Runnable{
    private boolean running;
    private SurvivalClient client;
    private LinkedList<PaintEntity> entities = new LinkedList<>();
    private LinkedList<PaintTile> tiles = new LinkedList<>();
    private LinkedList<Item> items = new LinkedList<>();
    private PaintReaction[] reactions = new PaintReaction[5];
    private boolean qKey;
    private boolean wKey;
    private boolean eKey;
    private boolean rKey;
    private boolean tKey;
    private boolean one;
    private boolean two;
    private boolean three;
    private boolean four;
    private boolean five;
    private boolean six;
    private boolean seven;
    private boolean eight;
    private boolean nine;
    private boolean end = false;
    private boolean spectator = false;
    public MarioSurvivalPanel(SurvivalClient client) {
        this.client = client;

        this.setSize(1080, 771);
        this.setLayout(null);
        this.setFocusable(true);
        this.requestFocus();
        this.requestFocusInWindow();
        this.start();
    }

    public synchronized void start(){
        if(running){
            return;
        }
        running = true;
        Thread thread = new Thread(this , "Thread");
        thread.start();
    }
    public synchronized void stop(){
        if(!running){
            return;
        }
        running = false;
    }
    public void init(){
        reactions[0] = new PaintReaction(15*64 + 12 , 10 , 40 , 40 , Id.REACTION , PaintReaction.ReactionType.LIKE );
        reactions[1] = new PaintReaction(15*64 + 12 , 10 + 45 , 40 , 40 , Id.REACTION , PaintReaction.ReactionType.DISLIKE);
        reactions[2] = new PaintReaction(15*64 + 12 , 10 + 45*2 , 40 , 40 , Id.REACTION , PaintReaction.ReactionType.LAUGH);
        reactions[3] = new PaintReaction(15*64 + 12 , 10 + 45*3 , 40 , 40 , Id.REACTION , PaintReaction.ReactionType.ANGER);
        reactions[4] = new PaintReaction(15*64 + 12 , 10 + 45*4 , 40 , 40 , Id.REACTION , PaintReaction.ReactionType.HEART);
        int length = 17;
        for(int i = 0; i < length; i++){
            tiles.add(new PaintEmptyBlock((i) * 64, 64*10.4, 64, 64, Id.EMPTY_BLOCK));
            tiles.add(new PaintEmptyBlock(33*32 - 8 , 64*(9.4 - i) , 64 , 64 , Id.EMPTY_BLOCK));
            tiles.add(new PaintEmptyBlock(-48 , 64*(9.4 - i) , 64 , 64 , Id.EMPTY_BLOCK));
            if(i%14 > 1){
                tiles.add(new PaintEmptyBlock((i)*64 , 64*6.7 , 64 , 64 , Id.EMPTY_BLOCK));
            }
        }
        keyInput();
    }
    public void keyInput(){
        addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent keyEvent) {

            }

            @Override
            public void keyPressed(KeyEvent keyEvent) {
                int key = keyEvent.getKeyCode();
                if (key == KeyEvent.VK_RIGHT){
                    client.send(( client.getUsername() + "RIGHT_PRESSED").getBytes());
                }
                else if (key == KeyEvent.VK_LEFT){
                    client.send(( client.getUsername() + "LEFT_PRESSED").getBytes());
                }
                else if (key == KeyEvent.VK_UP){
                    client.send(( client.getUsername() + "UP_PRESSED").getBytes());
                }
                else if (key == KeyEvent.VK_DOWN){
                    client.send(( client.getUsername() + "DOWN_PRESSED").getBytes());
                } else if (key == KeyEvent.VK_1) {
                    if (!one && !spectator){
                        client.send(( client.getUsername() + "1key").getBytes());
                    }
                    one = true;
                } else if (key == KeyEvent.VK_2) {
                    if (!two && !spectator){
                        client.send(( client.getUsername() + "2key").getBytes());
                    }
                    two = true;
                } else if (key == KeyEvent.VK_3) {
                    if (!three && !spectator){
                        client.send(( client.getUsername() + "3key").getBytes());
                    }
                    three = true;
                } else if (key == KeyEvent.VK_4) {
                    if (!four && !spectator){
                        client.send(( client.getUsername() + "4key").getBytes());
                    }
                    four = true;
                } else if (key == KeyEvent.VK_5) {
                    if (!five && !spectator){
                        client.send(( client.getUsername() + "5key").getBytes());
                    }
                    five = true;
                } else if (key == KeyEvent.VK_6) {
                    if (!six && !spectator){
                        client.send(( client.getUsername() + "6key").getBytes());
                    }
                    six = true;
                } else if (key == KeyEvent.VK_7) {
                    if (!seven && !spectator){
                        client.send(( client.getUsername() + "7key").getBytes());
                    }
                    seven = true;
                } else if (key == KeyEvent.VK_8) {
                    if (!eight && !spectator){
                        client.send(( client.getUsername() + "8key").getBytes());
                    }
                    eight = true;
                } else if (key == KeyEvent.VK_9) {
                    if (!nine && !spectator){
                        client.send(( client.getUsername() + "9key").getBytes());
                    }
                    nine = true;
                } else if (key == KeyEvent.VK_Q) {
                    if (!qKey && spectator){
                        client.send(( client.getUsername() + "qKey").getBytes());
                    }
                    qKey = true;
                } else if (key == KeyEvent.VK_W) {
                    if (!wKey && spectator){
                        client.send(( client.getUsername() + "wKey").getBytes());
                    }
                    wKey = true;
                } else if (key == KeyEvent.VK_E) {
                    if (!eKey && spectator){
                        client.send(( client.getUsername() + "eKey").getBytes());
                    }
                    eKey = true;
                } else if (key == KeyEvent.VK_R) {
                    if (!rKey && spectator){
                        client.send(( client.getUsername() + "rKey").getBytes());
                    }
                    rKey = true;
                } else if (key == KeyEvent.VK_T) {
                    if (!tKey && spectator){
                        client.send(( client.getUsername() + "tKey").getBytes());
                    }
                    tKey = true;
                }
            }

            @Override
            public void keyReleased(KeyEvent keyEvent) {
                int key = keyEvent.getKeyCode();
                if (key == KeyEvent.VK_RIGHT){
                    client.send(( client.getUsername() + "RIGHT_RELEASED").getBytes());
                }
                else if (key == KeyEvent.VK_LEFT){
                    client.send(( client.getUsername() + "LEFT_RELEASED").getBytes());
                }
                else if (key == KeyEvent.VK_UP){
                    client.send(( client.getUsername() + "UP_RELEASED").getBytes());
                }
                else if (key == KeyEvent.VK_DOWN){
                    client.send(( client.getUsername() + "DOWN_RELEASED").getBytes());
                } else if (key == KeyEvent.VK_1) {
                    one = false;
                } else if (key == KeyEvent.VK_2) {
                    two = false;
                } else if (key == KeyEvent.VK_3) {
                    three = false;
                } else if (key == KeyEvent.VK_4) {
                    four = false;
                } else if (key == KeyEvent.VK_5) {
                    five = false;
                } else if (key == KeyEvent.VK_6) {
                    six = false;
                } else if (key == KeyEvent.VK_7) {
                    seven = false;
                } else if (key == KeyEvent.VK_8) {
                    eight = false;
                } else if (key == KeyEvent.VK_9) {
                    nine = false;
                }  else if (key == KeyEvent.VK_Q) {
                    qKey = false;
                } else if (key == KeyEvent.VK_W) {
                    wKey = false;
                } else if (key == KeyEvent.VK_E) {
                    eKey = false;
                } else if (key == KeyEvent.VK_R) {
                    rKey = false;
                } else if (key == KeyEvent.VK_T) {
                    tKey = false;
                }
            }
        });
    }

    @Override
    public void run() {
        init();
        this.setFocusable(true);
        this.requestFocus();
        this.requestFocusInWindow();
        long lastTime = System.nanoTime();
        long timer = System.currentTimeMillis();
        double delta = 0.0;
        double ns = 100000000.0*14/60.0;
        int frames = 0;
        int updates = 0;
        while (running){
            long now = System.nanoTime();
            delta += (now-lastTime)/ns;
            lastTime = now;
            while (delta >= 1){
                update();
                updates++;
                delta--;
            }
            repaint();
            frames++;
            if(System.currentTimeMillis() - timer > 1000){
                timer += 1000;
                frames = 0;
                updates = 0;
            }
        }
        stop();
    }
    public void update(){
        if (!end) {
            if (!spectator){
                if (checkForSpectatorState()){
                    spectator = true;
                }
            }
            client.send((client.getUsername() + "UPDATE").getBytes());
        }else {
            stop();
        }
    }
    public boolean checkForSpectatorState(){
        for (int i = 0; i < entities.size(); i++){
            PaintEntity entity = entities.get(i);
            if(entity.getId() == Id.SPECTATOR){
                if(((PaintSpectator)entity).getUsername().equals(client.getUsername())){
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, getWidth(), getHeight());
        g.setFont(new Font("Courier", Font.BOLD, 50));

        if (end){
            g.setColor(Color.GREEN);
            g.drawString("End Of The Game" , 400 , 350);
        } else {
            if (!spectator) {
                paintItem(g);
                g.setColor(Color.WHITE);
                g.setFont(new Font("Courier", Font.BOLD, 20));
                if (searchMario() != null) {
                    String str = "Percent Life : " + searchMario().getLife();
                    g.drawString(str, 600, 200);
                }
            }
            g.setColor(Color.WHITE);
            g.setFont(new Font("Courier", Font.BOLD, 12));
            for (int i = 0; i < reactions.length; i++){
                reactions[i].paint(g);
            }
            g.setFont(new Font("Courier", Font.BOLD, 20));
            paintObject(g);
        }
    }
    public void paintObject(Graphics g){
        for (int i = 0; i < entities.size(); i++) {
            PaintEntity entity = entities.get(i);
            entity.paint(g);
        }
        for (int i = 0; i < tiles.size(); i++) {
            PaintTile tile = tiles.get(i);
            tile.paint(g);
        }
    }
    public void paintItem(Graphics g){
        for (int i = 0; i < items.size(); i++) {
            Item item = items.get(i);
            if (item.getItemType().equals(Item.ItemType.INVISIBLE)) {
                g.setColor(Color.PINK);
            } else if (item.getItemType().equals(Item.ItemType.SPEED)) {
                g.setColor(Color.BLUE);
            } else if (item.getItemType().equals(Item.ItemType.HEAL)) {
                g.setColor(Color.RED);
            } else if (item.getItemType().equals(Item.ItemType.EXPLOSIVE_BOMB)) {
                g.setColor(Color.CYAN);
            } else if (item.getItemType().equals(Item.ItemType.SPEED_BOMB)) {
                g.setColor(Color.YELLOW);
            } else if (item.getItemType().equals(Item.ItemType.HAMMER)) {
                g.setColor(Color.ORANGE);
            } else if (item.getItemType().equals(Item.ItemType.SWORD)) {
                g.setColor(Color.MAGENTA);
            }
            if (searchMario() != null) {
                int q = i / 24;
                int r = i - 24 * q;
                int x = 10 + 42 * r;
                int y = 10 + 42 * q;
                g.fillRect(x, y, 40, 40);
                g.setColor(Color.WHITE);
                g.setFont(new Font("Courier", Font.BOLD, 6));
                g.drawString(String.valueOf(item.getItemType()), x + 12, y + 12);
            }

        }
    }
    public PaintMario searchMario(){
        for (int i = 0; i < entities.size(); i++){
            PaintEntity entity = entities.get(i);
            if(entity.getId() == Id.MARIO){
                if(((PaintMario)entity).getUsername().equals(client.getUsername())){
                    return (PaintMario) entity;
                }
            }
        }
        return null;
    }

    public LinkedList<Item> getItems() {
        return items;
    }

    public void setItems(LinkedList<Item> items) {
        this.items = items;
    }

    public LinkedList<PaintEntity> getEntities() {
        return entities;
    }

    public void setEntities(LinkedList<PaintEntity> entities) {
        this.entities = entities;
    }

    public boolean isEnd() {
        return end;
    }

    public void setEnd(boolean end) {
        this.end = end;
    }
    public void setReactions(PaintReaction[] reactions) {
        this.reactions = reactions;
    }
}
