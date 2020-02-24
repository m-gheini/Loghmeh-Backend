package IECA.logic;
import IECA.database.*;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;


import java.io.IOException;
import java.util.*;


public class RestaurantManager {
    private static RestaurantManager instance;


    private ArrayList<Restaurant> restaurants;
    private ArrayList<Food> foods ;
    private User currentUser;
    private ArrayList<Delivery> deliveries;
    private int bestTime;
    private RestaurantManager() throws IOException {
        RestaurantDataset restaurantDataset = new RestaurantDataset();
        restaurantDataset.addToDataset(restaurantDataset.readFromWeb("http://138.197.181.131:8080/restaurants"));
        restaurants = restaurantDataset.getRestaurants();
        foods = restaurantDataset.getFoods();
        currentUser = new User();
        deliveries = new ArrayList<Delivery>();
    }

    public void setBestTime(int bestTime) {
        this.bestTime = bestTime;
    }

    public int getBestTime() {
        return bestTime;
    }

    public static RestaurantManager getInstance() throws IOException {
        if (instance == null)
            instance = new RestaurantManager();
        return instance;
    }
    public int getBestDelivery(Restaurant restaurant){
        int min = 999999999;
        for(Delivery delivery:deliveries){
            if (delivery.getTime(restaurant)<min)
                min = (int)delivery.getTime(restaurant);
        }
        System.out.println(min);
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

    public int addToCart(String jsonInString) throws IOException {
        int success = currentUser.getMyCart().addFood(jsonInString, foods);
        return success;
    }

    public boolean finalizeOrder() {
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