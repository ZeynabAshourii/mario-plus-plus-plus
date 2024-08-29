package org.example.team_game.send_object.paint_entity.enemies;

import org.example.resources.Resources;
import org.example.team_game.model.enums.Id;
import org.example.team_game.send_object.paint_entity.PaintEntity;

import java.awt.*;

public class PaintKoopa extends PaintEntity {
    public PaintKoopa(double x, double y, int width, int height, Id id, int section) {
        super(x, y, width, height, id, section);
    }

    @Override
    public void paint(Graphics g) {
        try {
            g.drawImage(Resources.getKoopaPic()[0].getSheet(), (int) getX(), (int) getY(), getWidth(), getHeight(), null);
        } catch (Exception e){}
    }
}
