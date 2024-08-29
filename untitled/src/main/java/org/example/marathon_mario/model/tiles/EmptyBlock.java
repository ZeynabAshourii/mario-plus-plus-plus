package org.example.marathon_mario.model.tiles;

import org.example.marathon_mario.controller.HandlerMarathon;
import org.example.marathon_mario.model.enums.Id;
import org.example.send_object.tile.PaintEmptyBlock;

public class EmptyBlock extends Tile {
    private PaintEmptyBlock paintEmptyBlock;
    public EmptyBlock(double x, double y, int width, int height, Id id, HandlerMarathon handler) {
        super(x, y, width, height, id, handler);
        this.paintEmptyBlock = new PaintEmptyBlock(getX() , getY() , getWidth() , getHeight() , getId());
        handler.getPaintTiles().add(paintEmptyBlock);
    }

    @Override
    public void update() {

    }
}
