package org.example.team_game.model.tile.pipes;

import org.example.team_game.controller.HandlerTeamGame;
import org.example.team_game.model.enums.Id;
import org.example.team_game.model.tile.Tile;
import org.example.team_game.send_object.paint_tile.PaintPipe;

public class PipeGame extends Tile {
    private PaintPipe paintPipe;
    public PipeGame(double x, double y, int width, int height, Id id, HandlerTeamGame handler , int section) {
        super(x, y, width, height, id, handler , section);
        this.paintPipe = new PaintPipe( x, y , width , height , id , section);
        getHandler().addPaintTile(paintPipe);
    }

    @Override
    public void update() {

    }
}
