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

    @RequestMapping(value = "/users/{id}", method = RequestMethod.DELETE)
    public @ResponseBody
    Object deleteUser(@PathVariable(value = "id") Integer id) throws IOException {
        for(User u:RestaurantManager.getInstance().getUsers()){
            if(u.getId()==id){
                RestaurantManager.getInstance().getUsers().remove(u);
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
                Error error = new Error();
                error.setErrorCode(404);
                error.setErrorMassage("Your cart is empty!");
                return error;
            }
            else
                return userCart;
        }
        else{
            Error error = new Error();
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
            Error error = new Error();
            error.setErrorMassage("no such id");
            error.setErrorCode(404);
            return error;
        }
        if(!restaurantFound){
            Error error = new Error();
            error.setErrorMassage("no such restaurantId");
            error.setErrorCode(404);
            return error;
        }
        if(!foodFound){
            Error error = new Error();
            error.setErrorMassage("no such foodName");
            error.setErrorCode(404);
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
            Error error = new Error();
            error.setErrorMassage("no such id");
            error.setErrorCode(404);
            return error;
        }
        if(!restaurantFound){
            Error error = new Error();
            error.setErrorMassage("no such restaurantId");
            error.setErrorCode(404);
            return error;
        }
        if(!foodFound){
            Error error = new Error();
            error.setErrorMassage("no such foodName");
            error.setErrorCode(404);
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
            Error error = new Error();
            error.setErrorMassage("no such id");
            error.setErrorCode(404);
            return error;
        }
        if(!restaurantFound){
            Error error = new Error();
            error.setErrorMassage("no such restaurantId");
            error.setErrorCode(404);
            return error;
        }
        if(!foodFound){
            Error error = new Error();
            error.setErrorMassage("no such foodName");
            error.setErrorCode(404);
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
        u=RestaurantManager.getInstance().getCurrentUser();
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
            Error error = new Error();
            error.setErrorMassage("no such id");
            error.setErrorCode(404);
            return error;
        }
        if(!restaurantFound){
            Error error = new Error();
            error.setErrorMassage("no such restaurantId");
            error.setErrorCode(404);
            return error;
        }
        if(!foodFound){
            Error error = new Error();
            error.setErrorMassage("no such foodName");
            error.setErrorCode(404);
            return error;
        }
        else{
            u.getMyCart().deleteSpecificFood(orderFood);
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
        u = RestaurantManager.getInstance().getCurrentUser();
        if(found){
            Cart userCart = u.getMyCart();
            if(userCart.getFoods().size()==0 && userCart.getSaleFoods().size()==0) {
                Error error = new Error();
                error.setErrorCode(404);
                error.setErrorMassage("Your cart is empty!");
                return error;
            }
            else {
                Integer total = RestaurantManager.getInstance().makeTotal();
                String restaurantId = u.getMyCart().getFoods().get(0).getRestaurantId();
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
                        Error error = new Error();
                        error.setErrorCode(400);
                        error.setErrorMassage("not enough credit");
                        return error;
                    }
                    else{
                        Error error = new Error();
                        error.setErrorCode(400);
                        error.setErrorMassage("empty cart");
                        return error;

                    }
                }
            }
        }
        else{
            Error error = new Error();
            error.setErrorCode(404);
            error.setErrorMassage("No such user!");
            return error;
        }
    }

}