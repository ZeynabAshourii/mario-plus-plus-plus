package org.example.team_game.model.entity.enemies;

import org.example.team_game.controller.HandlerTeamGame;
import org.example.team_game.model.entity.Entity;
import org.example.team_game.model.entity.items.Item;
import org.example.team_game.model.enums.Id;
import org.example.team_game.model.tile.Tile;

import java.awt.*;

public class EnemyGame extends Entity {
    public EnemyGame(double x, double y, int width, int height, Id id, HandlerTeamGame handler , int section) {
        super(x, y, width, height, id, handler , section);
    }

    @Override
    public void update() {

    }
    public Rectangle rightRectangle(){
        Rectangle rectangle = new Rectangle((int) (getX()+64), (int) (getY()+32), 64 , 64);
        return rectangle;
    }
    public Rectangle leftRectangle(){
        Rectangle rectangle = new Rectangle((int) (getX() - 64), (int) (getY() + 32), 64 , 64);
        return rectangle;
    }
    public boolean fall(){
        for (int i = 0; i < getHandler().getTiles().size(); i++) {
            Tile tile = getHandler().getTiles().get(i);
            if (tile.getSection() == getSection()) {
                if (getFacing() == 0) {
                    if (tile.getBounds().intersects(leftRectangle())) {
                        return false;
                    }
                } else {
                    if (tile.getBounds().intersects(rightRectangle())) {
                        return false;
                    }
                }
            }
        }
        return true;
    }
    public void antiFall(){
        if(fall()){
            if(getVelX() == 2){
                setX(getX() - 2);
                if (getId() == Id.GOOMBA){
                    ((Goomba)this).getPaintGoomba().setX(getX());
                } else if (getId() == Id.KOOPA) {
                    ((Koopa)this).getPaintKoopa().setX(getX());
                }
                setVelX(-2);
                setFacing(0);
            }
            else{
                setX(getX() + 2);
                if (getId() == Id.GOOMBA){
                    ((Goomba)this).getPaintGoomba().setX(getX());
                } else if (getId() == Id.KOOPA) {
                    ((Koopa)this).getPaintKoopa().setX(getX());
                }
                setVelX(2);
                setFacing(1);
            }
        }
    }
    public void hitTile(){
        for (int i = 0; i < getHandler().getTiles().size(); i++){
            Tile tile = getHandler().getTiles().get(i);
            if (tile.getSection() == getSection()) {
                if (getBoundsBottom().intersects(tile.getBounds())) {
                    setVelY(0);
                    if (isFalling()) {
                        setFalling(false);
                    }
                } else if (!isFalling()) {
                    setFalling(true);
                    setGravity(0.8);
                }
                if (getBoundsLeft().intersects(tile.getBounds())) {
                    setVelX(2);
                    setFacing(1);
                }
                if (getBoundsRight().intersects(tile.getBounds())) {
                    setVelX(-2);
                    setFacing(0);
                }
            }
        }
    }
    public void hitItem(){
        for(int i = 0; i < getHandler().getEntities().size(); i++){
            if (getHandler().getEntities().get(i).getSection() == getSection()) {
                if (getHandler().getEntities().get(i) instanceof Item) {
                    Item item = (Item) getHandler().getEntities().get(i);
                    if (getBoundsLeft().intersects(item.getBounds())) {
                        setVelX(2);
                        setFacing(1);
                    }
                    if (getBoundsRight().intersects(item.getBounds())) {
                        setVelX(-2);
                        setFacing(0);
                    }
                }
            }
        }
    }
}
