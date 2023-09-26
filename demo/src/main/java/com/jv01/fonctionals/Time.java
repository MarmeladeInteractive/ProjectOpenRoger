package com.jv01.fonctionals;

import java.text.SimpleDateFormat;

import java.util.Calendar;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class Time {
    private Save save =  new Save();
    private String gameName;

    private long timeForOneS;

    private Calendar calendar;

    private SimpleDateFormat dateFormat;
    private SimpleDateFormat heurFormat;

    private SimpleDateFormat savedFormat;

    private int season = 0; 

    public Time(String gameName) {
        this.gameName = gameName;

        this.dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        this.heurFormat = new SimpleDateFormat("HH:mm");
        this.savedFormat = new SimpleDateFormat("dd:MM:yyyy:HH:mm");

        this.calendar = Calendar.getInstance();

        getLocalSaveGameDate(); 

        ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
        scheduler.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                avancerTemps(60); 
            }
        }, 0, timeForOneS, TimeUnit.MILLISECONDS);
    }

    public void getLocalSaveGameDate(){
        Document doc = save.getDocumentXml(gameName,"game");
        Element element = save.getElementById(doc, "time", "time");

        String date = save.getChildFromElement(element,"date");
        long secondsPerDay = Integer.parseInt(save.getChildFromElement(element,"secondsPerDay"));

        this.timeForOneS = Math.round((secondsPerDay*1.0 / 86400)*1000*60);

        String[] date01 = date.split(":");
        int day = Integer.parseInt(date01[0]);
        int mouth = Integer.parseInt(date01[1]);
        int year = Integer.parseInt(date01[2]);
        int h = Integer.parseInt(date01[3]);
        int min = Integer.parseInt(date01[4]);
        
        calendar.set(year, mouth, day, h, min);
        updateSeason();
    }

    public void avancerTemps(long secondes) {
        calendar.add(Calendar.SECOND, (int) secondes);
        updateSeason();
    }

    public void changerDate(int annee, int mois, int jour) {
        calendar.set(annee, mois - 1, jour); 
        updateSeason();
    }

    public String getDate() {
        return dateFormat.format(calendar.getTime());
    }

    public String getSaison() {
        String[] saisons = {"Hiver", "Printemps", "Été", "Automne"};
        return saisons[season];
    }
    public String getHour(){
        return heurFormat.format(calendar.getTime());
    }

    private void updateSeason() {
        int mois = calendar.get(Calendar.MONTH);
        if (mois >= 0 && mois <= 2) {
            season = 0;
        } else if (mois >= 3 && mois <= 5) {
            season = 1;
        } else if (mois >= 6 && mois <= 8) {
            season = 2;
        } else {
            season = 3;
        }
    }

    public void saveDate(){
        save.changeElementChildValue(gameName,"game","time","time","date",savedFormat.format(calendar.getTime()));
    }
}
