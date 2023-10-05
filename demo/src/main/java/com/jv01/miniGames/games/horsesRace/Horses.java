package com.jv01.miniGames.games.horsesRace;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

import java.util.ArrayList;
import java.util.Random;
import java.util.List;
import java.util.Collections;

import java.awt.*;

class Horses {
    public Horse[] horses;
    public JPanel[] horsesSelectors;

    private Color normalColor = new Color(200,200,200);
    private Color selectedColor = new Color(255,20,20);

    public int selectedHorse = -1;

    private Random random = new Random();
    private int coteMin = 1;
    private int coteMax = 27;

    private List<Integer> nHorsesList = new ArrayList<>();
    private int nHorses = 9;

    public int height;

    public Horses(int n) {

        this.horses = new Horse[n];
        this.horsesSelectors = new JPanel[n];
        this.height = (600 - 15 * n) / n;

        for(int i = 0; i < nHorses; i++){
            nHorsesList.add(i);
        }
        Collections.shuffle(nHorsesList);

        for(int i = 0; i < horses.length; i++){
            this.horses[i] = new Horse((i+1),"Cheval "+String.valueOf(nHorsesList.get(i) + 1), getProb(), new JLabel(), height);
            this.horsesSelectors[i] = new JPanel();
        }
    }


    public void addHorsesSelectors(JPanel gamePanel) {
        int height = (600 - 15 * horses.length) / horses.length;
        for (int i = 0; i < horses.length; i++) {
            final int selector = i;
            horsesSelectors[i].setLayout(null);
    
            horsesSelectors[i].addMouseListener(new java.awt.event.MouseAdapter() {
                public void mouseClicked(java.awt.event.MouseEvent evt) {
                    resetAllHorsesSelectors();
                    horsesSelectors[selector].setBackground(selectedColor);
                    selectedHorse = selector;
                }
            });
            horsesSelectors[i].setBounds(80, 100 + i * (height + 15), 300, height);
    
            JLabel imageLabel = new JLabel();
            ImageIcon imageIcon = horses[i].horseIconsFrame[0];
            imageIcon = new ImageIcon(imageIcon.getImage().getScaledInstance(height - 5, height - 5, Image.SCALE_DEFAULT));
            imageLabel.setIcon(imageIcon);
    
            imageLabel.setBounds(2, 2, height - 5, height - 5);
            horsesSelectors[i].add(imageLabel);
    
            JLabel textLabel = new JLabel(horses[i].name);
            textLabel.setFont(new Font("Arial", Font.PLAIN, 20));
            textLabel.setBounds(height + 10, 2, 100, height - 5);
            horsesSelectors[i].add(textLabel);

            JLabel coteLabel = new JLabel(String.valueOf(horses[i].probability));
            coteLabel.setFont(new Font("Arial", Font.PLAIN, 20));
            coteLabel.setBounds(300-50, 2, 50, height - 5);
            horsesSelectors[i].add(coteLabel);
    
            gamePanel.add(horsesSelectors[i]);
        }
        resetAllHorsesSelectors();
    }

    public int[] ordreArriveeAleatoireAvecCotes(Horse[] horses) {
        int nombreDeChevaux = horses.length;
        int[] ordreArrivee = new int[nombreDeChevaux];
        
        List<Integer> listHorses = new ArrayList<>();
        
        for(Horse horse:horses){
            for(int i = 0; i<((coteMax+1)-horse.probability); i++){
                listHorses.add(horse.id);
            }
        }
        Collections.shuffle(listHorses);

        int currentHorse = 0;
        
        for(int j = 0; j < nombreDeChevaux; j++){
            currentHorse = listHorses.get(random.nextInt(listHorses.size()));

            ordreArrivee[j]=currentHorse;

            List<Integer> nouvelleListe = new ArrayList<>();

            for (Integer nombre : listHorses) {
                if (nombre!=currentHorse) {
                    nouvelleListe.add(nombre);
                }
            }
            listHorses = nouvelleListe;
        }

        return ordreArrivee;
    }
    
    private double getProb(){
        double probability = Math.random();

        if (probability < 0.8) { 
            return random.nextInt(11 - coteMin) + coteMin; 
        } else {
            return random.nextInt(coteMax - 11) + 11; 
        }
    }
  
    public void resetAllHorsesSelectors(){
        for (JPanel panel : horsesSelectors) {
            panel.setBackground(normalColor);
        }
        selectedHorse = -1;
    }
}


class Horse {
    public int id;
    public String name;
    public ImageIcon[] horseIconsFrame = new ImageIcon[4];
    public double probability;
    public JLabel label;

    public int step = 5;
    public int position = 0;

    public Horse(int id, String name, double probability, JLabel label, int height) {
        this.id = id;
        this.name = name;
        this.probability = probability;
        this.label = label;

        for(int i = 0; i < horseIconsFrame.length; i++){
            ImageIcon horsesIcons = new ImageIcon("demo/src/main/java/com/jv01/miniGames/games/horsesRace/img/horses/horse0"+ String.valueOf(id+1) + "/f0"+(i+1)+".png");
            Image horseImage = horsesIcons.getImage().getScaledInstance(height, height, Image.SCALE_SMOOTH);

            horseIconsFrame[i]=new ImageIcon(horseImage);

            if (horseIconsFrame[i].getImageLoadStatus() == MediaTracker.COMPLETE){
                // System.out.println("Image chargée avec succès.");
            }else{
                System.out.println("Erreur lors du chargement de l'image.");
            }
        }
    }
}
