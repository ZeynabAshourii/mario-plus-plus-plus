package org.example.mario_survival.model.entities;

import org.example.marathon_mario.model.enums.Id;
import org.example.send_object.entities.items.PaintHammer;
import org.example.mario_survival.controller.HandlerSurvival;
import org.example.mario_survival.model.tiles.Tile;
import java.util.Timer;
import java.util.TimerTask;

public class Hammer extends Entity {
    private boolean active = false;
    private String ownUsername;
    private PaintHammer paintHammer;
    public Hammer(double x, double y, int width, int height, Id id, HandlerSurvival handler , String ownUsername , boolean way) {
        super(x, y, width, height, id, handler);
        this.ownUsername = ownUsername;
        if (way) {
            setVelX(3.5);
        }else {
            setVelX(-3.5);
        }
        this.paintHammer = new PaintHammer(x, y, width, height, id);
        getHandler().getPaintEntities().add(paintHammer);
    }

    @Override
    public void update() {
        setX(getX() + getVelX());
        paintHammer.setX(getX());
        if(active){
            getHandler().getEntities().remove(this);
            getHandler().getPaintEntities().remove(paintHammer);
        }
        hitTiles();
        hitEntities();
        if(getX() >= 64*(getHandler().getLength()-1) || getX() <= 0){
            if (!active) {
                hit();
            }
        }
    }
    public void hitTiles(){
        for(int i = 0; i < getHandler().getTiles().size(); i++){
            Tile tile = getHandler().getTiles().get(i);
            if(getBounds().intersects(tile.getBounds())){
                if(!active) {
                    hit();
                }
            }
        }
    }
    public void hitEntities(){
        for(int i = 0; i < getHandler().getEntities().size(); i++){
            if (getBounds().intersects(getHandler().getEntities().get(i).getBounds())) {
                if (getHandler().getEntities().get(i).getId().equals(Id.MARIO)) {
                    Mario mario = (Mario) getHandler().getEntities().get(i);
                    if (!mario.getPaintMario().getUsername().equals(ownUsername)) {
                        mario.setHitHammer(true);
                        Timer timer = new Timer();
                        TimerTask timerTask = new TimerTask() {
                            @Override
                            public void run() {
                                mario.setHitHammer(false);
                            }
                        };
                        timer.schedule(timerTask , getHandler().getHammerStunPeriod()*1000);
                        if (!active){
                            hit();
                        }
                    }
                }
            }
        }
    }
    public void hit(){
        setVelX(0);
        setX(getX() - 64);
        paintHammer.setX(getX());
        setHeight(64*3);
        paintHammer.setHeight(64*3);
        setY(getY() - 64);
        paintHammer.setY(getY());
        setWidth(64*3);
        paintHammer.setWidth(64*3);
        active = true;
    }
}
