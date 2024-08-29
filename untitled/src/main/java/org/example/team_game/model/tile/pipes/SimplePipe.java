package org.example.team_game.model.tile.pipes;

import org.example.team_game.controller.HandlerTeamGame;
import org.example.team_game.model.enums.Id;

public class SimplePipe extends PipeGame{
    public SimplePipe(double x, double y, int width, int height, Id id, HandlerTeamGame handler , int section) {
        super(x, y, width, height, id, handler, section);
    }
}
