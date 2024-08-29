package org.example.mario_survival.model.tiles;

import org.example.marathon_mario.model.enums.Id;
import org.example.mario_survival.controller.HandlerSurvival;
import java.awt.*;
public abstract class Tile {
    private double x;
    private double y;
    private int width;
    private int height;
    private Id id;
    private HandlerSurvival handler;

    public Tile(double x, double y, int width, int height, Id id, HandlerSurvival handler) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.id = id;
        this.handler = handler;
    }
    public abstract void update();

    public Rectangle getBounds() {
        return new Rectangle((int) x, (int) y, width, height);
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

    public HandlerSurvival getHandler() {
        return handler;
    }

    public void setHandler(HandlerSurvival handler) {
        this.handler = handler;
    }
}
