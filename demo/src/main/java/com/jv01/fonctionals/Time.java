package com.jv01.fonctionals;

import java.text.SimpleDateFormat;

import java.util.Calendar;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import com.jv01.screens.NewsWindow;

public class Time {
    private Save save =  new Save();
    private String gameName;

    private volatile long timeForOneS;

    private Calendar calendar;

    private SimpleDateFormat dateFormat;
    private SimpleDateFormat hourFormat;

    private SimpleDateFormat savedFormat;

    private int season = 0; 

    public int nightFilterOpacity = 0;

    public volatile boolean isTime = true;
    private boolean newsPaperExist = false;

    private String[] saisons = {"Hiver", "Printemps", "Été", "Automne"};

    ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

    public Time(String gameName) {
        this.gameName = gameName;

        this.dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        this.hourFormat = new SimpleDateFormat("HH:mm");
        this.savedFormat = new SimpleDateFormat("dd:MM:yyyy:HH:mm");

        this.calendar = Calendar.getInstance();

        getLocalSaveGameDate(); 
        if(!isTime)timeForOneS = 1000;
        
        scheduler.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                if(isTime){advanceTime(60);}
            }
        }, 0, timeForOneS, TimeUnit.MILLISECONDS);

        updateNightFilterOpacity();
    }

    public void getLocalSaveGameDate(){
        Document doc = save.getDocumentXml(gameName,"game");
        Element element = save.getElementById(doc, "time", "time");

        String date = save.getChildFromElement(element,"date");
        long secondsPerDay = Integer.parseInt(save.getChildFromElement(element,"secondsPerDay"));

        if(secondsPerDay <= 0)isTime = false;

        this.timeForOneS = Math.round((secondsPerDay*1.0 / 86400)*1000*60);

        String[] date01 = date.split(":");
        int day = Integer.parseInt(date01[0]);
        int mouth = Integer.parseInt(date01[1]);
        int year = Integer.parseInt(date01[2]);
        int h = Integer.parseInt(date01[3]);
        int min = Integer.parseInt(date01[4]);
        
        calendar.set(year, mouth-1, day, h, min);
        updateSeason();
    }

    public void advanceTime(long secondes) {
        calendar.add(Calendar.SECOND, (int) secondes);
        updateSeason();
        updateNightFilterOpacity();
        getNewsPaper();
    }

    public void changeDate(int year, int mouth, int day) {
        calendar.set(year, mouth - 1, day); 
        updateSeason();
    }

    public String getDate() {
        return dateFormat.format(calendar.getTime());
    }

    public String getSaison() {
        return saisons[season];
    }

    public String getHour(){
        return hourFormat.format(calendar.getTime());
    }

    public void setHour(String hour){
        try {
            String[] hourString = hour.split(":");
            int[] hourInt = new int[]{Integer.parseInt(hourString[0]),Integer.parseInt(hourString[1])};

            if(hourInt[0]>=0 && hourInt[0]<=24 && hourInt[1]>=0 && hourInt[1]<=60){
                calendar.set(Calendar.HOUR_OF_DAY, hourInt[0]);
                calendar.set(Calendar.MINUTE, hourInt[1]);
                updateSeason();
                updateNightFilterOpacity();
            }else{
                
            } 
        } catch (NumberFormatException e) {

        }
    }

    private void updateSeason() {
        int mouth = calendar.get(Calendar.MONTH);
        if (mouth >= 0 && mouth <= 2) {
            season = 0;
        } else if (mouth >= 3 && mouth <= 5) {
            season = 1;
        } else if (mouth >= 6 && mouth <= 8) {
            season = 2;
        } else {
            season = 3;
        }
    }

    private void updateNightFilterOpacity(){
        int hour = Integer.parseInt(getHour().split(":")[0]);
        boolean isDay = false;
        if(hour > 6 && hour < 18){
            isDay = true;
        }else{
            isDay = false;
        }

        int minOpacity = 0;
        int maxOpacity = 200;

        if(isDay){
            nightFilterOpacity = minOpacity;
        }else{
            nightFilterOpacity = maxOpacity;
        }  

        if (hour > 12 && hour < 24) {
            nightFilterOpacity = minOpacity + (int)Math.round((maxOpacity - minOpacity) * ((hour-12) / 12.0)); 
        } else {
            nightFilterOpacity =  (int)Math.round((maxOpacity - minOpacity) * ((12-hour) / 12.0));
        }
    }

    private void getNewsPaper(){
        int hour = Integer.parseInt(getHour().split(":")[0]);
        if(hour == 8 && this.newsPaperExist==false){
            NewsWindow morningNewspaperWindow = new NewsWindow(gameName);
            morningNewspaperWindow.setVisible(true);
            this.newsPaperExist = true;
        }else if (hour == 8 && this.newsPaperExist==true){
            //
        }else if (hour != 8 && this.newsPaperExist==true){
            this.newsPaperExist = false;
        }
    }

    public void saveDate(){
        save.changeElementChildValue(gameName,"game","time","time","date",savedFormat.format(calendar.getTime()));
    }

    public void changeTimeForDay(long newTime) {
        save.changeElementChildValue(gameName, "game", "time", "time", "secondsPerDay", String.valueOf(newTime));
        if (newTime <= 0) {
            isTime = false;
            this.timeForOneS = 1000;
            scheduler.shutdown();
        } else {
            isTime = true;
            scheduler.shutdown();
            this.timeForOneS = Math.round((newTime * 1.0 / 86400) * 1000 * 60);
            scheduler = Executors.newScheduledThreadPool(1); 
            scheduler.scheduleAtFixedRate(new Runnable() {
                @Override
                public void run() {
                    if (isTime) {
                        advanceTime(60);
                    }
                }
            }, 0, timeForOneS, TimeUnit.MILLISECONDS);
        }
    }
}
