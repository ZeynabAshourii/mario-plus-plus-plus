package org.example.team_game.model.entity.enemies;

import org.example.team_game.controller.HandlerTeamGame;
import org.example.team_game.model.enums.Id;
import org.example.team_game.send_object.paint_entity.enemies.PaintKoopa;

import java.util.Random;

public class Koopa extends EnemyGame{
    private Random random = new Random();
    private boolean hit = false;
    private long fistHitTime;
    private boolean run = true;
    private PaintKoopa paintKoopa;
    public Koopa(double x, double y, int width, int height, Id id, HandlerTeamGame handler , int section) {
        super(x, y, width, height, id, handler, section);
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
        this.paintKoopa = new PaintKoopa( x, y , width , height , id , section);
        getHandler().addPaintEntity(paintKoopa);
    }
    @Override
    public void update() {
        if(run) {
            setX(getX() + getVelX());
            paintKoopa.setX(getX());
            setY(getY() + getVelY());
            paintKoopa.setY(getY());
            antiFall();
            hitTile();
            hitItem();
            if (isFalling()) {
                setGravity(getGravity() + 0.1);
                setVelY((int) getGravity());
            }
        }
        else {
            long now =  System.nanoTime()/1000000000L;
            if(now - fistHitTime >= 3){
                run = true;
            }
        }
    }

    public boolean isHit() {
        return hit;
    }

    public void setHit(boolean hit) {
        this.hit = hit;
    }

    public void setRun(boolean run) {
        this.run = run;
    }

    public void setFistHitTime(long fistHitTime) {
        this.fistHitTime = fistHitTime;
    }

    public PaintKoopa getPaintKoopa() {
        return paintKoopa;
    }
}
