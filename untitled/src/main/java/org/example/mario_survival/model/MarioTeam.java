package org.example.mario_survival.model;

import java.awt.*;
import java.util.LinkedList;
import java.util.Random;

public class MarioTeam {
    private LinkedList<PlayerSurvival> playerSoloSurvivals = new LinkedList<>();
    private Color color;

    public MarioTeam(LinkedList<PlayerSurvival> playerSoloSurvivals) {
        this.playerSoloSurvivals = playerSoloSurvivals;
        color = new Color(new Random().nextInt(255) , new Random().nextInt(255) , new Random().nextInt(255));
        for(int i = 0; i < playerSoloSurvivals.size(); i++){
            PlayerSurvival playerSoloSurvival = playerSoloSurvivals.get(i);
            playerSoloSurvival.setMarioTeam(this);
        }
    }

    public LinkedList<PlayerSurvival> getPlayerSoloSurvivals() {
        return playerSoloSurvivals;
    }

    public void setPlayerSoloSurvivals(LinkedList<PlayerSurvival> playerSoloSurvivals) {
        this.playerSoloSurvivals = playerSoloSurvivals;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }
}
