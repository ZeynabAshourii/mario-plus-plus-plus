package org.example.team_game.send_object.paint_entity;

import org.example.team_game.model.enums.Id;
import java.awt.*;
import java.io.Serializable;

public abstract class PaintEntity implements Serializable {
    private double x;
    private double y;
    private int width;
    private int height;
    private Id id;
    private int section;
    public PaintEntity(double x, double y, int width, int height, Id id , int section) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.id = id;
        this.section = section;
    }

    public abstract void paint(Graphics g);

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

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public Id getId() {
        return id;
    }

    public void setId(Id id) {
        this.id = id;
    }
    public Rectangle getBounds() {
        return new Rectangle((int) getX(), (int) getY(), getWidth(), getHeight());
    }

    public int getSection() {
        return section;
    }

    public void setSection(int section) {
        this.section = section;
    }

}
