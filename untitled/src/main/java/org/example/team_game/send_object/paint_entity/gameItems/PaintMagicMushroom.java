package org.example.team_game.send_object.paint_entity.gameItems;

import org.example.resources.Resources;
import org.example.team_game.model.enums.Id;
import org.example.team_game.send_object.paint_entity.PaintEntity;

import java.awt.*;

public class PaintMagicMushroom extends PaintEntity {
    public PaintMagicMushroom(double x, double y, int width, int height, Id id, int section) {
        super(x, y, width, height, id, section);
    }

    @Override
    public void paint(Graphics g) {
        try {
            g.drawImage(Resources.getMushroomPic().getSheet(), (int) getX(), (int) getY(), getWidth(), getHeight(), null);
        }
        catch (Exception e){}
    }
}
