package IECA.logic.schedulers;

import IECA.database.DeliveryDataset;
import IECA.logic.Restaurant;
import IECA.logic.RestaurantManager;

import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

public class DeliveryScheduler extends TimerTask{
    Restaurant restaurant = new Restaurant();
    private Timer timer ;

    public Restaurant getRestaurant() {
        return restaurant;
    }

    public void setRestaurant(Restaurant restaurant) {
        this.restaurant = restaurant;
    }

    public DeliveryScheduler(){
        timer = new Timer();
        timer.schedule(this,0,30000);
    }
    public void run() {
        DeliveryDataset deliveryDataset = new DeliveryDataset();
        try {
            deliveryDataset.addToDataset(deliveryDataset.readFromWeb("http://138.197.181.131:8080/deliveries"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            RestaurantManager.getInstance().setDeliveries(deliveryDataset.getDeliveries());
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (deliveryDataset.getDeliveries().size()!=0){
            try {
                int finalIndex = RestaurantManager.getInstance().getCurrentUser().getOrders().size()-1;
                RestaurantManager.getInstance().getCurrentUser().getOrders().get(finalIndex).setStatus("delivering");
                RestaurantManager.getInstance().getBestDelivery(restaurant);
                TimeScheduler timeScheduler = new TimeScheduler();
            } catch (IOException e) {
                e.printStackTrace();
            }
            timer.cancel();
        }
    }
}
