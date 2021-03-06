package IECA.database.mappers.restaurant;

import IECA.database.mappers.ConnectionPool;
import IECA.database.mappers.Mapper;
import IECA.database.mappers.food.FoodMapper;
import IECA.database.mappers.foodParty.FoodPartyMapper;
import IECA.logic.Food;
import IECA.logic.Location;
import IECA.logic.Restaurant;
import IECA.logic.SaleFood;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class RestaurantMapper extends Mapper<Restaurant, String> implements IRestaurantMapper {
    private static final String COLUMNS = " id, name, description, x, y, logo ";
    private static final String TABLE_NAME = "restaurant_table";

    private Boolean doManage;

    public RestaurantMapper(Boolean doManage) throws SQLException {
        if (this.doManage = doManage) {
            Connection con = ConnectionPool.getConnection();
            Statement st = con.createStatement();
//            st.executeUpdate(String.format("DROP TABLE IF EXISTS %s", TABLE_NAME));
            st.executeUpdate(String.format(
                    "CREATE TABLE if not exists %s" +
                            "(" +
                            "id varchar(50) not null, " +
                            "name varchar(100), " +
                            "description varchar(500), "+
                            "x integer, "+
                            "y integer, "+
                            "logo varchar(500), "+
                            "primary key(id) "+
                            ");",
                    TABLE_NAME));
            st.close();
            con.close();
        }
    }

    @Override
    protected String getFindStatement(ArrayList<String> keys) {
        String resId = keys.get(0);
        return "SELECT " + COLUMNS +
                " FROM " + TABLE_NAME +
                " WHERE id = "+ "'" + resId+ "'" + ";";
    }

    @Override
    protected String getInsertStatement(Restaurant restaurant) {
        return "INSERT IGNORE INTO " + TABLE_NAME +
                "(" + COLUMNS + ")" + " VALUES "+
                "("+
                "'" + restaurant.getId()+ "'," +
                "'" + restaurant.getName() + "'," +
                "'" + restaurant.getDescription() + "'," +
                restaurant.getLocation().getX() + "," +
                restaurant.getLocation().getY() + "," +
                "'" +restaurant.getLogo() + "'" +
                ");";
    }

    @Override
    protected String getDeleteStatement(ArrayList<String> keys) {
        String resId = keys.get(0);
        return "DELETE FROM " + TABLE_NAME +
                " WHERE id = " + resId + ";";
    }

    @Override
    protected Restaurant convertResultSetToObject(ResultSet rs) throws SQLException {
        Restaurant restaurant = new Restaurant();
        Location location = new Location();
        restaurant.setId(rs.getString(1));
        restaurant.setName(rs.getString(2));
        restaurant.setDescription(rs.getString(3));
        location.setX(rs.getInt(4));
        location.setY(rs.getInt(5));
        restaurant.setLocation(location);
        restaurant.setLogo(rs.getString(6));
        ArrayList<String > input = new ArrayList<>();
        input.add(restaurant.getId());
        FoodMapper foodMapper = new FoodMapper(false);
        FoodPartyMapper foodPartyMapper = new FoodPartyMapper(false);
        Connection connection = ConnectionPool.getConnection();
        ArrayList<Food> foods = foodMapper.findByForeignKey(input);
//        ArrayList<SaleFood> saleFoods = foodPartyMapper.findByForeignKey(input);
        restaurant.setMenu(foods);
//        restaurant.setSaleMenu(saleFoods);
        connection.close();
        return  restaurant;
    }

    @Override
    public List<Restaurant> findRestaurantsInRadius() throws SQLException {
        String statement = "SELECT " + COLUMNS + " FROM " + TABLE_NAME +
                " Where SQRT(POWER(x, 2) + POWER(y, 2)) <= 170";
        return executingGivenQuery(statement);
    }

    @Override
    public List<Restaurant> searchRestaurantByName(String inName) throws SQLException {
        String statement = "SELECT " + COLUMNS + " FROM " + TABLE_NAME +
                " Where name LIKE " + "'%" + inName + "%'";
        return executingGivenQuery(statement);
    }

//    @Override
//    public int getSizeOfDatabase() throws SQLException {
//        String statement = "SELECT count(id) FROM " + TABLE_NAME ;
//        try (Connection con = ConnectionPool.getConnection();
//             PreparedStatement st = con.prepareStatement(statement);
//        ) {
//            int result;
//            try {
//                result = st.executeQuery();
//                return result;
//            } catch (SQLException ex) {
//                System.out.println("error in Mapper.findByID query.");
//                throw ex;
//            }
//        }
//    }

    private List<Restaurant> executingGivenQuery(String statement) throws SQLException {
        List<Restaurant> result = new ArrayList<Restaurant>();
        try (Connection con = ConnectionPool.getConnection();
             PreparedStatement st = con.prepareStatement(statement);
        ) {
            ResultSet resultSet;
            try {
                resultSet = st.executeQuery();
                while (resultSet.next())
                    result.add(convertResultSetToObject(resultSet));
                return result;
            } catch (SQLException ex) {
                System.out.println("error in Mapper.findByID query.");
                throw ex;
            }
        }
    }

    @Override
    protected String getAllRows() {
        return "SELECT " + COLUMNS +
                " FROM " + TABLE_NAME ;
    }
}
