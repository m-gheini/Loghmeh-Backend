package IECA.database.mappers;

import IECA.logic.*;

import java.sql.*;
import java.util.ArrayList;

public class CartMapper extends Mapper<Cart, Integer> implements ICartMapper {
    private static final String COLUMNS = " id, number, restaurantId, name";
    private static final String TABLE_NAME = "cart_table";

    private Boolean doManage;

    public CartMapper(Boolean doManage) throws SQLException {
        if (this.doManage = doManage) {
            Connection con = ConnectionPool.getConnection();
            Statement st = con.createStatement();
            st.executeUpdate(String.format("DROP TABLE IF EXISTS %s", TABLE_NAME));
            st.executeUpdate(String.format(
                    "CREATE TABLE  %s " +
                            "(" +
                            "id int, " +
                            "number int, " +
                            "restaurantId varchar(255), " +
                            "name varchar(255), " +
                            "PRIMARY KEY(id, name), " +
                            "foreign key(restaurantId, name) references food_table(restaurantId, name) on delete  cascade, " +
                            "foreign key(id) references user_table(id) on delete  cascade" +
                            ");",
                    TABLE_NAME));
            st.close();
            con.close();
        }
    }

    @Override
    protected String getFindStatement(ArrayList<Integer> keys) {
        Integer id = keys.get(0);
        return "SELECT " + COLUMNS +
                " FROM " + TABLE_NAME +
                " WHERE id = "+  id   +";";
    }

    @Override
    protected String getInsertStatement(Cart cart) {
        System.out.println("INSERT IGNORE INTO " + TABLE_NAME +
                "(" + COLUMNS + ")" + " VALUES "+
                "("+
                cart.getUserId()+ "," +
                cart.getNumberOfFood().get(0) + "," +
                "'" + cart.getFoods().get(0).getRestaurantId() + "'," +
                "'" + cart.getFoods().get(0).getName() + "'" +
                ") ON DUPLICATE KEY UPDATE number = " + cart.getNumberOfFood().get(0) +";");
        return "INSERT IGNORE INTO " + TABLE_NAME +
                "(" + COLUMNS + ")" + " VALUES "+
                "("+
                cart.getUserId()+ "," +
                cart.getNumberOfFood().get(0) + "," +
                "'" + cart.getFoods().get(0).getRestaurantId() + "'," +
                "'" + cart.getFoods().get(0).getName() + "'" +
                ") ON DUPLICATE KEY UPDATE number = " + cart.getNumberOfFood().get(0) +";";
    }
    @Override
    protected String getDeleteStatement(ArrayList<Integer> keys) {
        Integer id = keys.get(0);
        return "DELETE FROM " + TABLE_NAME +
                " WHERE id = " + id + ";";
    }

    @Override
    protected Cart convertResultSetToObject(ResultSet rs) throws SQLException {
        Cart cart = new Cart();
        cart.setUserId(rs.getInt(1));
        FoodMapper foodMapper = new FoodMapper(false);
        RestaurantMapper restaurantMapper = new RestaurantMapper(false);
        Connection connection = ConnectionPool.getConnection();
        ArrayList<String > resId = new ArrayList<String>();
        resId.add(rs.getString(3));
        cart.setRestaurantName(restaurantMapper.find(resId).getName());
        ArrayList<String> keys = new ArrayList<String>();
        keys.add(rs.getString(4));
        keys.add(rs.getString(3));
        ArrayList<Food> foods = new ArrayList<Food>();
        ArrayList<Integer> numberOfFoods = new ArrayList<Integer>();
        foods.add(foodMapper.find(keys));
        numberOfFoods.add(rs.getInt(2));
        cart.setFoods(foods);
        cart.setNumberOfFood(numberOfFoods);
        connection.close();
        return  cart;
    }

    @Override
    protected String getAllRows() {
        return "SELECT " + COLUMNS +
                " FROM " + TABLE_NAME ;
    }

    public Cart findSpecRow(Integer id,String foodName) throws SQLException {
        String stm = "SELECT " + COLUMNS + " FROM " + TABLE_NAME + " WHERE id = "+ id + " and name = " + "'" + foodName+ "'" +";";
        System.out.println("1::"+stm);
        try (Connection con = ConnectionPool.getConnection();
             PreparedStatement st = con.prepareStatement(stm)
        ) {
            ResultSet resultSet;
            try {
                resultSet = st.executeQuery();
                Object res = resultSet.next();
                if(!(Boolean)res)
                    return null;
                return convertResultSetToObject(resultSet);
            } catch (SQLException ex) {
                System.out.println("error in Mapper.findByID query.");
                throw ex;
            }
        }
    }

    public void deleteSpecRow(Integer id,String foodName) throws SQLException {
        String stm = "DELETE FROM " + TABLE_NAME + " WHERE id = " + id +" and name = " + "'" + foodName+ "'" +";";
        System.out.println("1::"+stm);
        try (Connection con = ConnectionPool.getConnection();
             PreparedStatement st = con.prepareStatement(stm)
        ) {
            try {
                st.executeUpdate();
            } catch (SQLException ex) {
                System.out.println("error in Mapper.delete query.");
                throw ex;
            }
        }
    }


}
