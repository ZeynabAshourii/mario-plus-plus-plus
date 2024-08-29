package org.example.offline_game.controller;

import org.example.offline_game.model.entity.enemies.EnemyGame;
import org.example.offline_game.model.entity.Entity;
import org.example.offline_game.model.enums.Id;
import org.example.offline_game.model.tile.Gate;
import org.example.offline_game.model.tile.Tile;
import org.example.config_loader.Level;

import java.io.Serializable;
import java.util.LinkedList;

public class CheckPoint implements Cloneable , Serializable {
    private long time;
    private int PR;
    private int score;
    private int coin;
    private LinkedList<Entity> entities = new LinkedList<>();
    private LinkedList<Tile> tiles = new LinkedList<>();
    private Level level;
    private HandlerOfflineGame handler;
    public CheckPoint(HandlerOfflineGame currentHandler){
        entities.clear();
        tiles.clear();
        this.handler = currentHandler.clone();
        this.level = handler.getCurrentLevel();
        this.time = handler.getGame().getElapsedTime();
        this.score = handler.getGame().getScore();
        this.coin = handler.getGame().getCoin();
        enemyCopy();
        gateCopy();
        PR = handler.getGame().getCoin()*dx()/level.length();
        currentHandler.setCheckPoint(this);
        currentHandler.setNumberOfCheckPoint(currentHandler.getNumberOfCheckPoint()+1);
    }
    public void enemyCopy(){
        for(int i = 0; i < handler.getEntities().size(); i++){
            Entity entity = handler.getEntities().get(i).clone();
            if(handler.getEntities().get(i) instanceof EnemyGame){
                ((EnemyGame) handler.getEntities().get(i)).setEnemyCopy((EnemyGame) entity);
            }
            entities.add(entity);
        }
    }
    public void gateCopy(){
        for(int i = 0; i < handler.getTiles().size(); i++){
            Tile tile = handler.getTiles().get(i).clone();
            if(handler.getTiles().get(i) instanceof Gate){
                ((Gate) handler.getTiles().get(i)).setGateCopy((Gate)tile);
            }
            tiles.add(tile);
        }
    }
    public int dx(){
        int dx = 0;
        for(int i = 0; i < handler.getGame().getSection(); i++){
            dx += level.sections.get(i).length;
        }
        for (int i = 0; i < entities.size(); i++){
            if(entities.get(i).getId() == Id.player){
                dx += (entities.get(i).getX()-64);
            }
        }
        return dx;
    }

    public LinkedList<Entity> getEntities() {
        return entities;
    }

    public LinkedList<Tile> getTiles() {
        return tiles;
    }

    public long getTime() {
        return time;
    }

    public int getScore() {
        return score;
    }

    public int getCoin() {
        return coin;
    }
    public int getPR() {
        return PR;
    }
}
