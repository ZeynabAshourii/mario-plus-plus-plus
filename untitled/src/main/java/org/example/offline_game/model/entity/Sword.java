package org.example.offline_game.model.entity;


import org.example.offline_game.controller.HandlerOfflineGame;
import org.example.offline_game.model.entity.enemies.Bowser;
import org.example.offline_game.model.entity.enemies.EnemyGame;
import org.example.offline_game.model.enums.Id;

import java.awt.*;
public class Sword extends Entity{
    private int velWidth;
    private boolean afzayesh = true;
    public Sword(int x, int y, int width, int height, Id id, HandlerOfflineGame handler) {
        super(x, y, width, height, id, handler);
        velWidth = 2;
    }

    @Override
    public void paint(Graphics g) {
        g.setColor(new Color(0 , 70 , 0));
        g.fillRect(getX() , getY() , getWidth() , getHeight());
    }

    @Override
    public void update() {
        if(!getHandler().getGame().isShowDeathScreen()) {
            setWidth(getWidth() + velWidth);
            if (getWidth() >= 4 * 64) {
                afzayesh = false;
            }
            if (getWidth() <= 0) {
                die();
            }
            if (!afzayesh) {
                velWidth = -2;
            }
            if (getPlayer().getFacing() == 1) {
                setX(getPlayer().getX() + getPlayer().getWidth());
            } else {
                setX(getPlayer().getX() - getWidth());
            }
            setY(getPlayer().getY() + getPlayer().getHeight() - 64);
            hitEnemy();
        }
    }
    public void hitEnemy(){
        for(int i = 0; i < getHandler().getEntities().size(); i++){
            Entity entity = getHandler().getEntities().get(i);
            if(getBounds().intersects(entity.getBounds()) && entity instanceof EnemyGame){
                if(entity.getId() == Id.bowser){
                    Bowser bowser = (Bowser) entity;
                    bowser.setHp(bowser.getHp()-2);
                    bowser.hurt();
                    die();
                }else {
                    entity.die();
                    die();
                }
            }
        }
    }

    public Player getPlayer(){
        Player player = null;
        for(int i = 0; i < getHandler().getEntities().size(); i++){
            Entity entity = getHandler().getEntities().get(i);
            if(entity.getId() == Id.player){
                player = (Player) entity;
            }
        }
        return player;
    }
}
