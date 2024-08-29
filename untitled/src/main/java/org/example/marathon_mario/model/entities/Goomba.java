package org.example.marathon_mario.model.entities;

import org.example.marathon_mario.controller.HandlerMarathon;
import org.example.marathon_mario.model.enums.Id;
import org.example.send_object.entities.PaintGoomba;
import org.example.send_object.entities.items.PaintAdditionalItem;
import java.util.Random;
public class Goomba extends Entity {
    private PaintGoomba paintGoomba;
    public Goomba(double x, double y, int width, int height, Id id, HandlerMarathon handler) {
        super(x, y, width, height, id, handler);
        switch ((new Random()).nextInt(2)){
            case 0 :
                setVelX(-2);
                break;
            case 1 :
                setVelX(2);
                break;
        }
        this.paintGoomba = new PaintGoomba(x , y , width , height , id);
        getHandler().getPaintEntities().add(paintGoomba);
    }

    @Override
    public void update() {
        setX(getX() + getVelX());
        setY(getY() + getVelY());
        paintGoomba.setX(getX());
        paintGoomba.setY(getY());

        hitItem();
    }
    public void hitItem(){
        for(int i = 0; i < getHandler().getPaintEntities().size(); i++){
            if(getHandler().getPaintEntities().get(i).getId().equals(Id.ADDITIONAL_ITEM)){
                PaintAdditionalItem item = (PaintAdditionalItem) getHandler().getPaintEntities().get(i);
                if(getBoundsLeft().intersects(item.getBounds())){
                    setVelX(2);
                }
                if(getBoundsRight().intersects(item.getBounds())){
                    setVelX(-2);
                }
            }
        }
    }
    public PaintGoomba getPaintGoomba() {
        return paintGoomba;
    }
}
