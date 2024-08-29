package org.example.team_game.model.entity.enemies;

import org.example.team_game.controller.HandlerTeamGame;
import org.example.team_game.model.entity.Entity;
import org.example.team_game.model.enums.Id;
import org.example.team_game.model.tile.Tile;
import org.example.team_game.send_object.paint_entity.enemies.PaintSpiny;

import java.util.Random;

public class Spiny extends EnemyGame{
    private double v;
    private Random random = new Random();
    private PaintSpiny paintSpiny;
    public Spiny(double x, double y, int width, int height, Id id, HandlerTeamGame handler , int section) {
        super(x, y, width, height, id, handler , section);
        switch (random.nextInt(2)){
            case 0 :
                setVelX(-2);
                break;
            case 1 :
                setVelX(2);
                break;
        }
        this.paintSpiny = new PaintSpiny(x , y , width , height , id , section);
        getHandler().addPaintEntity(paintSpiny);
    }
    public void update() {
        if(distance() != 1000){
            if(distance() < 0){
                v = v - 0.1;
            }else {
                System.out.println();
                v = v + 0.1;
            }
        }
        else{
            v = 0;
        }
        setX((int) (getX() + v));
        paintSpiny.setX(getX());
        setY(getY() + getVelY());
        paintSpiny.setY(getY());
        if(fall()){
            v = -v;
        }
        hitTiles();
        if(isFalling()){
            setGravity(getGravity()+0.1);
            setVelY((int) getGravity());
        }
        hitItem();
    }
    public void hitTiles(){
        for (int i = 0; i < getHandler().getTiles().size(); i++){
            Tile tile = getHandler().getTiles().get(i);
            if (tile.getSection() == getSection()) {
                if (getBoundsBottom().intersects(tile.getBounds())) {
                    setVelY(0);
                    if (isFalling()) {
                        setFalling(false);
                    }
                } else if (!isFalling()) {
                    setFalling(true);
                    setGravity(0.8);
                }
                if (getBoundsLeft().intersects(tile.getBounds())) {
                    v = -v;
                }
                if (getBoundsRight().intersects(tile.getBounds())) {
                    v = -v;
                }
            }
        }
    }
    public double distance(){
        double distance = 1000;
        double xMario = 0;
        double yMario = 0;
        int heightMario = 0;
        for(int i = 0; i <getHandler().getEntities().size(); i++){
            Entity entity = getHandler().getEntities().get(i);
            if (entity.getSection() == getSection()) {
                if (entity.getId() == Id.PLAYER) {
                    xMario = entity.getX();
                    yMario = entity.getY();
                    heightMario = entity.getHeight();
                }
            }
        }
        if((yMario + heightMario)/64 == (getY() + getHeight())/64){
            if(Math.abs(xMario - getX()) <= 64*4){
                distance = xMario - getX();
            }
        }
        return distance;
    }

    public PaintSpiny getPaintSpiny() {
        return paintSpiny;
    }
}
