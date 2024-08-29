package org.example.team_game.model.tile.blocks;

import org.example.team_game.controller.HandlerTeamGame;
import org.example.team_game.model.enums.Id;
import org.example.team_game.send_object.paint_tile.blocks.PaintSimpleBlock;

public class SimpleBlock extends BlockGame{
    private PaintSimpleBlock paintSimpleBlock;
    public SimpleBlock(double x, double y, int width, int height, Id id, HandlerTeamGame handler , int section) {
        super(x, y, width, height, id, handler , section);
        this.paintSimpleBlock = new PaintSimpleBlock(x , y , width , height , id , section);
        getHandler().addPaintTile(paintSimpleBlock);
    }

    public PaintSimpleBlock getPaintSimpleBlock() {
        return paintSimpleBlock;
    }
}
