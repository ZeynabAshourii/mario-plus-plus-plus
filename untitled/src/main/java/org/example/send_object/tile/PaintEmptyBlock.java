package org.example.send_object.tile;

import org.example.marathon_mario.model.enums.Id;
import org.example.resources.Resources;

import java.awt.*;

public class PaintEmptyBlock extends PaintTile{
    public PaintEmptyBlock(double x, double y, int width, int height, Id id) {
        super(x, y, width, height, id);
    }

    @Override
    public void paint(Graphics g) {
        try {
            g.drawImage(Resources.getEmptyBlock().getSheet() , (int) getX(), (int) getY(), getWidth() , getHeight() , null);
        }
        catch (Exception e){}
    }
}
