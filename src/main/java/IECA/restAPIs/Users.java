package IECA.restAPIs;

import IECA.logic.*;
import IECA.logic.Error;
import IECA.logic.schedulers.DeliveryScheduler;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

@RestController
public class Users {

    @RequestMapping(value = "/users", method = RequestMethod.GET)
    public @ResponseBody
    ArrayList<User> allUsers() throws IOException {
        ArrayList<User> users = RestaurantManager.getInstance().getUsers();
        return users;
    }

    @RequestMapping(value = "/users/{id}", method = RequestMethod.GET)
    public @ResponseBody
    Object specificUser(@PathVariable(value = "id") Integer id) throws IOException {
        User result = RestaurantManager.getInstance().findSpecUser(id);
        if(result != null)
            return result;
        Error error = new Error(404,"no such id");
        return error;

    }

    @RequestMapping(value = "/users/{id}/credit", method = RequestMethod.GET)
    public @ResponseBody
    Object specificUserCredit(@PathVariable(value = "id") Integer id) throws IOException {
        User result = RestaurantManager.getInstance().findSpecUser(id);
        if(result != null)
            return result.getCredit();
        Error error = new Error(404,"no such id");
        return error;

    }

    @RequestMapping(value = "/users/{id}", method = RequestMethod.DELETE)
    public @ResponseBody
    Object deleteUser(@PathVariable(value = "id") Integer id) throws IOException {
        for(User u:RestaurantManager.getInstance().getUsers()){
            if(u.getId()==id){
                RestaurantManager.getInstance().getUsers().remove(u);
                Error error = new Error(200,"user deleted successfully");
                return error;
            }
        }
        Error error = new Error(404,"no such id");
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
        user.setAllParameters(id,name,familyName,email,credit,phoneNumber);
        Integer prevSize=RestaurantManager.getInstance().getUsers().size();
        RestaurantManager.getInstance().addUser(user);
        if(prevSize==RestaurantManager.getInstance().getUsers().size()){
            Error error = new Error(404,"already existed");
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
        User user = RestaurantManager.getInstance().findSpecUser(id);
        if(user == null){
            Error error=new Error(400,"no such id");
            return error;
        }
        else{
            user.addCredit(credit);
        }
        return user;
    }

    @RequestMapping(value = "/users/{id}/orders",method = RequestMethod.GET)
    public @ResponseBody
    Object allOrders(@PathVariable(value = "id") Integer id) throws IOException {
        User user = RestaurantManager.getInstance().findSpecUser(id);
        if(user!=null){
            return user.getOrders();
        }
        else{
            Error error = new Error(400,"no such id");
            return error;
        }
    }

    @RequestMapping(value = "/users/{id}/orders/{index}",method = RequestMethod.GET)
    public @ResponseBody
    Object allOrders(@PathVariable(value = "id") Integer id,
                   @PathVariable(value = "index") Integer index) throws IOException {
        User user = RestaurantManager.getInstance().findSpecUser(id);
        if(user!=null){
            Cart result = RestaurantManager.getInstance().findSpecOrder(index,user);
            if(result!=null){
                return result;
            }
            return new Error(400,"no such index of orders");
        }
        else
            return new Error(400,"no such user id");
    }

    @RequestMapping(value = "/users/{id}/cart",method = RequestMethod.GET)
    public @ResponseBody
    Object userCart(@PathVariable(value = "id") Integer id) throws IOException {
        User user = RestaurantManager.getInstance().findSpecUser(id);
        if(user!=null){
            Cart userCart = user.getMyCart();
            if(userCart.getFoods().size()==0 && userCart.getSaleFoods().size()==0)
                return new Error(404,"Your cart is empty!");
            return userCart;
        }
        else
            return new Error(404,"No such user!");
    }

    @RequestMapping(value = "/users/{id}/cart", params = "foodName",method = RequestMethod.PUT)
    public @ResponseBody
    Object addToCart(
            @PathVariable(value = "id") Integer id,
            @RequestParam(value = "restaurantId") String restaurantId,
            @RequestParam(value = "foodName") String foodName) throws IOException {
        boolean restaurantNotFound = RestaurantManager.getInstance().searchInProperResById(restaurantId);
        if(restaurantNotFound)
            return new Error(404,"no such restaurantId");
        User u = RestaurantManager.getInstance().findSpecUser(id);
        if(u==null)
            return new Error(404,"no such id");
        String jsonInString = "{\"foodName\":\""+foodName+"\",\"restaurantId\":\""+restaurantId+"\"}";
        Food orderFood = RestaurantManager.getInstance().searchForFood(jsonInString);
        if(orderFood.getName()==null)
            return new Error(404,"no such foodName");
        int status = u.getMyCart().addFood(jsonInString,RestaurantManager.getInstance().getFoods());
        if (status==0)
            return new Error(400,"you can not choose from different restaurants");
        return u.getMyCart();
    }

    @RequestMapping(value = "/users/{id}/cart", params = "saleFoodName",method = RequestMethod.PUT)
    public @ResponseBody
    Object addSaleFoodToCart(
            @PathVariable(value = "id") Integer id,
            @RequestParam(value = "restaurantId") String restaurantId,
            @RequestParam(value = "saleFoodName") String foodName) throws IOException {
        boolean restaurantFound = RestaurantManager.getInstance().restaurantIdOfSaleFoodFound(restaurantId);
        if(!restaurantFound)
            return new Error(404,"no such restaurantId");
        boolean foodFound = false;
        User u = RestaurantManager.getInstance().findSpecUser(id);
        if(u==null)
            return new Error(404,"no such id");
        String jsonInString = "{\"foodName\":\""+foodName+"\",\"restaurantId\":\""+restaurantId+"\"}";
        SaleFood orderFood = RestaurantManager.getInstance().findSpecialSaleFood(restaurantId,foodName);
        if(orderFood==null)
            return new Error(404,"no such foodName");
        int status = u.getMyCart().addSaleFood(jsonInString,RestaurantManager.getInstance().getSaleFoods());
        if (status==0)
            return new Error(400,"you can not choose from different restaurants");
        return u.getMyCart();
    }

    @RequestMapping(value = "/users/{id}/cart", params = "foodName",method = RequestMethod.DELETE)
    public @ResponseBody
    Object deleteFromCart(
            @PathVariable(value = "id") Integer id,
            @RequestParam(value = "restaurantId") String restaurantId,
            @RequestParam(value = "foodName") String foodName) throws IOException {
        boolean restaurantNotFound = RestaurantManager.getInstance().searchInProperResById(restaurantId);
        if(restaurantNotFound)
            return new Error(404,"no such restaurantId");
        User u = RestaurantManager.getInstance().findSpecUser(id);
        if(u==null)
            return new Error(404,"no such id");
        String jsonInString = "{\"foodName\":\""+foodName+"\",\"restaurantId\":\""+restaurantId+"\"}";
        Food orderFood = RestaurantManager.getInstance().searchForFood(jsonInString);
        if(orderFood.getName()==null)
            return new Error(404,"no such foodName");
        u.getMyCart().deleteSpecificFood(orderFood);
        return u.getMyCart();
    }

    @RequestMapping(value = "/users/{id}/cart", params = "saleFoodName",method = RequestMethod.DELETE)
    public @ResponseBody
    Object deleteSaleFoodFromCart(
            @PathVariable(value = "id") Integer id,
            @RequestParam(value = "restaurantId") String restaurantId,
            @RequestParam(value = "saleFoodName") String foodName) throws IOException {
        boolean restaurantFound = RestaurantManager.getInstance().restaurantIdOfSaleFoodFound(restaurantId);
        if(!restaurantFound)
            return new Error(404,"no such restaurantId");
        boolean foodFound = false;
        User u = RestaurantManager.getInstance().findSpecUser(id);
        if(u==null)
            return new Error(404,"no such id");
        String jsonInString = "{\"foodName\":\""+foodName+"\",\"restaurantId\":\""+restaurantId+"\"}";
        SaleFood orderFood = RestaurantManager.getInstance().findSpecialSaleFood(restaurantId,foodName);
        if(orderFood==null)
            return new Error(404,"no such foodName");
        u.getMyCart().deleteSpecificSaleFood(orderFood);
        return u.getMyCart();
    }

    @RequestMapping(value = "/users/{id}/cart",method = RequestMethod.POST)
    public @ResponseBody
    Object finalizeCart(@PathVariable(value = "id") Integer id) throws IOException {
        User u = RestaurantManager.getInstance().findSpecUser(id);
        if(u!=null){
            Cart userCart = u.getMyCart();
            if(userCart.getFoods().size()==0 && userCart.getSaleFoods().size()==0)
                return new Error(404,"Your cart is empty!");
            else {
                Integer total = RestaurantManager.getInstance().makeTotal();
                String restaurantId = "";
                if(u.getMyCart().getFoods().size()>0)
                    restaurantId = u.getMyCart().getFoods().get(0).getRestaurantId();
                else
                    restaurantId = u.getMyCart().getSaleFoods().get(0).getRestaurantId();
                if(u.getCredit()>=total && total!=0){
                    Cart previousCart = new Cart();
                    u.addCredit(-total);
                    DeliveryScheduler deliveryScheduler = new DeliveryScheduler();
                    deliveryScheduler.setRestaurant(restaurantId);
                    previousCart.setAllParameters(u);
                    u.addOrder(previousCart);
                    u.getMyCart().clearCart();
                    return u.getMyCart();
                }
                else{
                    if(u.getCredit()<total)
                        return new Error(400,"not enough credit");
                    else
                        return new Error(400,"empty cart");
                }
            }
        }
        return new Error(404,"No such user!");
    }
}