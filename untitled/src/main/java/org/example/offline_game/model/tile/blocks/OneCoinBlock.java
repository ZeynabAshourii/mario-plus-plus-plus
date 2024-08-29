package org.example.offline_game.model.tile.blocks;


import org.example.offline_game.controller.HandlerOfflineGame;
import org.example.offline_game.model.entity.items.Coin;
import org.example.offline_game.model.enums.Id;
import org.example.resources.Resources;
import java.awt.*;
public class OneCoinBlock extends BlockGame {
    private boolean activated1 = false;
    private boolean activated2 = false;
    private boolean activeScore = false;
    private int spriteY1 = getY();
    private boolean poppedUp = false;
    public OneCoinBlock(int x, int y, int width, int height, Id id, HandlerOfflineGame handler) {
        super(x, y, width, height, id, handler);
    }
    @Override
    public void paint(Graphics g) {
        try {
            if (!poppedUp) {
                g.drawImage(Resources.getCoinPic().getSheet(), getX(), spriteY1, getWidth(), getHeight(), null);
            }
            if(isActivated1()){
                g.drawImage(Resources.getWallPic().getSheet(), getX(), getY(), getWidth(), getHeight(), null);
            }
            else {
                g.drawImage(Resources.getOneCoinBlock().getSheet(), getX(), getY(), getWidth(), getHeight(), null);
            }
        }
        catch (Exception e){}
    }

    @Override
    public void update() {
        if (activated2) {
            die();
        }
        else if (activated1 && !poppedUp) {
            spriteY1--;
            if(spriteY1  <= getY()-getHeight()){
                getHandler().addEntity(new Coin( getX() , spriteY1 , getWidth() , getHeight() , Id.coin , getHandler()));
                poppedUp = true;
            }
        }
        timeCalculation();
    }
    public void timeCalculation(){
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
}
