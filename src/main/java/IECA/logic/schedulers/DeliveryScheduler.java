package IECA.logic.schedulers;

import IECA.database.DeliveryDataset;
import IECA.database.mappers.ConnectionPool;
import IECA.database.mappers.OrderMapper;
import IECA.logic.Cart;
import IECA.logic.Restaurant;
import IECA.logic.RestaurantManager;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class DeliveryScheduler extends TimerTask{
    String restaurantId = "";
    private Timer timer ;

    public String getRestaurant() {
        return restaurantId;
    }

    public void setRestaurant(String restaurantId) {
        this.restaurantId = restaurantId;
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
        } catch (IOException | SQLException e) {
            e.printStackTrace();
        }
        if (deliveryDataset.getDeliveries().size()!=0){
            try {
                //todo saleOrder
                int finalIndex = RestaurantManager.getInstance().getCurrentUser().getOrders().size()-1;
                RestaurantManager.getInstance().getCurrentUser().getOrders().get(finalIndex).setStatus("delivering");
                OrderMapper orderMapper = new OrderMapper(false);
                Connection connection = ConnectionPool.getConnection();
                ArrayList<Integer> keys = new ArrayList<Integer>();
                keys.add(RestaurantManager.getInstance().getCurrentUser().getId(),finalIndex);
                ArrayList<Cart> order = orderMapper.findByForeignKey(keys);
                for (Cart c:order){
                    c.setStatus("delivering");
                    orderMapper.insert(c);
                }
                connection.close();
                RestaurantManager.getInstance().getBestDelivery(restaurantId);
                TimeScheduler timeScheduler = new TimeScheduler();
            } catch (IOException | SQLException e) {
                e.printStackTrace();
            }
            timer.cancel();
        }
    }
}
