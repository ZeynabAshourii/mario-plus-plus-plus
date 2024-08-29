package org.example.team_game.model.tile.pipes;

import org.example.team_game.controller.HandlerTeamGame;
import org.example.team_game.model.entity.enemies.Plant;
import org.example.team_game.model.enums.Id;

public class PiranhaTrapPipe extends PipeGame{
    public PiranhaTrapPipe(double x, double y, int width, int height, Id id, HandlerTeamGame handler , int section) {
        super(x, y, width, height, id, handler, section);
        handler.addEntity( new Plant(x , y - 64 , 64 , 64 , Id.PLANT , handler, section) );
    }
}
