package org.example.offline_game.model.entity.enemies;


import org.example.offline_game.controller.HandlerOfflineGame;
import org.example.offline_game.model.enums.Id;
import org.example.resources.Resources;
import java.awt.*;
import java.util.Random;
public class Goomba extends EnemyGame {
    public Goomba(int x, int y, int width, int height, Id id, HandlerOfflineGame handler) {
        super(x, y, width, height , id, handler);
        switch (new Random().nextInt(2)){
            case 0 :
                setVelX(-2);
                setFacing(0);
                break;
            case 1 :
                setVelX(2);
                setFacing(1);
                break;
        }
    }

    @Override
    public void paint(Graphics g) {
        try {
            g.drawImage(Resources.getGoomba().getSheet(), getX() , getY() , getWidth() , getHeight() , null);
        }
        catch (Exception e){}
    }

    @Override
    public void update() {
        setX(getX() + getVelX());
        setY(getY() + getVelY());

        antiFall();
        hitTile();
        hitItem();

        if(isFalling()){
            setGravity(getGravity()+0.1);
            setVelY((int) getGravity());
        }

    }
}
