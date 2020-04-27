package IECA.database.mappers;

import IECA.logic.Food;
import IECA.logic.Location;
import IECA.logic.Restaurant;
import IECA.logic.SaleFood;
import IECA.servlets.FoodParty;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class FoodPartyMapper extends Mapper<SaleFood, String> implements IFoodPartyMapper {
    private static final String COLUMNS = " restaurantId, count, oldPrice, price, popularity, x, y, image, restaurantImage, restaurantName, name, description";
    private static final String TABLE_NAME = "foodParty_table";

    private Boolean doManage;

    public FoodPartyMapper(Boolean doManage) throws SQLException {
        if (this.doManage = doManage) {
            Connection con = ConnectionPool.getConnection();
            Statement st = con.createStatement();
            st.executeUpdate(String.format("DROP TABLE IF EXISTS %s", TABLE_NAME));
            st.executeUpdate(String.format(
                    "CREATE TABLE  %s " +
                            "(" +
                            "restaurantId varchar(50) not null , " +
                            "count integer, "+
                            "oldPrice integer, "+
                            "price integer, "+
                            "popularity float, "+
                            "x int, "+
                            "y int, "+
                            "image varchar(500), "+
                            "restaurantImage varchar(500), "+
                            "restaurantName varchar(100), " +
                            "name varchar(100), " +
                            "description varchar(500), "+
                            "primary key(restaurantId, name) "+
                            ");",
                    TABLE_NAME));
            st.close();
            con.close();
        }
    }

    @Override
    protected String getFindStatement(ArrayList<String> keys) {
        if(keys.size() < 2){
            String resId = keys.get(0);
            return "SELECT " + COLUMNS +
                    " FROM " + TABLE_NAME +
                    " WHERE restaurantId = " + "'" + resId + "'" +";";
        }
        String name = keys.get(0);
        String resId = keys.get(1);
        return "SELECT " + COLUMNS +
                " FROM " + TABLE_NAME +
                " WHERE name = "+ "'" + name + "'" + "and restaurantId = " + "'" + resId + "'" +";";
    }

    @Override
    protected String getInsertStatement(SaleFood saleFood) {
        System.out.println("INSERT IGNORE INTO " + TABLE_NAME +
                "(" + COLUMNS + ")" + " VALUES "+
                "("+
                "'" + saleFood.getRestaurantId()+ "'," +
                saleFood.getCount() + "," +
                saleFood.getOldPrice() + "," +
                saleFood.getPrice() + "," +
                saleFood.getPopularity() + "," +
                saleFood.getRestaurantLocation().getX() + ","+
                saleFood.getRestaurantLocation().getY() + ","+
                "'" +saleFood.getImage() + "'," +
                "'" + saleFood.getRestaurantImage() + "',"+
                "'" + saleFood.getRestaurantName()+ "'," +
                "'" + saleFood.getName() + "'," +
                "'" + saleFood.getDescription() + "'" +
                ") ON DUPLICATE KEY UPDATE count = " + saleFood.getCount() +";");
        return "INSERT IGNORE INTO " + TABLE_NAME +
                "(" + COLUMNS + ")" + " VALUES "+
                "("+
                "'" + saleFood.getRestaurantId()+ "'," +
                saleFood.getCount() + "," +
                saleFood.getOldPrice() + "," +
                saleFood.getPrice() + "," +
                saleFood.getPopularity() + "," +
                saleFood.getRestaurantLocation().getX() + ","+
                saleFood.getRestaurantLocation().getY() + ","+
                "'" +saleFood.getImage() + "'," +
                "'" + saleFood.getRestaurantImage() + "',"+
                "'" + saleFood.getRestaurantName()+ "'," +
                "'" + saleFood.getName() + "'," +
                "'" + saleFood.getDescription() + "'" +
                ") ON DUPLICATE KEY UPDATE count = " + saleFood.getCount() +";";
    }

    @Override
    protected String getDeleteStatement(ArrayList<String> keys) {
        String primary_key = keys.get(0);
        String foreign_key = keys.get(1);
        return "DELETE FROM " + TABLE_NAME +
                " WHERE name = " + primary_key + "and restaurantId = " + foreign_key + ";";
    }

    @Override
    protected SaleFood convertResultSetToObject(ResultSet rs) throws SQLException {
        SaleFood saleFood = new SaleFood();
        saleFood.setRestaurantId(rs.getString(1));
        saleFood.setRestaurantName(rs.getString(10));
        saleFood.setName(rs.getString(11));
        saleFood.setDescription(rs.getString(12));
        saleFood.setCount(rs.getInt(2));
        saleFood.setOldPrice(rs.getInt(3));
        saleFood.setPrice(rs.getInt(4));
        saleFood.setPopularity(rs.getFloat(5));
        saleFood.setImage(rs.getString(8));
        saleFood.setRestaurantImage(rs.getString(9));
        Location l = new Location();
        l.setX(rs.getInt(6));
        l.setY(rs.getInt(7));
        saleFood.setRestaurantLocation(l);
        return  saleFood;
    }

    @Override
    public void emptyTable() throws SQLException {
        String statement = "DELETE FROM " + TABLE_NAME;
        executingGivenQuery(statement);
    }

    @Override
    protected String getAllRows() {
        return "SELECT " + COLUMNS +
                " FROM " + TABLE_NAME ;
    }

    private void executingGivenQuery(String statement) throws SQLException {
        try (Connection con = ConnectionPool.getConnection();
             PreparedStatement st = con.prepareStatement(statement);
        ) {
            try {
                st.executeUpdate();
            } catch (SQLException ex) {
                System.out.println("error in Mapper.findByID query.");
                throw ex;
            }
        }
    }
}
