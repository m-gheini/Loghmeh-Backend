package IECA.database.mappers;

import IECA.logic.Food;
import IECA.logic.Location;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class FoodMapper extends Mapper<Food, String> implements IFoodMapper {

    private static final String COLUMNS = " name, description, popularity, restaurantId, price, image";
    private static final String TABLE_NAME = "food_table";

    private Boolean doManage;

    public FoodMapper(Boolean doManage) throws SQLException {
        if (this.doManage = doManage) {
            Connection con = ConnectionPool.getConnection();
            Statement st = con.createStatement();
            st.executeUpdate(String.format("DROP TABLE IF EXISTS %s", TABLE_NAME));
            st.executeUpdate(String.format(
                    "CREATE TABLE  %s " +
                            "(" +
                            "name varchar(100), " +
                            "description varchar(255), " +
                            "popularity FLOAT, " +
                            "restaurantId varchar(50), " +
                            "price int, " +
                            "image varchar(255), " +
                            "PRIMARY KEY(name, restaurantId), " +
                            "foreign key(restaurantId) references restaurant_table(id) on delete  cascade" +
                            ");",
                    TABLE_NAME));
            st.close();
            con.close();
        }
    }

    @Override
    protected String getFindStatement(ArrayList<String> keys) {
        if(keys.size() == 1){
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
    protected String getInsertStatement(Food food) {
        return "INSERT IGNORE INTO " + TABLE_NAME +
                "(" + COLUMNS + ")" + " VALUES "+
                "("+
                "'" + food.getName()+ "'," +
                "'" + food.getDescription() + "'," +
                food.getPopularity() + "," +
                "'" + food.getRestaurantId() + "'," +
                food.getPrice() + "," +
                "'" +food.getImage() + "'" +
                ");";
    }

    @Override
    protected String getDeleteStatement(ArrayList<String> keys) {
        String primary_key = keys.get(0);
        String foreign_key = keys.get(1);
        return "DELETE FROM " + TABLE_NAME +
                " WHERE name = '" + primary_key + "' and restaurantId = '" + foreign_key + "';";
    }

    @Override
    protected Food convertResultSetToObject(ResultSet rs) throws SQLException {
        Food food = new Food();
        food.setName(rs.getString(1));
        food.setDescription(rs.getString(2));
        food.setPopularity(rs.getFloat(3));
        food.setRestaurantId(rs.getString(4));
        food.setPrice(rs.getInt(5));
        food.setImage(rs.getString(6));
        return  food;
    }

}
