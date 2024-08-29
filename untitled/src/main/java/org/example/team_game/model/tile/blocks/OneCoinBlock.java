package org.example.team_game.model.tile.blocks;

import org.example.team_game.controller.HandlerTeamGame;
import org.example.team_game.model.entity.items.Coin;
import org.example.team_game.model.enums.Id;
import org.example.team_game.send_object.paint_tile.blocks.PaintOneCoinBlock;

public class OneCoinBlock extends BlockGame{
    private boolean activated1 = false;
    private boolean activated2 = false;
    private boolean activeScore = false;
    private double spriteY1 = getY();
    private boolean poppedUp = false;
    private PaintOneCoinBlock paintOneCoinBlock;
    public OneCoinBlock(double x, double y, int width, int height, Id id, HandlerTeamGame handler , int section ) {
        super(x, y, width, height, id, handler , section);
        this.paintOneCoinBlock = new PaintOneCoinBlock(x , y , width , height , id , section);
        getHandler().addPaintTile(paintOneCoinBlock);
    }
    @Override
    public void update() {
        if (activated2) {
            die();
            getHandler().removePaintTiles(paintOneCoinBlock);
        }
        else if (activated1 && !poppedUp) {
            spriteY1--;
            paintOneCoinBlock.setSpriteY1(spriteY1);
            if(spriteY1  <= getY()-getHeight()){
                getHandler().addEntity(new Coin( getX() , spriteY1 , getWidth() , getHeight() , Id.COIN , getHandler() , getSection()));
                poppedUp = true;
                paintOneCoinBlock.setPoppedUp(true);
            }
        }
    }
    public boolean isActivated1() {
        return activated1;
    }

    public void setActivated1(boolean activated1) {
        this.activated1 = activated1;
        paintOneCoinBlock.setActivated1(activated1);
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
