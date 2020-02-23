package IECA.logic.schedulers;

import IECA.logic.RestaurantManager;

import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

public class TimeScheduler extends TimerTask {
    private Timer timer ;
    public TimeScheduler(){
        timer = new Timer();
        timer.schedule(this,0,1000);
    }
    @Override
    public void run() {
        try {
            RestaurantManager.getInstance().setBestTime(RestaurantManager.getInstance().getBestTime()-1);
            if(RestaurantManager.getInstance().getBestTime()<=0){
                RestaurantManager.getInstance().getCurrentUser().getMyCart().setStatus("done");
                System.out.println(RestaurantManager.getInstance().getBestTime());
                timer.cancel();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
