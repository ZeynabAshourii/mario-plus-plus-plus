package org.example.mario_survival.model.entities;

import org.example.marathon_mario.model.enums.Id;
import org.example.send_object.entities.PaintMario;
import org.example.send_object.entities.items.PaintAdditionalItem;
import org.example.server.model.Item;
import org.example.mario_survival.controller.HandlerSurvival;
import org.example.mario_survival.model.PlayerSurvival;
import org.example.mario_survival.model.tiles.Tile;

public class Mario extends Entity {
    private PlayerSurvival player;
    private PaintMario paintMario;
    private boolean speed = false;
    private boolean speedBomb = false;
    private boolean hitHammer = false;

    public Mario(double x, double y, int width, int height, Id id, HandlerSurvival handler , PlayerSurvival player) {
        super(x, y, width, height, id, handler);
        this.player = player;
        this.paintMario = new PaintMario(getX() , getY() , getWidth(), getHeight() , getId() , player.getUsername());
        paintMario.setColor(player.getMarioTeam().getColor());
        handler.getPaintEntities().add(paintMario);
    }

    @Override
    public void update() {
        if (!hitHammer) {
            setX(getX() + getVelX());
            setY(getY() + getVelY());

            paintMario.setX(getX());
            paintMario.setY(getY());

            if(getY() > getHandler().getDeathY() + 64) die();

            hitTiles();
            hitPaintEntities();
            mangeGravity();
        }
        hitEntities();

        if(getVelX() != 0){
            paintMario.manageFrame();
        }
    }

    public void hitPaintEntities() {
        for (int i = 0; i < getHandler().getPaintEntities().size(); i++){
            if (getBounds().intersects(getHandler().getPaintEntities().get(i).getBounds())) {
                if (getHandler().getPaintEntities().get(i).getId().equals(Id.ADDITIONAL_ITEM)) {
                    PaintAdditionalItem additionalItem = (PaintAdditionalItem) getHandler().getPaintEntities().get(i);
                    Item item = new Item(additionalItem.getItemType(), paintMario.getUsername());
                    item.setAdditional(true);
                    player.getAllItems().add(item);
                    System.out.println(player.getAllItems().size());
                    getHandler().setDateType("items");
                    getHandler().setSenderName(paintMario.getUsername());
                    getHandler().setSendData(true);
                    getHandler().getPaintEntities().remove(additionalItem);
                }
            }
        }
    }
    public void hitEntities() {
        for (int i = 0; i < getHandler().getEntities().size(); i++) {
            if (getBounds().intersects(getHandler().getEntities().get(i).getBounds()) && getHandler().getEntities().get(i).getId() == Id.GOOMBA) {
                Goomba goomba = (Goomba)getHandler().getEntities().get(i);
                if (getBoundsBottom().intersects(goomba.getBoundsTop())) {
                    getHandler().getEntities().remove(goomba);
                    getHandler().getPaintEntities().remove(goomba.getPaintGoomba());
                } else if (getBounds().intersects(goomba.getBounds())) {
                    die();
                }
            }
        }
    }

    public void hitTiles(){
        for(int i = 0; i < getHandler().getTiles().size(); i++){
            Tile tile = getHandler().getTiles().get(i);
            if (getBoundsTop().intersects(tile.getBounds())) {
                hitTileFromTop(tile);
            }
            if (getBoundsBottom().intersects(tile.getBounds())) {
                hitTileFromBottom(tile);
            } else if (!isFalling() && !isJumping()) {
                setGravity(0.8);
                setFalling(true);
            }
            if (getBoundsRight().intersects(tile.getBounds())) {
                setVelX(0);
                setX(tile.getX() - getWidth());
            }
            if (getBoundsLeft().intersects(tile.getBounds())) {
                setVelX(0);
                setX(tile.getX() + getWidth());
            }
        }
    }
    public void hitTileFromBottom(Tile tile){
        setVelY(0);
        if (isFalling()) {
            setFalling(false);
        }
    }
    public void hitTileFromTop(Tile tile){
        setVelY(0);
        if (isJumping()) {
            setJumping(false);
            setGravity(0.8);
            setFalling(true);
        }
    }
    public void mangeGravity(){
        if(isJumping()){
            setGravity(getGravity() - 0.17);
            setVelY((int)-getGravity());
            if(getGravity() <= 0.5){
                setJumping(false);
                setFalling(true);
            }
        }
        if(isFalling()){
            setGravity(getGravity() + 0.17);
            setVelY((int)getGravity());
        }
    }
    public void die(){
        player.setAlive(false);
        getHandler().getEntities().remove(this);
        getHandler().getPaintEntities().remove(paintMario);
        getHandler().addEntity(new Spectator(64, 64*9.4 , 64 , 64 , Id.SPECTATOR , getHandler() , player));
    }
    public PlayerSurvival getPlayer() {
        return player;
    }
    public PaintMario getPaintMario() {
        return paintMario;
    }
    public void setHitHammer(boolean hitHammer) {
        this.hitHammer = hitHammer;
    }

    public boolean isSpeed() {
        return speed;
    }

    public void setSpeed(boolean speed) {
        this.speed = speed;
    }
    public boolean isSpeedBomb() {
        return speedBomb;
    }
    public void setSpeedBomb(boolean speedBomb) {
        this.speedBomb = speedBomb;
    }
}

