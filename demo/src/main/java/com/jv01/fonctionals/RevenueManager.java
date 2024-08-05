package com.jv01.fonctionals;

import com.jv01.generations.MainGameWindow;

public class RevenueManager implements Time.NewDayListener{
    MainGameWindow mainGameWindow;
    Time time;

    public RevenueManager(MainGameWindow mainGameWindow) {
        this.mainGameWindow = mainGameWindow;
        this.time = mainGameWindow.date;

        this.time.addNewDayListener(this);
    }

    @Override
    public void onNewDay() {
        updateRevenues();
    }

    public void updateRevenues(){
        mainGameWindow.player.addMoney(100);
    }
}
