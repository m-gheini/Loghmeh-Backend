package IECA.logic;
import IECA.database.*;
import IECA.database.mappers.RestaurantMapper;
import IECA.logic.schedulers.FoodPartyScheduler;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;


import java.io.IOException;
import java.sql.SQLException;
import java.util.*;


public class RestaurantManager {
    private static RestaurantManager instance;

    private ArrayList<Restaurant> restaurants;
    private ArrayList<Food> foods ;
    private ArrayList<Restaurant> saleRestaurants;//TODO initialize
    private ArrayList<SaleFood> saleFoods ;
    private User currentUser;
    private ArrayList<Delivery> deliveries;
    private int bestTime;
    private ArrayList<User> users;
    private int remainingTime;
    private RestaurantManager() throws IOException, SQLException {
        DatabaseManager db = new DatabaseManager();
        db.createDatabases();
        RestaurantDataset restaurantDataset = new RestaurantDataset();
        restaurantDataset.addToDataset(restaurantDataset.readFromWeb("http://138.197.181.131:8080/restaurants"));
        restaurants = db.getPermanentRestaurants();
        foods = db.getPermanentFoods();
        currentUser = new User();
        deliveries = new ArrayList<Delivery>();
        remainingTime = 0;
        FoodPartyScheduler foodPartyScheduler = new FoodPartyScheduler();
        users = db.getPermanentUser();
//        users=new ArrayList<User>();
//        users.add(currentUser);
    }

    public ArrayList<User> getUsers() {
        return users;
    }

    public void setUsers(ArrayList<User> users) {
        this.users = users;
    }
    public void addUser(User newUser){
        boolean found=false;
        for(User user:users){
            if(user.getId()==newUser.getId()){
                found=true;
            }
        }
        if(found==false){
            users.add((newUser));
        }
    }

    public ArrayList<SaleFood> getSaleFoods() {
        return saleFoods;
    }

    public ArrayList<Restaurant> getSaleRestaurants() {
        return saleRestaurants;
    }

    public void setSaleRestaurants(ArrayList<Restaurant> saleRestaurants) {
        this.saleRestaurants = saleRestaurants;
    }

    public void setSaleFoods(ArrayList<SaleFood> saleFoods) {
        this.saleFoods = saleFoods;
    }

    public ArrayList<Restaurant> getAllRestaurants(){
        return restaurants;
    }
    public void setBestTime(int bestTime) {
        this.bestTime = bestTime;
    }

    public int getBestTime() {
        return bestTime;
    }

    public int getRemainingTime() {
        return remainingTime;
    }

    public void setRemainingTime(int remainingTime) {
        this.remainingTime = remainingTime;
    }
    public void updateRemainingTime(){
        remainingTime = remainingTime-1000;
    }

    public static RestaurantManager getInstance() throws IOException, SQLException {
        if (instance == null)
            instance = new RestaurantManager();
        return instance;
    }
    public int getBestDelivery(String restaurantId) throws IOException, SQLException {
        int min = 999999999;
        for(Delivery delivery:deliveries){
            if (delivery.getTime(restaurantId)<min)
                min = (int)delivery.getTime(restaurantId);
        }
        bestTime = min;
        return min;
    }
    public ArrayList<Food> getFoods() {
        return foods;
    }

    public ArrayList<Delivery> getDeliveries() {
        return deliveries;
    }

    public User getCurrentUser() {
        return currentUser;
    }

    public void setRestaurants(ArrayList<Restaurant> restaurants) {
        this.restaurants = restaurants;
    }

    public void setFoods(ArrayList<Food> foods) {
        this.foods = foods;
    }

    public void setDeliveries(ArrayList<Delivery> deliveries) {
        this.deliveries = deliveries;
    }

    public void setCurrentUser(User currentUser) {
        this.currentUser = currentUser;
    }

    public boolean searchForResInAllRes(String resId) throws IOException, SQLException {
        boolean totallyNotFound = true;
        for(Restaurant restaurant : RestaurantManager.getInstance().getAllRestaurants()){
            if (restaurant.getId().equals(resId)) {
                totallyNotFound = false;
                break;
            }
        }
        return totallyNotFound;
    }
    public String setRestaurantId(String restaurantId) throws IOException, SQLException {
        if(getCurrentUser().getMyCart().getFoods().size()>0)
            restaurantId = getCurrentUser().getMyCart().getFoods().get(0).getRestaurantId();
        else if(getCurrentUser().getMyCart().getSaleFoods().size()>0)
            restaurantId = (RestaurantManager.getInstance().getCurrentUser().getMyCart().getSaleFoods().get(0).getRestaurantId());
        return restaurantId;
    }
    public String setRestaurantName(String restaurantName,String restaurantId) throws IOException, SQLException {
        restaurantId = setRestaurantId(restaurantId);
        if(getCurrentUser().getMyCart().getFoods().size()>0) {
            restaurantName =RestaurantManager.getInstance().searchForRestaurant("{\"id\":\""+restaurantId+"\"}").getName();
        }
        else if(RestaurantManager.getInstance().getCurrentUser().getMyCart().getSaleFoods().size()>0) {
            restaurantName = (RestaurantManager.getInstance().getCurrentUser().getMyCart().getSaleFoods().get(0).getRestaurantName());
        }
        return restaurantName;
    }
    public String getRestaurantNameForSpecOrder(Cart order) throws IOException {
        String restaurantName="";
        String restaurantId = "";
        if(order.getFoods().size()>0 ) {
            restaurantId = order.getFoods().get(0).getRestaurantId();
            restaurantName = searchForRestaurant("{\"id\":\""+restaurantId+"\"}").getName();
        }
        else if(order.getSaleFoods().size()>0 ) {
            restaurantName = order.getSaleFoods().get(0).getRestaurantName();
        }
        return restaurantName;
    }
    public int makeTotal(){
        int total=0;
        for (int i =0;i<getCurrentUser().getMyCart().getFoods().size();i++){
            total+=getCurrentUser().getMyCart().getNumberOfFood().get(i)*
                    getCurrentUser().getMyCart().getFoods().get(i).getPrice();
        }
        for (int i =0;i<getCurrentUser().getMyCart().getSaleFoods().size();i++){
            total+=getCurrentUser().getMyCart().getNumberOfSaleFood().get(i)*
                    getCurrentUser().getMyCart().getSaleFoods().get(i).getPrice();
        }
        return total;
    }
    public boolean searchInProperResById(String resId) throws IOException, SQLException {
        boolean notFound = true;
        for (Restaurant restaurant : RestaurantManager.getInstance().getRestaurants()) {
            if (restaurant.getId().equals(resId)) {
                notFound = false;
                break;
            }
        }
        return notFound;
    }
    public Restaurant searchResById(String id) throws IOException, SQLException {
        Restaurant nullRest = new Restaurant();
        for (Restaurant restaurant : RestaurantManager.getInstance().getRestaurants()) {
            if (restaurant.getId().equals(id)) {
                return restaurant;
            }
        }
        return nullRest;
    }
    public Restaurant searchForRestaurant(String jsonInString) throws IOException {
        Restaurant nullRest = new Restaurant();

        ObjectMapper mapper = new ObjectMapper();
        Map<String, String> jsonMap = mapper.readValue(jsonInString, new TypeReference<Map<String, String>>() {});
        String restaurantId = jsonMap.get("id");
        for (Restaurant current : restaurants) {
            if (current.getId().equals(restaurantId))
                return current;
        }
        return nullRest;
    }
    public Food searchForFood(String jsonInString) throws IOException {
        Food nullFood = new Food();
        ObjectMapper mapper = new ObjectMapper();
        Map<String, String> jsonMap = mapper.readValue(jsonInString, new TypeReference<Map<String, String>>() {
        });
        String foodName = jsonMap.get("foodName");
        String restaurantId = jsonMap.get("restaurantId");
        for (Food current : foods) {
            if (current.getName().equals(foodName) && current.getRestaurantId().equals(restaurantId)) {
                return current;
            }
        }
        return nullFood;
    }
    public ArrayList<SaleFood> saleFoodsOfSpecialRestaurant(String restaurantId){
        ArrayList<SaleFood> result = new ArrayList<SaleFood>();
        for(SaleFood sf:saleFoods) {
        if (sf.getRestaurantId().equals(restaurantId))
            result.add(sf);
        }
        return result;
    }
    public SaleFood findSpecialSaleFood(String restaurantId,String foodName){
        ArrayList<SaleFood> allSaleFoodsOfSpecRes = saleFoodsOfSpecialRestaurant(restaurantId);
        for(SaleFood saleFood:allSaleFoodsOfSpecRes){
            if (saleFood.getName().equals(foodName))
                return saleFood;
        }
        return null;
    }
    public boolean restaurantIdOfSaleFoodFound(String restaurantId){
        boolean restaurantFound = false;
        for(SaleFood sf:saleFoods){
            if(sf.getRestaurantId().equals(restaurantId)) {
                restaurantFound = true;
            }
        }
        return restaurantFound;
    }
    public boolean foodNameOfSaleFoodFound(String restaurantId,String saleFoodName){
        boolean found = false;
        for(SaleFood sf:saleFoods){
            if(sf.getRestaurantId().equals(restaurantId) && sf.getName().equals(saleFoodName)) {
                found = true;
            }
        }
    return found;
    }
    public Error errorForRestaurant(String id) throws IOException, SQLException {
        boolean totallyNotFound = searchForResInAllRes(id);
        if (totallyNotFound) {
            Error error = new Error(404,"no such id");
            return error;
        }
        boolean notFound = searchInProperResById(id);
        if (notFound) {
            Error error = new Error(403,"no such restaurant near you");
            return error;
        }
        return null;
    }
    public User findSpecUser(int id){
        for(User u:users){
            if(u.getId()==id){
                return u;
            }
        }
        return null;
    }
    public Cart findSpecOrder(int index,User user) {
        for (Cart order : user.getOrders()) {
            if (order.getIndex() == index) {
                return order;
            }
        }
        return null;
    }
    public void addRestaurant(String jsonInString) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        Restaurant newRestaurant= mapper.readValue(jsonInString, Restaurant.class);
        String restaurantId = newRestaurant.getId();
        for(Food current :newRestaurant.getMenu()) {
            current.setRestaurantId(restaurantId);
        }
        for(Restaurant current:restaurants){
            if(current.getId().equals(restaurantId)){
                System.out.println("THIS RESTAURANT HAS ALREADY EXISTED");
                return;
            }
        }
        restaurants.add(newRestaurant);
        foods.addAll(newRestaurant.getMenu());
    }
    public void addFood(String jsonInString) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        Food new_food = mapper.readValue(jsonInString, Food.class);
        String restaurantId = new_food.getRestaurantId();
        for(Restaurant current:restaurants) {
            if (current.getName().equals(restaurantId)) {
                for(Food current_food:current.getMenu()){
                    if(current_food.getName().equals(new_food.getName())){
                        System.out.println("THIS RESTAURANT ALREADY HAS THIS FOOD");
                        return;
                    }
                }
                foods.add(new_food);
                current.addFood(new_food);
            }
        }
    }
    public ArrayList<Restaurant> getRestaurants() {
        ArrayList<Restaurant> result = new ArrayList<Restaurant>();
        for (Restaurant res : restaurants) {
            Location Location = res.getLocation();
            Double distance = Math.sqrt(Math.pow(Location.getX(), 2) + Math.pow(Location.getY(), 2));
            if(distance <= 170){
                result.add(res);
            }
        }
        return result;
    }

    public Restaurant getRestaurant(String jsonInString) throws IOException {
        Restaurant inRes = searchForRestaurant(jsonInString);
        if (inRes.getName() == (null)) {
            System.out.println("NOT EXISTING RESTAURANT");
            return new Restaurant();
        } else {
            Location Location = inRes.getLocation();
            Double distance = Math.sqrt(Math.pow(Location.getX(), 2) + Math.pow(Location.getY(), 2));
            if(distance <= 170){
                return inRes;
            }
            return new Restaurant();
        }
    }

    public int addToCart(String jsonInString) throws IOException, SQLException {
        int success = currentUser.getMyCart().addFood(jsonInString, foods);
        if(success==1) {
            ObjectMapper mapper = new ObjectMapper();
            Map<String, Object> jsonMap = mapper.readValue(jsonInString, new TypeReference<Map<String, Object>>() {});
            String restaurantId = (String) jsonMap.get("restaurantId");
            System.out.println(currentUser.getMyCart().getRestaurantName());
            currentUser.getMyCart().setRestaurantName(RestaurantManager.getInstance().searchForRestaurant("{\"id\":\"" + restaurantId + "\"}").getName());
            System.out.println(currentUser.getMyCart().getRestaurantName());
        }
        return success;
    }
    public int addToCartSaleFood(String jsonInString) throws IOException, SQLException {
        int success = currentUser.getMyCart().addSaleFood(jsonInString, saleFoods);
        if(success==1) {
            ObjectMapper mapper = new ObjectMapper();
            Map<String, Object> jsonMap = mapper.readValue(jsonInString, new TypeReference<Map<String, Object>>() {
            });
            String restaurantId = (String) jsonMap.get("restaurantId");
            currentUser.getMyCart().setRestaurantName(RestaurantManager.getInstance().searchForRestaurant("{\"id\":\"" + restaurantId + "\"}").getName());
        }

        return success;
    }
    public boolean finalizeOrder(int credit) {
        int totalCost=0;
        for (int i = 0; i<currentUser.getMyCart().getFoods().size();i++){
            for(int j =0;j<currentUser.getMyCart().getNumberOfFood().get(i);j++){
                totalCost+=currentUser.getMyCart().getFoods().get(i).getPrice();
            }
        }
        if (totalCost<= currentUser.getCredit() && currentUser.getMyCart().getFoods().size()!=0) {
            currentUser.addCredit(-totalCost);
            return true;
        }
        return false;
    }
    public boolean searchInSaleFoods(SaleFood saleFood){
        for (SaleFood current:saleFoods){
            if(current.getRestaurantId().equals(saleFood.getRestaurantId()) && current.getName().equals(saleFood.getName()))
                return true;
        }
        return false;
    }
    public ArrayList<String> getRecommendedRestaurants() {
        Map<Double, String> treemap = new TreeMap<Double, String>(Collections.reverseOrder());
        ArrayList<String> result = new ArrayList<String>();
        for (Restaurant res : restaurants) {
            ArrayList<Food> res_menu = res.getMenu();
            double sum_popularity = 0;
            double count = 0;
            for (Food foo : res_menu) {
                sum_popularity += foo.getPopularity();
                count += 1;
            }
            Double avg_popularity = sum_popularity / count;
            Location Location = res.getLocation();
            Double distance = Math.sqrt(Math.pow(Location.getX(), 2) + Math.pow(Location.getY(), 2));
            Double like_rate = avg_popularity / distance;
            treemap.put(like_rate, res.getName());
        }
        Set set = treemap.entrySet();
        Iterator it = set.iterator();
        int i = 0;
        while (i < 3 && it.hasNext()) {
            Map.Entry me = (Map.Entry) it.next();
            i++;
            result.add((String) me.getValue());
            System.out.println(me.getValue());
        }
        return result;
    }

}