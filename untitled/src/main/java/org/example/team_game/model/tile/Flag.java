package org.example.team_game.model.tile;

import org.example.team_game.controller.HandlerTeamGame;
import org.example.team_game.model.entity.Player;
import org.example.team_game.model.enums.Id;
import org.example.team_game.send_object.paint_tile.PaintFlag;

public class Flag extends Tile{
    private Player player;
    private PaintFlag paintFlag;
    private boolean goNextSection = false;
    public Flag(double x, double y, int width, int height, Id id, HandlerTeamGame handler , int section) {
        super(x, y, width, height, id, handler, section);
        this.paintFlag = new PaintFlag(x , y , width , height , id , section);
        paintFlag.setH(y);
        getHandler().addPaintTile(paintFlag);
    }

    @Override
    public void update() {
        setY(getY() + getVelY());
        paintFlag.setY(getY());
        if(isActive()){
            setVelY(2);
        }
        if(144 - getY() + paintFlag.getH() == 0){
            setVelY(0);
            if (!goNextSection) {
                player.nextSection();
                goNextSection = true;
            }
        }
        if (goNextSection){
            die();
            getHandler().removePaintTiles(paintFlag);
            getHandler().addTile(new Flag(getX() , (-3)*64 , 64, 48, Id.FLAG, getHandler() , getSection()));
        }
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }
}
