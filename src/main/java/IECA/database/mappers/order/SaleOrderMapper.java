package IECA.database.mappers.order;

import IECA.database.mappers.ConnectionPool;
import IECA.database.mappers.Mapper;
import IECA.database.mappers.foodParty.FoodPartyMapper;
import IECA.database.mappers.order.ISaleOrderMapper;
import IECA.logic.Cart;
import IECA.logic.SaleFood;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class SaleOrderMapper extends Mapper<Cart, Integer> implements ISaleOrderMapper {
    private static final String COLUMNS = " id, number, saleOrderIndex, status, restaurantId, name, restaurantName";
    private static final String TABLE_NAME = "saleOrder_table";

    private Boolean doManage;

    public SaleOrderMapper(Boolean doManage) throws SQLException {
        if (this.doManage = doManage) {
            Connection con = ConnectionPool.getConnection();
            Statement st = con.createStatement();
//            st.executeUpdate(String.format("DROP TABLE IF EXISTS %s", TABLE_NAME));
            st.executeUpdate(String.format(
                    "CREATE TABLE  %s if not exists" +
                            "(" +
                            "id int, " +
                            "number int, " +
                            "saleOrderIndex int, " +
                            "status varchar(255), " +
                            "restaurantId varchar(255), " +
                            "name varchar(255), " +
                            "restaurantName varchar(255), " +
                            "PRIMARY KEY(id, name, saleOrderIndex), " +
                            "foreign key(restaurantId, name) references foodParty_table(restaurantId, name) on delete  cascade, " +
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
                    " WHERE id = " + id + " and saleOrderIndex = " + index + ";";

        }
        Integer id = keys.get(0);
        return "SELECT " + COLUMNS +
                " FROM " + TABLE_NAME +
                " WHERE id = " + id + ";";
    }

    @Override
    protected String getInsertStatement(Cart cart) {
        System.out.println("INSERT IGNORE INTO " + TABLE_NAME +
                "(" + COLUMNS + ")" + " VALUES "+
                "("+
                cart.getUserId()+ "," +
                cart.getNumberOfSaleFood().get(0) + "," +
                cart.getIndex()+ "," +
                "'" + cart.getStatus() + "'," +
                "'" + cart.getSaleFoods().get(0).getRestaurantId() + "'," +
                "'" + cart.getSaleFoods().get(0).getName() + "'," +
                "'" + cart.getSaleFoods().get(0).getRestaurantName() + "'" +
                ") ON DUPLICATE KEY UPDATE number = " + "'" + cart.getStatus() + "'" +";");
        return "INSERT IGNORE INTO " + TABLE_NAME +
                "(" + COLUMNS + ")" + " VALUES "+
                "("+
                cart.getUserId()+ "," +
                cart.getNumberOfSaleFood().get(0) + "," +
                cart.getIndex()+ "," +
                "'" + cart.getStatus() + "'," +
                "'" + cart.getSaleFoods().get(0).getRestaurantId() + "'," +
                "'" + cart.getSaleFoods().get(0).getName() + "'," +
                "'" + cart.getSaleFoods().get(0).getRestaurantName() + "'" +
                ") ON DUPLICATE KEY UPDATE status = " + "'" + cart.getStatus() + "'" +";";
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
        FoodPartyMapper foodPartyMapper = new FoodPartyMapper(false);
//        RestaurantMapper restaurantMapper = new RestaurantMapper(false);
        Connection connection = ConnectionPool.getConnection();
//        ArrayList<String > resId = new ArrayList<String>();
//        resId.add(rs.getString(3));
        cart.setRestaurantName(rs.getString(7));
        ArrayList<String> keys = new ArrayList<String>();
        keys.add(rs.getString(6));
        keys.add(rs.getString(5));
        ArrayList<SaleFood> foodParties = new ArrayList<SaleFood>();
        ArrayList<Integer> numberOfSaleFoods = new ArrayList<Integer>();
        foodParties.add(foodPartyMapper.find(keys));
        numberOfSaleFoods.add(rs.getInt(2));
        cart.setSaleFoods(foodParties);
        cart.setNumberOfSaleFood(numberOfSaleFoods);
        connection.close();
        return  cart;
    }

    @Override
    protected String getAllRows() {
        return "SELECT " + COLUMNS +
                " FROM " + TABLE_NAME ;
    }

}
