package org.example.offline_game.model.entity.enemies;

import org.example.offline_game.controller.HandlerOfflineGame;
import org.example.offline_game.model.entity.Entity;
import org.example.offline_game.model.entity.Player;
import org.example.offline_game.model.enums.Id;
import org.example.offline_game.model.enums.PlayerState;
import org.example.offline_game.model.tile.Tile;
import org.example.resources.Resources;
import java.awt.*;
import java.util.Random;
public class Bowser extends EnemyGame {
    private int firstX;
    private int coldDown;
    private int hp;
    private int frame = 0;
    private int frameDelay = 0;
    private final int maxHp = 20;
    private Attack attack;
    private boolean face = false;
    private boolean jumping = false;
    private boolean falling = false;
    private boolean health = true;
    private boolean cutScene = false;
    private boolean xKey = false;
    private double gravity = 0.0;
    private long grabAttackTime;
    private long freeTime;
    private long notOnEarthTime;
    private long cutSceneTime;
    private long timeHurt;
    public Bowser(int x, int y, int width, int height, Id id, HandlerOfflineGame handler) {
        super(x, y, width, height, id, handler);
        hp = maxHp;
        attack = Attack.noAttack;
    }
    @Override
    public void paint(Graphics g) {
        if(getPlayer() != null) {
            paintHpBar(g);
            paintBowser(g);
        }
    }
    public void paintHpBar(Graphics g){
        g.setColor(Color.white);
        g.drawRect(getPlayer().getX() - 30, getPlayer().getY() - 300, maxHp * 33, 30);
        g.setColor(Color.red);
        g.fillRect(getPlayer().getX() - 30, getPlayer().getY() - 300, hp * 33, 30);
        g.setColor(Color.blue);
        g.drawString("HP : " + hp, getPlayer().getX(), getPlayer().getY() - 280);
    }
    public void paintBowser(Graphics g){
        if(attack != Attack.cutScene) {
            if (falling) {
                if (getPlayer().getX() >= getX()) {
                    face = true;
                    g.drawImage(Resources.getKingKoopaRF().getSheet(), getX(), getY(), getWidth(), getHeight(), null);
                } else {
                    face = false;
                    g.drawImage(Resources.getKingKoopaLF().getSheet(), getX(), getY(), getWidth(), getHeight(), null);
                }
            } else {
                if (getPlayer().getX() >= getX()) {
                    face = true;
                    g.drawImage(Resources.getKingKoopaRight().getSheet(), getX(), getY(), getWidth(), getHeight(), null);
                } else {
                    face = false;
                    g.drawImage(Resources.getKingKoopaLeft().getSheet(), getX(), getY(), getWidth(), getHeight(), null);
                }
            }
        }else {
            paintCutScene(g);
        }
    }
    public void paintCutScene(Graphics g){
        if (getPlayer().getX() >= getX()) {
            face = true;
            g.drawImage(Resources.getPunchingBowser()[frame].getSheet(), getX(), getY(), getWidth(), getHeight(), null);
        } else {
            face = false;
            g.drawImage(Resources.getPunchingBowser()[frame+3].getSheet(), getX(), getY(), getWidth(), getHeight(), null);
        }
        for (int i = 0; i <= getHandler().getLength(); i++) {
            g.drawImage(Resources.getFire().getSheet(), i * 64, -33, 64, 64, null);
        }
    }

    @Override
    public void update() {
        if(getPlayer() != null) {
            if (health) {
                checkCutScene();
                if (attack != Attack.cutScene) {
                    noCutScene();

                } else {
                    startCutScene();
                }
            } else {
                long now = System.nanoTime() / 1000000000L;
                if (now - timeHurt > 1) {
                    health = true;
                }
            }
            checkAlive();
        }
    }
    public void checkCutScene(){
        if (hp <= maxHp/2 && !cutScene) {
            cutSceneTime = System.nanoTime() / 1000000000L;
            attack = Attack.cutScene;
            cutScene = true;
            setVelX(0);
            setVelY(0);
        }
    }
    public void startCutScene(){
        manageFrame();
        destroyTiles();
        Player player = getPlayer();
        player.setCutScene(true);
        if(player.getState() == PlayerState.MINI){
            player.setY(player.getY() - 64);
            player.setHeight(128);
        }
        player.setState(PlayerState.FIRE);
        long now = System.nanoTime() / 1000000000L;
        if (now - cutSceneTime > 2) {
            player.setCutScene(false);
            attack = Attack.noAttack;
        }
    }
    public void destroyTiles(){
        for(int i = 0; i < getHandler().getTiles().size(); i++){
            Tile tile = getHandler().getTiles().get(i);
            if ((tile.getX() != (64 * getHandler().getLength() + 64)) && (tile.getX() != -64) && (tile.getY() != 0)) {
                tile.die();
            }
        }
    }
    public void noCutScene(){
        if (distanceFromWall()) {
            setX(getX() + getVelX());
            setY(getY() + getVelY());
        } else {
            setVelX(0);
            attack = Attack.noAttack;
        }
        heatTheBlock();
        if (!isPlayerOnEarth()) {
            notOnEarthTime = System.nanoTime() / 1000000000L;
        }

        noAttack();
        firstAction();
        fireBallAttack();
        grabAttack();
        jumpAttack();
        bomb();
        finalAction();
    }
    public void noAttack(){
        if (attack == Attack.noAttack) {
            setVelX(0);
            long now = System.nanoTime() / 1000000000L;
            if (now - freeTime >= coldDown) {
                long distance = Math.abs(getX() - getPlayer().getX());
                if (distance > 8 * 64) {
                    attack = Attack.firstAction;
                    coldDown = 0;
                } else if (distance < 10 * 64 && distance > 6 * 64) {
                    attack = Attack.fireballAttack;
                    coldDown = 2;
                } else if (distance < 2 * 64) {
                    grabAttackTime = System.nanoTime() / 1000000000L;
                    attack = Attack.grabAttack;
                    coldDown = 4;
                } else if (now - notOnEarthTime > 4) {
                    if (!jumping) {
                        jumping = true;
                        gravity = 8;
                    }
                    attack = Attack.jumpAttack;
                    coldDown = 3;
                }
                else {
                    attack = Attack.finalAction;
                    coldDown = 0;
                    firstX = getX();
                }
            }
        }
    }
    public void firstAction(){
        if (attack == Attack.firstAction) {
            long distance = Math.abs(getX() - getPlayer().getX());
            if (face) {
                setVelX(2);
            } else {
                setVelX(-2);
            }
            if (distance <= 4 * 64) {
                setVelX(0);
                setVelY(0);
                attack = Attack.noAttack;
            }
        }
    }
    public void fireBallAttack(){
        if (attack == Attack.fireballAttack) {
            freeTime = System.nanoTime() / 1000000000L;
            if (!face) {
                getHandler().addEntity(new FireBallAttack(getX() - 64, getY() + getHeight() - 96, 96, 96, Id.fireBallAttack, getHandler(), 0));
                getHandler().addEntity(new FireBallAttack(getX() - 64, getY() + getHeight() - 96 * 2 - 64, 96, 96, Id.fireBallAttack, getHandler(), 0));
            } else {
                getHandler().addEntity(new FireBallAttack(getX() + 64, getY() + getHeight() - 96, 96, 96, Id.fireBallAttack, getHandler(), 1));
                getHandler().addEntity(new FireBallAttack(getX() + 64, getY() + getHeight() - 96 * 2 - 64, 96, 96, Id.fireBallAttack, getHandler(), 1));
            }
            attack = Attack.noAttack;
        }
    }
    public void grabAttack(){
        if (attack == Attack.grabAttack) {
            Player player = getPlayer();
            player.setX(getX());
            player.setY(getY() + getHeight() - player.getHeight());
            player.setGrabAttack(true);
            if (player.getNumberOfAD() >= 10) {
                if (face) {
                    player.setX(player.getX() - 64 * 2);
                } else {
                    player.setX(player.getX() + 64 * 2);
                }
                attack = Attack.noAttack;
                player.setGrabAttack(false);
                player.setNumberOfAD(0);
                freeTime = System.nanoTime() / 1000000000L;
            }
            long now = System.nanoTime() / 1000000000L;
            if (now - grabAttackTime > 5) {
                player.hitPlayer(this);
                attack = Attack.noAttack;
                player.setGrabAttack(false);
                player.setNumberOfAD(0);
                freeTime = System.nanoTime() / 1000000000L;
            }
        }
    }
    public void jumpAttack(){
        if (attack == Attack.jumpAttack) {
            if (jumping) {
                gravity = gravity - 0.15;
                setVelY((int) -gravity);
                if (gravity <= 0.5) {
                    jumping = false;
                    falling = true;
                }
            }
            if (falling) {
                gravity = gravity - 0.15;
                setVelY((int) -gravity);
                if ((getY() + getHeight()) / 5 == 0) {
                    falling = false;
                    setVelY(0);
                    attack = Attack.noAttack;
                    freeTime = System.nanoTime() / 1000000000L;
                    if (isPlayerOnEarth()) {
                        getPlayer().setJumpAttack(true);
                        getPlayer().setStartTimeJumpAttack(System.nanoTime() / 1000000000L);
                    }
                }
            }
        }
    }
    public void bomb(){
        if (xKey && (System.nanoTime() / 1000000000L - freeTime >= coldDown)) {
            freeTime = System.nanoTime() / 1000000000L;
            int random = new Random().nextInt(getHandler().getLength() - 5);
            random = random + 3;
            getHandler().addEntity(new NukeBomb(random * 64, (getHandler().getLength()) * (-64), 64, 64, Id.nukeBomb, getHandler()));
            coldDown = 3;
            xKey = false;
        }
        else if(xKey){
            xKey = false;
        }
    }
    public void finalAction(){
        if (attack == Attack.finalAction) {
            if (Math.abs(getX() - firstX) >= 3 * 64) {
                setVelX(0);
                setVelY(0);
                attack = Attack.noAttack;
            } else {
                if (face) {
                    if ((Math.abs(getX()) >= 2 * 64)) {
                        setVelX(1);
                    }
                } else {
                    if ((Math.abs(getX() - 64 * getHandler().getLength()) >= 2 * 64)) {
                        setVelX(-1);
                    }
                }
            }
        }
    }
    public void checkAlive(){
        if (hp <= 0) {
            die();
            getHandler().getGame().setFinishedGame(true);
        }
    }
    public boolean distanceFromWall(){
        if((Math.abs(getX()) >= 2*64) && (Math.abs(getX() - 64* getHandler().getLength()) >= 2*64)){
            return true;
        }else {
            return false;
        }
    }

    public void hurt(){
        health = false;
        timeHurt = System.nanoTime()/1000000000L;
        hp = hp - 1;
    }
    public void heatTheBlock(){
        for(int i = 0; i < getHandler().getTiles().size(); i++){
            Tile tile = getHandler().getTiles().get(i);
            if((tile.getX() != 64*getHandler().getLength()+64) && (tile.getX() != -64) && (tile.getY() != 0) ) {
                if (getBounds().intersects(tile.getBounds())) {
                    tile.die();
                }
            }
        }
    }
    public Rectangle head(){
        return new Rectangle(getX() , getY() , getWidth() , 32);
    }
    public boolean isPlayerOnEarth(){
        boolean playerOnEarth = false;
            if ((getPlayer().getY() + getPlayer().getHeight()) / 10 == 0) {
                playerOnEarth = true;
            }
        return playerOnEarth;
    }
    public Player getPlayer(){
        Player player = null;
        for(int i = 0; i < getHandler().getEntities().size(); i++){
            Entity entity = getHandler().getEntities().get(i);
            if(entity.getId() == Id.player){
                player = (Player) entity;
            }
        }
        return player;
    }
    public void manageFrame(){
        frameDelay++;
        if (frameDelay >= 15) {
            frame++;
            if (frame >= 3) {
                frame = 0;
            }
            frameDelay = 0;
        }
    }

    public Attack getAttack() {
        return attack;
    }

    public boolean isHealth() {
        return health;
    }
    public void setxKey(boolean xKey) {
        this.xKey = xKey;
    }

    public boolean isCutScene() {
        return cutScene;
    }
    public int getHp() {
        return hp;
    }

    public void setHp(int hp) {
        this.hp = hp;
    }

}
