package org.example.team_game.model.tile.pipes;

import org.example.team_game.controller.HandlerTeamGame;
import org.example.team_game.model.enums.Id;

public class TeleSimplePipe extends PipeGame{
    private int facing;
    public TeleSimplePipe(double x, double y, int width, int height, Id id, HandlerTeamGame handler , int facing , int section) {
        super(x, y, width, height, id, handler , section);
        this.facing = facing;
    }

    public int getFacing() {
        return facing;
    }
}
