package org.example.team_game.send_object.paint_entity;

import org.example.resources.Resources;
import org.example.team_game.model.enums.Id;
import org.example.team_game.model.enums.PlayerState;
import java.awt.*;

public class PaintPlayer extends PaintEntity {
    private int frame;
    private int frameDelay;
    private PlayerState playerState;
    private int facing = 1;
    private String username;
    private boolean invisible = false;
    public PaintPlayer(double x, double y, int width, int height, Id id, int section , PlayerState playerState , String username) {
        super(x, y, width, height, id, section);
        this.playerState = playerState;
        this.username = username;
    }

    @Override
    public void paint(Graphics g) {
        try {
            if (!invisible) {
                if (playerState == PlayerState.MINI) {
                    if (facing == 0) {
                        g.drawImage(Resources.getMarioPic()[frame + 5].getSheet(), (int) getX(), (int) getY(), getWidth(), getHeight(), null);
                    } else if (facing == 1) {
                        g.drawImage(Resources.getMarioPic()[frame].getSheet(), (int) getX(), (int) getY(), getWidth(), getHeight(), null);
                    }
                } else if (playerState == PlayerState.MEGA) {
                    if (facing == 0) {
                        g.drawImage(Resources.getMegaMarioPic()[frame + 5].getSheet(), (int) getX(), (int) getY(), getWidth(), getHeight(), null);
                    } else if (facing == 1) {
                        g.drawImage(Resources.getMegaMarioPic()[frame].getSheet(), (int) getX(), (int) getY(), getWidth(), getHeight(), null);
                    }
                } else if (playerState == PlayerState.FIRE) {
                    if (facing == 0) {
                        g.drawImage(Resources.getFireMarioPic()[frame + 5].getSheet(), (int) getX(), (int) getY(), getWidth(), getHeight(), null);
                    } else if (facing == 1) {
                        g.drawImage(Resources.getFireMarioPic()[frame].getSheet(), (int) getX(), (int) getY(), getWidth(), getHeight(), null);
                    }
                }
                g.drawString(username, (int) (getX() + 5), (int) (getY() - 10));
            }
        }
        catch (Exception e){
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
    public void setPlayerState(PlayerState playerState) {
        this.playerState = playerState;
    }

    public void setFacing(int facing) {
        this.facing = facing;
    }

    public String getUsername() {
        return username;
    }

    public void setInvisible(boolean invisible) {
        this.invisible = invisible;
    }
}
