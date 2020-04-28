package IECA.database;

import IECA.database.mappers.*;
import IECA.database.mappers.cart.CartMapper;
import IECA.database.mappers.cart.SaleCartMapper;
import IECA.database.mappers.food.FoodMapper;
import IECA.database.mappers.foodParty.FoodPartyMapper;
import IECA.database.mappers.order.OrderMapper;
import IECA.database.mappers.restaurant.RestaurantMapper;
import IECA.database.mappers.user.UserMapper;
import IECA.logic.*;
import IECA.logic.schedulers.FoodPartyScheduler;

import java.io.IOException;
import java.sql.*;
import java.util.*;


public class DatabaseManager {
    private static DatabaseManager instance;
    private ArrayList<Restaurant> restaurants;
    private ArrayList<Food> foods ;
    private User currentUser = new User();
    private ArrayList<SaleFood> saleFoods;

    public DatabaseManager() throws IOException {
        RestaurantDataset restaurantDataset = new RestaurantDataset();
        restaurantDataset.addToDataset(restaurantDataset.readFromWeb("http://138.197.181.131:8080/restaurants"));
        restaurants = restaurantDataset.getRestaurants();
        foods = restaurantDataset.getFoods();
//        saleFoods = restaurantDataset.getFoodsOnSale();
//        currentUser = new User();
//        deliveries = new ArrayList<Delivery>();
//        remainingTime = 0;
//        FoodPartyScheduler foodPartyScheduler = new FoodPartyScheduler();
//        users=new ArrayList<User>();
//        users.add(currentUser);
    }

    public static DatabaseManager getInstance() throws IOException {
        if (instance == null)
            instance = new DatabaseManager();
        return instance;
    }

    public boolean existInDatabase(String tableName) throws SQLException {
        boolean result = false;
        Connection connection = ConnectionPool.getConnection();
        DatabaseMetaData md = connection.getMetaData();
        ResultSet rs = md.getTables("loghmeh", null, "%", null);
        while (rs.next()) {
            if(rs.getString(3).equals(tableName)) {
                result = true;
                break;
            }
        }
        connection.close();
        return result;
    }

//    public int getSizeOfDatabase(String TABLE_NAME) throws SQLException {
//        int result = -1;
//        String statement = "SELECT count(id) AS resNum FROM " + TABLE_NAME ;
//        try (Connection con = ConnectionPool.getConnection();
//             PreparedStatement st = con.prepareStatement(statement);
//        ) {
//            ResultSet resultSet;
//            try {
//                resultSet = st.executeQuery();
//                if(resultSet.next())
//                    result = resultSet.getInt("resNum");
//                return result;
//            } catch (SQLException ex) {
//                System.out.println("error in Mapper.findByID query.");
//                throw ex;
//            }
//        }
//    }

    public void createDatabases() throws SQLException {
        System.out.println("IN DATABASE MANAGER!!!");
        boolean resDoManage, foodDoManage, saleFoodDoManage, userDoManage, cartDoManage, orderDoManage, saleCDoManage, saleOManage;
        resDoManage = !(existInDatabase("restaurant_table"));
        foodDoManage = !(existInDatabase("food_table"));
        saleFoodDoManage = !(existInDatabase("foodparty_table"));
        userDoManage = !(existInDatabase("user_table"));
        cartDoManage = !(existInDatabase("cart_table"));
        orderDoManage = !(existInDatabase("order_table"));
        saleCDoManage = !(existInDatabase("salecart_table"));
        saleOManage = !(existInDatabase("saleorder_table"));
        RestaurantMapper rm = new RestaurantMapper(resDoManage);
        FoodMapper fm = new FoodMapper(foodDoManage);
        System.out.println("AA::"+saleFoodDoManage);
        FoodPartyMapper fpm = new FoodPartyMapper(saleFoodDoManage);
        UserMapper um = new UserMapper(userDoManage);
        CartMapper cm = new CartMapper(cartDoManage);
        OrderMapper om = new OrderMapper(orderDoManage);
        SaleCartMapper scm = new SaleCartMapper(saleCDoManage);
        //SaleOrderMapper som = new SaleOrderMapper(saleOManage);
        Connection connection = ConnectionPool.getConnection();
        for(Restaurant res: restaurants){
            rm.insert(res);
        }
        for(Food food: foods){
            fm.insert(food);
        }
        um.insert(currentUser);
        System.out.println("HEREE");
        System.out.println(um.convertAllResultToObject().get(0).getCredit());
        FoodPartyScheduler foodPartyScheduler = new FoodPartyScheduler();
        connection.close();
    }

    public ArrayList<Restaurant> getPermanentRestaurants() throws SQLException {
        ArrayList<Restaurant> result;
        RestaurantMapper rm = new RestaurantMapper(false);
        Connection connection = ConnectionPool.getConnection();
        result = rm.convertAllResultToObject();
        connection.close();
        return result;
    }

    public ArrayList<Food> getPermanentFoods() throws SQLException {
        ArrayList<Food> result;
        FoodMapper fm = new FoodMapper(false);
        Connection connection = ConnectionPool.getConnection();
        result = fm.convertAllResultToObject();
        connection.close();
        return result;
    }

    public ArrayList<SaleFood> getPermanentSaleFoods() throws SQLException {
        ArrayList<SaleFood> result;
        FoodPartyMapper fpm = new FoodPartyMapper(false);
        Connection connection = ConnectionPool.getConnection();
        result = fpm.convertAllResultToObject();
        connection.close();
        return result;
    }

    public ArrayList<User> getPermanentUser() throws SQLException {
        ArrayList<User> result;
        UserMapper um = new UserMapper(false);
        Connection connection = ConnectionPool.getConnection();
        result = um.convertAllResultToObject();
        connection.close();
        return result;
    }

}
