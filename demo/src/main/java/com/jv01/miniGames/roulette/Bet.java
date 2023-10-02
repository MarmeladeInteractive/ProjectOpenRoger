package com.jv01.miniGames.roulette;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.swing.JLabel;
import javax.swing.JPanel;

public class Bet {
    public Roulette roulette;
    public long betValue = 10;
    public long totalBet = 0;
    public long maxTotalBet = 1000;

    public int[] simpleNumberBet = new int[36];
    public int[] squareNumberBet = new int[22];
    public int[] double01NumberBet = new int[33];
    public int[] double02NumberBet = new int[24];
    public int[] twoToOneNumberBet = new int[3];
    public int[] thirdNumberBet = new int[3];
    public int[] otherNumberBet = new int[6];

    public int number0Bet = 0;

    public Bet(Roulette roulette){
        this.roulette = roulette;
        fillAll();
    }

    public void clickOnTable(int x, int y, String click){
        if(x>=100 && x<=280){
            if(y>=80 && y<=550){
                if(
                    (x>=150&&x<=170)||
                    (x>=210&&x<=230)
                ){ 
                    int step = 7;
                    if(
                        (y>=(100-step)&&y<=(100+step))||
                        (y>=(137-step)&&y<=(137+step))||
                        (y>=(176-step)&&y<=(176+step))||
                        (y>=(214-step)&&y<=(214+step))||
                        (y>=(254-step)&&y<=(254+step))||
                        (y>=(294-step)&&y<=(294+step))||
                        (y>=(334-step)&&y<=(334+step))||
                        (y>=(373-step)&&y<=(373+step))||
                        (y>=(412-step)&&y<=(412+step))||
                        (y>=(452-step)&&y<=(452+step))||
                        (y>=(489-step)&&y<=(489+step))||
                        (y>=(529-step)&&y<=(529+step))
                    ){
                        int x1 = (x - 130)/60;
                        int y1 = (y - 80)/40;

                        int double02BetIndex = 2 * y1 + x1 + 1;
                        changedouble02Bet(double02BetIndex,click);
                    }else{
                        int x1 = (x - 130)/60;
                        int y1 = (y - 100)/40;
                        int squareIndex = 2 * y1 + x1 + 1;
                        changeSquareNumberBet(squareIndex,click);   
                    }               
                }else if(
                    (y>=114&&y<=124)||
                    (y>=153&&y<=163)||
                    (y>=192&&y<=202)||
                    (y>=233&&y<=243)||
                    (y>=272&&y<=282)||
                    (y>=310&&y<=320)||
                    (y>=349&&y<=359)||
                    (y>=389&&y<=399)||
                    (y>=427&&y<=437)||
                    (y>=466&&y<=476)||
                    (y>=506&&y<=516)
                ){
                    int x1 = (x - 100)/60;
                    int y1 = (y - 100)/40;
                    int double01BetIndex = 3 * y1 + x1 + 1;
                    changedouble01Bet(double01BetIndex,click);
                }else{
                    int x1 = (x - 100)/60;
                    int y1 = (y - 80)/40;
                    int numIndex = 3 * y1 + x1 + 1;
                    changeSimpleNumberBet(numIndex,click);
                }           
            }else if(y>=0 && y<80){
                change0Bet(click);
            }else if(y>550 && y<=590){
                int x1 = (x - 100)/60;
                changeTwoToOneBet(x1,click);
            }
        }else if(x>=55 && x<100){
            if(y>=80 && y<=550){
                int y1 = (y - 80)/156;
                changeThirdBet(y1,click);
            }
        }else if(x>=10 && x<55){
            if(y>=80 && y<=550){
                int y1 = (y - 80)/78;
                changeOtherBet(y1,click);
            }
        }
    }

    private void changeSimpleNumberBet(int numIndex, String click){
        if(click.equals("left")){
            if(totalBet + betValue <= maxTotalBet){
                totalBet += betValue;
                simpleNumberBet[numIndex-1] += betValue;
            }
            changeRouletteValue();
        }else{
            if(simpleNumberBet[numIndex-1] > 0){
                if(simpleNumberBet[numIndex-1] >= betValue){
                    simpleNumberBet[numIndex-1] -= betValue;
                    totalBet -= betValue;
                }else{
                    totalBet -= simpleNumberBet[numIndex-1];
                    simpleNumberBet[numIndex-1] = 0;
                }
            }
            changeRouletteValue();
        }
        roulette.tokens.changeTokenValue(roulette.tokens.simpleNumberBet[numIndex-1], simpleNumberBet[numIndex-1]);  
    }

    private void changeSquareNumberBet(int squareIndex, String click){
        if(click.equals("left")){
            if(totalBet + betValue <= maxTotalBet){
                totalBet += betValue;
                squareNumberBet[squareIndex-1] += betValue;
            }
            changeRouletteValue();
        }else{
            if(squareNumberBet[squareIndex-1] > 0){
                if(squareNumberBet[squareIndex-1] >= betValue){
                    squareNumberBet[squareIndex-1] -= betValue;
                    totalBet -= betValue;
                }else{
                    totalBet -= squareNumberBet[squareIndex-1];
                    squareNumberBet[squareIndex-1] = 0;
                }
            }
            changeRouletteValue();
        }  
        roulette.tokens.changeTokenValue(roulette.tokens.squareNumberBet[squareIndex-1], squareNumberBet[squareIndex-1]); 
    }
    private void change0Bet(String click){
        if(click.equals("left")){
            if(totalBet + betValue <= maxTotalBet){
                totalBet += betValue;
                number0Bet += betValue;
            }
            changeRouletteValue();
        }else{
            if(number0Bet > 0){
                if(number0Bet >= betValue){
                    number0Bet -= betValue;
                    totalBet -= betValue;
                }else{
                    totalBet -= number0Bet;
                    number0Bet = 0;
                }
            }
            changeRouletteValue();
        }  
        roulette.tokens.changeTokenValue(roulette.tokens.number0Bet, number0Bet);
    }
    
    private void changedouble01Bet(int double01BetIndex, String click){
        if(click.equals("left")){
            if(totalBet + betValue <= maxTotalBet){
                totalBet += betValue;
                double01NumberBet[double01BetIndex-1] += betValue;
            }
            changeRouletteValue();
        }else{
            if(double01NumberBet[double01BetIndex-1] > 0){
                if(double01NumberBet[double01BetIndex-1] >= betValue){
                    double01NumberBet[double01BetIndex-1] -= betValue;
                    totalBet -= betValue;
                }else{
                    totalBet -= double01NumberBet[double01BetIndex-1];
                    double01NumberBet[double01BetIndex-1] = 0;
                }
            }
            changeRouletteValue();
        }  
        roulette.tokens.changeTokenValue(roulette.tokens.double01NumberBet[double01BetIndex-1], double01NumberBet[double01BetIndex-1]);
    }

    private void changedouble02Bet(int double02BetIndex, String click){
        if(click.equals("left")){
            if(totalBet + betValue <= maxTotalBet){
                totalBet += betValue;
                double02NumberBet[double02BetIndex-1] += betValue;
            }
            changeRouletteValue();
        }else{
            if(double02NumberBet[double02BetIndex-1] > 0){
                if(double02NumberBet[double02BetIndex-1] >= betValue){
                    double02NumberBet[double02BetIndex-1] -= betValue;
                    totalBet -= betValue;
                }else{
                    totalBet -= double02NumberBet[double02BetIndex-1];
                    double02NumberBet[double02BetIndex-1] = 0;
                }
            }
            changeRouletteValue();
        }  
        roulette.tokens.changeTokenValue(roulette.tokens.double02NumberBet[double02BetIndex-1], double02NumberBet[double02BetIndex-1]);
    }

    private void changeTwoToOneBet(int numIndex, String click){
        if(click.equals("left")){
            if(totalBet + betValue <= maxTotalBet){
                totalBet += betValue;
                twoToOneNumberBet[numIndex] += betValue;
            }
            changeRouletteValue();
        }else{
            if(twoToOneNumberBet[numIndex] > 0){
                if(twoToOneNumberBet[numIndex] >= betValue){
                    twoToOneNumberBet[numIndex] -= betValue;
                    totalBet -= betValue;
                }else{
                    totalBet -= twoToOneNumberBet[numIndex];
                    twoToOneNumberBet[numIndex] = 0;
                }
            }
            changeRouletteValue();
        } 
        roulette.tokens.changeTokenValue(roulette.tokens.twoToOneNumberBet[numIndex], twoToOneNumberBet[numIndex]);
    }

    private void changeThirdBet(int numIndex, String click){
        if(click.equals("left")){
            if(totalBet + betValue <= maxTotalBet){
                totalBet += betValue;
                thirdNumberBet[numIndex] += betValue;
            }
            changeRouletteValue();
        }else{
            if(thirdNumberBet[numIndex] > 0){
                if(thirdNumberBet[numIndex] >= betValue){
                    thirdNumberBet[numIndex] -= betValue;
                    totalBet -= betValue;
                }else{
                    totalBet -= thirdNumberBet[numIndex];
                    thirdNumberBet[numIndex] = 0;
                }
            }
            changeRouletteValue();
        } 
        roulette.tokens.changeTokenValue(roulette.tokens.thirdNumberBet[numIndex], thirdNumberBet[numIndex]);
    }

    private void changeOtherBet(int numIndex, String click){
        if(click.equals("left")){
            if(totalBet + betValue <= maxTotalBet){
                totalBet += betValue;
                otherNumberBet[numIndex] += betValue;
            }
            changeRouletteValue();
        }else{
            if(otherNumberBet[numIndex] > 0){
                if(otherNumberBet[numIndex] >= betValue){
                    otherNumberBet[numIndex] -= betValue;
                    totalBet -= betValue;
                }else{
                    totalBet -= otherNumberBet[numIndex];
                    otherNumberBet[numIndex] = 0;
                }
            }
            changeRouletteValue();
        }
        roulette.tokens.changeTokenValue(roulette.tokens.otherNumberBet[numIndex], otherNumberBet[numIndex]);
    }

    private void changeRouletteValue(){
        roulette.currentBet.setText(String.valueOf(totalBet));
    }
    private void fillSimpleNumberBet(){
        Arrays.fill(simpleNumberBet, 0);
        for(JLabel jl:roulette.tokens.simpleNumberBet){
            roulette.tokens.changeTokenValue(jl, 0);
        }
    }
    private void fillsquareNumberBet(){
        Arrays.fill(squareNumberBet, 0);
        for(JLabel jl:roulette.tokens.squareNumberBet){
            roulette.tokens.changeTokenValue(jl, 0);
        }
    }
    private void fillDouble01NumberBet(){
        Arrays.fill(double01NumberBet, 0);
        for(JLabel jl:roulette.tokens.double01NumberBet){
            roulette.tokens.changeTokenValue(jl, 0);
        }
    }
    private void fillDouble02NumberBet(){
        Arrays.fill(double02NumberBet, 0);
        for(JLabel jl:roulette.tokens.double02NumberBet){
            roulette.tokens.changeTokenValue(jl, 0);
        }
    }
    private void fillTwoToOneNumberBet(){
        Arrays.fill(twoToOneNumberBet, 0);
        for(JLabel jl:roulette.tokens.twoToOneNumberBet){
            roulette.tokens.changeTokenValue(jl, 0);
        }
    }
    private void fillThirdNumberBet(){
        Arrays.fill(thirdNumberBet, 0);
        for(JLabel jl:roulette.tokens.thirdNumberBet){
            roulette.tokens.changeTokenValue(jl, 0);
        }
    }
    private void fillOtherNumberBet(){
        Arrays.fill(otherNumberBet, 0);
        for(JLabel jl:roulette.tokens.otherNumberBet){
            roulette.tokens.changeTokenValue(jl, 0);
        }
    }

    private void fillAll(){
        fillSimpleNumberBet();
        fillsquareNumberBet();
        fillDouble01NumberBet();
        fillDouble02NumberBet();
        fillTwoToOneNumberBet();
        fillThirdNumberBet();
        fillOtherNumberBet();
        number0Bet = 0;
        roulette.tokens.changeTokenValue(roulette.tokens.number0Bet, 0);
    }

    private void giveMoneyToPlayer(long money){
        roulette.arcade.mainGameWindow.player.money += money;
        roulette.arcade.mainGameWindow.player.saveMoney();
        roulette.arcade.mainGameWindow.updateLabels();
    }
    private void takeMoneyToPlayer(long money){
        roulette.arcade.mainGameWindow.player.isEnoughMoney((int)money,true);
         roulette.arcade.mainGameWindow.updateLabels();
    }

    public long getResult(String number, String colors){
        long result = 0;
        int numberInt = Integer.parseInt(number);

        if(numberInt!=0)if((simpleNumberBet[numberInt-1]>0)){
            result += simpleNumberBet[numberInt-1]*37;
        }

        int yIndexMultipleNumber = (int)Math.ceil(numberInt*1.0/3.0)-1;

        if(numberInt!=0)switch (numberInt % 3) {
            case 0:
                if (yIndexMultipleNumber * 2 + 1 < 21) {
                    result += (squareNumberBet[yIndexMultipleNumber * 2 + 1] > 0) ? squareNumberBet[yIndexMultipleNumber * 2 + 1] * 9 : 0;
                }
                if (yIndexMultipleNumber - 1 >= 0) {
                    result += (squareNumberBet[(yIndexMultipleNumber - 1) * 2 + 1] > 0) ? squareNumberBet[(yIndexMultipleNumber - 1) * 2 + 1] * 9 : 0;
                }
                break;
            case 1:
                if (yIndexMultipleNumber * 2 < 21) {
                    result += (squareNumberBet[yIndexMultipleNumber * 2] > 0) ? squareNumberBet[yIndexMultipleNumber * 2] * 9 : 0;
                }
                if (yIndexMultipleNumber - 1 >= 0) {
                    result += (squareNumberBet[(yIndexMultipleNumber - 1) * 2] > 0) ? squareNumberBet[(yIndexMultipleNumber - 1) * 2] * 9 : 0;
                }
                break;
            case 2:
                if (yIndexMultipleNumber * 2 < 21) {
                    result += (squareNumberBet[yIndexMultipleNumber * 2] > 0) ? squareNumberBet[yIndexMultipleNumber * 2] * 9 : 0;
                }
                if (yIndexMultipleNumber * 2 + 1 < 21) {
                    result += (squareNumberBet[yIndexMultipleNumber * 2 + 1] > 0) ? squareNumberBet[yIndexMultipleNumber * 2 + 1] * 9 : 0;
                }
                if (yIndexMultipleNumber - 1 >= 0) {
                    result += (squareNumberBet[(yIndexMultipleNumber - 1) * 2] > 0) ? squareNumberBet[(yIndexMultipleNumber - 1) * 2] * 9 : 0;
                    result += (squareNumberBet[(yIndexMultipleNumber - 1) * 2 + 1] > 0) ? squareNumberBet[(yIndexMultipleNumber - 1) * 2 + 1] * 9 : 0;
                }
                break;
            default:
                break;
        }

        if(numberInt!=0)if(numberInt-1<=32)if(double01NumberBet[numberInt-1] > 0)result += double01NumberBet[numberInt-1] * 18;
        if(numberInt!=0)if((numberInt-1-3)>=0)if(double01NumberBet[numberInt-1-3] > 0)result += double01NumberBet[numberInt-1-3] * 18;
        
        if(numberInt!=0)switch (numberInt % 3) {
            case 0:

                    result += (double02NumberBet[yIndexMultipleNumber*2+1] > 0) ? double02NumberBet[yIndexMultipleNumber *2+1] * 18 : 0;

                    result += (twoToOneNumberBet[2] > 0) ? twoToOneNumberBet[2] * 3 : 0;
                break;
            case 1:

                    result += (double02NumberBet[yIndexMultipleNumber*2] > 0) ? double02NumberBet[yIndexMultipleNumber *2] * 18 : 0;

                    result += (twoToOneNumberBet[0] > 0) ? twoToOneNumberBet[0] * 3 : 0;
                break;
            case 2:
                    result += (double02NumberBet[(yIndexMultipleNumber*2+1)] > 0) ? double02NumberBet[(yIndexMultipleNumber *2+1)] * 18 : 0;
                    result += (double02NumberBet[(yIndexMultipleNumber*2)] > 0) ? double02NumberBet[(yIndexMultipleNumber*2)] * 18 : 0;

                    result += (twoToOneNumberBet[1] > 0) ? twoToOneNumberBet[1] * 3 : 0;
                break;
            default:
                break;
        }

        if(numberInt!=36 && numberInt!=24 && numberInt!=12)if(numberInt!=0)if(thirdNumberBet[(int)Math.ceil(numberInt/12)]>0)result+=thirdNumberBet[(int)Math.ceil(numberInt/12)]*3;
        if(numberInt==36)if(thirdNumberBet[(int)Math.ceil(2)]>0)result+=thirdNumberBet[(int)Math.ceil(2)]*3;
        if(numberInt==24)if(thirdNumberBet[(int)Math.ceil(1)]>0)result+=thirdNumberBet[(int)Math.ceil(1)]*3;
        if(numberInt==12)if(thirdNumberBet[(int)Math.ceil(0)]>0)result+=thirdNumberBet[(int)Math.ceil(0)]*3;

        if(colors.equals("noir"))if(otherNumberBet[3]>0)result+=otherNumberBet[3]*2;
        if(colors.equals("rouge"))if(otherNumberBet[2]>0)result+=otherNumberBet[2]*2;
        if(numberInt!=0)if(numberInt<=18){
            if(otherNumberBet[0]>0)result+=otherNumberBet[0]*2;
        }else{
            if(otherNumberBet[5]>0)result+=otherNumberBet[5]*2;
        }
        if(numberInt!=0)if(numberInt%2==0){
            if(otherNumberBet[1]>0)result+=otherNumberBet[1]*2;
        }else{
            if(otherNumberBet[4]>0)result+=otherNumberBet[4]*2;
        }


        
        if(totalBet<=result){
            roulette.scoreLabel.setText(String.valueOf( "Gains : " + (result - totalBet) ));
            if(roulette.isInGame)giveMoneyToPlayer(result - totalBet);
        }else{
            roulette.scoreLabel.setText(String.valueOf( "Losses : " + (totalBet - result) ));
            if(roulette.isInGame)takeMoneyToPlayer(totalBet - result);
        }

        fillAll();

        totalBet = 0;

        changeRouletteValue();
        return result;
    }
}
