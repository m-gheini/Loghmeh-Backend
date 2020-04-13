package IECA.logic.schedulers;
import IECA.database.DeliveryDataset;
import IECA.database.FoodPartyDataset;
import IECA.logic.Restaurant;
import IECA.logic.RestaurantManager;
import IECA.logic.SaleFood;

import java.io.IOException;
import java.sql.Time;
import java.util.Timer;
import java.util.TimerTask;

public class FoodPartyScheduler extends TimerTask {
    private static final int HALFHOUR=1800000;//TODO 30000
    private Timer timer;

    public FoodPartyScheduler() {
        timer = new Timer();
        timer.schedule(this, 0, HALFHOUR);
    }

    @Override
    public void run() {
        FoodPartyDataset foodPartyDataset = new FoodPartyDataset();
        try {
            RestaurantManager.getInstance().setRemainingTime(HALFHOUR);
            RemainingTime remainingTime = new RemainingTime();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            foodPartyDataset.addToDataset(foodPartyDataset.readFromWeb("http://138.197.181.131:8080/foodparty"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            RestaurantManager.getInstance().setSaleFoods(foodPartyDataset.getFoodsOnSale());
            for(SaleFood saleFood: RestaurantManager.getInstance().getCurrentUser().getMyCart().getSaleFoods()){
                if(RestaurantManager.getInstance().searchInSaleFoods(saleFood))
                    continue;
                else
                    RestaurantManager.getInstance().getCurrentUser().getMyCart().deleteSaleFood(saleFood);
                System.out.println(timer);

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
