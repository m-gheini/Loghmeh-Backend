package IECA.restAPIs;

import IECA.logic.*;
import IECA.logic.Error;
import org.springframework.core.annotation.Order;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.ArrayList;

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
    @RequestMapping(value = "/restaurants/{id}" , method = RequestMethod.GET)
    public @ResponseBody
    Object specificRestaurant(@PathVariable(value = "id") String id) throws IOException {
        boolean totallyNotFound = RestaurantManager.getInstance().searchForResInAllRes(id);
        if (totallyNotFound) {
            Error error = new Error();
            error.setErrorCode(404);
            error.setErrorMassage("no such id");
            return error;
        }
        boolean notFound = RestaurantManager.getInstance().searchInProperResById(id);
        if (notFound) {
            Error error = new Error();
            error.setErrorCode(403);
            error.setErrorMassage("no such restaurant near you");
            return error;
        }
        return RestaurantManager.getInstance().searchResById(id);
    }



}