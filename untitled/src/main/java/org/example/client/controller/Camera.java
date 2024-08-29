package org.example.client.controller;

import org.example.send_object.entities.PaintEntity;
import java.io.Serializable;
public class Camera implements Serializable {
    private double x;
    private double y;
    public void update(PaintEntity entity){
        setX( ((-1*entity.getX() + 1080) - 840));
        setY( (-1*entity.getY() + 771 - 240));
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
}
