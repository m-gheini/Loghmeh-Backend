package IECA.database.mappers;

import IECA.database.DatabaseManager;
import IECA.logic.Food;
import IECA.logic.Location;
import IECA.logic.Restaurant;
import IECA.logic.SaleFood;
import IECA.servlets.FoodParty;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class Main {
    public static void main(String[] args) throws SQLException, IOException {
        Connection connection = ConnectionPool.getConnection();
        DatabaseManager dbm = new DatabaseManager();
        dbm.createDatabases();
//        RestaurantMapper rm = new RestaurantMapper(false);
//        Restaurant R1 = new Restaurant();
//        R1.setId("5e4fcf14af68ed25d5900ebc");
//        R1.setName("رستوران");
//        Location L1 = new Location();
//        L1.setX(1);
//        L1.setY(1);
//        R1.setLocation(L1);
//        R1.setLogo("https://static.snapp-food.com/200x201/cdn/29/26/9/vendor/5d346019a73f7.jpg");
//        rm.insert(R1);
//        Restaurant R2 = new Restaurant();
//        R2.setId("6e4fcf14af68ed25d5900ebc");
//        R2.setName("رستوران جدید");
//        Location L2 = new Location();
//        L2.setX(200);
//        L2.setY(30);
//        R2.setLocation(L2);
//        R2.setLogo("https://static.snapp-food.com/200x201/cdn/29/26/9/vendor/5d346019a73f7.jpg");
//        rm.insert(R2);
////        int cnt = rm.getSizeOfDatabase();
////        System.out.println("SIZE::"+cnt);
//        FoodPartyMapper fpm = new FoodPartyMapper(false);
//        SaleFood f1 = new SaleFood();
//        f1.setName("پیتزا مخصوص");
//        f1.setDescription("تهیه شده از بهترین مواد اولیه");
//        f1.setPopularity((float) 0.9);
//        f1.setOldPrice(20000);
//        f1.setPrice(17000);
//        f1.setCount(3);
//        f1.setRestaurantId("5e4fcf14af68ed25d5900ebc");
//        f1.setImage("https://static.snapp-food.com/200x201/cdn/29/26/9/vendor/5d346019a73f7.jpg");
//        SaleFood f2 = new SaleFood();
//        f2.setName("پیتزا مرغ");
//        f2.setDescription("تهیه شده از بهترین مواد اولیه");
//        f2.setPopularity((float) 0.9);
//        f2.setOldPrice(20000);
//        f2.setPrice(17000);
//        f2.setCount(3);
//        f2.setRestaurantId("5e4fcf14af68ed25d5900ebc");
//        f2.setImage("https://static.snapp-food.com/200x201/cdn/29/26/9/vendor/5d346019a73f7.jpg");
//        fpm.insert(f2);
//        List<Restaurant> res = new ArrayList<Restaurant>();
//        res = rm.searchRestaurantByName("رستوران جدید");
//        System.out.println(res.size());
//        System.out.println(res.get(0).getId());
        connection.close();
    }
}
