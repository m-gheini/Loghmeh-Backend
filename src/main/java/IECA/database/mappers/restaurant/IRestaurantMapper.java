package IECA.database.mappers.restaurant;

import IECA.database.mappers.IMapper;
import IECA.logic.Restaurant;

import java.sql.SQLException;
import java.util.List;

interface IRestaurantMapper extends IMapper<Restaurant, String> {
    List<Restaurant> findRestaurantsInRadius() throws SQLException;
    List<Restaurant> searchRestaurantByName(String inName) throws SQLException;
//    int getSizeOfDatabase() throws SQLException;
}
