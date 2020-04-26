package IECA.database.mappers;

import IECA.logic.Cart;
import IECA.logic.Food;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class OrderMapper extends Mapper<Cart, Integer> implements IOrderMapper {
    private static final String COLUMNS = " id, number, index, status, restaurantId, name";
    private static final String TABLE_NAME = "order_table";

    private Boolean doManage;

    public OrderMapper(Boolean doManage) throws SQLException {
        if (this.doManage = doManage) {
            Connection con = ConnectionPool.getConnection();
            Statement st = con.createStatement();
            st.executeUpdate(String.format("DROP TABLE IF EXISTS %s", TABLE_NAME));
            st.executeUpdate(String.format(
                    "CREATE TABLE  %s " +
                            "(" +
                            "id int, " +
                            "number int, " +
                            "index int, " +
                            "status varchar(255), " +
                            "restaurantId varchar(255), " +
                            "name varchar(255), " +
                            "PRIMARY KEY(id, index, name), " +
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
        if(keys.size()==2){
            Integer id = keys.get(0);
            Integer index = keys.get(1);
            return "SELECT " + COLUMNS +
                    " FROM " + TABLE_NAME +
                    " WHERE id = " + id + "and index = " + index + ";";

        }
        Integer id = keys.get(0);
        return "SELECT " + COLUMNS +
                " FROM " + TABLE_NAME +
                " WHERE id = " + id + ";";

    }

    @Override
    protected String getInsertStatement(Cart cart) {
        return "INSERT IGNORE INTO " + TABLE_NAME +
                "(" + COLUMNS + ")" + " VALUES "+
                "("+
                cart.getUserId()+ "," +
                cart.getNumberOfFood().get(0) + "," +
                cart.getIndex()+ "," +
                "'" + cart.getStatus() + "'," +
                "'" + cart.getFoods().get(0).getRestaurantId() + "'," +
                "'" + cart.getFoods().get(0).getName() + "'" +
                ") ON DUPLICATE KEY UPDATE status = " + cart.getStatus() +";";
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
        cart.setIndex(rs.getInt(3));
        cart.setStatus(rs.getString(4));
        FoodMapper foodMapper = new FoodMapper(false);
        RestaurantMapper restaurantMapper = new RestaurantMapper(false);
        Connection connection = ConnectionPool.getConnection();
        ArrayList<String > resId = new ArrayList<String>();
        resId.add(rs.getString(5));
        cart.setRestaurantName(restaurantMapper.find(resId).getName());
        ArrayList<String> keys = new ArrayList<String>();
        keys.add(rs.getString(6));
        keys.add(rs.getString(5));
        ArrayList<Food> foods = new ArrayList<Food>();
        ArrayList<Integer> numberOfFoods = new ArrayList<Integer>();
        foods.add(foodMapper.find(keys));
        numberOfFoods.add(rs.getInt(2));
        cart.setFoods(foods);
        cart.setNumberOfFood(numberOfFoods);
        connection.close();
        return  cart;
    }

}
