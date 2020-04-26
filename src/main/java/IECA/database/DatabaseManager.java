package IECA.database;

import IECA.database.mappers.*;
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
        boolean resDoManage, foodDoManage, saleFoodDoManage, userDoManage;
        resDoManage = !(existInDatabase("restaurant_table"));
        foodDoManage = !(existInDatabase("food_table"));
        saleFoodDoManage = !(existInDatabase("foodParty_table"));
        userDoManage = !(existInDatabase("user_table"));
        RestaurantMapper rm = new RestaurantMapper(resDoManage);
        FoodMapper fm = new FoodMapper(foodDoManage);
        FoodPartyMapper fpm = new FoodPartyMapper(saleFoodDoManage);
        FoodPartyScheduler foodPartyScheduler = new FoodPartyScheduler();
        UserMapper um = new UserMapper(userDoManage);
        Connection connection = ConnectionPool.getConnection();
        for(Restaurant res: restaurants){
            rm.insert(res);
        }
        for(Food food: foods){
            fm.insert(food);
        }
        um.insert(currentUser);
//        for(SaleFood saleFood: saleFoods){
//            fpm.insert(saleFood);
//        }
//        ArrayList<Restaurant> res;
//        res = (ArrayList<Restaurant>) rm.findRestaurantsInRadius();
//        System.out.println(res.size());
        connection.close();
    }
}
