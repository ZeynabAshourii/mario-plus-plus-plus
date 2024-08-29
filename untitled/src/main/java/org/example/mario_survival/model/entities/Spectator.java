package org.example.mario_survival.model.entities;

import org.example.marathon_mario.model.enums.Id;
import org.example.send_object.entities.PaintSpectator;
import org.example.mario_survival.controller.HandlerSurvival;
import org.example.mario_survival.model.PlayerSurvival;

public class Spectator extends Entity{
    private PaintSpectator spectator;
    public Spectator(double x, double y, int width, int height, Id id, HandlerSurvival handler , PlayerSurvival playerSoloSurvival) {
        super(x, y, width, height, id, handler);
        this.spectator = new PaintSpectator(x , y , width , height , Id.SPECTATOR , playerSoloSurvival.getUsername());
        handler.getPaintEntities().add(spectator);
    }

    @Override
    public void update() {
        setX(getX() + getVelX());
        setY(getY() + getVelY());
        spectator.setX(getX());
        spectator.setY(getY());
        if(getVelX() != 0){
            spectator.manageFrame();
        }
    }

    public PaintSpectator getSpectator() {
        return spectator;
    }

    public void setSpectator(PaintSpectator spectator) {
        this.spectator = spectator;
    }
}
