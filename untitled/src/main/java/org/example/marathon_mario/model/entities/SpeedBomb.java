package org.example.marathon_mario.model.entities;

import org.example.marathon_mario.controller.HandlerMarathon;
import org.example.marathon_mario.model.enums.Id;
import org.example.marathon_mario.model.tiles.Tile;
import org.example.send_object.entities.items.PaintSpeedBomb;
public class SpeedBomb extends Entity{
    private boolean active = false;
    private String ownUsername;
    private PaintSpeedBomb speedBomb;
    public SpeedBomb(double x, double y, int width, int height, Id id, HandlerMarathon handler , String ownUsername) {
        super(x, y, width, height, id, handler);
        this.ownUsername = ownUsername;
        setVelX(5);
        this.speedBomb = new PaintSpeedBomb(x, y, width, height, id);
        getHandler().getPaintEntities().add(speedBomb);
    }

    @Override
    public void update() {
        setX(getX() + getVelX());
        speedBomb.setX(getX());
        if(active){
            getHandler().getEntities().remove(this);
            getHandler().getPaintEntities().remove(speedBomb);
        }
        hitTiles();
        hitEntities();
        if(getX() >= 64*(getHandler().getLength()-10)){
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
    public void hurtEnemyMarios(){
        for(int i = 0; i < getHandler().getEntities().size(); i++){
            if(getHandler().getEntities().get(i).getId().equals(Id.MARIO)){
                Mario mario = (Mario) getHandler().getEntities().get(i);
                if (!mario.getPaintMario().getUsername().equals(ownUsername)) {
                    if (distance(mario) <= getHandler().getSpeedBombBlockArea()*64) {
                        mario.setVelX(mario.getVelX()*getHandler().getSpeedBombMultiplier());
                    }
                }
            }
        }
    }
    public double distance(Mario mario){
        double xMario = mario.getX() + mario.getWidth()/2;
        double yMario = mario.getY() + mario.getHeight()/2;
        double xBomb = getX() + getWidth()/2;
        double yBomb = getY() + getHeight()/2;
        return Math.sqrt((xMario - xBomb) * (xMario - xBomb) + (yMario - yBomb) * (yMario - yBomb));
    }
    public void hitEntities(){
        for(int i = 0; i < getHandler().getEntities().size(); i++){
            if (getBounds().intersects(getHandler().getEntities().get(i).getBounds())) {
                if (getHandler().getEntities().get(i).getId().equals(Id.MARIO)) {
                    Mario mario = (Mario) getHandler().getEntities().get(i);
                    if (!mario.getPaintMario().getUsername().equals(ownUsername)) {
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
        speedBomb.setX(getX());
        setHeight(getHeight()*3);
        speedBomb.setHeight(getHeight());
        setY(getY() - 64);
        speedBomb.setY(getY());
        setWidth(getWidth()*3);
        speedBomb.setWidth(getWidth());
        hurtEnemyMarios();
        active = true;
    }
}
