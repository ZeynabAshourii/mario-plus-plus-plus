package org.example.mario_survival.model.entities;

import org.example.marathon_mario.model.enums.Id;
import org.example.send_object.entities.items.PaintSword;
import org.example.server.model.Item;
import org.example.mario_survival.controller.HandlerSurvival;
import java.util.Timer;
import java.util.TimerTask;

public class Sword extends Entity {
    private Mario mario;
    private int[] xPoints = new int[4];
    private int[] yPoints = new int[4];
    private PaintSword sword;
    private double degree = 0;
    private boolean active = true;
    public Sword(double x, double y, int width, int height, Id id, HandlerSurvival handler , Mario mario) {
        super(x, y, width, height, id, handler);
        this.mario = mario;
        this.sword = new PaintSword(x , y , width , height , id , xPoints , yPoints);
        getHandler().getPaintEntities().add(sword);
    }
    public void handelXPoints(){
        int distance = 8;
        if(mario.getPaintMario().getFacing() == 1) {
            xPoints[0] = (int) (distance + mario.getWidth() + mario.getX());
            xPoints[1] = (int) (distance + mario.getWidth() + mario.getX() + Math.cos(degree)*getWidth());
            xPoints[2] = (int) (distance + mario.getWidth() + mario.getX() + Math.cos(degree)*getWidth() + Math.sin(degree)*getHeight());
            xPoints[3] = (int) (distance + mario.getWidth() + mario.getX() + Math.sin(degree)*getHeight());
        }else {
            xPoints[0] = (int) ((-1)*distance + mario.getX());
            xPoints[1] = (int) ((-1)*distance + mario.getX() + (-1)*Math.cos(degree)*getWidth());
            xPoints[2] = (int) ((-1)*distance + mario.getX() + (-1)*Math.cos(degree)*getWidth() + (-1)*Math.sin(degree)*getHeight());
            xPoints[3] = (int) ((-1)*distance + mario.getX() + (-1)*Math.sin(degree)*getHeight());
        }
    }
    public void handelYPoints(){
        yPoints[0] = (int) (mario.getY() + mario.getHeight()/2);
        yPoints[1] = (int) (mario.getY() + mario.getHeight()/2 + Math.sin(degree)*getWidth());
        yPoints[2] = (int) (mario.getY() + mario.getHeight()/2 + Math.sin(degree)*getWidth() - Math.cos(degree)*getHeight());
        yPoints[3] = (int) (mario.getY() + mario.getHeight()/2 - Math.cos(degree)*getHeight());
    }
    public void hurtEnemyMarios(){
        if(active) {
            for (int i = 0; i < getHandler().getEntities().size(); i++) {
                if (getHandler().getEntities().get(i).getId().equals(Id.MARIO)) {
                    Mario mario = (Mario) getHandler().getEntities().get(i);
                    if (!mario.getPaintMario().getUsername().equals(this.mario.getPaintMario().getUsername())) {
                        if (distance(mario) <= getHandler().getSwordBlockRange() * 64) {
                            mario.setX(mario.getX() - getHandler().getSwordPushBackBlock() * 64);
                            mario.getPaintMario().setX(mario.getX());
                            if (mario.getPaintMario().getLife() > getHandler().getSwordPercentDamage()) {
                                this.mario.getPlayer().getDamageDealt().add(getHandler().getSwordPercentDamage());
                                mario.getPaintMario().setLife(mario.getPaintMario().getLife() - getHandler().getSwordPercentDamage());
                            } else {
                                this.mario.getPlayer().getDamageDealt().add(mario.getPaintMario().getLife());
                                mario.getPaintMario().setLife(0);
                                mario.die();
                            }
                            active = false;
                        }
                    }
                }
            }
        }
    }
    public double distance(Mario mario){
        double xMario = mario.getX() + mario.getWidth()/2;
        double yMario = mario.getY() + mario.getHeight()/2;
        double xSword = xPoints[0];
        double ySword = yPoints[0];
        return Math.sqrt((xMario - xSword) * (xMario - xSword) + (yMario - ySword) * (yMario - ySword));
    }

    @Override
    public void update() {
        degree += 0.01;
        handelXPoints();
        handelYPoints();
        sword.setxPoints(xPoints);
        sword.setyPoints(yPoints);
        hurtEnemyMarios();
        if(degree >= 3.2){
            endOfSword();
        }
    }
    public void endOfSword(){
        getHandler().getEntities().remove(this);
        getHandler().getPaintEntities().remove(sword);
        Timer timer = new Timer();
        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                Item item = new Item(Item.ItemType.SWORD , mario.getPaintMario().getUsername());
                item.setAdditional(true);
                mario.getPlayer().getAllItems().add(item);
                getHandler().setDateType("items");
                getHandler().setSenderName(mario.getPaintMario().getUsername());
                getHandler().setSendData(true);
            }
        };
        timer.schedule(timerTask , getHandler().getSwordCoolDown()*1000);
    }
}
