package IECA.logic.schedulers;
import IECA.database.DatabaseManager;
import IECA.database.DeliveryDataset;
import IECA.database.FoodPartyDataset;
import IECA.database.mappers.ConnectionPool;
import IECA.database.mappers.FoodPartyMapper;
import IECA.logic.Restaurant;
import IECA.logic.RestaurantManager;
import IECA.logic.SaleFood;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Time;
import java.util.Timer;
import java.util.TimerTask;

public class FoodPartyScheduler extends TimerTask {
    private static final int HALFHOUR=60000;//TODO 30000
    private Timer timer;
    private RemainingTime remainingTime;

    public FoodPartyScheduler() {
        timer = new Timer();
        remainingTime = new RemainingTime();
        timer.schedule(this, 0, HALFHOUR);
    }

    @Override
    public void run() {
        remainingTime.run();
        try {
            RestaurantManager.getInstance().setRemainingTime(HALFHOUR);
        } catch (IOException | SQLException e) {
            e.printStackTrace();
        }
        FoodPartyDataset foodPartyDataset = new FoodPartyDataset();
        try {
            RestaurantManager.getInstance().setRemainingTime(HALFHOUR);
        } catch (IOException | SQLException e) {
            e.printStackTrace();
        }
        try {
            foodPartyDataset.addToDataset(foodPartyDataset.readFromWeb("http://138.197.181.131:8080/foodparty"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        try{
            Connection connection = ConnectionPool.getConnection();
            System.out.println("HEREEEE.");
            System.out.println("NOWWW");
            //boolean DoManage = ! DatabaseManager.getInstance().existInDatabase("foodParty_table");
            //System.out.println("HII"+DoManage);
            FoodPartyMapper fpm = new FoodPartyMapper(false);
            fpm.emptyTable();
            System.out.println("AA");
            for(SaleFood saleFood : foodPartyDataset.getFoodsOnSale()) {
                fpm.insert(saleFood);
            }
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            //RestaurantManager.getInstance().setSaleFoods(foodPartyDataset.getFoodsOnSale());
            RestaurantManager.getInstance().setSaleFoods(DatabaseManager.getInstance().getPermanentSaleFoods());
            for(SaleFood saleFood: RestaurantManager.getInstance().getCurrentUser().getMyCart().getSaleFoods()){
                if(RestaurantManager.getInstance().searchInSaleFoods(saleFood))
                    continue;
                else
                    RestaurantManager.getInstance().getCurrentUser().getMyCart().deleteSaleFood(saleFood);
                //System.out.println(timer);

            }
        } catch (IOException | SQLException e) {
            e.printStackTrace();
        }
    }
}
