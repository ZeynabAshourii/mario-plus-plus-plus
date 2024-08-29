package org.example.team_game.model.entity.items;

import org.example.team_game.controller.HandlerTeamGame;
import org.example.team_game.model.enums.Id;
import org.example.team_game.model.tile.Tile;
import org.example.team_game.send_object.paint_entity.gameItems.PaintMagicMushroom;

public class MagicMushroom extends Item{
    private PaintMagicMushroom paintMagicMushroom;
    public MagicMushroom(double x, double y, int width, int height, Id id, HandlerTeamGame handler , int section) {
        super(x, y, width, height, id, handler , section);
        paintMagicMushroom = new PaintMagicMushroom(x , y , width , height , id , section);
        getHandler().addPaintEntity(paintMagicMushroom);
    }
    @Override
    public void update() {
        long endTime = System.nanoTime()/1000000000L;
        if(endTime - getStartTime() >= 3){
            setVelX(2);
        }
        setX(getX() + getVelX());
        paintMagicMushroom.setX(getX());
        setY(getY() + getVelY());
        paintMagicMushroom.setY(getY());
        for (int i = 0; i < getHandler().getTiles().size(); i++) {
            Tile tile = getHandler().getTiles().get(i);
            if (tile.getSection() == getSection()) {
                if (getBoundsBottom().intersects(tile.getBounds())) {
                    setVelY(0);
                    if (isFalling()) {
                        setFalling(false);
                    }
                } else if (!isFalling()) {
                    setGravity(0.8);
                    setFalling(true);
                }
                if (getBoundsRight().intersects(tile.getBounds())) {
                    setVelX(-2);
                }
                if (getBoundsLeft().intersects(tile.getBounds())) {
                    setVelX(2);
                }
            }
        }
        if (isFalling()) {
            setGravity(getGravity()+0.1);
            setVelY((int) getGravity());
        }
    }

    public PaintMagicMushroom getPaintMagicMushroom() {
        return paintMagicMushroom;
    }
}
