package org.example.client.view.main_panels.online_game;

import org.example.client.controller.Camera;
import org.example.client.controller.MarathonClient;
import org.example.marathon_mario.model.enums.Id;
import org.example.send_object.entities.PaintEntity;
import org.example.send_object.entities.PaintMario;
import org.example.send_object.tile.PaintEmptyBlock;
import org.example.send_object.tile.PaintTile;
import org.example.resources.Resources;
import org.example.server.model.Item;
import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.LinkedList;
public class MarathonMarioPanel extends JPanel implements Runnable{
    private MarathonClient client;
    private boolean running;
    private Camera camera;
    private LinkedList<PaintEntity> entities = new LinkedList<>();
    private LinkedList<PaintTile> tiles = new LinkedList<>();
    private boolean right;
    private boolean left;
    private boolean up;
    private boolean down;
    private boolean one;
    private boolean two;
    private boolean three;
    private boolean four;
    private boolean five;
    private boolean six;
    private boolean seven;
    private boolean eight;
    private boolean nine;
    private long startTime;
    private boolean start = true;
    private boolean end = false;
    private LinkedList<Item> items = new LinkedList<>();
    public MarathonMarioPanel(MarathonClient client) {
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


    @Override
    public void run() {
        init();
        this.setFocusable(true);
        this.requestFocus();
        this.requestFocusInWindow();
        long lastTime = System.nanoTime();
        long timer = System.currentTimeMillis();
        double delta = 0.0;
        double ns = 100000000.0*7/60.0;
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
    public void init(){
        camera = new Camera();
        startTime = System.nanoTime()/1000000000L;
        int length = 1000;
        for(int i = -1; i <= length+1; i++){
            if (i%30 != 0) {
                tiles.add(new PaintEmptyBlock((i) * 64, 0, 64, 64, Id.EMPTY_BLOCK));
            }
            if(i%35 > 4){
                tiles.add(new PaintEmptyBlock((i)*64 , -64*3.5 , 64 , 64 , Id.EMPTY_BLOCK));
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
                if (!end) {
                    if (key == KeyEvent.VK_RIGHT) {
                        if (!right) {
                            client.send((client.getUsername() + "RIGHT").getBytes());
                        }
                        right = true;
                    } else if (key == KeyEvent.VK_LEFT) {
                        if (!left) {
                            client.send((client.getUsername() + "LEFT").getBytes());
                        }
                        left = true;
                    } else if (key == KeyEvent.VK_UP) {
                        if (!up) {
                            client.send((client.getUsername() + "UP").getBytes());
                        }
                        up = true;
                    } else if (key == KeyEvent.VK_DOWN) {
                        if (!down) {
                            client.send((client.getUsername() + "DOWN").getBytes());
                        }
                        down = true;
                    } else if (key == KeyEvent.VK_1) {
                        if (!one) {
                            client.send((client.getUsername() + "1key").getBytes());
                        }
                        one = true;
                    } else if (key == KeyEvent.VK_2) {
                        if (!two) {
                            client.send((client.getUsername() + "2key").getBytes());
                        }
                        two = true;
                    } else if (key == KeyEvent.VK_3) {
                        if (!three) {
                            client.send((client.getUsername() + "3key").getBytes());
                        }
                        three = true;
                    } else if (key == KeyEvent.VK_4) {
                        if (!four) {
                            client.send((client.getUsername() + "4key").getBytes());
                        }
                        four = true;
                    } else if (key == KeyEvent.VK_5) {
                        if (!five) {
                            client.send((client.getUsername() + "5key").getBytes());
                        }
                        five = true;
                    } else if (key == KeyEvent.VK_6) {
                        if (!six) {
                            client.send((client.getUsername() + "6key").getBytes());
                        }
                        six = true;
                    } else if (key == KeyEvent.VK_7) {
                        if (!seven) {
                            client.send((client.getUsername() + "7key").getBytes());
                        }
                        seven = true;
                    } else if (key == KeyEvent.VK_8) {
                        if (!eight) {
                            client.send((client.getUsername() + "8key").getBytes());
                        }
                        eight = true;
                    } else if (key == KeyEvent.VK_9) {
                        if (!nine) {
                            client.send((client.getUsername() + "9key").getBytes());
                        }
                        nine = true;
                    }
                }
            }

            @Override
            public void keyReleased(KeyEvent keyEvent) {
                int key = keyEvent.getKeyCode();
                if (key == KeyEvent.VK_RIGHT){
                    right = false;
                } else if (key == KeyEvent.VK_LEFT){
                    left = false;
                } else if (key == KeyEvent.VK_UP){
                    up = false;
                } else if (key == KeyEvent.VK_DOWN){
                    down = false;
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
                }
            }
        });
    }
    public void update(){
        if (!end) {
            if (searchMario() != null) {
                camera.update(searchMario());
            }
            client.send((client.getUsername() + "UPDATE").getBytes());
        }else {
            stop();
        }
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
            if ((searchMario() == null && !start)) {
                g.drawImage(Resources.getMarioPic()[0].getSheet(), 300, 300, 100, 100, null);
                g.setColor(Color.WHITE);
                g.drawString("death screen", 500, 370);
            } else {
                paintItem(g);
                g.setColor(Color.WHITE);
                g.setFont(new Font("Courier", Font.BOLD, 20));
                long time = System.nanoTime() / 1000000000L - startTime;
                g.drawString("Time : " + time, 500, 100);
                if (searchMario() != null) {
                    String str = "Percent Life : " + searchMario().getLife();
                    g.drawString(str, 700, 200);

                }
                g.translate((int) camera.getX(), (int) camera.getY());
                paintObject(g);
            }
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
                start = false;
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
    public MarathonClient getClient() {
        return client;
    }

    public void setClient(MarathonClient client) {
        this.client = client;
    }

    public LinkedList<PaintEntity> getEntities() {
        return entities;
    }

    public void setEntities(LinkedList<PaintEntity> entities) {
        this.entities = entities;
    }

    public LinkedList<PaintTile> getTiles() {
        return tiles;
    }

    public void setTiles(LinkedList<PaintTile> tiles) {
        this.tiles = tiles;
    }

    public LinkedList<Item> getItems() {
        return items;
    }

    public void setItems(LinkedList<Item> items) {
        this.items = items;
    }

    public boolean isEnd() {
        return end;
    }

    public void setEnd(boolean end) {
        this.end = end;
    }
}
