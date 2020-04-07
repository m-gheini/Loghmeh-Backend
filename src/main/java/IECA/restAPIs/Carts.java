package IECA.restAPIs;

import IECA.logic.*;
import IECA.logic.Error;
import org.springframework.core.annotation.Order;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.ArrayList;

@RestController
public class Carts {
    @RequestMapping(value = "/cart", method = RequestMethod.GET)
    public @ResponseBody
    Object cart() throws IOException {
        Cart userCart = RestaurantManager.getInstance().getCurrentUser().getMyCart();
        if(userCart.getFoods().size()==0 && userCart.getSaleFoods().size()==0) {
            Error error = new Error();
            error.setErrorCode(404);
            error.setErrorMassage("Your cart is empty!");
            return error;
        }
        else
            return userCart;
    }
    @RequestMapping(value = "/users", method = RequestMethod.POST)
    public @ResponseBody
    Object addUser(
            @RequestParam(value = "restaurantId") String restaurantId,
            @RequestParam(value = "foodName") String foodName) throws IOException {
        Cart userCart = RestaurantManager.getInstance().getCurrentUser().getMyCart();
        ArrayList<Food> foods = new ArrayList<Food>();
        ArrayList<Cart> orders = new ArrayList<Cart>();
        if(userCart.getFoods().size()==0 && userCart.getSaleFoods().size()==0){
            Cart myCart = new Cart();
            return myCart;
        }
        else{
            if(userCart.getSaleFoods().size()!=0) {
                if (restaurantId.equals(userCart.getSaleFoods().get(0).getRestaurantId())){
                    return userCart;
                }
                else{
                    Error error = new Error();
                    error.setErrorCode(400);
                    error.setErrorMassage("You can not order from different restaurant!");
                    return error;
                }
            }
            if(userCart.getFoods().size()!=0) {
                if (restaurantId.equals(userCart.getFoods().get(0).getRestaurantId())){
                    return userCart;
                }
                else{
                    Error error = new Error();
                    error.setErrorCode(400);
                    error.setErrorMassage("You can not order from different restaurant!");
                    return error;
                }
            }
        }
        return userCart;
    }



}