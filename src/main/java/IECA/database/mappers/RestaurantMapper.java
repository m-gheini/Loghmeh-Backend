package IECA.database.mappers;

import IECA.logic.Location;
import IECA.logic.Restaurant;

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
            st.executeUpdate(String.format("DROP TABLE IF EXISTS %s", TABLE_NAME));
            st.executeUpdate(String.format(
                    "CREATE TABLE  %s " +
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
        return  restaurant;
    }

    @Override
    public List<Restaurant> findRestaurantsInRadius() throws SQLException {
        List<Restaurant> nearRestaurants = new ArrayList<Restaurant>();
        String statement = "SELECT " + COLUMNS + " FROM " + TABLE_NAME +
                " Where SQRT(POWER(x, 2) + POWER(y, 2)) <= 170";
        return executingGivenQuery(nearRestaurants, statement);
    }

    @Override
    public List<Restaurant> searchRestaurantByName(String inName) throws SQLException {
        List<Restaurant> nearRestaurants = new ArrayList<Restaurant>();
        String statement = "SELECT " + COLUMNS + " FROM " + TABLE_NAME +
                " Where name LIKE " + "'%" + inName + "%'";
        return executingGivenQuery(nearRestaurants, statement);
    }

    private List<Restaurant> executingGivenQuery(List<Restaurant> result, String statement) throws SQLException {
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
}
