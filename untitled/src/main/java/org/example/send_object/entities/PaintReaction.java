package org.example.send_object.entities;

import org.example.marathon_mario.model.enums.Id;
import org.example.resources.Resources;
import java.awt.*;
public class PaintReaction extends PaintEntity{
    private ReactionType reactionType;
    private volatile int numberOfReaction = 0;
    public PaintReaction(double x, double y, int width, int height, Id id , ReactionType reactionType) {
        super(x, y, width, height, id);
        this.reactionType = reactionType;
    }

    @Override
    public void paint(Graphics g) {
        if (reactionType.equals(ReactionType.LIKE)){
            g.drawImage(Resources.getLike().getSheet(), (int) getX(), (int) getY(), getWidth() , getHeight() , null);
        } else if (reactionType.equals(ReactionType.DISLIKE)) {
            g.drawImage(Resources.getDislike().getSheet(), (int) getX(), (int) getY(), getWidth() , getHeight() , null);
        } else if (reactionType.equals(ReactionType.LAUGH)) {
            g.drawImage(Resources.getLaugh().getSheet(), (int) getX(), (int) getY(), getWidth() , getHeight() , null);
        } else if (reactionType.equals(ReactionType.ANGER)) {
            g.drawImage(Resources.getAngry().getSheet(), (int) getX(), (int) getY(), getWidth() , getHeight() , null);
        } else if (reactionType.equals(ReactionType.HEART)) {
            g.drawImage(Resources.getHeart().getSheet(), (int) getX(), (int) getY(), getWidth() , getHeight() , null);
        }
        g.drawString( "X " + numberOfReaction , (int) (getX() + getWidth() + 6), (int) (getY() + 24));
    }
    public enum ReactionType {
        LIKE , DISLIKE , LAUGH , ANGER , HEART;
    }

    public ReactionType getReactionType() {
        return reactionType;
    }

    public void setReactionType(ReactionType reactionType) {
        this.reactionType = reactionType;
    }

    public int getNumberOfReaction() {
        return numberOfReaction;
    }

    public void setNumberOfReaction(int numberOfReaction) {
        this.numberOfReaction = numberOfReaction;
    }
}
