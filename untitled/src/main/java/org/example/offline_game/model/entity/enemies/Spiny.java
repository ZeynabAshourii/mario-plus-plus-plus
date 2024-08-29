package org.example.offline_game.model.entity.enemies;


import org.example.offline_game.controller.HandlerOfflineGame;
import org.example.offline_game.model.entity.Entity;
import org.example.offline_game.model.enums.Id;
import org.example.offline_game.model.tile.Tile;
import org.example.resources.Resources;
import java.awt.*;
import java.util.Random;
public class Spiny extends EnemyGame {
    private double v;
    private Random random = new Random();
    public Spiny(int x, int y, int width, int height, Id id, HandlerOfflineGame handler) {
        super(x, y, width, height, id, handler);
        switch (random.nextInt(2)){
            case 0 :
                setVelX(-2);
                break;
            case 1 :
                setVelX(2);
                break;
        }
    }
    @Override
    public void paint(Graphics g) {
        try {
            g.drawImage(Resources.getSpiny().getSheet(), getX() , getY() , getWidth() , getHeight() , null);
        }
        catch (Exception e){}
    }
    @Override
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
        setY(getY() + getVelY());
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
            if(getBoundsBottom().intersects(tile.getBounds())) {
                setVelY(0);
                if (isFalling()) {
                    setFalling(false);
                }
            }else if (!isFalling()) {
                setFalling(true);
                setGravity(0.8);
            }
            if(getBoundsLeft().intersects(tile.getBounds())){
                v = -v;
            }
            if(getBoundsRight().intersects(tile.getBounds())){
                v = -v;
            }
        }
    }
    public int distance(){
        int distance = 1000;
        int xMario = 0;
        int yMario = 0;
        int heightMario = 0;
        for(int i = 0; i <getHandler().getEntities().size(); i++){
            Entity entity = getHandler().getEntities().get(i);
            if(entity.getId() == Id.player){
                xMario = entity.getX();
                yMario = entity.getY();
                heightMario = entity.getHeight();
            }
        }
        if((yMario + heightMario)/64 == (getY() + getHeight())/64){
            if(Math.abs(xMario - getX()) <= 64*4){
                distance = xMario - getX();
            }
        }
        return distance;
    }

}
