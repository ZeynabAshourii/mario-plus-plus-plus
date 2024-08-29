package org.example.send_object.entities.items;

import org.example.marathon_mario.model.enums.Id;
import org.example.send_object.entities.PaintEntity;

import java.awt.*;

public class PaintSword extends PaintEntity {
    private int[] xPoints;
    private int[] yPoints;
    public PaintSword(double x, double y, int width, int height, Id id , int[] xPoints , int[] yPoints) {
        super(x, y, width, height, id);
        this.xPoints = xPoints;
        this.yPoints = yPoints;
    }

    @Override
    public void paint(Graphics g) {
        g.setColor(Color.GREEN);
        g.fillPolygon(new Polygon(xPoints , yPoints , xPoints.length));
    }

    public int[] getxPoints() {
        return xPoints;
    }

    public void setxPoints(int[] xPoints) {
        this.xPoints = xPoints;
    }

    public int[] getyPoints() {
        return yPoints;
    }

    public void setyPoints(int[] yPoints) {
        this.yPoints = yPoints;
    }
}
