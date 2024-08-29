package org.example.team_game.send_object.paint_entity.enemies;

import org.example.team_game.model.enums.Id;
import org.example.team_game.send_object.paint_entity.PaintEntity;

import java.awt.*;

public class PaintPlant extends PaintEntity {
    public PaintPlant(double x, double y, int width, int height, Id id, int section) {
        super(x, y, width, height, id, section);
    }

    @Override
    public void paint(Graphics g) {
        g.setColor(Color.green);
        g.fillRect((int) getX(), (int) getY(), getWidth() , getHeight());
    }
}
