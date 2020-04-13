package IECA.logic.schedulers;

import IECA.logic.RestaurantManager;

import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

public class RemainingTime extends TimerTask {
    private static final int SECOND = 1000;
    private Timer timer ;
    public RemainingTime(){
        timer = new Timer();
        timer.schedule(this,0,1000);
    }
    @Override
    public void run() {
        try {
            RestaurantManager.getInstance().updateRemainingTime();
            System.out.println("--------------------------------------------------");
            System.out.println(RestaurantManager.getInstance().getRemainingTime());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}