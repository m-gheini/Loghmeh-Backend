package IECA.restAPIs;

import IECA.logic.*;
import IECA.logic.Error;
import org.codehaus.jackson.map.util.JSONPObject;
import org.json.JSONObject;
import org.springframework.core.annotation.Order;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

@RestController
public class Restaurants {
    @RequestMapping(value = "/restaurants", method = RequestMethod.GET)
    public @ResponseBody
    ArrayList<Restaurant> allRestaurants() throws IOException {
        ArrayList<Restaurant> restaurants = RestaurantManager.getInstance().getRestaurants();
        return restaurants;
    }

    @RequestMapping(value = "/saleFoods", method = RequestMethod.GET)
    public @ResponseBody
    ArrayList<SaleFood> allSaleFoods() throws IOException {
        ArrayList<SaleFood> saleFoods = RestaurantManager.getInstance().getSaleFoods();
        return saleFoods;
    }

    @RequestMapping(value = "/saleFoods/{id}", method = RequestMethod.GET)
    public @ResponseBody
    ArrayList<SaleFood> allSaleFoodsOfRestaurant(@PathVariable(value = "id") String id) throws IOException {
        ArrayList<SaleFood> result = RestaurantManager.getInstance().saleFoodsOfSpecialRestaurant(id);
        return result;
    }

    @RequestMapping(value = "/saleFoods/{id}",params = "saleFoodName",method = RequestMethod.GET)
    public @ResponseBody
    Object specificSaleFood(@PathVariable(value = "id") String id,
                              @RequestParam(value = "saleFoodName") String saleFoodName) throws IOException {
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
        SaleFood result = RestaurantManager.getInstance().findSpecialSaleFood(id,saleFoodName);
        return result;
    }

    @RequestMapping(value = "/restaurants/{id}" , method = RequestMethod.GET)
    public @ResponseBody
    Object specificRestaurant(@PathVariable(value = "id") String id) throws IOException {
        if(RestaurantManager.getInstance().errorForRestaurant(id)!=null)
            return RestaurantManager.getInstance().errorForRestaurant(id);
        else {
            return RestaurantManager.getInstance().searchResById(id);
        }
    }
    @RequestMapping(value = "/restaurants/{id}" , params = "foodName",method = RequestMethod.GET)
    public @ResponseBody
    Object specificFood(@PathVariable(value = "id") String id,
                        @RequestParam(value = "foodName") String foodName) throws IOException {
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
    HashMap<String,Integer> foodPartyRemainingTime() throws IOException {
        HashMap<String,Integer> result = new HashMap<>();
        result.put("remainingTime",RestaurantManager.getInstance().getRemainingTime());
        return result;
    }


}