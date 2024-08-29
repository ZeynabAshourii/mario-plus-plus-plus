package org.example.resources;

import org.example.resources.Resources;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.io.File;
import java.io.Serializable;
public class Sound implements Serializable {
    private Clip clip;
    private boolean mute = false;
    public Sound(String path){
        try {
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File(path));
            AudioFormat baseFormat = audioInputStream.getFormat();
            AudioFormat decodeFormat = new AudioFormat(AudioFormat.Encoding.PCM_SIGNED , baseFormat.getSampleRate() ,16
                    , baseFormat.getChannels() ,baseFormat.getChannels()*2 , baseFormat.getSampleRate() ,false);
            AudioInputStream dais = AudioSystem.getAudioInputStream(decodeFormat , audioInputStream);
            clip = AudioSystem.getClip();
            clip.open(dais);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    public void play(){
        if(!Resources.isMute()) {
            if (clip == null) {
                return;
            }
            stop();
            clip.setFramePosition(0);
            clip.start();
        }
    }
    public void close(){
        stop();
        clip.close();
    }
    public void stop(){
        if(clip.isRunning()){
            clip.stop();
        }
    }

    public Clip getClip() {
        return clip;
    }

    public boolean isMute() {
        return mute;
    }

    public void setMute(boolean mute) {
        this.mute = mute;
    }
}
