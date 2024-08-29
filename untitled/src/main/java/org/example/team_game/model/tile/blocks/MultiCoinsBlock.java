package org.example.team_game.model.tile.blocks;

import org.example.team_game.controller.HandlerTeamGame;
import org.example.team_game.model.enums.Id;
import org.example.team_game.send_object.paint_tile.blocks.PaintMultiCoinsBlock;

public class MultiCoinsBlock extends BlockGame{
    private int hit = 0;
    private boolean activated1 = false;
    private boolean activated2 = false;
    private boolean activeScore = false;
    private boolean empty = false;
    private PaintMultiCoinsBlock paintMultiCoinsBlock;
    public MultiCoinsBlock(double x, double y, int width, int height, Id id, HandlerTeamGame handler , int section) {
        super(x, y, width, height, id, handler , section);
        this.paintMultiCoinsBlock = new PaintMultiCoinsBlock(x , y , width , height , id , section);
        getHandler().addPaintTile(paintMultiCoinsBlock);
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
        paintMultiCoinsBlock.setEmpty(empty);
    }

    public int getHit() {
        return hit;
    }

    public void setHit(int hit) {
        this.hit = hit;
    }
}
