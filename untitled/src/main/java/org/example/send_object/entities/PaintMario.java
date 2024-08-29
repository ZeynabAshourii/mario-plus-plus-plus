package org.example.send_object.entities;

import org.example.marathon_mario.model.enums.Id;
import org.example.resources.Resources;

import java.awt.*;

public class PaintMario extends PaintEntity {
    String username;
    private int frame;
    private int frameDelay;
    private int facing = 1;
    private int life = 100;
    private boolean invisible = false;
    private Color color = Color.GREEN;
    public PaintMario(double x, double y, int width, int height, Id id , String username) {
        super(x, y, width, height, id);
        this.username = username;
    }

    @Override
    public void paint(Graphics g) {
        if (!invisible) {
            if (facing == 0) {
                g.drawImage(Resources.getMarioPic()[frame + 5].getSheet(), (int) getX(), (int) getY(), getWidth(), getHeight(), null);
            } else if (facing == 1) {
                g.drawImage(Resources.getMarioPic()[frame].getSheet(), (int) getX(), (int) getY(), getWidth(), getHeight(), null);
            }
            g.setColor(color);
            g.drawString(username, (int) (getX() + 5), (int) (getY() - 10));
        }
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

    public void setUsername(String username) {
        this.username = username;
    }

    public int getFrame() {
        return frame;
    }

    public void setFrame(int frame) {
        this.frame = frame;
    }

    public int getFrameDelay() {
        return frameDelay;
    }

    public void setFrameDelay(int frameDelay) {
        this.frameDelay = frameDelay;
    }

    public int getFacing() {
        return facing;
    }

    public void setFacing(int facing) {
        this.facing = facing;
    }
    public boolean isInvisible() {
        return invisible;
    }

    public void setInvisible(boolean invisible) {
        this.invisible = invisible;
    }

    public int getLife() {
        return life;
    }

    public void setLife(int life) {
        this.life = life;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }
}
