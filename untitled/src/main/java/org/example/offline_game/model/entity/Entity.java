package org.example.offline_game.model.entity;

import org.example.offline_game.controller.HandlerOfflineGame;
import org.example.offline_game.model.entity.enemies.*;
import org.example.offline_game.model.enums.Id;

import org.example.offline_game.model.enums.PlayerState;
import org.example.resources.Resources;
import java.awt.*;
import java.io.Serializable;
public abstract class Entity implements Cloneable , Serializable {
    private int x;
    private int y;
    private int width;
    private int height;
    private int velX;
    private int velY;
    private int facing ;
    private double gravity = 0.0;
    private Id id;
    private HandlerOfflineGame handler;
    private boolean jumping = false;
    private boolean falling = true;
    private boolean sitting = false;
    public Entity(int x , int y, int width , int height, Id id , HandlerOfflineGame handler){
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.id = id;
        this.handler = handler;
    }
    public abstract void paint(Graphics g);
    public abstract void update();
    public void die(){
        getHandler().removeEntity(this);
        diePlayer();
        dieEnemy();
    }
    public void diePlayer(){
        if(this.getId() == Id.player){
            Player player = (Player) this;
            Resources.getHurtMario().play();
            getHandler().getGame().setLives(getHandler().getGame().getLives()-1);
            getHandler().getGame().setShowDeathScreen(true);
            player.setState(PlayerState.MINI);
            getHandler().setMarioState(0);
            int Dn = ((handler.getNumberOfCheckPoint()+1)*getHandler().getGame().getCoin() + getHandler().getCheckPoint().getPR())/(handler.getNumberOfCheckPoint()+4);
            getHandler().getGame().setCoin(getHandler().getGame().getCoin()-Dn);
            if(getHandler().getGame().getLives() <= 0){
                getHandler().getGame().setGameOver(true);
                getHandler().getGame().setFinishedGame(true);
            }
            if(handler.getGame().isBossFight()){
                for (int i = 0; i < handler.getEntities().size(); i++){
                    if(handler.getEntities().get(i).getId() == Id.bowser){
                        Bowser bowser = (Bowser) handler.getEntities().get(i);
                        bowser.setHp((int) (bowser.getHp()*1.25));
                    }
                }
            }
        }
    }
    public void dieEnemy(){
        if(this instanceof EnemyGame){
            calculateScore();
            if(getHandler().getCheckPoint().getEntities().remove(((EnemyGame) this).getEnemyCopy())){
                System.out.println();
            }
            Resources.getKillEnemy().play();
        }
    }
    public void calculateScore(){
        if(this instanceof Spiny){
            getHandler().getGame().setScore(getHandler().getGame().getScore() + 3);
        } else if (this instanceof Koopa) {
            getHandler().getGame().setScore(getHandler().getGame().getScore() + 2);
        } else if (this instanceof Goomba) {
            getHandler().getGame().setScore(getHandler().getGame().getScore() + 1);
        } else if (this instanceof Plant) {
            getHandler().getGame().setScore(getHandler().getGame().getScore() + 1);
        }
    }
    public Rectangle getBounds(){
        return  new Rectangle(getX() , getY() , getWidth() , getHeight());
    }
    public Rectangle getBoundsTop(){
        return new Rectangle(getX()+10 , getY() ,  getWidth()-20 , 5 );
    }
    public Rectangle getBoundsBottom(){
        return new Rectangle(getX()+10 , getY()+getHeight()-5 , getWidth()-20 , 5);
    }
    public Rectangle getBoundsLeft(){
        return new Rectangle(getX() , getY()+10 , 5 , getHeight()-20);
    }
    public Rectangle getBoundsRight(){
        return new Rectangle(getX()+getWidth()-5 , getY()+10 , 5 , getHeight()-20);
    }
    public int getWidth() {
        return width;
    }
    public int getHeight() {
        return height;
    }
    public int getFacing() {
        return facing;
    }
    public boolean isJumping() {
        return jumping;
    }
    public boolean isFalling() {
        return falling;
    }
    public int getVelX() {
        return velX;
    }
    public int getVelY() {
        return velY;
    }
    public double getGravity() {
        return gravity;
    }
    public HandlerOfflineGame getHandler() {
        return handler;
    }
    public int getX() {
        return x;
    }
    public int getY() {
        return y;
    }
    public Id getId() {
        return id;
    }
    public boolean isSitting() {
        return sitting;
    }
    public void setX(int x) {
        this.x = x;
    }
    public void setY(int y) {
        this.y = y;
    }
    public void setWidth(int width) {
        this.width = width;
    }
    public void setHeight(int height) {
        this.height = height;
    }
    public void setFacing(int facing) {
        this.facing = facing;
    }
    public void setJumping(boolean jumping) {
        this.jumping = jumping;
    }
    public void setFalling(boolean falling) {
        this.falling = falling;
    }
    public void setVelX(int velX) {
        this.velX = velX;
    }
    public void setVelY(int velY) {
        this.velY = velY;
    }
    public void setId(Id id) {
        this.id = id;
    }
    public void setGravity(double gravity) {
        this.gravity = gravity;
    }
    public void setHandler(HandlerOfflineGame handler) {
        this.handler = handler;
    }
    public void setSitting(boolean sitting) {
        this.sitting = sitting;
    }
    @Override
    public Entity clone() {
        try {
            Entity clone = (Entity) super.clone();
            // TODO: copy mutable state here, so the clone can't change the internals of the original
            return clone;
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }
}
