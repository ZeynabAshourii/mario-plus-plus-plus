package org.example.team_game.model.entity.enemies;

import org.example.team_game.controller.HandlerTeamGame;
import org.example.team_game.model.enums.Id;
import org.example.team_game.send_object.paint_entity.enemies.PaintPlant;

public class Plant extends EnemyGame{
    private int pixelsTravelled = 0;
    private boolean moving;
    private boolean insidePipe;
    private long startTime;
    private PaintPlant paintPlant;
    public Plant(double x, double y, int width, int height, Id id, HandlerTeamGame handler , int section) {
        super(x, y, width, height, id, handler, section);
        moving = false;
        insidePipe = true;
        startTime = System.nanoTime()/1000000000L + 1;
        this.paintPlant = new PaintPlant(x , y , width , height , id , section);
        getHandler().addPaintEntity(paintPlant);
    }
    @Override
    public void update() {
        setY(getY() + getVelY());
        paintPlant.setY(getY());
        long now =  System.nanoTime()/1000000000L;
        if(now - startTime >= 3){
            insidePipe = !insidePipe;
            moving = true;
            startTime = System.nanoTime()/1000000000L;
        }
        if(moving){
            if(insidePipe) setVelY(-3);
            else setVelY(3);

            pixelsTravelled += getVelY();

            if(pixelsTravelled >= getHeight() || pixelsTravelled <= -getHeight()){
                pixelsTravelled = 0;
                moving = false;
                setVelY(0);
            }
        }
    }

    public PaintPlant getPaintPlant() {
        return paintPlant;
    }
}
