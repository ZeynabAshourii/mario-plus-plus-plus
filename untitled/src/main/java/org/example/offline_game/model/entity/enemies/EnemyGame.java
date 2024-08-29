package org.example.offline_game.model.entity.enemies;


import org.example.offline_game.controller.HandlerOfflineGame;
import org.example.offline_game.model.entity.Entity;
import org.example.offline_game.model.entity.items.Item;
import org.example.offline_game.model.enums.Id;
import org.example.offline_game.model.tile.Tile;


import java.awt.*;
public class EnemyGame extends Entity {
    private EnemyGame enemyCopy;
    public EnemyGame(int x, int y, int width, int height, Id id, HandlerOfflineGame handler) {
        super(x, y, width, height, id, handler);
    }
    @Override
    public void paint(Graphics g) {

    }
    @Override
    public void update() {

    }
    public EnemyGame getEnemyCopy() {
        return enemyCopy;
    }
    public void setEnemyCopy(EnemyGame enemyCopy) {
        this.enemyCopy = enemyCopy;
    }
    public Rectangle rightRectangle(){
        Rectangle rectangle = new Rectangle(getX()+64 , getY()+32 , 64 , 64);
        return rectangle;
    }
    public Rectangle leftRectangle(){
        Rectangle rectangle = new Rectangle(getX() - 64, getY() + 32 , 64 , 64);
        return rectangle;
    }
    public boolean fall(){
        for (int i = 0; i < getHandler().getTiles().size(); i++) {
            Tile tile = getHandler().getTiles().get(i);
            if(getFacing() == 0){
                if(tile.getBounds().intersects(leftRectangle())){
                    return false;
                }
            }
            else {
                if(tile.getBounds().intersects(rightRectangle())){
                    return false;
                }
            }
        }
        return true;
    }
    public void antiFall(){
        if(fall()){
            if(getVelX() == 2){
                setX(getX() - 2);
                setVelX(-2);
                setFacing(0);
            }
            else{
                setX(getX() + 2);
                setVelX(2);
                setFacing(1);
            }
        }
    }
    public void hitTile(){
        for (int i = 0; i < getHandler().getTiles().size(); i++){
            Tile tile = getHandler().getTiles().get(i);
            if(getBoundsBottom().intersects(tile.getBounds())) {
                setVelY(0);
                if (isFalling()) {
                    setFalling(false);
                }
            }else if (!isFalling()) {
                setFalling(true);
                setGravity(0.8);
            }
            if(getBoundsLeft().intersects(tile.getBounds())){
                setVelX(2);
                setFacing(1);
            }
            if(getBoundsRight().intersects(tile.getBounds())){
                setVelX(-2);
                setFacing(0);
            }
        }
    }
    public void hitItem(){
        for(int i = 0; i < getHandler().getEntities().size(); i++){
            if(getHandler().getEntities().get(i) instanceof Item){
                Item item = (Item) getHandler().getEntities().get(i);
                if(getBoundsLeft().intersects(item.getBounds())){
                    setVelX(2);
                    setFacing(1);
                }
                if(getBoundsRight().intersects(item.getBounds())){
                    setVelX(-2);
                    setFacing(0);
                }
            }
        }
    }
}
