package org.example.offline_game.model.tile.pipes;

import org.example.offline_game.controller.HandlerOfflineGame;
import org.example.offline_game.model.enums.Id;
import org.example.offline_game.model.tile.Tile;
import org.example.team_game.send_object.paint_tile.PaintPipe;

import java.awt.*;
public class PipeGame extends Tile {
    public PipeGame(int x, int y, int width, int height, Id id, HandlerOfflineGame handler) {
        super(x, y, width, height, id, handler);
    }
    @Override
    public void paint(Graphics g) {
     g.setColor(new Color(128 , 128 , 128));
     g.fillRect(getX() , getY() , getWidth() , getHeight());
    }
    @Override
    public void update() {

    }

}
