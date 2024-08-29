package org.example.order;

public class GameMessage {
    private GameMessageType gameMessageType;
    private int whichGame;

    public GameMessage(GameMessageType gameMessageType, int whichGame) {
        this.gameMessageType = gameMessageType;
        this.whichGame = whichGame;
    }

    public enum GameMessageType{
        START_NEW_GAME , DELETE_GAME;
    }

    public GameMessageType getGameMessageType() {
        return gameMessageType;
    }

    public void setGameMessageType(GameMessageType gameMessageType) {
        this.gameMessageType = gameMessageType;
    }

    public int getWhichGame() {
        return whichGame;
    }

    public void setWhichGame(int whichGame) {
        this.whichGame = whichGame;
    }
}
