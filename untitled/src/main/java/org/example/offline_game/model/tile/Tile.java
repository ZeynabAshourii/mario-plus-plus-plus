package org.example.offline_game.model.tile;

import org.example.offline_game.controller.HandlerOfflineGame;
import org.example.offline_game.model.enums.Id;

import java.awt.*;
import java.io.Serializable;
public abstract class Tile implements Cloneable , Serializable {
    private int x;
    private int y;
    private int width;
    private int height;
    private int velX;
    private int velY;
    private Id id;
    private HandlerOfflineGame handler;

    public Tile(int x , int y, int width , int height , Id id , HandlerOfflineGame handler){
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.id = id;
        this.handler = handler;
    }
    public abstract void paint(Graphics g) ;
    public abstract void update();
    public void die(){
        handler.removeTile(this);
        if(this instanceof Gate){
            if(getHandler().getCheckPoint().getTiles().remove(((Gate) this).getGateCopy())){
            }
        }
    }
    public Rectangle getBounds(){
        return  new Rectangle(getX() , getY() , getWidth() , getHeight());
    }
    public int getX() {
        return x;
    }
    public int getY() {
        return y;
    }
    public int getWidth() {
        return width;
    }
    public int getHeight() {
        return height;
    }
    public Id getId() {
        return id;
    }
    public HandlerOfflineGame getHandler() {
        return handler;
    }
    public int getVelX() {
        return velX;
    }
    public int getVelY() {
        return velY;
    }
    public void setX(int x) {
        this.x = x;
    }
    public void setY(int y) {
        this.y = y;
    }
    public void setWidth(int width) {
        this.width = width;
    }
    public void setHeight(int height) {
        this.height = height;
    }
    public void setId(Id id) {
        this.id = id;
    }
    public void setHandler(HandlerOfflineGame handler) {
        this.handler = handler;
    }
    public void setVelX(int velX) {
        this.velX = velX;
    }
    public void setVelY(int velY) {
        this.velY = velY;
    }

    @Override
    public Tile clone() {
        try {
            Tile clone = (Tile) super.clone();
            // TODO: copy mutable state here, so the clone can't change the internals of the original
            return clone;
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }
}
