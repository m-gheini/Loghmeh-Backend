package IECA.database.mappers;

import IECA.logic.Food;
import IECA.logic.User;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

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
                ");";
    }

    @Override
    protected String getDeleteStatement(ArrayList<Integer> keys) {
        Integer id = keys.get(0);
        return "DELETE FROM " + TABLE_NAME +
                " WHERE id = " + id + ";";
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
        return  user;
    }

}
