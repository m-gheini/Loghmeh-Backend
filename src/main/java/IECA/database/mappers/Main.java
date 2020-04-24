package IECA.database.mappers;

import IECA.logic.Food;
import IECA.logic.Location;
import IECA.logic.SaleFood;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

public class Main {
    public static void main(String[] args) throws SQLException {
        FoodPartyMapper fm = new FoodPartyMapper(false);
        Connection connection = ConnectionPool.getConnection();
        SaleFood f1 = new SaleFood();
        f1.setName("پیتزا مخصوص");
        f1.setDescription("تهیه شده از بهترین مواد اولیه");
        f1.setPopularity((float) 0.9);
        f1.setOldPrice(20000);
        f1.setPrice(17000);
        f1.setCount(3);
        f1.setRestaurantId("5e4fcf14af68ed25d5900ebc");
        f1.setImage("https://static.snapp-food.com/200x201/cdn/29/26/9/vendor/5d346019a73f7.jpg");
        fm.insert(f1);

        ArrayList<String> keys = new ArrayList<String>();
        keys.add("5e4fcf14af68ed25d5900ebc");
        ArrayList<SaleFood> f = fm.findByForeignKey(keys);
        for(SaleFood food: f){
            System.out.println(food);

        }

//        fm.delete(keys);
//        Location l = lm.find(1);
//        System.out.println(l);
//        lm.delete(2);
//        lm.delete(1);
        connection.close();
    }
}
