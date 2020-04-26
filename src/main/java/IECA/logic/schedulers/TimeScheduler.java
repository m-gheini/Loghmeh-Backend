package IECA.logic.schedulers;

import IECA.logic.RestaurantManager;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Timer;
import java.util.TimerTask;

public class TimeScheduler extends TimerTask {
    private static final int SECOND = 1000;
    private Timer timer ;
    public TimeScheduler(){
        timer = new Timer();
        timer.schedule(this,0,1000);
    }
    @Override
    public void run() {
        try {
            int newValue = (RestaurantManager.getInstance().getBestTime())-1;
            RestaurantManager.getInstance().setBestTime(newValue);
            if(RestaurantManager.getInstance().getBestTime()<=0){
                int finalIndex = RestaurantManager.getInstance().getCurrentUser().getOrders().size()-1;
                RestaurantManager.getInstance().getCurrentUser().getOrders().get(finalIndex).setStatus("done");
                System.out.println(RestaurantManager.getInstance().getBestTime());
                timer.cancel();
            }
        } catch (IOException | SQLException e) {
            e.printStackTrace();
        }
    }
}
