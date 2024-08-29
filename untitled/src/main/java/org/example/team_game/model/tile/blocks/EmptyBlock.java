package org.example.team_game.model.tile.blocks;

import org.example.team_game.controller.HandlerTeamGame;
import org.example.team_game.model.enums.Id;
import org.example.team_game.send_object.paint_tile.blocks.PaintEmptyBlock;

public class EmptyBlock extends BlockGame{
    private PaintEmptyBlock paintEmptyBlock;
    public EmptyBlock(double x, double y, int width, int height, Id id, HandlerTeamGame handler , int section) {
        super(x, y, width, height, id, handler , section);
        paintEmptyBlock = new PaintEmptyBlock(x , y , width , height , id , section);
        getHandler().addPaintTile(paintEmptyBlock);
    }
}
