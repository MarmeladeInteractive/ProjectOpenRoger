package com.jv01;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineEvent;
import javax.sound.sampled.LineListener;
import java.io.File;
import javax.sound.sampled.*;

public class SoundManager {
    private Clip clip;

    public SoundManager() {
        try {
            // Créez un AudioInputStream à partir du fichier audio
            File soundFile = new File("demo/xml/save/functional/test00.wav");
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(soundFile);

            // Obtenez le clip audio
            clip = AudioSystem.getClip();
            clip.open(audioInputStream);

            // Ajoutez un LineListener pour gérer les événements audio
            clip.addLineListener(new LineListener() {
                @Override
                public void update(LineEvent event) {
                    if (event.getType() == LineEvent.Type.STOP) {
                        clip.close();
                    }
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public SoundManager(String pathToAudio) {
        try {
            // Créez un AudioInputStream à partir du fichier audio
            File soundFile = new File(pathToAudio);
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(soundFile);

            // Obtenez le clip audio
            clip = AudioSystem.getClip();
            clip.open(audioInputStream);

            // Ajoutez un LineListener pour gérer les événements audio
            clip.addLineListener(new LineListener() {
                @Override
                public void update(LineEvent event) {
                    if (event.getType() == LineEvent.Type.STOP) {
                        clip.close();
                    }
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void playSound() {
        if (clip != null) {
            clip.start();
        }
    }

    public void stopSound() {
        if (clip != null) {
            clip.stop();
            clip.setFramePosition(0); // Réinitialisez la position de lecture au début
        }
    }

    public void increaseVolume(float amount) {
        if (clip != null) {
            FloatControl gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
            gainControl.setValue(gainControl.getValue() + amount);
        }
    }

    public void decreaseVolume(float amount) {
        if (clip != null) {
            FloatControl gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
            gainControl.setValue(gainControl.getValue() - amount);
        }
    }
}