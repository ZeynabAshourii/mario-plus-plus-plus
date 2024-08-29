package org.example.team_game.model.entity.items;

import org.example.team_game.controller.HandlerTeamGame;
import org.example.team_game.model.enums.Id;
import org.example.team_game.model.tile.Tile;
import org.example.team_game.send_object.paint_entity.gameItems.PaintCoin;

public class Coin extends Item{
    private PaintCoin paintCoin;
    public Coin(double x, double y, int width, int height, Id id, HandlerTeamGame handler , int section) {
        super(x, y, width, height, id, handler,section);
        this.paintCoin = new PaintCoin(x , y , width , height , id , section);
        getHandler().addPaintEntity(paintCoin);
    }
    @Override
    public void update() {
        setY(getY() + getVelY());
        paintCoin.setY(getY());
        for (int i = 0; i < getHandler().getTiles().size(); i++){
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
            }
        }
        if(isFalling()){
            setGravity(getGravity()+0.1);
            setVelY((int)getGravity());
        }
    }

    public PaintCoin getPaintCoin() {
        return paintCoin;
    }
}
