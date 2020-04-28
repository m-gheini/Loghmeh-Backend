package IECA.restAPIs;

import IECA.database.mappers.ConnectionPool;
import IECA.database.mappers.FoodMapper;
import IECA.database.mappers.FoodPartyMapper;
import IECA.database.mappers.RestaurantMapper;
import IECA.logic.*;
import IECA.logic.Error;
import org.codehaus.jackson.map.util.JSONPObject;
import org.json.JSONObject;
import org.springframework.core.annotation.Order;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

@RestController
public class Restaurants {
    @RequestMapping(value = "/restaurants", method = RequestMethod.GET)
    public @ResponseBody
    ArrayList<Restaurant> allRestaurants() throws IOException, SQLException {
//        ArrayList<Restaurant> restaurants = RestaurantManager.getInstance().getRestaurants();
        RestaurantMapper restaurantMapper= new RestaurantMapper(false);
        Connection connection = ConnectionPool.getConnection();
        ArrayList<Restaurant> restaurants = restaurantMapper.convertAllResultToObject();
        connection.close();
        return restaurants;
    }

    @RequestMapping(value = "/saleFoods", method = RequestMethod.GET)
    public @ResponseBody
    ArrayList<SaleFood> allSaleFoods() throws IOException, SQLException {
//        ArrayList<SaleFood> saleFoods = RestaurantManager.getInstance().getSaleFoods();
        FoodPartyMapper foodPartyMapper= new FoodPartyMapper(false);
        Connection connection = ConnectionPool.getConnection();
        ArrayList<SaleFood> saleFoods = foodPartyMapper.convertAllResultToObject();
        connection.close();

        return saleFoods;
    }

    @RequestMapping(value = "/saleFoods/{id}", method = RequestMethod.GET)
    public @ResponseBody
    ArrayList<SaleFood> allSaleFoodsOfRestaurant(@PathVariable(value = "id") String id) throws IOException, SQLException {
//        ArrayList<SaleFood> result = RestaurantManager.getInstance().saleFoodsOfSpecialRestaurant(id);
        FoodPartyMapper foodPartyMapper= new FoodPartyMapper(false);
        Connection connection = ConnectionPool.getConnection();
        ArrayList<String> ids = new ArrayList<String>();
        ids.add(id);
        ArrayList<SaleFood> saleFoods = foodPartyMapper.findByForeignKey(ids);
        connection.close();
        return saleFoods;
    }

    @RequestMapping(value = "/saleFoods/{id}",params = "saleFoodName",method = RequestMethod.GET)
    public @ResponseBody
    Object specificSaleFood(@PathVariable(value = "id") String id,
                              @RequestParam(value = "saleFoodName") String saleFoodName) throws IOException, SQLException {
        ArrayList<SaleFood> saleFoods = RestaurantManager.getInstance().getSaleFoods();
        boolean restaurantFound = RestaurantManager.getInstance().restaurantIdOfSaleFoodFound(id);
        boolean foodFound = RestaurantManager.getInstance().foodNameOfSaleFoodFound(id,saleFoodName);
        if (!restaurantFound) {
            Error error = new Error(404,"this restaurant is not on foodParty or not existed");
            return error;
        }
        if(!foodFound) {
            Error error = new Error(404, "no such saleFood");
            return error;
        }
//        SaleFood result = RestaurantManager.getInstance().findSpecialSaleFood(id,saleFoodName);
        FoodPartyMapper foodPartyMapper= new FoodPartyMapper(false);
        Connection connection = ConnectionPool.getConnection();
        ArrayList<String> ids = new ArrayList<String>();
        ids.add(saleFoodName);
        ids.add(id);
        ArrayList<SaleFood> result = foodPartyMapper.findByForeignKey(ids);
        connection.close();
        return result;
    }

    @RequestMapping(value = "/restaurants/{id}" , method = RequestMethod.GET)
    public @ResponseBody
    Object specificRestaurant(@PathVariable(value = "id") String id) throws IOException, SQLException {
        if(RestaurantManager.getInstance().errorForRestaurant(id)!=null)
            return RestaurantManager.getInstance().errorForRestaurant(id);
        else {
            return RestaurantManager.getInstance().searchResById(id);
        }
    }

    @RequestMapping(value = "/restaurants/{name}" , method = RequestMethod.GET)
    public @ResponseBody
    Object specificRestaurantByName(@PathVariable(value = "name") String name) throws IOException, SQLException {
        RestaurantMapper restaurantMapper = new RestaurantMapper(false);
        Connection connection = ConnectionPool.getConnection();
        ArrayList<Restaurant> res = (ArrayList<Restaurant>) restaurantMapper.searchRestaurantByName(name);
        connection.close();
        if(res.size()==0)
            return new Error(404,"no such restaurant");
        return res;
    }
    @RequestMapping(value = "/foods/{name}" , method = RequestMethod.GET)
    public @ResponseBody
    Object specificFoodByName(@PathVariable(value = "name") String name) throws IOException, SQLException {
        FoodMapper foodMapper = new FoodMapper(false);
        Connection connection = ConnectionPool.getConnection();
        ArrayList<Food> res = (ArrayList<Food>) foodMapper.searchFoodByName(name);
        connection.close();
        if(res.size()==0)
            return new Error(404,"no such food");
        return res;
    }

    @RequestMapping(value = "/restaurants/{id}" , params = "foodName",method = RequestMethod.GET)
    public @ResponseBody
    Object specificFood(@PathVariable(value = "id") String id,
                        @RequestParam(value = "foodName") String foodName) throws IOException, SQLException {
        if(RestaurantManager.getInstance().errorForRestaurant(id)!=null)
            return RestaurantManager.getInstance().errorForRestaurant(id);
        Restaurant restaurant = RestaurantManager.getInstance().searchResById(id);
        for(Food food:restaurant.getMenu()){
            if(food.getName().equals(foodName))
                return food;
        }
        Error error = new Error(404,"no such food in this restaurants");
        return  error;
    }
    @RequestMapping(value = "/foodPartyTime", method = RequestMethod.GET)
    public @ResponseBody
    HashMap<String,Integer> foodPartyRemainingTime() throws IOException, SQLException {
        HashMap<String,Integer> result = new HashMap<>();
        result.put("remainingTime",RestaurantManager.getInstance().getRemainingTime());
        return result;
    }


}