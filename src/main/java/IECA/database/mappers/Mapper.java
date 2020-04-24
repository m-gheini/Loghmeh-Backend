package IECA.database.mappers;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public abstract class Mapper<T, I> implements IMapper<T, I> {

    protected Map<I, T> loadedMap = new HashMap<I, T>();

    abstract protected String getFindStatement(ArrayList<I> keys);

    abstract protected String getInsertStatement(T location);

    abstract protected String getDeleteStatement(ArrayList<I> keys);

    abstract protected T convertResultSetToObject(ResultSet rs) throws SQLException;

    public T find(ArrayList<I> keys) throws SQLException {
        String primaryKey = (String) keys.get(0);
        String foreignKey = "";
        if(keys.size()==2)
            foreignKey = (String) keys.get(1);
        T result = loadedMap.get(primaryKey);
        if (result != null)
            return result;

        try (Connection con = ConnectionPool.getConnection();
             PreparedStatement st = con.prepareStatement(getFindStatement(keys))
        ) {
            ResultSet resultSet;
            try {
                resultSet = st.executeQuery();
                resultSet.next();
                return convertResultSetToObject(resultSet);
            } catch (SQLException ex) {
                System.out.println("error in Mapper.findByID query.");
                throw ex;
            }
        }
    }

    public void insert(T obj) throws SQLException {
        try (Connection con = ConnectionPool.getConnection();
             PreparedStatement st = con.prepareStatement(getInsertStatement(obj))
        ) {
            try {
                st.executeUpdate();
            } catch (SQLException ex) {
                System.out.println("error in Mapper.insert query.");
                throw ex;
            }
        }
    }

    public void delete(ArrayList<I> keys) throws SQLException {
        try (Connection con = ConnectionPool.getConnection();
             PreparedStatement st = con.prepareStatement(getDeleteStatement(keys))
        ) {
            try {
                st.executeUpdate();
            } catch (SQLException ex) {
                System.out.println("error in Mapper.delete query.");
                throw ex;
            }
        }
    }
}