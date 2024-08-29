package org.example.offline_game.model.entity.enemies;


import org.example.offline_game.controller.HandlerOfflineGame;
import org.example.offline_game.model.entity.Entity;
import org.example.offline_game.model.entity.Player;
import org.example.offline_game.model.enums.Id;
import org.example.offline_game.model.tile.Tile;
import org.example.resources.Resources;
import java.awt.*;
public class FireBallAttack extends Entity {
    public FireBallAttack(int x, int y, int width, int height, Id id, HandlerOfflineGame handler , int facing) {
        super(x, y, width, height, id, handler);
        switch (facing){
            case 0 :
                setVelX(-5);
                break;
            case 1:
                setVelX(5);
                break;
        }
    }

    @Override
    public void paint(Graphics g) {
        try {
            g.drawImage(Resources.getCircleFire().getSheet(), getX(), getY(), getWidth(), getHeight(), null);
        }
        catch (Exception e){}
    }

    @Override
    public void update() {
        setX(getX() + getVelX());
        setY(getY() + getVelY());
        hitTile();
        hitPlayer();
    }
    public void hitTile(){
        for(int i = 0; i < getHandler().getTiles().size(); i++){
            Tile tile = getHandler().getTiles().get(i);
            if(getBounds().intersects(tile.getBounds())){
                die();
            }
        }
    }
    public void hitPlayer(){
        for(int i = 0; i < getHandler().getEntities().size(); i++){
            Entity entity = getHandler().getEntities().get(i);
            if(getBounds().intersects(entity.getBounds())) {
                if (entity.getId() == Id.player) {
                    Player player = (Player) entity;
                    die();
                    player.hitPlayer(this);
                }
                if(entity.getId() == Id.sword){
                    die();
                    entity.die();
                }
            }
        }
    }
}
