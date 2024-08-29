package org.example.offline_game.model.entity.enemies;


import org.example.offline_game.controller.HandlerOfflineGame;
import org.example.offline_game.model.enums.Id;
import org.example.resources.Resources;
import java.awt.*;
import java.util.Random;
public class Koopa extends EnemyGame {
    private int frame = 0;
    private int frameDelay = 0;
    private boolean hit = false;
    private long fistHitTime;
    private boolean run = true;
    public Koopa(int x, int y, int width, int height, Id id, HandlerOfflineGame handler) {
        super(x, y, width, height, id, handler);
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
            if (getFacing() == 0) {
                g.drawImage(Resources.getKoopaPic()[frame + 3].getSheet(), getX(), getY(), getWidth(), getHeight(), null);
            } else if (getFacing() == 1) {
                g.drawImage(Resources.getKoopaPic()[frame].getSheet(), getX(), getY(), getWidth(), getHeight(), null);
            }
        }
        catch (Exception e){}
    }

    @Override
    public void update() {
        if(run) {
            setX(getX() + getVelX());
            setY(getY() + getVelY());
            antiFall();
            hitTile();
            hitItem();
            if (isFalling()) {
                setGravity(getGravity() + 0.1);
                setVelY((int) getGravity());
            }
            manageFrame();
        }
        else {
            long now =  System.nanoTime()/1000000000L;
            if(now - fistHitTime >= 3){
                run = true;
            }
        }
    }
    public void manageFrame(){
        if (getVelX() != 0) {
            frameDelay++;
            if (frameDelay >= 2) {
                frame++;
                if (frame >= 3) {
                    frame = 0;
                }
                frameDelay = 0;
            }
        }
    }
    public boolean isHit() {
        return hit;
    }
    public void setHit(boolean hit) {
        this.hit = hit;
    }
    public void setFistHitTime(long fistHitTime) {
        this.fistHitTime = fistHitTime;
    }
    public void setRun(boolean run) {
        this.run = run;
    }
}
