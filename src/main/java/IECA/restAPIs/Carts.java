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



}