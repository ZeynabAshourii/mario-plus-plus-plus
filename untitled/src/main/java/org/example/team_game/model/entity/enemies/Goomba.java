package org.example.team_game.model.entity.enemies;

import org.example.team_game.controller.HandlerTeamGame;
import org.example.team_game.model.enums.Id;
import org.example.team_game.send_object.paint_entity.enemies.PaintGoomba;

import java.util.Random;

public class Goomba extends EnemyGame {
    private Random random = new Random();
    private PaintGoomba paintGoomba;

    public Goomba(double x, double y, int width, int height, Id id, HandlerTeamGame handler , int section) {
        super(x, y, width, height, id, handler , section);
        switch (random.nextInt(2)){
            case 0 :
                setVelX(2);
                setFacing(0);
                break;
            case 1 :
                setVelX(2);
                setFacing(1);
                break;
        }
        this.paintGoomba = new PaintGoomba(x , y , width , height , id , section);
        getHandler().addPaintEntity(paintGoomba);
    }
    @Override
    public void update() {
        setX(getX() + getVelX());
        paintGoomba.setX(getX());
        setY(getY() + getVelY());
        paintGoomba.setY(getY());

        antiFall();
        hitTile();
        hitItem();

        if(isFalling()){
            setGravity(getGravity()+0.1);
            setVelY((int) getGravity());
        }

    }

    public PaintGoomba getPaintGoomba() {
        return paintGoomba;
    }
}
