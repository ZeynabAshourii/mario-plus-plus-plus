package org.example.mario_survival.model.entities;

import org.example.marathon_mario.model.enums.Id;
import org.example.mario_survival.controller.HandlerSurvival;
import java.awt.*;

public abstract class Entity {
    private double x;
    private double y;
    private int width;
    private int height;
    private double velX;
    private double velY;
    private double gravity = 0.0;
    private boolean jumping = false;
    private boolean falling = true;
    private HandlerSurvival handler;
    private Id id;

    public Entity(double x, double y, int width, int height, Id id, HandlerSurvival handler) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.id = id;
        this.handler = handler;
    }
    public abstract void update();

    public Rectangle getBounds() {
        return new Rectangle((int) getX(), (int) getY(), getWidth(), getHeight());
    }
    public Rectangle getBoundsTop() {
        return new Rectangle((int) (getX() + 10), (int) getY(), getWidth() - 20, 5);
    }

    public Rectangle getBoundsBottom() {
        return new Rectangle((int) (getX() + 10), (int) (getY() + getHeight() - 5), getWidth() - 20, 5);
    }

    public Rectangle getBoundsLeft() {
        return new Rectangle((int) getX(), (int) (getY() + 10), 5, getHeight() - 20);
    }

    public Rectangle getBoundsRight() {
        return new Rectangle((int) (getX() + getWidth() - 5), (int) (getY() + 10), 5, getHeight() - 20);
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

    public void setVelX(double velX) {
        this.velX = velX;
    }

    public void setVelY(double velY) {
        this.velY = velY;
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

    public double getVelX() {
        return velX;
    }

    public double getVelY() {
        return velY;
    }

    public double getGravity() {
        return gravity;
    }

    public void setGravity(double gravity) {
        this.gravity = gravity;
    }

    public Id getId() {
        return id;
    }

    public void setId(Id id) {
        this.id = id;
    }

    public HandlerSurvival getHandler() {
        return handler;
    }
    public void setHandler(HandlerSurvival handler) {
        this.handler = handler;
    }
    public boolean isJumping() {
        return jumping;
    }
    public void setJumping(boolean jumping) {
        this.jumping = jumping;
    }

    public boolean isFalling() {
        return falling;
    }

    public void setFalling(boolean falling) {
        this.falling = falling;
    }

}

