package org.example.client.view.main_panels.online_game;

import org.example.client.controller.CameraTeamGame;
import org.example.client.controller.TeamGameClient;
import org.example.resources.Resources;
import org.example.server.model.Item;
import org.example.team_game.model.enums.Id;
import org.example.team_game.send_object.paint_entity.PaintEntity;
import org.example.team_game.send_object.paint_entity.PaintPlayer;
import org.example.team_game.send_object.paint_tile.PaintTile;
import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.LinkedList;
public class TeamGamePanel extends JPanel implements Runnable {
    private TeamGameClient teamGameClient;
    private CameraTeamGame camera;
    private LinkedList<PaintEntity> entities = new LinkedList<>();
    private LinkedList<PaintTile> tiles = new LinkedList<>();
    private boolean one;
    private boolean two;
    private boolean three;
    private boolean four;
    private boolean five;
    private boolean six;
    private boolean seven;
    private boolean eight;
    private boolean nine;
    private boolean finishedGame = false;
    private boolean gameOver = false;
    private boolean paused = false;
    private boolean running = false;
    private long startTime;
    private long endTime;
    private long elapsedTime;
    private long sigmaTime = 0;
    private int lives = 3;
    private int score = 0;
    private int coin = 0;
    private int section = 0;
    private boolean enter = false;
    private LinkedList<Item> items = new LinkedList<>();
    public TeamGamePanel(TeamGameClient client) {
        this.teamGameClient = client;

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
        double ns = 100000000.0*30/60.0;
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
        camera = new CameraTeamGame();
        startTime = System.nanoTime()/1000000000L;
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
                    teamGameClient.send(( teamGameClient.getClient().getUsername() + "RIGHT_PRESSED").getBytes());
                }
                else if (key == KeyEvent.VK_LEFT){
                    teamGameClient.send(( teamGameClient.getClient().getUsername() + "LEFT_PRESSED").getBytes());
                }
                else if (key == KeyEvent.VK_UP){
                    teamGameClient.send(( teamGameClient.getClient().getUsername() + "UP_PRESSED").getBytes());
                }
                else if (key == KeyEvent.VK_DOWN){
                    teamGameClient.send(( teamGameClient.getClient().getUsername() + "DOWN_PRESSED").getBytes());
                } else if (key == KeyEvent.VK_1) {
                    if (!one){
                        teamGameClient.send(( teamGameClient.getClient().getUsername() + "1key").getBytes());
                    }
                    one = true;
                } else if (key == KeyEvent.VK_2) {
                    if (!two){
                        teamGameClient.send(( teamGameClient.getClient().getUsername() + "2key").getBytes());
                    }
                    two = true;
                } else if (key == KeyEvent.VK_3) {
                    if (!three){
                        teamGameClient.send(( teamGameClient.getClient().getUsername() + "3key").getBytes());
                    }
                    three = true;
                } else if (key == KeyEvent.VK_4) {
                    if (!four){
                        teamGameClient.send(( teamGameClient.getClient().getUsername() + "4key").getBytes());
                    }
                    four = true;
                } else if (key == KeyEvent.VK_5) {
                    if (!five){
                        teamGameClient.send(( teamGameClient.getClient().getUsername() + "5key").getBytes());
                    }
                    five = true;
                } else if (key == KeyEvent.VK_6) {
                    if (!six){
                        teamGameClient.send(( teamGameClient.getClient().getUsername() + "6key").getBytes());
                    }
                    six = true;
                } else if (key == KeyEvent.VK_7) {
                    if (!seven){
                        teamGameClient.send(( teamGameClient.getClient().getUsername() + "7key").getBytes());
                    }
                    seven = true;
                } else if (key == KeyEvent.VK_8) {
                    if (!eight){
                        teamGameClient.send(( teamGameClient.getClient().getUsername() + "8key").getBytes());
                    }
                    eight = true;
                } else if (key == KeyEvent.VK_9) {
                    if (!nine){
                        teamGameClient.send(( teamGameClient.getClient().getUsername() + "9key").getBytes());
                    }
                    nine = true;
                } else if (key == keyEvent.VK_ENTER) {
                    if (!enter){
                        teamGameClient.send(( teamGameClient.getClient().getUsername() + "enter").getBytes());
                    }
                    enter = true;
                }
            }

            @Override
            public void keyReleased(KeyEvent keyEvent) {
                int key = keyEvent.getKeyCode();
                if (key == KeyEvent.VK_RIGHT){
                    teamGameClient.send(( teamGameClient.getClient().getUsername() + "RIGHT_RELEASED").getBytes());
                }
                else if (key == KeyEvent.VK_LEFT){
                    teamGameClient.send(( teamGameClient.getClient().getUsername() + "LEFT_RELEASED").getBytes());
                }
                else if (key == KeyEvent.VK_UP){
                    teamGameClient.send(( teamGameClient.getClient().getUsername() + "UP_RELEASED").getBytes());
                }
                else if (key == KeyEvent.VK_DOWN){
                    teamGameClient.send(( teamGameClient.getClient().getUsername() + "DOWN_RELEASED").getBytes());
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
                } else if (key == keyEvent.VK_ENTER) {
                    enter = false;
                }
            }
        });
    }
    public void update(){
        if (!finishedGame && !paused) {
            timeCalculation();
            if (searchMario() != null) {
                camera.update(searchMario());
            }
            teamGameClient.send((teamGameClient.getClient().getUsername() + "UPDATE_ENTITIES").getBytes());
            teamGameClient.send((teamGameClient.getClient().getUsername() + "UPDATE_TILES").getBytes());
        }else {
            stop();
        }
    }
    @Override
    public void paint(Graphics g) {
        super.paint(g);
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, getWidth(), getHeight());

        g.setFont(new Font("Courier", Font.BOLD, 20));

        if(!gameOver && !finishedGame){
            alivePaint(g);
        }
        if(finishedGame && !gameOver){
            g.setColor(Color.green);
            g.drawString("VICTORY", 610, 270);
            g.drawString("Game Finished", 610, 370);
        }
        if (!finishedGame && gameOver){
            g.setColor(Color.red);
            g.drawString("GAME OVER", 610, 270);
            g.drawString("Game Finished", 610, 370);
        }
    }
    public void alivePaint(Graphics g){
        paintInfo(g);
        paintItem(g);

        g.translate((int) camera.getX(), (int) camera.getY());

        for (int i = 0; i < entities.size(); i++) {
            PaintEntity entity = entities.get(i);
            entity.paint(g);
        }
        for (int i = 0; i < tiles.size(); i++) {
            PaintTile tile = tiles.get(i);
            tile.paint(g);
        }
    }
    public void paintInfo(Graphics g){
        g.setColor(Color.white);
        g.drawString( "Time : " + String.valueOf(elapsedTime), 150 , 20 );
        g.drawImage(Resources.getCoinPic().getSheet(), 50, 20, 75, 75, null);
        g.setColor(Color.yellow);
        g.drawString("x" + coin, 100, 95);
        g.setColor(Color.red);
        g.drawString("Score : " + score, 150 , 40);
        g.setColor(Color.green);
        g.drawString("Section : " + (section+1) , 360 , 40);
        g.setColor(Color.orange);
        g.drawString(" L I V E S : " + lives , 180 , 70);
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
    public void timeCalculation(){
        endTime = System.nanoTime()/1000000000L;
        elapsedTime = endTime - startTime + sigmaTime;
    }

    public PaintPlayer searchMario(){
        for (int i = 0; i < entities.size(); i++){
            PaintEntity entity = entities.get(i);
            if(entity.getId() == Id.PLAYER){
                if(((PaintPlayer)entity).getUsername().equals(teamGameClient.getClient().getUsername())){
                    return (PaintPlayer) entity;
                }
            }
        }
        return null;
    }

    public boolean isFinishedGame() {
        return finishedGame;
    }

    public void setFinishedGame(boolean finishedGame) {
        this.finishedGame = finishedGame;
    }

    public TeamGameClient getTeamGameClient() {
        return teamGameClient;
    }

    public void setTeamGameClient(TeamGameClient teamGameClient) {
        this.teamGameClient = teamGameClient;
    }

    public CameraTeamGame getCamera() {
        return camera;
    }

    public void setCamera(CameraTeamGame camera) {
        this.camera = camera;
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

    public boolean isOne() {
        return one;
    }

    public void setOne(boolean one) {
        this.one = one;
    }

    public boolean isTwo() {
        return two;
    }

    public void setTwo(boolean two) {
        this.two = two;
    }

    public boolean isThree() {
        return three;
    }

    public void setThree(boolean three) {
        this.three = three;
    }

    public boolean isFour() {
        return four;
    }

    public void setFour(boolean four) {
        this.four = four;
    }

    public boolean isFive() {
        return five;
    }

    public void setFive(boolean five) {
        this.five = five;
    }

    public boolean isSix() {
        return six;
    }

    public void setSix(boolean six) {
        this.six = six;
    }

    public boolean isSeven() {
        return seven;
    }

    public void setSeven(boolean seven) {
        this.seven = seven;
    }

    public boolean isEight() {
        return eight;
    }

    public void setEight(boolean eight) {
        this.eight = eight;
    }

    public boolean isNine() {
        return nine;
    }

    public void setNine(boolean nine) {
        this.nine = nine;
    }

    public boolean isGameOver() {
        return gameOver;
    }

    public void setGameOver(boolean gameOver) {
        this.gameOver = gameOver;
    }

    public boolean isPaused() {
        return paused;
    }

    public void setPaused(boolean paused) {
        this.paused = paused;
    }

    public boolean isRunning() {
        return running;
    }

    public void setRunning(boolean running) {
        this.running = running;
    }

    public long getStartTime() {
        return startTime;
    }

    public void setStartTime(long startTime) {
        this.startTime = startTime;
    }

    public long getEndTime() {
        return endTime;
    }

    public void setEndTime(long endTime) {
        this.endTime = endTime;
    }

    public long getElapsedTime() {
        return elapsedTime;
    }

    public void setElapsedTime(long elapsedTime) {
        this.elapsedTime = elapsedTime;
    }

    public long getSigmaTime() {
        return sigmaTime;
    }

    public void setSigmaTime(long sigmaTime) {
        this.sigmaTime = sigmaTime;
    }

    public int getLives() {
        return lives;
    }

    public void setLives(int lives) {
        this.lives = lives;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public int getCoin() {
        return coin;
    }

    public void setCoin(int coin) {
        this.coin = coin;
    }

    public int getSection() {
        return section;
    }

    public void setSection(int section) {
        this.section = section;
    }

    public LinkedList<Item> getItems() {
        return items;
    }

    public void setItems(LinkedList<Item> items) {
        this.items = items;
    }


}
