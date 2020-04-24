package IECA.database;

import IECA.logic.Location;

import java.sql.Connection;
import java.sql.SQLException;

public class Main {
    public static void main(String[] args) throws SQLException {
        LocationMapper lm = new LocationMapper(true);
        Connection connection = ConnectionPool.getConnection();
        Location l1 = new Location();
        l1.setX(1);
        l1.setY(2);
        lm.insert(l1);
        Location l = lm.find(1);
        System.out.println(l);
        lm.delete(2);
        lm.delete(1);
        connection.close();
    }
}
