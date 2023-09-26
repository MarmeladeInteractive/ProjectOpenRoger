package com.jv01.fonctionals;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineEvent;
import javax.sound.sampled.LineListener;

import org.w3c.dom.*;

import java.io.File;

import com.github.javafaker.Faker;

public class SoundManager {
    public Save save = new Save();
    public  Faker faker = new Faker();

    public String gameName;

    private Document sfxDocument;
    private Document musicDocument;

    private Clip currentSFXClip;
    private Clip currentMusicClip;
    private boolean toLoop;

    public SoundManager(String gameName){ // used in game
        this.gameName = gameName;
        this.toLoop = true;

        sfxDocument = save.getDocumentXml(gameName, "functional/sounds");
        musicDocument = save.getDocumentXml(gameName, "functional/music");
    }

    public SoundManager(){ // used in main menu
        this.toLoop = true;

        sfxDocument = save.getDocumentXmlFromRoot("functional/sounds");
        musicDocument = save.getDocumentXmlFromRoot("functional/music");
    }

    public void playSFX(int sfxID) {
        Element sfxElement = save.getElementById(sfxDocument,"sound",String.valueOf(sfxID));

        if (sfxElement != null) {
            String[] soundURLArray = save.stringToStringArray(save.getChildFromElement(sfxElement, "soundUrl"));
            String soundURL = soundURLArray[faker.number().numberBetween(0,soundURLArray.length-1)];
            soundURL = save.dropSpaceFromString(soundURL);

            //System.out.println(soundURL);
            try {
                // Charger le son à partir de l'URL
                AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File(soundURL));
                currentSFXClip = AudioSystem.getClip();
                currentSFXClip.open(audioInputStream);
                currentSFXClip.start();
                //Thread.sleep(5000);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("Sound not found for ID: " + sfxID);
        }
    }

    public void playMusic(int musicID) {
        Element musicElement = save.getElementById(musicDocument, "music", String.valueOf(musicID));

        if (musicElement != null) {
            String musicURL = save.stringToStringArray(save.getChildFromElement(musicElement, "musicUrl"))[0];
            musicURL = save.dropSpaceFromString(musicURL);

            try {
                // Charger le son à partir de l'URL
                AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File(musicURL));
                currentMusicClip = AudioSystem.getClip();
                currentMusicClip.open(audioInputStream);

                // Ajouter un LineListener pour détecter lorsque la musique s'arrête
                currentMusicClip.addLineListener(new LineListener() {
                    @Override
                    public void update(LineEvent event) {
                        if (event.getType() == LineEvent.Type.STOP) {
                            if (toLoop) {
                                // Si toLoop est vrai, redémarrez la musique
                                currentMusicClip.setMicrosecondPosition(0); // Réinitialiser la position
                                currentMusicClip.start();
                            }
                        }
                    }
                });

                currentMusicClip.start();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("Sound not found for ID: " + musicID);
        }
    }

    // Ajoutez une méthode pour définir la valeur de toLoop
    public void setLoop(boolean loop) {
        this.toLoop = loop;
    }

    public void stopSFX() {
        currentSFXClip.stop();
    }

    public void stopMusic(boolean loop) {
        this.toLoop = loop; // true : music will still loop, false it will stop
        currentMusicClip.stop();
    }

    public static void main(String[] args) {
        
        SoundManager soundManager = new SoundManager("ee");
        //soundManager.playSFX(0); // Joue le son avec l'ID 0 (vous pouvez remplacer l'ID par celui que vous souhaitez).
        //soundManager.playMusic(0);
        //soundManager.stopMusic();
        //soundManager.playSFX(1);
        soundManager.playSFX(2);
        
    }
}