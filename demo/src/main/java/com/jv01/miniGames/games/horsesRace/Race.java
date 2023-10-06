package com.jv01.miniGames.games.horsesRace;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

import java.awt.*;

import java.util.ArrayList;
import java.util.Random;
import java.util.List;

public class Race {
    private HorsesRace horsesRace;

    private JLabel[] horsesLabels;

    private JLabel back;
    private int backXOffset = 0;
    private volatile ImageIcon backPic;

    private int refreshInterval = 50;

    private volatile int positionFinal = 600;
    private volatile int stepMax = 7;
    private volatile int stepMin = -4;

    private volatile int[] order;

    private volatile List<Integer> finalOrder = new ArrayList<>();

    private Random random = new Random();

    public Race(HorsesRace horsesRace){
        this.horsesRace = horsesRace;


        displayRaceScreen();
        addBack();
        addHorses();

        runRace();
    }

    private void displayRaceScreen(){
        horsesRace.refreshGamePanel();
    }

    private void addBack(){
        backPic = new ImageIcon("demo/src/main/java/com/jv01/miniGames/games/roulette/img/horseRaceBack01.jpg");
        Image backImage = backPic.getImage().getScaledInstance(4800, 800, Image.SCALE_SMOOTH);
        backPic = new ImageIcon(backImage);

        if (backPic.getImageLoadStatus() == MediaTracker.COMPLETE){
            // System.out.println("Image chargée avec succès.");
        }else{
            System.out.println("Erreur lors du chargement de l'image.");
        }

        back = new JLabel(backPic);
        back.setHorizontalAlignment(SwingConstants.CENTER);

        back.setBounds(backXOffset, 0, 4800, 800);

        horsesRace.gamePanel.add(back);
    }

    private void addHorses(){
        int height = (600 - 15 * horsesRace.horses.horses.length) / horsesRace.horses.horses.length;
        horsesLabels = new JLabel[horsesRace.horses.horses.length];

        for (int i = 0; i < horsesRace.horses.horses.length; i++){
            horsesLabels[i] = new JLabel(horsesRace.horses.horses[i].horseIconsFrame[0]);
            horsesLabels[i].setHorizontalAlignment(SwingConstants.CENTER);

            horsesLabels[i].setBounds(height-40, 100 + i * (height + 15), height, height);

            horsesRace.gamePanel.add(horsesLabels[i],1);
        }
    }

    private void changeHorseFrame(int id){
        int idFrame = (Math.abs(horsesRace.horses.horses[id].position) % 16)/4;

        horsesLabels[id].setIcon(horsesRace.horses.horses[id].horseIconsFrame[idFrame]);
    }


    private void runRace(){
        if(horsesRace.isInGame)horsesRace.arcade.setCloseButtonVisibility(false);
        order = horsesRace.horses.ordreArriveeAleatoireAvecCotes(horsesRace.horses.horses);
        final int height = (600 - 15 * horsesRace.horses.horses.length) / horsesRace.horses.horses.length;
        finalOrder.clear();

        Timer animationHorsesTimer = new Timer(refreshInterval, new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                boolean endBack = false;
                backXOffset -= stepMax*5;
                if(backXOffset<=-3250){
                    endBack = true;
                }
                if(endBack)positionFinal-=stepMax*5; 
                back.setBounds(backXOffset, 0, 4800, 800);
                for(int i = 0; i < horsesLabels.length; i++){
                    horsesRace.horses.horses[i].step = getStep(horsesRace.horses.horses[i]);
                    if(i==(order[0]-1))if(horsesRace.horses.horses[i].step<stepMax-3)horsesRace.horses.horses[i].step=stepMax-3;
                    if(i==(order[1]-1))if(horsesRace.horses.horses[i].step<stepMax-5)horsesRace.horses.horses[i].step=stepMax-5;


                    if(i!=(order[0]-1))if((horsesRace.horses.horses[i].position-horsesRace.horses.horses[order[0]-1].position)>100){
                        horsesRace.horses.horses[i].step -= 3;
                        horsesRace.horses.horses[order[0]-1].step += 2;
                    }

                    if(i!=(order[0]-1)&&i!=(order[1]-1))if((horsesRace.horses.horses[i].position-horsesRace.horses.horses[order[1]-1].position)>100){
                        horsesRace.horses.horses[i].step -= 2;
                        horsesRace.horses.horses[order[1]-1].step += 2;
                    }


                    if(backXOffset<=-3250+450){
                        horsesRace.horses.horses[order[0]-1].step = stepMax+6;
                        if(i!=(order[0]-1))horsesRace.horses.horses[i].step = stepMax - 7;
                        if(i==(order[1]-1))horsesRace.horses.horses[i].step = stepMax+3;
                        if(i==(order[3]-1))horsesRace.horses.horses[i].step = stepMax - 8;
                        if(i==(order[4]-1))horsesRace.horses.horses[i].step = stepMax - 2;
                    }
                    if(finalOrder.size()>0){
                        horsesRace.horses.horses[order[1]-1].step=stepMax+6;
                    }

                    if(horsesRace.horses.horses[i].step==0)horsesRace.horses.horses[i].step=-1;

                    boolean stop = horsesRace.horses.horses[i].position>=positionFinal;
    
                    horsesRace.horses.horses[i].position += horsesRace.horses.horses[i].step;

                    changeHorseFrame(i);
                    horsesLabels[i].setBounds(height-40 + horsesRace.horses.horses[i].position, 100 + i * (height + 15) + (horsesRace.horses.horses[i].position%50)/10, height, height);

                    if(stop)if(!horseIsInFinalList(horsesRace.horses.horses[i].id))finalOrder.add(horsesRace.horses.horses[i].id); 
                    
                }
                if(finalOrder.size() == order.length){
                    ((Timer) e.getSource()).stop();
                    stopRace();
                }
            }
        });      
        animationHorsesTimer.start();
    }

    private boolean horseIsInFinalList(int id){
        boolean isInList = false;
        for(int i:finalOrder){
            if(i==id)isInList = true;
        }
        return isInList;
    }

    private void stopRace(){
        if(horsesRace.isInGame)horsesRace.arcade.setCloseButtonVisibility(true);
        for(int h:finalOrder){
            System.out.println(h);
        }
         System.out.println(":::::::::::");
        for(int h:order){
            System.out.println(h);
        }
    }

    private int getStep(Horse horse){
        int step = horse.step;
        //int newStepMax = horsesRace.horses.horses[order[0]-1].step + 3;
        if((horse.position%(20*stepMax)<stepMax)||(horse.position<stepMax)){
            step = random.nextInt(stepMax-stepMin)+stepMin;
        }
        return step;
    }
}
