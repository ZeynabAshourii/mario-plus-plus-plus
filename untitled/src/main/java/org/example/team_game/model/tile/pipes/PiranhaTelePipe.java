package org.example.team_game.model.tile.pipes;

import org.example.team_game.controller.HandlerTeamGame;
import org.example.team_game.model.entity.enemies.Plant;
import org.example.team_game.model.enums.Id;

public class PiranhaTelePipe extends PipeGame{
    private int facing;
    public PiranhaTelePipe(double x, double y, int width, int height, Id id, HandlerTeamGame handler , int facing , int section) {
        super(x, y, width, height, id, handler , section);
        this.facing = facing;
        handler.addEntity( new Plant(x , y - 64 , 64 , 64 , Id.PLANT , handler , section) );
    }

    public int getFacing() {
        return facing;
    }
}
