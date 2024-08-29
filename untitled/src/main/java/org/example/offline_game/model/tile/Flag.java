package org.example.offline_game.model.tile;

import org.example.offline_game.controller.HandlerOfflineGame;
import org.example.offline_game.model.enums.Id;
import java.awt.*;
public class Flag extends Tile{
    private int h;
    private boolean active = false;
    public Flag(int x, int y, int width, int height, Id id, HandlerOfflineGame handler) {
        super(x, y, width, height, id, handler);
        this.h = y;
    }

    @Override
    public void paint(Graphics g) {
        g.setColor(Color.red);
        g.fillRect(getX() , getY() , getWidth() , getHeight());
        g.setColor(Color.white);
        g.fillRect(getX()+getWidth()-16 , getY()+getHeight() , 16 , 144 - getY() + h );
    }

    @Override
    public void update() {
        setY(getY() + getVelY());
        if(isActive()){
            setVelY(1);
        }
        if(144 - getY() + h == 0){
            setVelY(0);
            getHandler().getGame().switchLevel();
        }
    }
    public boolean isActive() {
        return active;
    }
    public void setActive(boolean active) {
        this.active = active;
    }
}
