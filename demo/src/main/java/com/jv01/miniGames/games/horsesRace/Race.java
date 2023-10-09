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
        
        runRace();
    }

    private void displayRaceScreen(){
        horsesRace.refreshGamePanel();
        addBack();
        addHorses();
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
        showResults();
    }

    private void showResults() {
        horsesRace.refreshGamePanel();

        backPic = new ImageIcon("demo/src/main/java/com/jv01/miniGames/games/horsesRace/img/null.png");
        Image backImage = backPic.getImage().getScaledInstance(horsesRace.boxSize, horsesRace.boxSize, Image.SCALE_SMOOTH);
        backPic = new ImageIcon(backImage);

        if (backPic.getImageLoadStatus() == MediaTracker.COMPLETE){
            // System.out.println("Image chargée avec succès.");
        }else{
            System.out.println("Erreur lors du chargement de l'image.");
        }

        back = new JLabel(backPic);
        back.setHorizontalAlignment(SwingConstants.CENTER);

        JPanel panel = createResultHorseBox(finalOrder.get(0)-1,"1er",new Color(0,193,12));
        panel.setBounds(horsesRace.boxSize/2-100, horsesRace.boxSize/2-200-50, 200, 400);
        back.add(panel);

        panel = createResultHorseBox(finalOrder.get(1)-1,"2nd",new Color(155,193,0));
        panel.setBounds(horsesRace.boxSize/2-100-200-40, horsesRace.boxSize/2-(400)/2, 200, 400-50);
        back.add(panel);

        panel = createResultHorseBox(finalOrder.get(2)-1,"3éme",new Color(195,137,0));
        panel.setBounds(horsesRace.boxSize/2-100+200+40, horsesRace.boxSize/2-200+50, 200, 300);
        back.add(panel);

        JButton runRaceButton = new JButton("Lancer une nouvelle course");
        runRaceButton.setBounds(horsesRace.boxSize - 200 - 80, (horsesRace.boxSize)-50-100, 200, 50);

        JLabel gain = new JLabel("");
        gain.setFont(new Font("Arial", Font.PLAIN, 24));
        gain.setHorizontalAlignment(SwingConstants.CENTER);
        gain.setBounds(horsesRace.boxSize/2 -100, (horsesRace.boxSize/2) +100, 200, 200);
        String gainText = "";
        double totGain;

        if(horsesRace.horses.selectedHorse == finalOrder.get(0)-1){
            totGain = horsesRace.currentBet*1.0 * horsesRace.horses.horses[horsesRace.horses.selectedHorse].probability;

            gainText += "Gain : " + String.valueOf((long)(totGain));
            if(horsesRace.isInGame){
                horsesRace.arcade.mainGameWindow.player.money += (long)(totGain-horsesRace.currentBet);
                horsesRace.arcade.mainGameWindow.player.saveMoney();
                horsesRace.arcade.mainGameWindow.updateLabels();
                horsesRace.arcade.saveXml(horsesRace.currentBet,(long)(totGain-horsesRace.currentBet));
            }
            gain.setForeground(Color.GREEN);
        }else{
            gainText += "Perte : -" + horsesRace.currentBet;
            if(horsesRace.isInGame){
                horsesRace.arcade.mainGameWindow.player.money -= (long)horsesRace.currentBet;
                horsesRace.arcade.mainGameWindow.player.saveMoney();
                horsesRace.arcade.mainGameWindow.updateLabels();
                horsesRace.arcade.saveXml(horsesRace.currentBet,(-1)*(horsesRace.currentBet));
            }
            gain.setForeground(Color.RED);
        }
        gain.setText(gainText);
        back.add(gain);
 
        runRaceButton.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                horsesRace.startGame();
            }
        });

        back.add(runRaceButton);

        back.setBounds(0, 0, horsesRace.boxSize, horsesRace.boxSize);

        horsesRace.gamePanel.add(back);
    }
    private JPanel createResultHorseBox(int id, String po, Color color) {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));  
        panel.setBackground(color);

        JPanel panelPic = new JPanel();   
        panelPic.setPreferredSize(new Dimension(100, 100));
        panelPic.add(horsesLabels[id]);
        panelPic.setBackground(new Color(205,205,205));
        panel.add(panelPic);
    
        JLabel nameValueSquareLabel = new JLabel(horsesRace.horses.horses[id].name);
        nameValueSquareLabel.setFont(new Font("Arial", Font.PLAIN, 20));
        nameValueSquareLabel.setHorizontalAlignment(SwingConstants.CENTER);
        nameValueSquareLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        nameValueSquareLabel.setPreferredSize(new Dimension(nameValueSquareLabel.getPreferredSize().width, 80));
        panel.add(nameValueSquareLabel);

        JLabel coteValueSquareLabel = new JLabel(String.valueOf(horsesRace.horses.horses[id].probability));
        coteValueSquareLabel.setFont(new Font("Arial", Font.PLAIN, 22));
        coteValueSquareLabel.setHorizontalAlignment(SwingConstants.CENTER);
        coteValueSquareLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        coteValueSquareLabel.setPreferredSize(new Dimension(coteValueSquareLabel.getPreferredSize().width, 40));
        panel.add(coteValueSquareLabel);
    
        JLabel poValueSquareLabel = new JLabel(po);
        poValueSquareLabel.setFont(new Font("Arial", Font.PLAIN, 24));
        poValueSquareLabel.setHorizontalAlignment(SwingConstants.CENTER);
        poValueSquareLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        poValueSquareLabel.setPreferredSize(new Dimension(nameValueSquareLabel.getPreferredSize().width, 40));
        panel.add(poValueSquareLabel);

        JLabel finValueSquareLabel = new JLabel("");
        panel.add(finValueSquareLabel);
    
        return panel;
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
