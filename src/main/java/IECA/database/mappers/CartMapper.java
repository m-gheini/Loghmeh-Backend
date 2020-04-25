package IECA.database.mappers;

import IECA.logic.Cart;
import IECA.logic.User;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class CartMapper extends Mapper<Cart, Integer> implements ICartMapper {
    private static final String COLUMNS = " id, restaurantId, name, number";
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
                            "restaurantId varchar(255), " +
                            "name varchar(255), " +
                            "number int, " +
                            "PRIMARY KEY(id, restaurantId, name), " +
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
        return "INSERT IGNORE INTO " + TABLE_NAME +
                "(" + COLUMNS + ")" + " VALUES "+
                "("+
                cart.getUserId()+ "," +
                "'" + cart.getFoods().get(0).getRestaurantId() + "'," +
                "'" + cart.getFoods().get(0).getName() + "'," +
                 cart.getNumberOfFood().get(0) + "," +
                ") ON DUPLICATE KEY UPDATE number = " + cart.getNumberOfFood().get(0) +";" ;
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
        return  cart;
    }

}
