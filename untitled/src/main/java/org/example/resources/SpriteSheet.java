package org.example.resources;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;
public class SpriteSheet implements Serializable {
    private Image sheet;
    public SpriteSheet(String path){
        try {
            sheet = ImageIO.read(new File("C:\\Users\\ASUS\\OneDrive\\Desktop\\untitled12final\\src\\main\\java\\org\\example\\Resources\\images\\" + path));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public Image getSheet() {
        return sheet;
    }
}