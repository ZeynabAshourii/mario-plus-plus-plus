package org.example.local_data;

import org.example.server.model.Item;
import java.io.Serializable;

public class Store implements Serializable {
    private int numberLimit;
    private volatile int invisible;
    private volatile int speed;
    private volatile int heal;
    private volatile int explosiveBomb;
    private volatile int speedBomb;
    private volatile int hammer;
    private volatile int sword;
    public Store(){

    }

    public Store(int numberLimit, int invisible, int speed, int heal, int explosiveBomb, int speedBomb, int hammer , int sword) {
        this.numberLimit = numberLimit;
        this.invisible = invisible;
        this.speed = speed;
        this.heal = heal;
        this.explosiveBomb = explosiveBomb;
        this.speedBomb = speedBomb;
        this.hammer = hammer;
        this.sword = sword;
    }

    public boolean buy(Item.ItemType itemType){
        switch (itemType){
            case INVISIBLE :
                if(invisible <= numberLimit -1){
                    invisible = invisible + 1;
                    return true;
                }else {
                    return false;
                }
            case SPEED:
                if (speed <= numberLimit - 1){
                    speed = speed + 1;
                    return true;
                }else {
                    return false;
                }
            case HEAL:
                if(heal <= numberLimit - 1){
                    heal = heal + 1;
                    return true;
                }else {
                    return false;
                }
        }
        return true;
    }

    public int getNumberLimit() {
        return numberLimit;
    }

    public void setNumberLimit(int numberLimit) {
        this.numberLimit = numberLimit;
    }

    public int getInvisible() {
        return invisible;
    }

    public void setInvisible(int invisible) {
        this.invisible = invisible;
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public int getHeal() {
        return heal;
    }

    public void setHeal(int heal) {
        this.heal = heal;
    }

    public int getExplosiveBomb() {
        return explosiveBomb;
    }

    public void setExplosiveBomb(int explosiveBomb) {
        this.explosiveBomb = explosiveBomb;
    }

    public int getSpeedBomb() {
        return speedBomb;
    }

    public void setSpeedBomb(int speedBomb) {
        this.speedBomb = speedBomb;
    }

    public int getHammer() {
        return hammer;
    }

    public void setHammer(int hammer) {
        this.hammer = hammer;
    }
}
