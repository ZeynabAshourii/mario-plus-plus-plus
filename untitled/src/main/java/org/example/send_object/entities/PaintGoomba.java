package org.example.send_object.entities;

import org.example.marathon_mario.model.enums.Id;
import org.example.resources.Resources;

import java.awt.*;

public class PaintGoomba extends PaintEntity {
    public PaintGoomba(double x, double y, int width, int height, Id id) {
        super(x, y, width, height, id);
    }

    @Override
    public void paint(Graphics g) {
        g.drawImage(Resources.getGoomba().getSheet(), (int) getX(), (int) getY(), getWidth() , getHeight() , null);
    }
}
