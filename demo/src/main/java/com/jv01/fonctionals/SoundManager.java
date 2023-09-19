package com.jv01.fonctionals;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineEvent;
import javax.sound.sampled.LineListener;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.*;
import javax.xml.parsers.*;
import java.io.*;
import javax.sound.sampled.*;

import java.io.File;
import javax.sound.sampled.*;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import org.w3c.dom.*;

import com.github.javafaker.Faker;

public class SoundManager {
    public Save save = new Save();
    public  Faker faker = new Faker();

    public String gameName;

    private Document sfxDocument;
    private Document musicDocument;

    private Clip currentSFXClip;
    private Clip currentMusicClip;

    public SoundManager(String gameName){
        this.gameName = gameName;

        sfxDocument = save.getDocumentXml(gameName, "functional/sounds");
        musicDocument = save.getDocumentXml(gameName, "functional/music");
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
        Element musicElement = save.getElementById(musicDocument,"music",String.valueOf(musicID));

        if (musicElement != null) {
            String musicURL = save.stringToStringArray(save.getChildFromElement(musicElement, "musicUrl"))[0];
            musicURL = save.dropSpaceFromString(musicURL);
            
            try {
                // Charger le son à partir de l'URL
                AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File(musicURL));
                currentMusicClip = AudioSystem.getClip();
                currentMusicClip.open(audioInputStream);
                currentMusicClip.start();
                //Thread.sleep(5000);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("Sound not found for ID: " + musicID);
        }
    }

    public void stopSFX() {
        currentSFXClip.stop();
    }

    public void stopMusic() {
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