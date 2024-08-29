package org.example.mario_survival.model.entities;

import org.example.marathon_mario.model.enums.Id;
import org.example.send_object.entities.items.PaintExplosiveBomb;
import org.example.mario_survival.controller.HandlerSurvival;
import org.example.mario_survival.model.tiles.Tile;

public class ExplosiveBomb extends Entity {
    private boolean active = false;
    private Mario mario;
    private PaintExplosiveBomb explosiveBomb;
    public ExplosiveBomb(double x, double y, int width, int height, Id id, HandlerSurvival handler , Mario mario) {
        super(x, y, width, height, id, handler);
        this.mario = mario;
        setVelY(3.5);
        this.explosiveBomb = new PaintExplosiveBomb(x, y, width, height, id);
        getHandler().getPaintEntities().add(explosiveBomb);
    }

    @Override
    public void update() {
        setY(getY() + getVelY());
        explosiveBomb.setY(getY());
        if(active){
            getHandler().getEntities().remove(this);
            getHandler().getPaintEntities().remove(explosiveBomb);
        }
        hitTiles();
        hitEntities();
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
                if (!mario.getPaintMario().getUsername().equals(mario.getPaintMario().getUsername())) {
                    if (distance(mario) <= getHandler().getDamageBombBlockArea()*64) {
                        if (mario.getPaintMario().getLife() > getHandler().getDamageBombPercent()) {
                            this.mario.getPlayer().getDamageDealt().add(getHandler().getDamageBombPercent());
                            mario.getPaintMario().setLife(mario.getPaintMario().getLife() - getHandler().getDamageBombPercent());
                        } else {
                            this.mario.getPlayer().getDamageDealt().add(mario.getPaintMario().getLife());
                            mario.getPaintMario().setLife(0);
                            mario.die();
                        }
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
                    if (!mario.getPaintMario().getUsername().equals(mario.getPaintMario().getUsername())) {
                        if (!active){
                            hit();
                        }
                    }
                }
            }
        }
    }
    public void hit(){
        setVelY(0);
        setX(getX() - 64);
        explosiveBomb.setX(getX());
        setHeight(getHeight()*3);
        explosiveBomb.setHeight(getHeight());
        setY(getY() - 64);
        explosiveBomb.setY(getY());
        setWidth(getWidth()*3);
        explosiveBomb.setWidth(getWidth());
        hurtEnemyMarios();
        active = true;
    }
}
