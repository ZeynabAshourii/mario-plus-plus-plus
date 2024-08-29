package org.example.offline_game.model.entity.items;


import org.example.offline_game.controller.HandlerOfflineGame;
import org.example.offline_game.model.entity.Entity;
import org.example.offline_game.model.enums.Id;

import java.awt.*;
public class Item extends Entity {
    private long startTime;
    public Item(int x, int y, int width, int height, Id id, HandlerOfflineGame handler) {
        super(x, y, width, height, id, handler);
    }
    @Override
    public void paint(Graphics g) {

    }
    @Override
    public void update() {

    }
    public long getStartTime() {
        return startTime;
    }
    public void setStartTime(long startTime) {
        this.startTime = startTime;
    }
}
