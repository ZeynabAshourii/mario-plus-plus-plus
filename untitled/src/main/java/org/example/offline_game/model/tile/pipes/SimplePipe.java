package org.example.offline_game.model.tile.pipes;


import org.example.offline_game.controller.HandlerOfflineGame;
import org.example.offline_game.model.enums.Id;

import java.awt.*;
public class SimplePipe extends PipeGame {
    public SimplePipe(int x, int y, int width, int height, Id id, HandlerOfflineGame handler) {
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
