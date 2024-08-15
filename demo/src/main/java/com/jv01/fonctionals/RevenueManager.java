package com.jv01.fonctionals;

import com.jv01.fonctionals.ownerships.PlayerOwnerships;
import com.jv01.generations.MainGameWindow;

public class RevenueManager implements Time.NewDayListener{
    MainGameWindow mainGameWindow;
    Time time;

    PlayerOwnerships playerOwnerships;

    public RevenueManager(MainGameWindow mainGameWindow) {
        this.mainGameWindow = mainGameWindow;
        this.time = mainGameWindow.date;

        this.time.addNewDayListener(this);

        this.playerOwnerships = mainGameWindow.playerOwnerships;
    }

    @Override
    public void onNewDay() {
        updateRevenues();
    }

    public void updateRevenues(){
        long revenues = 0L;

        revenues += playerOwnerships.getDailyRevenues();

        mainGameWindow.player.addMoney(revenues);
    }
}
