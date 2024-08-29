package org.example.team_game.model.entity;

import org.example.team_game.controller.HandlerTeamGame;
import org.example.team_game.model.entity.enemies.Goomba;
import org.example.team_game.model.entity.enemies.Koopa;
import org.example.team_game.model.entity.enemies.Plant;
import org.example.team_game.model.entity.enemies.Spiny;
import org.example.team_game.model.enums.Id;
import org.example.team_game.model.tile.Tile;
import org.example.team_game.send_object.paint_entity.PaintFireBall;

public class FireBall extends Entity{
    private double firstX;
    private PaintFireBall paintFireBall;
    public FireBall(double x, double y, int width, int height, Id id, HandlerTeamGame handler , int facing  , int section) {
        super(x, y, width, height, id, handler , section);
        this.firstX = x;
        switch (facing){
            case 0 :
                setVelX(-5);
                break;
            case 1:
                setVelX(5);
                break;
        }
        this.paintFireBall = new PaintFireBall(x , y , width , height , id , section);
        getHandler().addPaintEntity(paintFireBall);
    }

    @Override
    public void update() {
        setX(getX() + getVelX());
        paintFireBall.setX(getX());
        setY(getY() + getVelY());
        paintFireBall.setY(getY());
        if(getX() - firstX >= 64*8 ) {
            die();
            getHandler().removePaintEntity(paintFireBall);
        }
        hitTiles();
        hitEntities();
        if(isJumping()){
            setGravity(getGravity()-0.17);
            setVelY((int)-getGravity());
            if(getGravity() <= 0.5){
                setJumping(false);
                setFalling(true);
            }
        }
        if(isFalling()){
            setGravity(getGravity()+0.17);
            setVelY((int)getGravity());
        }
    }
    public void hitTiles(){
        for (int i = 0; i < getHandler().getTiles().size(); i++){
            Tile tile = getHandler().getTiles().get(i);
            if (tile.getSection() == getSection()) {
                if (getBoundsLeft().intersects(tile.getBounds()) || getBoundsRight().intersects(tile.getBounds())) {
                    die();
                    getHandler().removePaintEntity(paintFireBall);
                }
                if (getBoundsBottom().intersects(tile.getBounds())) {
                    setJumping(true);
                    setFalling(false);
                    setGravity(4.0);
                } else if (!isFalling() && !isJumping()) {
                    setFalling(true);
                    setGravity(1.0);
                }
            }
        }
    }
    public void hitEntities(){
        for(int i = 0 ; i < getHandler().getEntities().size(); i++){
            Entity entity = getHandler().getEntities().get(i);
            if (entity.getSection() == getSection()) {
                if (getBounds().intersects(entity.getBounds()) && !(entity.equals(this))) {
                    if (entity.getId() == Id.GOOMBA) {
                        entity.die();
                        getHandler().removePaintEntity(((Goomba) entity).getPaintGoomba());
                    } else if (entity.getId() == Id.KOOPA) {
                        entity.die();
                        getHandler().removePaintEntity(((Koopa) entity).getPaintKoopa());
                    } else if (entity.getId() == Id.SPINY) {
                        entity.die();
                        getHandler().removePaintEntity(((Spiny) entity).getPaintSpiny());
                    } else if (entity.getId() == Id.PLANT) {
                        entity.die();
                        getHandler().removePaintEntity(((Plant) entity).getPaintPlant());
                    }
                    die();
                    getHandler().removePaintEntity(paintFireBall);
                }
            }
        }
    }
}
