package org.example.send_object.entities;

import org.example.marathon_mario.model.enums.Id;
import org.example.resources.Resources;

import java.awt.*;

public class PaintSpectator extends PaintEntity{
    String username;
    private int frame;
    private int frameDelay;
    private int facing = 1;
    public PaintSpectator(double x, double y, int width, int height, Id id , String username) {
        super(x, y, width, height, id);
        this.username = username;
    }

    @Override
    public void paint(Graphics g) {
        if (facing == 0) {
            g.drawImage(Resources.getMegaMarioPic()[frame + 5].getSheet(), (int) getX(), (int) getY(), getWidth(), getHeight(), null);
        } else if (facing == 1) {
            g.drawImage(Resources.getMegaMarioPic()[frame].getSheet(), (int) getX(), (int) getY(), getWidth(), getHeight(), null);
        }
        g.setColor(Color.LIGHT_GRAY);
        g.drawString(username, (int) (getX() + 5), (int) (getY() - 10));
    }
    public void manageFrame(){
        frameDelay++;
        if(frameDelay >= 3){
            frame++;
            if(frame >= 5){
                frame = 0;
            }
            frameDelay = 0;
        }
    }

    public String getUsername() {
        return username;
    }

    public int getFacing() {
        return facing;
    }

    public void setFacing(int facing) {
        this.facing = facing;
    }
}
