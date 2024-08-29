package org.example.send_object.entities.items;

import org.example.marathon_mario.model.enums.Id;
import org.example.send_object.entities.PaintEntity;

import java.awt.*;

public class PaintExplosiveBomb extends PaintEntity {
    public PaintExplosiveBomb(double x, double y, int width, int height, Id id) {
        super(x, y, width, height, id);
    }

    @Override
    public void paint(Graphics g) {
        g.setColor(Color.CYAN);
        g.fillRect((int) getX(), (int) getY(), getWidth() , getHeight());
    }
}
