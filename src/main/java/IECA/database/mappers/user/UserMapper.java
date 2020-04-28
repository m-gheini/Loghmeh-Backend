package IECA.database.mappers.user;

import IECA.database.mappers.ConnectionPool;
import IECA.database.mappers.Mapper;
import IECA.database.mappers.cart.CartMapper;
import IECA.database.mappers.cart.SaleCartMapper;
import IECA.database.mappers.order.OrderMapper;
import IECA.logic.Cart;
import IECA.logic.Food;
import IECA.logic.SaleFood;
import IECA.logic.User;
import org.springframework.core.annotation.Order;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;

public class UserMapper extends Mapper<User, Integer> implements IUserMapper {
    private static final String COLUMNS = " id, name, familyName, email, credit, phoneNumber";
    private static final String TABLE_NAME = "user_table";

    private Boolean doManage;

    public UserMapper(Boolean doManage) throws SQLException {
        if (this.doManage = doManage) {
            Connection con = ConnectionPool.getConnection();
            Statement st = con.createStatement();
            st.executeUpdate(String.format("DROP TABLE IF EXISTS %s", TABLE_NAME));
            st.executeUpdate(String.format(
                    "CREATE TABLE  %s " +
                            "(" +
                            "id int, " +
                            "name varchar(255), " +
                            "familyName varchar(255), " +
                            "email varchar(255), " +
                            "credit int, " +
                            "phoneNumber varchar(255), " +
                            "PRIMARY KEY(id) " +
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
    protected String getInsertStatement(User user) {
        return "INSERT IGNORE INTO " + TABLE_NAME +
                "(" + COLUMNS + ")" + " VALUES "+
                "("+
                user.getId()+ "," +
                "'" + user.getName() + "'," +
                "'" + user.getFamilyName() + "'," +
                "'" + user.getEmail() + "'," +
                user.getCredit() + "," +
                "'" +user.getPhoneNumber() + "'" +
                ") ON DUPLICATE KEY UPDATE credit = " + user.getCredit() +";";
    }

    @Override
    protected String getDeleteStatement(ArrayList<Integer> keys) {
        Integer id = keys.get(0);
        return "DELETE FROM " + TABLE_NAME +
                " WHERE id = " + id + ";";
    }
    @Override
    public User find(ArrayList<Integer> keys) throws SQLException {
        Integer primaryKey = (Integer) keys.get(0);
//        String foreignKey = "";
//        if(keys.size()==2)
//            foreignKey = (String) keys.get(1);
        User result = loadedMap.get(primaryKey);
        if (result != null)
            return result;

        try (Connection con = ConnectionPool.getConnection();
             PreparedStatement st = con.prepareStatement(getFindStatement(keys))
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

    @Override
    protected User convertResultSetToObject(ResultSet rs) throws SQLException {
        User user = new User();
        user.setId(rs.getInt(1));
        user.setName(rs.getString(2));
        user.setFamilyName(rs.getString(3));
        user.setEmail(rs.getString(4));
        user.setCredit(rs.getInt(5));
        user.setPhoneNumber(rs.getString(6));
        CartMapper cartMapper = new CartMapper(false);
        OrderMapper orderMapper = new OrderMapper(false);
        SaleCartMapper saleCartMapper = new SaleCartMapper(false);
        Connection connection = ConnectionPool.getConnection();
        ArrayList<Integer> id = new ArrayList<Integer>();
        id.add(rs.getInt(1));
        ArrayList<Cart> foods = cartMapper.findByForeignKey(id);
        ArrayList<Cart> saleFoods = saleCartMapper.findByForeignKey(id);
        HashMap<Integer, ArrayList<Cart>> allOrders = new HashMap<Integer, ArrayList<Cart>>();
//        ArrayList<ArrayList<Cart>> allOrders = new ArrayList<ArrayList<Cart>>();
        ArrayList<Cart> orders = orderMapper.findByForeignKey(id);
        if(orders.size()>0) {
            for (Cart c : orders) {
                Integer index = c.getIndex();
                ArrayList<Cart> specOrder = new ArrayList<Cart>();
                if (allOrders.get(index) == null) {
                    specOrder.add(c);
                    allOrders.put(index, specOrder);
                } else {
                    specOrder = allOrders.get(index);
                    specOrder.add(c);
                    allOrders.put(index, specOrder);
                }
            }
            ArrayList<Cart> resultOrder = new ArrayList<Cart>();
            for (int i = 0; i < allOrders.size(); i++) {
                Cart newCart = new Cart();
                newCart.setUserId(rs.getInt(1));
                newCart.setIndex(i);
                newCart.setStatus(allOrders.get(i).get(0).getStatus());
                newCart.setRestaurantName(allOrders.get(i).get(0).getRestaurantName());
                ArrayList<Cart> spec = allOrders.get(i);
                ArrayList<Food> orderFoods = new ArrayList<Food>();
                ArrayList<Integer> orderNumOfFoods = new ArrayList<Integer>();
                for (Cart c : spec) {
                    orderFoods.add(c.getFoods().get(0));
                    orderNumOfFoods.add(c.getNumberOfFood().get(0));
                }
                resultOrder.add(newCart);
            }
            user.setOrders(resultOrder);
        }
        Cart tempCart = new Cart();
        if(foods.size()>0) {
            tempCart.setUserId(rs.getInt(1));
            tempCart.setRestaurantName(foods.get(0).getRestaurantName());
            ArrayList<Food> allFoods = new ArrayList<Food>();
            ArrayList<Integer> numberOfFoods = new ArrayList<Integer>();
            for(Cart c:foods){
                allFoods.add(c.getFoods().get(0));
                numberOfFoods.add(c.getNumberOfFood().get(0));
            }
            tempCart.setFoods(allFoods);
            tempCart.setNumberOfFood(numberOfFoods);
        }
        if(saleFoods.size()>0) {
            tempCart.setUserId(rs.getInt(1));
            tempCart.setRestaurantName(saleFoods.get(0).getRestaurantName());
            ArrayList<SaleFood> allSaleFoods = new ArrayList<SaleFood>();
            ArrayList<Integer> numberOfSaleFoods = new ArrayList<Integer>();
            for(Cart c:saleFoods){
                allSaleFoods.add(c.getSaleFoods().get(0));
                numberOfSaleFoods.add(c.getNumberOfSaleFood().get(0));
            }
            tempCart.setSaleFoods(allSaleFoods);
            tempCart.setNumberOfSaleFood(numberOfSaleFoods);
        }
        user.setMyCart(tempCart);
        connection.close();
        return  user;
    }

    @Override
    protected String getAllRows() {
        return "SELECT " + COLUMNS +
                " FROM " + TABLE_NAME ;
    }

}
