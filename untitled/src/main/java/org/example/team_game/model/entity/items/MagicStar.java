package org.example.team_game.model.entity.items;

import org.example.team_game.controller.HandlerTeamGame;
import org.example.team_game.model.enums.Id;
import org.example.team_game.model.tile.Tile;
import org.example.team_game.send_object.paint_entity.gameItems.PaintMagicStar;

public class MagicStar extends Item{
    private PaintMagicStar paintMagicStar;
    public MagicStar(double x, double y, int width, int height, Id id, HandlerTeamGame handler , int section) {
        super(x, y, width, height, id, handler , section);
        setFalling(true);
        setGravity(0.1);
        paintMagicStar = new PaintMagicStar(x , y , width , height , id , section);
        getHandler().addPaintEntity(paintMagicStar);
    }
    @Override
    public void update() {
        long endTime = System.nanoTime()/1000000000L;
        if(endTime - getStartTime() >= 3) {
            if(getVelX() == 0) {
                setVelX(2);
            }
            setX(getX() + getVelX());
            paintMagicStar.setX(getX());
            setY(getY() + getVelY());
            paintMagicStar.setY(getY());
            hitTiles();
            if (isJumping()) {
                setGravity(getGravity() - 0.1);
                setVelY((int) -getGravity());
                if (getGravity() <= 0.5) {
                    setJumping(false);
                    setFalling(true);
                }
            }
            if (isFalling()) {
                setGravity(getGravity() + 0.1);
                setVelY((int) getGravity());
            }
        }
    }
    public void hitTiles(){
        for (int i = 0; i < getHandler().getTiles().size(); i++) {
            Tile tile = getHandler().getTiles().get(i);
            if (tile.getSection() == getSection()) {
                if (getBoundsTop().intersects(tile.getBounds())) {
                    setVelY(0);
                    if (isJumping()) {
                        setJumping(false);
                        setGravity(1.0);
                        setFalling(true);
                    }
                }
                if (getBoundsBottom().intersects(tile.getBounds())) {
                    setJumping(true);
                    setGravity(6.0);
                    setVelY(0);
                    if (isFalling()) {
                        setFalling(false);
                    }
                }
                if (getBoundsRight().intersects(tile.getBounds())) {
                    setVelX(-2);
                }
                if (getBoundsLeft().intersects(tile.getBounds())) {
                    setVelX(2);
                }
            }
        }
    }

    public PaintMagicStar getPaintMagicStar() {
        return paintMagicStar;
    }
}
