package IECA.restAPIs;

import IECA.logic.Cart;
import IECA.logic.RestaurantManager;
import IECA.logic.User;
import IECA.logic.Error;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.ArrayList;

@RestController
public class Users {
    @RequestMapping(value = "/users", method = RequestMethod.GET)
    public @ResponseBody
    ArrayList<User> allUsers() throws IOException {
        ArrayList<User> users = new ArrayList<User>();
        User user = RestaurantManager.getInstance().getCurrentUser();
        users.add(user);
        return users;
    }

    @RequestMapping(value = "/users/{id}", method = RequestMethod.GET)
    public @ResponseBody
    Object specificUser(@PathVariable(value = "id") Integer id) throws IOException {
        for(User u:RestaurantManager.getInstance().getUsers()){
            if(u.getId()==id){
                return u;
            }
        }

        Error error = new Error();
        error.setErrorCode(404);
        error.setErrorMassage("no such id");
        return error;

    }

    @RequestMapping(value = "/users", method = RequestMethod.POST)
    public @ResponseBody
    Object addUser(
            @RequestParam(value = "id") Integer id,
            @RequestParam(value = "name") String name,
            @RequestParam(value = "familyName") String familyName,
            @RequestParam(value = "credit") Integer credit,
            @RequestParam(value = "email") String email,
            @RequestParam(value = "phoneNumber") String phoneNumber) throws IOException {
        User user = new User();
        Cart myCart = new Cart();
        ArrayList<Cart> orders = new ArrayList<Cart>();
        user.setId(id);
        user.setName(name);
        user.setFamilyName(familyName);
        user.setCredit(credit);
        user.setEmail(email);
        user.setPhoneNumber(phoneNumber);
        user.setMyCart(myCart);
        user.setOrders(orders);
        Integer prevSize=RestaurantManager.getInstance().getUsers().size();
        RestaurantManager.getInstance().addUser(user);
        if(prevSize==RestaurantManager.getInstance().getUsers().size()){
            Error error = new Error();
            error.setErrorCode(400);
            error.setErrorMassage("already existed");
            return error;
        }
        else {
            return RestaurantManager.getInstance().getUsers();
        }
    }

    @RequestMapping(value = "/users/{id}", method = RequestMethod.PUT)
    public @ResponseBody
    Object updateUserCredit(
            @PathVariable(value = "id") Integer id,
            @RequestParam(value = "credit") Integer credit) throws IOException {
        boolean found=false;
        User user = new User();
        for(User u:RestaurantManager.getInstance().getUsers()){
            if(u.getId()==id){
                found = true;
                user = u;
                break;
            }
        }
        if(found==false){
            Error error=new Error();
            error.setErrorCode(400);
            error.setErrorMassage("no such id");
            return error;
        }
        else{
            user.addCredit(credit);
        }
        return user;
    }

    @RequestMapping(value = "/users/{id}/orders",method = RequestMethod.GET)
    public @ResponseBody
    ArrayList<Cart> allOrders(@PathVariable(value = "id") Integer id) throws IOException {
        boolean found=false;
        User u = new User();
        for(User user:RestaurantManager.getInstance().getUsers()){
            if(user.getId()==id)
                found=true;
            u = user;
        }
        if(found){
            return u.getOrders();
        }
        else{
            ArrayList<Cart> emptyOrders = new ArrayList<Cart>();
            return emptyOrders;
        }
    }

    @RequestMapping(value = "/users/{id}/orders/{index}",method = RequestMethod.GET)
    public @ResponseBody
    Cart allOrders(@PathVariable(value = "id") Integer id,
                   @PathVariable(value = "index") Integer index) throws IOException {
        boolean found=false;
        User u = new User();
        for(User user:RestaurantManager.getInstance().getUsers()){
            if(user.getId()==id)
                found=true;
            u = user;
        }
        if(found){
            Cart result = new Cart();
            ArrayList<Cart> orders = u.getOrders();
            boolean indexFound = false;
            for (Cart order:orders){
                if (order.getIndex()==index){
                    indexFound = true;
                    result = order;
                    break;
                }
            }
            if(indexFound){
                return result;
            }
            else{
                //TODO not existing index
                return result;
            }
        }
        else{
            //TODO not existing user
            Cart emptyOrder = new Cart();
            return emptyOrder;
        }
    }

    @RequestMapping(value = "/users/{id}/cart",method = RequestMethod.GET)
    public @ResponseBody
    Cart userCart(@PathVariable(value = "id") Integer id) throws IOException {
        boolean found=false;
        User u = new User();
        for(User user:RestaurantManager.getInstance().getUsers()){
            if(user.getId()==id)
                found=true;
            u = user;
        }
        if(found){
            return u.getMyCart();
        }
        else{
            Cart emptyCart = new Cart();
            return emptyCart;
        }
    }

}