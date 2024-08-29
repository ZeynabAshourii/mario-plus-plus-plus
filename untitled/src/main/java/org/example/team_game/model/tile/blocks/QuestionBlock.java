package org.example.team_game.model.tile.blocks;

import org.example.team_game.controller.HandlerTeamGame;
import org.example.team_game.model.entity.items.*;
import org.example.team_game.model.enums.Id;
import org.example.team_game.send_object.paint_tile.blocks.PaintQuestionBlock;

public class QuestionBlock extends BlockGame{
    private boolean activated1 = false;
    private boolean activeScore = false;
    private double spriteY1 = getY();
    private boolean poppedUp = false;
    private Item item;
    private PaintQuestionBlock paintQuestionBlock;

    public QuestionBlock(double x, double y, int width, int height, Id id, HandlerTeamGame handler , Item item , int section) {
        super(x, y, width, height, id, handler, section);
        this.item = item;
        this.paintQuestionBlock = new PaintQuestionBlock(x , y , width , height , id , section , item.getId());
        getHandler().addPaintTile(paintQuestionBlock);
    }
    @Override
    public void update() {
        if (activated1 && !poppedUp) {
            spriteY1--;
            paintQuestionBlock.setSpriteY1(spriteY1);
            if(spriteY1  <= getY()-getHeight()){
                long startTime = System.nanoTime()/1000000000L;
                if(item.getId() == Id.COIN){
                    Coin coin = new Coin(getX() , spriteY1 , getWidth() , getHeight() , Id.COIN , getHandler() , getSection());
                    coin.setStartTime(startTime);
                    getHandler().addEntity(coin);
                } else if (item.getId() == Id.MAGIC_FLOWER) {
                    MagicFlower magicFlower = new MagicFlower(getX() , spriteY1 , getWidth() , getHeight() , Id.MAGIC_FLOWER , getHandler() , getSection());
                    magicFlower.setStartTime(startTime);
                    getHandler().addEntity(magicFlower);
                } else if (item.getId() == Id.MAGIC_MUSHROOM) {
                    MagicMushroom magicMushroom = new MagicMushroom(getX() , spriteY1 , getWidth() , getHeight() , Id.MAGIC_MUSHROOM , getHandler() , getSection());
                    magicMushroom.setStartTime(startTime);
                    getHandler().addEntity(magicMushroom);
                } else if (item.getId() == Id.MAGIC_STAR) {
                    MagicStar magicStar = new MagicStar(getX() , spriteY1 , getWidth() , getHeight() , Id.MAGIC_STAR , getHandler() , getSection());
                    magicStar.setStartTime(startTime);
                    getHandler().addEntity(magicStar);
                }
                poppedUp = true;
                paintQuestionBlock.setPoppedUp(true);
            }
        }
    }

    public boolean isPoppedUp() {
        return poppedUp;
    }

    public boolean isActivated1() {
        return activated1;
    }

    public boolean isActiveScore() {
        return activeScore;
    }

    public void setActiveScore(boolean activeScore) {
        this.activeScore = activeScore;
    }

    public void setActivated1(boolean activated1) {
        this.activated1 = activated1;
        paintQuestionBlock.setActivated1(activated1);
    }
}
