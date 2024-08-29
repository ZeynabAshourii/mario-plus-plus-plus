package org.example.team_game.send_object.paint_tile;

import org.example.team_game.model.enums.Id;

import java.awt.*;

public class PaintPipe extends PaintTile{
    public PaintPipe(double x, double y, int width, int height, Id id, int section) {
        super(x, y, width, height, id, section);
    }

    @Override
    public void paint(Graphics g) {
        g.setColor(new Color(128 , 128 , 128));
        g.fillRect((int) getX(), (int) getY(), getWidth() , getHeight());
    }
}
