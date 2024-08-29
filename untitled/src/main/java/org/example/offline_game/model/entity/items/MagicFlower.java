package org.example.offline_game.model.entity.items;

import org.example.offline_game.controller.HandlerOfflineGame;
import org.example.offline_game.model.enums.Id;
import org.example.resources.Resources;
import java.awt.*;
public class MagicFlower extends Item{
    public MagicFlower(int x, int y, int width, int height, Id id, HandlerOfflineGame handler) {
        super(x, y, width, height, id, handler);
    }
    @Override
    public void paint(Graphics g) {
        try {
            g.drawImage(Resources.getFlowerPic().getSheet(), getX(), getY(), getWidth(), getHeight(), null);
        }
        catch (Exception e){}
    }

    @Override
    public void update() {
    }
}
