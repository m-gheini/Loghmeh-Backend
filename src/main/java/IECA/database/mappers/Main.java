package IECA.database.mappers;

import IECA.database.DatabaseManager;
import IECA.database.mappers.restaurant.RestaurantMapper;
import IECA.database.mappers.user.UserMapper;
import IECA.logic.*;
import IECA.servlets.FoodParty;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;

import javax.swing.*;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class Main {
    public static void main(String[] args) throws SQLException, IOException {
        String s = "leila";
        MD5 hash = new MD5();
        System.out.println("Your HashCode Generated by MD5 is: " + hash.getMd5(s));
        System.out.println("Your HashCode Generated by MD5 is: " + hash.getMd5(s));
        String token = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXUyJ9.eyJpc3MiOiJhdXRoMCJ9.AbIJTDMFc7yUa5MhvcP03nJPyCPzZtQcGEp-zWfOkEE";
//        try {
//            Algorithm algorithm = Algorithm.HMAC256("secret");
//            JWTVerifier verifier = JWT.require(algorithm)
////                    .withIssuer("auth0")
//                    .build(); //Reusable verifier instance
//            DecodedJWT jwt = verifier.verify(token);
//            System.out.println(jwt.getClaim("iss").asString());
//        } catch (JWTVerificationException exception){
//            //Invalid signature/claims
//        }
//        MD4 md = new MD4();
//        System.out.println(md);
//        UserMapper userMapper = new UserMapper(false);
//        Connection connection = ConnectionPool.getConnection();
//        ArrayList<Integer> key = new ArrayList<Integer>();
//        key.add(2);
//        userMapper.delete(key);
//        connection.close();
//        RestaurantMapper restaurantMapper= new RestaurantMapper(false);
//        Connection connection = ConnectionPool.getConnection();
//        ArrayList<String> key = new ArrayList<String>();
//        key.add("5e4fcf14af68ed25d5900e92");
//        Restaurant restaurant = restaurantMapper.find(key);
//        System.out.println(restaurant.getName());
//        connection.close();
//
//        UserMapper userMapper = new UserMapper(true);
//        FoodMapper foodMapper = new FoodMapper(true);
//        CartMapper cartMapper = new CartMapper(true);
//        Connection connection = ConnectionPool.getConnection();
//        Cart cart = new Cart();
//        cart.setUserId(1);
//        cart.setRestaurantName("تهیه غذای زیگورات");
//        ArrayList<Food> f = new ArrayList<Food>();
//        Food tempFood = new Food();
//        ArrayList<String> k = new ArrayList<String>();
//        k.add("چلو جوجه کباب");
//        k.add("5e4fcf14af68ed25d5900e95");
//        f.add(foodMapper.find(k));
//        cart.setFoods(f);
//        ArrayList<Integer> n = new ArrayList<Integer>();
//        n.add(1);
//        cart.setNumberOfFood(n);
//        cartMapper.insert(cart);
//        ArrayList<Integer> nn = new ArrayList<Integer>();
//        nn.add(2);
//        cart.setNumberOfFood(nn);
//        cartMapper.insert(cart);
//        User u = userMapper.find(n);
//        System.out.println(u.getMyCart().getFoods());
//        System.out.println(u.getMyCart().getNumberOfFood());
//        DatabaseManager dbm = new DatabaseManager();
//        dbm.createDatabases();
//        RestaurantMapper rm = new RestaurantMapper(true);
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
//        FoodPartyMapper fpm = new FoodPartyMapper(true);
//        SaleFood f1 = new SaleFood();
//        f1.setName("پیتزا مخصوص");
//        f1.setDescription("تهیه شده از بهترین مواد اولیه");
//        f1.setPopularity((float) 0.9);
//        f1.setOldPrice(20000);
//        f1.setPrice(17000);
//        f1.setCount(3);
//        f1.setRestaurantId("5e4fcf14af68ed25d5900ebc");
//        f1.setImage("https://static.snapp-food.com/200x201/cdn/29/26/9/vendor/5d346019a73f7.jpg");
//        fpm.insert(f1);
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
//        connection.close();
    }
}
