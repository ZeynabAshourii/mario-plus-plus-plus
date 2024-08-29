package org.example.marathon_mario.model.entities;

import org.example.marathon_mario.controller.HandlerMarathon;
import org.example.marathon_mario.model.PlayerMarathon;
import org.example.marathon_mario.model.enums.Id;
import org.example.marathon_mario.model.tiles.Tile;
import org.example.send_object.entities.PaintMario;
import org.example.send_object.entities.items.PaintAdditionalItem;
import org.example.server.model.Item;
import java.util.Timer;
import java.util.TimerTask;

public class Mario extends Entity {
    private PlayerMarathon playerMarathon;
    private PaintMario paintMario;
    private long startTime;
    private boolean hitHammer = false;
    private long lifeTime;
    public Mario(double x, double y, int width, int height, Id id, HandlerMarathon handler , PlayerMarathon playerMarathon) {
        super(x, y, width, height, id, handler);
        startTime = System.nanoTime()/1000000000L;
        this.paintMario = new PaintMario(getX() , getY() , getWidth(), getHeight() , getId() , playerMarathon.getUsername());
        handler.getPaintEntities().add(paintMario);
        this.playerMarathon = playerMarathon;
    }

    @Override
    public void update() {
        lifeTime = System.nanoTime()/1000000000L - startTime;
        if (!hitHammer) {
            setX(getX() + getVelX());
            setY(getY() + getVelY());

            paintMario.setX(getX());
            paintMario.setY(getY());

            if(getY() > getHandler().getDeathY() + 64) die();

            hitTiles();
            hitPaintEntities();
            mangeGravity();
        }
        hitEntities();


        if(getVelX() != 0){
            paintMario.manageFrame();
        }
    }

    public void hitPaintEntities() {
        for (int i = 0; i < getHandler().getPaintEntities().size(); i++){
            if (getBounds().intersects(getHandler().getPaintEntities().get(i).getBounds())) {
                if (getHandler().getPaintEntities().get(i).getId().equals(Id.ADDITIONAL_ITEM)) {
                    PaintAdditionalItem additionalItem = (PaintAdditionalItem) getHandler().getPaintEntities().get(i);
                    Item item = new Item(additionalItem.getItemType(), paintMario.getUsername());
                    item.setAdditional(true);
                    playerMarathon.getAdditionalItems().add(item);
                    getHandler().setSenderName(paintMario.getUsername());
                    getHandler().setSendItems(true);
                    getHandler().getPaintEntities().remove(additionalItem);
                }
            }
        }
    }
    public void hitEntities() {
        for (int i = 0; i < getHandler().getEntities().size(); i++) {
            if (getBounds().intersects(getHandler().getEntities().get(i).getBounds()) && getHandler().getEntities().get(i).getId() == Id.GOOMBA) {
                Goomba goomba = (Goomba)getHandler().getEntities().get(i);
                if (getBoundsBottom().intersects(goomba.getBoundsTop())) {
                    getHandler().getEntities().remove(goomba);
                    getHandler().getPaintEntities().remove(goomba.getPaintGoomba());
                } else if (getBounds().intersects(goomba.getBounds())) {
                    die();
                }
            }
        }
    }

    public void hitTiles(){
        for(int i = 0; i < getHandler().getTiles().size(); i++){
            Tile tile = getHandler().getTiles().get(i);
            if (getBoundsTop().intersects(tile.getBounds())) {
                hitTileFromTop(tile);
            }
            if (getBoundsBottom().intersects(tile.getBounds())) {
                hitTileFromBottom(tile);
            } else if (!isFalling() && !isJumping()) {
                setGravity(0.8);
                setFalling(true);
            }
            if (getBoundsRight().intersects(tile.getBounds())) {
                setVelX(0);
                setX(tile.getX() - getWidth());
            }
            if (getBoundsLeft().intersects(tile.getBounds())) {
                setVelX(0);
                setX(tile.getX() + getWidth());
            }
        }
    }
    public void hitTileFromBottom(Tile tile){
        setVelY(0);
        if (isFalling()) {
            setFalling(false);
        }
    }
    public void hitTileFromTop(Tile tile){
        setVelY(0);
        if (isJumping()) {
            setJumping(false);
            setGravity(0.8);
            setFalling(true);
        }
    }
    public void mangeGravity(){
        if(isJumping()){
            setGravity(getGravity() - 0.17);
            setVelY((int)-getGravity());
            if(getGravity() <= 0.5){
                setJumping(false);
                setFalling(true);
            }
        }
        if(isFalling()){
            setGravity(getGravity() + 0.17);
            setVelY((int)getGravity());
        }
    }
    public void die(){
        getPlayerMarathon().getLifeTimes().add((int) lifeTime);
        getPlayerMarathon().getDistanceFromStarts().add((int) getX());
        getHandler().getEntities().remove(this);
        getHandler().getPaintEntities().remove(paintMario);
        setSpeed();
    }
    public void setSpeed(){
        Timer timer = new Timer();
        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                Mario mario = new Mario(64*2, -64*3 , getWidth() , getHeight() , getId() , getHandler() , getPlayerMarathon());
                mario.setVelX(mario.getVelX()*getHandler().getMarathonMultiplierSlowDown());
                getHandler().addEntity(mario);
                getHandler().setSenderName(paintMario.getUsername());
                getHandler().setSendItems(true);
                Timer anotherTimer = new Timer();
                TimerTask anotherTimerTask = new TimerTask() {
                    @Override
                    public void run() {
                        mario.setVelX(mario.getVelX()/getHandler().getMarathonMultiplierSpeed());
                    }
                };
                anotherTimer.schedule(anotherTimerTask , getHandler().getMarathonPeriodSlowDown()*1000);
            }
        };
        timer.schedule(timerTask , 1000);
    }
    public PlayerMarathon getPlayerMarathon() {
        return playerMarathon;
    }
    public PaintMario getPaintMario() {
        return paintMario;
    }
    public void setHitHammer(boolean hitHammer) {
        this.hitHammer = hitHammer;
    }
}
