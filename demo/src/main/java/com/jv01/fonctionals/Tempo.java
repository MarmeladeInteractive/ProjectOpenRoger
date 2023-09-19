package com.jv01.fonctionals;

public class Tempo {
    public boolean isRunning = false;
    public long currentTimeMillis;
    public long StopTimeMillis;

    public void start(int milliseconds) {
        currentTimeMillis = System.currentTimeMillis();
        StopTimeMillis = currentTimeMillis + milliseconds;

        if(StopTimeMillis >= currentTimeMillis)isRunning = true;
    }

    public boolean isRunning(){
        currentTimeMillis = System.currentTimeMillis();
        if(StopTimeMillis <= currentTimeMillis)isRunning = false;
        return isRunning;
    }
}
