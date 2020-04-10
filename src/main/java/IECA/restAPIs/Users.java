package IECA.restAPIs;

import IECA.logic.*;
import IECA.logic.Error;
import IECA.logic.schedulers.DeliveryScheduler;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
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
        boolean found=false;
        User u = new User();
        for(User user:RestaurantManager.getInstance().getUsers()){
            if(user.getId()==id) {
                found = true;
                u = user;
            }
        }
        if(found){
            Cart userCart = u.getMyCart();
            if(userCart.getFoods().size()==0 && userCart.getSaleFoods().size()==0) {
                Error error = new Error(404,"Your cart is empty!");
                return error;
            }
            else
                return userCart;
        }
        else{
            Error error = new Error(404,"No such user!");
            error.setErrorCode(404);
            error.setErrorMassage("No such user!");
            return error;
        }
    }
    @RequestMapping(value = "/users/{id}/cart", params = "foodName",method = RequestMethod.PUT)
    public @ResponseBody
    Object addToCart(
            @PathVariable(value = "id") Integer id,
            @RequestParam(value = "restaurantId") String restaurantId,
            @RequestParam(value = "foodName") String foodName) throws IOException {
        boolean restaurantFound = false;
        boolean foodFound = false;
        boolean userFound = false;
        User u = new User();
        for(User user:RestaurantManager.getInstance().getUsers()){
            if(user.getId()==id) {
                userFound = true;
                u = user;
            }
        }

        Food orderFood = new Food();
        for (Restaurant restaurant:RestaurantManager.getInstance().getRestaurants()){
            if(restaurant.getId().equals(restaurantId)){
                restaurantFound = true;
                for(Food food:restaurant.getMenu()){
                    if(food.getName().equals(foodName)){
                        foodFound = true;
                        orderFood = food;
                    }
                }
            }
        }
        if (!userFound){
            Error error = new Error(404,"no such id");
            return error;
        }
        if(!restaurantFound){
            Error error = new Error(404,"no such restaurantId");
            return error;
        }
        if(!foodFound){
            Error error = new Error(404,"no such foodName");
            return error;
        }
        else{
            String jsonInString = "{\"foodName\":\""+foodName+"\",\"restaurantId\":\""+restaurantId+"\"}";
            u.getMyCart().addFood(jsonInString,RestaurantManager.getInstance().getFoods());
            return u.getMyCart();
        }
    }
    @RequestMapping(value = "/users/{id}/cart", params = "saleFoodName",method = RequestMethod.PUT)
    public @ResponseBody
    Object addSaleFoodToCart(
            @PathVariable(value = "id") Integer id,
            @RequestParam(value = "restaurantId") String restaurantId,
            @RequestParam(value = "saleFoodName") String foodName) throws IOException {
        boolean restaurantFound = false;
        boolean foodFound = false;
        boolean userFound = false;
        User u = new User();
        for(User user:RestaurantManager.getInstance().getUsers()){
            if(user.getId()==id) {
                userFound = true;
                u = user;
            }
        }

        SaleFood orderFood = new SaleFood();
        for(SaleFood food:RestaurantManager.getInstance().getSaleFoods()){
            if(food.getRestaurantId().equals(restaurantId)){
                restaurantFound = true;
                if(food.getName().equals(foodName)){
                    foodFound = true;
                }
            }
        }
        if (!userFound){
            Error error = new Error(404,"no such id");
            return error;
        }
        if(!restaurantFound){
            Error error = new Error(404,"no such restaurantId");
            return error;
        }
        if(!foodFound){
            Error error = new Error(404,"no such foodName");
            return error;
        }
        else{
            String jsonInString = "{\"foodName\":\""+foodName+"\",\"restaurantId\":\""+restaurantId+"\"}";
            u.getMyCart().addSaleFood(jsonInString,RestaurantManager.getInstance().getSaleFoods());
            return u.getMyCart();
        }
    }

    @RequestMapping(value = "/users/{id}/cart", params = "foodName",method = RequestMethod.DELETE)
    public @ResponseBody
    Object deleteFromCart(
            @PathVariable(value = "id") Integer id,
            @RequestParam(value = "restaurantId") String restaurantId,
            @RequestParam(value = "foodName") String foodName) throws IOException {
        boolean restaurantFound = false;
        boolean foodFound = false;
        boolean userFound = false;
        User u = new User();
        for(User user:RestaurantManager.getInstance().getUsers()){
            if(user.getId()==id) {
                userFound = true;
                u = user;
            }
        }

        Food orderFood = new Food();
        for (Restaurant restaurant:RestaurantManager.getInstance().getRestaurants()){
            if(restaurant.getId().equals(restaurantId)){
                restaurantFound = true;
                for(Food food:restaurant.getMenu()){
                    if(food.getName().equals(foodName)){
                        foodFound = true;
                        orderFood = food;
                    }
                }
            }
        }
        if (!userFound){
            Error error = new Error(404,"no such id");
            return error;
        }
        if(!restaurantFound){
            Error error = new Error(404,"no such restaurantId");
            return error;
        }
        if(!foodFound){
            Error error = new Error(404,"no such foodName");
            return error;
        }
        else{
            u.getMyCart().deleteSpecificFood(orderFood);
            return u.getMyCart();
        }
    }

    @RequestMapping(value = "/users/{id}/cart", params = "saleFoodName",method = RequestMethod.DELETE)
    public @ResponseBody
    Object deleteSaleFoodFromCart(
            @PathVariable(value = "id") Integer id,
            @RequestParam(value = "restaurantId") String restaurantId,
            @RequestParam(value = "saleFoodName") String foodName) throws IOException {
        boolean restaurantFound = false;
        boolean foodFound = false;
        boolean userFound = false;
        User u = new User();
        for(User user:RestaurantManager.getInstance().getUsers()){
            if(user.getId()==id) {
                userFound = true;
                u = user;
            }
        }
        SaleFood orderFood = new SaleFood();
        for(SaleFood food:RestaurantManager.getInstance().getSaleFoods()){
            if(food.getRestaurantId().equals(restaurantId)){
                restaurantFound = true;
                if(food.getName().equals(foodName)){
                    foodFound = true;
                    orderFood = food;
                }
            }
        }
        if (!userFound){
            Error error = new Error(404,"no such id");
            return error;
        }
        if(!restaurantFound){
            Error error = new Error(404,"no such restaurantId");
            return error;
        }
        if(!foodFound){
            Error error = new Error(404,"no such foodName");
            return error;
        }
        else{
            u.getMyCart().deleteSpecificSaleFood(orderFood);
            return u.getMyCart();
        }
    }

    @RequestMapping(value = "/users/{id}/cart",method = RequestMethod.POST)
    public @ResponseBody
    Object finalizeCart(@PathVariable(value = "id") Integer id) throws IOException {
        boolean found=false;
        User u = new User();
        for(User user:RestaurantManager.getInstance().getUsers()){
            if(user.getId()==id) {
                found = true;
                u = user;
            }
        }
        if(found){
            Cart userCart = u.getMyCart();
            if(userCart.getFoods().size()==0 && userCart.getSaleFoods().size()==0) {
                Error error = new Error(404,"Your cart is empty!");
                return error;
            }
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
                    ArrayList<Food> foods = new ArrayList<>(RestaurantManager.getInstance().getCurrentUser().getMyCart().getFoods());
                    ArrayList<SaleFood> saleFoods = new ArrayList<>(RestaurantManager.getInstance().getCurrentUser().getMyCart().getSaleFoods());
                    ArrayList<Integer> counts = new ArrayList<>(RestaurantManager.getInstance().getCurrentUser().getMyCart().getNumberOfFood());
                    ArrayList<Integer> saleCounts = new ArrayList<>(RestaurantManager.getInstance().getCurrentUser().getMyCart().getNumberOfSaleFood());
                    previousCart.setFoods(foods);
                    previousCart.setSaleFoods(saleFoods);
                    previousCart.setNumberOfFood(counts);
                    previousCart.setNumberOfSaleFood(saleCounts);
                    u.addOrder(previousCart);
                    u.getMyCart().clearCart();
                    return u.getMyCart();
                }
                else{
                    if(u.getCredit()<total) {
                        Error error = new Error(400,"not enough credit");
                        return error;
                    }
                    else{
                        Error error = new Error(400,"empty cart");
                        return error;

                    }
                }
            }
        }
        else{
            Error error = new Error(404,"No such user!");
            return error;
        }
    }

}