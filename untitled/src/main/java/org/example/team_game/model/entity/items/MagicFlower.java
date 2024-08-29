package org.example.team_game.model.entity.items;

import org.example.team_game.controller.HandlerTeamGame;
import org.example.team_game.model.enums.Id;
import org.example.team_game.send_object.paint_entity.gameItems.PaintMagicFlower;

public class MagicFlower extends Item{
    private PaintMagicFlower paintMagicFlower;
    public MagicFlower(double x, double y, int width, int height, Id id, HandlerTeamGame handler , int section) {
        super(x, y, width, height, id, handler , section);
        paintMagicFlower = new PaintMagicFlower(x , y , width , height , id , section);
        getHandler().addPaintEntity(paintMagicFlower);
    }

    public PaintMagicFlower getPaintMagicFlower() {
        return paintMagicFlower;
    }

}
