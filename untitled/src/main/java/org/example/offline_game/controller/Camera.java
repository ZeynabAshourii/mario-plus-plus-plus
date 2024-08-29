package org.example.offline_game.controller;

import org.example.offline_game.model.entity.Entity;
import java.io.Serializable;
public class Camera implements Serializable {
    private int x;
    private int y;
    public void update(Entity entity){
        setX((-1*entity.getX() + 1080) - 840);
        setY((-1*entity.getY() + 771 - 240));
    }
    public int getX() {
        return x;
    }
    public void setX(int x) {
        this.x = x;
    }
    public int getY() {
        return y;
    }
    public void setY(int y) {
        this.y = y;
    }
}
