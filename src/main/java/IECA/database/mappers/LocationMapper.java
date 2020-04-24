//package IECA.database.mappers;
//
//import IECA.logic.Location;
//
//import java.sql.*;
//import java.util.ArrayList;
//import java.util.List;
//
//public class LocationMapper extends Mapper<Location, Integer> implements ILocationMapper {
//
//    private static final String COLUMNS = " x, y ";
//    private static final String TABLE_NAME = "location_table";
//
//    private Boolean doManage;
//
//    public LocationMapper(Boolean doManage) throws SQLException {
//        if (this.doManage = doManage) {
//            Connection con = ConnectionPool.getConnection();
//            Statement st = con.createStatement();
//            st.executeUpdate(String.format("DROP TABLE IF EXISTS %s", TABLE_NAME));
//            st.executeUpdate(String.format(
//                    "CREATE TABLE  %s " +
//                            "(" +
//                            "x integer PRIMARY KEY, " +
//                            "y integer " +
//                            ");",
//                    TABLE_NAME));
//            st.close();
//            con.close();
//        }
//    }
//
//    @Override
//    protected String getFindStatement(Integer x) {
//        return "SELECT " + COLUMNS +
//                " FROM " + TABLE_NAME +
//                " WHERE x = "+ x + ";";
//    }
//
//    @Override
//    protected String getInsertStatement(Location location) {
//        return "INSERT INTO " + TABLE_NAME +
//                "(" + COLUMNS + ")" + " VALUES "+
//                "("+
//                location.getX()+ "," +
//                '"' + location.getY() + '"' +
//                ");";
//    }
//
//    @Override
//    protected String getDeleteStatement(Integer x) {
//        return "DELETE FROM " + TABLE_NAME +
//                " WHERE x = " + x + ";";
//    }
//
//    @Override
//    protected Location convertResultSetToObject(ResultSet rs) throws SQLException {
//        Location location = new Location();
//        location.setX(rs.getInt(1));
//        location.setY(rs.getInt(2));
//        return  location;
//    }
//
//}
