package org.example.offline_game.model.tile;


import org.example.offline_game.controller.HandlerOfflineGame;
import org.example.offline_game.model.enums.Id;
import org.example.resources.Resources;
import java.awt.*;
public class Gate extends Tile {
    private boolean active = false;
    private Gate gateCopy;
    public Gate(int x, int y, int width, int height, Id id, HandlerOfflineGame handler) {
        super(x, y, width, height, id, handler);
    }

    @Override
    public void paint(Graphics g) {
        try {
            g.drawImage(Resources.getGatePic().getSheet(), getX(), getY(), getWidth(), getHeight(), null);
        }
        catch (Exception e){}
    }

    @Override
    public void update() {
    }
    public boolean isActive() {
        return active;
    }
    public void setActive(boolean active) {
        this.active = active;
    }
    public Gate getGateCopy() {
        return gateCopy;
    }
    public void setGateCopy(Gate gateCopy) {
        this.gateCopy = gateCopy;
    }
}
