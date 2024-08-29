package org.example.order;

import java.io.Serializable;
import java.util.LinkedList;

public class Information<T> implements Serializable {
    private int level;
    private LinkedList<T> linkedList;
    private InformationType informationType;
    private T[] array;

    private int port;

    public Information(T[] array , InformationType informationType) {
        this.array = array;
        this.informationType = informationType;
    }

    public Information(LinkedList<T> linkedList , InformationType informationType , int port){
        this.linkedList = linkedList;
        this.informationType = informationType;
        this.port = port;
    }
    public Information(LinkedList<T> linkedList, InformationType informationType) {
        this.linkedList = linkedList;
        this.informationType = informationType;
    }

    public Information(int level, InformationType informationType, T[] array) {
        this.level = level;
        this.informationType = informationType;
        this.array = array;
    }

    public enum InformationType{
        MESSAGE , NOTIFICATION , CHAT_PV , ITEMS , SPECIAL_BAGS , TILE , ENTITY , GAME_MARIO, START_MARATHON_MARIO , HIT_ITEM, START_SOLO_MARIO_SURVIVAL, REACTIONS, START_TEAM_GAME, GAME_MARIO_ENTITIES, GAME_MARIO_TILES;
    }

    public LinkedList<T> getLinkedList() {
        return linkedList;
    }

    public void setLinkedList(LinkedList<T> linkedList) {
        this.linkedList = linkedList;
    }

    public InformationType getInformationType() {
        return informationType;
    }

    public void setInformationType(InformationType informationType) {
        this.informationType = informationType;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public T[] getArray() {
        return array;
    }

    public void setArray(T[] array) {
        this.array = array;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    @Override
    public String toString() {
        return "Information{" +
                "linkedList=" + linkedList +
                ", informationType=" + informationType +
                '}';
    }
}
