package org.example.send_object.tile;

import org.example.marathon_mario.model.enums.Id;
import java.awt.*;
import java.io.Serializable;

public abstract class PaintTile implements Serializable {
    private double x;
    private double y;
    private int width;
    private int height;
    private Id id;
    public PaintTile(double x, double y, int width, int height, Id id) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.id = id;
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
}
