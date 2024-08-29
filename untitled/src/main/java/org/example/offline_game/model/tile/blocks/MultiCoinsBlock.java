package org.example.offline_game.model.tile.blocks;

import org.example.offline_game.controller.HandlerOfflineGame;
import org.example.offline_game.model.enums.Id;
import org.example.resources.Resources;
import java.awt.*;
public class MultiCoinsBlock extends BlockGame {
    private boolean activated1 = false;
    private boolean activated2 = false;
    private boolean activeScore = false;
    private int hit = 0;
    private boolean empty = false;
    public MultiCoinsBlock(int x, int y, int width, int height, Id id, HandlerOfflineGame handler) {
        super(x, y, width, height, id, handler);
    }
    @Override
    public void paint(Graphics g) {
        try {
            if(empty){
                g.drawImage(Resources.getEmptyBlock().getSheet(), getX(), getY(), getWidth(), getHeight(), null);
            }
            else {
                g.drawImage(Resources.getMultiCoinBlock().getSheet(), getX(), getY(), getWidth(), getHeight(), null);
            }
        }
        catch (Exception e){}
    }

    @Override
    public void update() {
        if(isTimer()) {
            if (isMarioOnBlock()) {
                setTime(System.nanoTime() / 1000000000L - getStartTime() + getSigmaTime() );
                setMarioOnBlock(false);
            } else if(isMarioOnEarth()) {
                setFirstTime(true);
                setTime(0);
            } else {
                setFirstTime(true);
            }
            if(getTime() > 2){
                die();
            }
        }
    }
    public boolean isActivated1() {
        return activated1;
    }

    public void setActivated1(boolean activated1) {
        this.activated1 = activated1;
    }

    public boolean isActivated2() {
        return activated2;
    }

    public void setActivated2(boolean activated2) {
        this.activated2 = activated2;
    }
    public boolean isActiveScore() {
        return activeScore;
    }

    public void setActiveScore(boolean activeScore) {
        this.activeScore = activeScore;
    }

    public boolean isEmpty() {
        return empty;
    }

    public void setEmpty(boolean empty) {
        this.empty = empty;
    }

    public int getHit() {
        return hit;
    }

    public void setHit(int hit) {
        this.hit = hit;
    }
}
