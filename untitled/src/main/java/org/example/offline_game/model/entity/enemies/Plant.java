package org.example.offline_game.model.entity.enemies;

import org.example.offline_game.controller.HandlerOfflineGame;
import org.example.offline_game.model.enums.Id;

import java.awt.*;
public class Plant extends EnemyGame {
    private int pixelsTravelled = 0;
    private boolean moving;
    private boolean insidePipe;
    private long startTime;
    public Plant(int x, int y, int width, int height, Id id, HandlerOfflineGame handler) {
        super(x, y, width, height, id, handler);
        moving = false;
        insidePipe = true;
        startTime = System.nanoTime()/1000000000L + 1;
    }
    @Override
    public void paint(Graphics g) {
        g.setColor(Color.green);
        g.fillRect(getX() , getY() , getWidth() , getHeight());
    }

    @Override
    public void update() {
        setY(getY() + getVelY());
        long now =  System.nanoTime()/1000000000L;
        if(now - startTime >= 3){
            insidePipe = !insidePipe;
            moving = true;
            startTime = System.nanoTime()/1000000000L;
        }
        if(moving){
            if(insidePipe) setVelY(-3);
            else setVelY(3);

            pixelsTravelled += getVelY();

            if(pixelsTravelled >= getHeight() || pixelsTravelled <= -getHeight()){
                pixelsTravelled = 0;
                moving = false;
                setVelY(0);
            }
        }
    }
}
