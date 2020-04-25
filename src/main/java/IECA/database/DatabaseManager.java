package IECA.database;

import IECA.database.mappers.ConnectionPool;
import IECA.database.mappers.RestaurantMapper;
import IECA.logic.Food;
import IECA.logic.Restaurant;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;

public class DatabaseManager {
    private ArrayList<Restaurant> restaurants;
    private ArrayList<Food> foods ;

    public DatabaseManager() throws IOException {
        RestaurantDataset restaurantDataset = new RestaurantDataset();
        restaurantDataset.addToDataset(restaurantDataset.readFromWeb("http://138.197.181.131:8080/restaurants"));
        restaurants = restaurantDataset.getRestaurants();
        foods = restaurantDataset.getFoods();
//        currentUser = new User();
//        deliveries = new ArrayList<Delivery>();
//        remainingTime = 0;
//        FoodPartyScheduler foodPartyScheduler = new FoodPartyScheduler();
//        users=new ArrayList<User>();
//        users.add(currentUser);
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
        boolean doManage;
        doManage = !(existInDatabase("restaurant_table"));
        RestaurantMapper rm = new RestaurantMapper(doManage);
        Connection connection = ConnectionPool.getConnection();
        for(Restaurant res: restaurants){
            rm.insert(res);
        }
        connection.close();
    }
}
