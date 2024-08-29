package org.example.offline_game.model.tile.blocks;


import org.example.offline_game.controller.HandlerOfflineGame;
import org.example.offline_game.model.entity.items.*;
import org.example.offline_game.model.enums.Id;
import org.example.resources.Resources;
import java.awt.*;
public class QuestionBlock extends BlockGame {
    private boolean activated1 = false;
    private boolean activeScore = false;
    private int spriteY1 = getY();
    private boolean poppedUp = false;
    private Item item;
    public QuestionBlock(int x, int y, int width, int height, Id id, HandlerOfflineGame handler , Item item) {
        super(x, y, width, height, id, handler);
        this.item = item;
    }
    @Override
    public void paint(Graphics g) {
        try {
            if (!poppedUp) {
                if(item.getId() == Id.coin){
                    g.drawImage(Resources.getCoinPic().getSheet() , getX() , spriteY1 , getWidth() , getHeight() , null);
                } else if (item.getId() == Id.magicFlower) {
                    g.drawImage(Resources.getFlowerPic().getSheet() , getX() , spriteY1 , getWidth() , getHeight() , null);
                } else if (item.getId() == Id.magicMushroom) {
                    g.drawImage(Resources.getMushroomPic().getSheet() , getX() , spriteY1 , getWidth() , getHeight() , null);
                } else if (item.getId() == Id.magicStar) {
                    g.drawImage(Resources.getStarPic().getSheet() , getX() , spriteY1 , getWidth() , getHeight() , null);
                }
            }
            if(activated1){
                g.drawImage(Resources.getEmptyBlock().getSheet(), getX(), getY(), getWidth(), getHeight(), null);
            }
            else {
                g.drawImage(Resources.getPowerUp().getSheet(), getX(), getY(), getWidth(), getHeight(), null);
            }
        }
        catch (Exception e){}
    }

    @Override
    public void update() {
        if (activated1 && !poppedUp) {
            spriteY1--;
            if(spriteY1  <= getY()-getHeight()){
                long startTime = System.nanoTime()/1000000000L;
                if(item.getId() == Id.coin){
                    Coin coin = new Coin(getX() , spriteY1 , getWidth() , getHeight() , Id.coin , getHandler());
                    coin.setStartTime(startTime);
                    getHandler().addEntity(coin);
                } else if (item.getId() == Id.magicFlower) {
                    MagicFlower magicFlower = new MagicFlower(getX() , spriteY1 , getWidth() , getHeight() , Id.magicFlower , getHandler());
                    magicFlower.setStartTime(startTime);
                    getHandler().addEntity(magicFlower);
                } else if (item.getId() == Id.magicMushroom) {
                    MagicMushroom magicMushroom = new MagicMushroom(getX() , spriteY1 , getWidth() , getHeight() , Id.magicMushroom , getHandler());
                    magicMushroom.setStartTime(startTime);
                    getHandler().addEntity(magicMushroom);
                } else if (item.getId() == Id.magicStar) {
                    MagicStar magicStar = new MagicStar(getX() , spriteY1 , getWidth() , getHeight() , Id.magicStar , getHandler());
                    magicStar.setStartTime(startTime);
                    getHandler().addEntity(magicStar);
                }
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
    public boolean isActiveScore() {
        return activeScore;
    }
    public void setActiveScore(boolean activeScore) {
        this.activeScore = activeScore;
    }
    public boolean isPoppedUp() {
        return poppedUp;
    }

}
