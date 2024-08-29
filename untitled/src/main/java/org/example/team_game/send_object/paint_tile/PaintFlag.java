package org.example.team_game.send_object.paint_tile;


import org.example.team_game.model.enums.Id;

import java.awt.*;

public class PaintFlag extends PaintTile{
    private double h;
    public PaintFlag(double x, double y, int width, int height, Id id , int section) {
        super(x, y, width, height, id , section);
    }

    @Override
    public void paint(Graphics g) {
        g.setColor(Color.red);
        g.fillRect((int) getX(), (int) getY(), getWidth() , getHeight());
        g.setColor(Color.white);
        g.fillRect((int) (getX()+getWidth()-16), (int) (getY()+getHeight()), 16 , (int) (144 - getY() + h));
    }

    public double getH() {
        return h;
    }

    public void setH(double h) {
        this.h = h;
    }
}
