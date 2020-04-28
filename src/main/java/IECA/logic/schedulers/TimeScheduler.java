package IECA.logic.schedulers;

import IECA.database.mappers.ConnectionPool;
import IECA.database.mappers.OrderMapper;
import IECA.database.mappers.SaleOrderMapper;
import IECA.logic.Cart;
import IECA.logic.RestaurantManager;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
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
                OrderMapper orderMapper = new OrderMapper(false);
                SaleOrderMapper saleOrderMapper = new SaleOrderMapper(false);
                Connection connection = ConnectionPool.getConnection();
                ArrayList<Integer> keys = new ArrayList<Integer>();
                keys.add(RestaurantManager.getInstance().getCurrentUser().getId());
                keys.add(finalIndex);
                ArrayList<Cart> order = orderMapper.findByForeignKey(keys);
                ArrayList<Cart> saleOrder = saleOrderMapper.findByForeignKey(keys);
                if(order.size() != 0) {
                    for (Cart c : order) {
                        c.setStatus("done");
                        orderMapper.insert(c);
                    }
                }
                if(saleOrder.size() != 0){
                    for (Cart c : saleOrder) {
                        c.setStatus("done");
                        saleOrderMapper.insert(c);
                    }
                }
                connection.close();

                System.out.println(RestaurantManager.getInstance().getBestTime());
                timer.cancel();
            }
        } catch (IOException | SQLException e) {
            e.printStackTrace();
        }
    }
}
