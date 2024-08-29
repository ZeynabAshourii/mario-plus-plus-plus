package org.example.offline_game.model.tile.pipes;


import org.example.offline_game.controller.HandlerOfflineGame;
import org.example.offline_game.model.entity.enemies.Plant;
import org.example.offline_game.model.enums.Id;

import java.awt.*;
public class PiranhaTelePipe extends PipeGame {
    private int facing;
    public PiranhaTelePipe(int x, int y, int width, int height, Id id, HandlerOfflineGame handler , int facing) {
        super(x, y, width, height, id, handler);
        this.facing = facing;
        handler.addEntity( new Plant(x , y - 64 , 64 , 64 , Id.plant , handler) );
    }
    @Override
    public void paint(Graphics g) {
        g.setColor(new Color(128 , 128 , 128));
        g.fillRect(getX() , getY() , getWidth() , getHeight());
    }
    @Override
    public void update() {

    }
    public int getFacing() {
        return facing;
    }

    public void setFacing(int facing) {
        this.facing = facing;
    }
}
