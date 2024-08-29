package org.example.mario_survival.model.tiles;

import org.example.marathon_mario.model.enums.Id;
import org.example.send_object.tile.PaintEmptyBlock;
import org.example.mario_survival.controller.HandlerSurvival;
public class EmptyBlock extends Tile {
    private PaintEmptyBlock paintEmptyBlock;
    public EmptyBlock(double x, double y, int width, int height, Id id, HandlerSurvival handler) {
        super(x, y, width, height, id, handler);
        this.paintEmptyBlock = new PaintEmptyBlock(getX() , getY() , getWidth() , getHeight() , getId());
        handler.getPaintTiles().add(paintEmptyBlock);
    }

    @Override
    public void update() {

    }
}
