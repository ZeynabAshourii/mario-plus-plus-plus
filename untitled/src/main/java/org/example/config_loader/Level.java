package org.example.config_loader;

import java.io.Serializable;
import java.util.LinkedList;
public class Level implements Serializable {
    public LinkedList<Section> sections = new LinkedList<>();

    public int length(){
        int length = 0;
        for(int i = 0; i < sections.size(); i++){
            length += sections.get(i).length;
        }
        return 64*length;
    }

    @Override
    public String toString() {
        return "Level{" +
                "sections=" + sections +
                '}';
    }
}
