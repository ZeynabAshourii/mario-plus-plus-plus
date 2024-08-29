package org.example.team_game.model.tile;

import org.example.team_game.controller.HandlerTeamGame;
import org.example.team_game.model.enums.Id;

import java.awt.*;

public abstract class Tile {
    private double x;
    private double y;
    private int width;
    private int height;
    private double velX;
    private double velY;
    private Id id;
    private HandlerTeamGame handler;
    private boolean active = false;
    private int section;

    public Tile(double x, double y, int width, int height, Id id, HandlerTeamGame handler, int section) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.id = id;
        this.handler = handler;
        this.section  = section;
    }
    public abstract void update();

    public Rectangle getBounds() {
        return new Rectangle((int) x, (int) y, width, height);
    }
    public void die(){
        handler.removeTile(this);
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public Id getId() {
        return id;
    }

    public void setId(Id id) {
        this.id = id;
    }

    public HandlerTeamGame getHandler() {
        return handler;
    }

    public void setHandler(HandlerTeamGame handler) {
        this.handler = handler;
    }

    public double getVelX() {
        return velX;
    }

    public void setVelX(double velX) {
        this.velX = velX;
    }

    public double getVelY() {
        return velY;
    }

    public void setVelY(double velY) {
        this.velY = velY;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public int getSection() {
        return section;
    }

    public void setSection(int section) {
        this.section = section;
    }
}
