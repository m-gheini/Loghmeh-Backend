package IECA.database.mappers;

import IECA.logic.Food;
import IECA.logic.Location;
import IECA.logic.SaleFood;
import IECA.servlets.FoodParty;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class FoodPartyMapper extends Mapper<SaleFood, String> implements IFoodPartyMapper {
    private static final String COLUMNS = " restaurantId, name, description, count, oldPrice, price, popularity, image ";
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
                            "restaurantId varchar(50) not null references restaurant_table(id) on delete cascade, " +
                            "name varchar(100), " +
                            "description varchar(500), "+
                            "count integer, "+
                            "oldPrice integer, "+
                            "price integer, "+
                            "popularity float, "+
                            "image varchar(500), "+
                            "primary key(restaurantId, name), "+
                            "foreign key(restaurantId) references restaurant_table(id) on delete cascade "+
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
        return "INSERT IGNORE INTO " + TABLE_NAME +
                "(" + COLUMNS + ")" + " VALUES "+
                "("+
                "'" + saleFood.getRestaurantId()+ "'," +
                "'" + saleFood.getName() + "'," +
                "'" + saleFood.getDescription() + "'," +
                saleFood.getCount() + "," +
                saleFood.getOldPrice() + "," +
                saleFood.getPrice() + "," +
                saleFood.getPopularity() + "," +
                "'" +saleFood.getImage() + "'" +
                ");";
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
        saleFood.setName(rs.getString(2));
        saleFood.setDescription(rs.getString(3));
        saleFood.setCount(rs.getInt(4));
        saleFood.setOldPrice(rs.getInt(5));
        saleFood.setPrice(rs.getInt(6));
        saleFood.setPopularity(rs.getFloat(7));
        saleFood.setImage(rs.getString(8));
        return  saleFood;
    }
}
