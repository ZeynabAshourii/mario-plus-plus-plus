package org.example.team_game.model.entity.items;

import org.example.team_game.controller.HandlerTeamGame;
import org.example.team_game.model.entity.Entity;
import org.example.team_game.model.enums.Id;

public class Item extends Entity {
    private long startTime;

    public Item(double x, double y, int width, int height, Id id, HandlerTeamGame handler , int section) {
        super(x, y, width, height, id, handler , section);
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
