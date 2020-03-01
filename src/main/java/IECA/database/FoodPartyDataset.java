package IECA.database;

import IECA.logic.Delivery;
import IECA.logic.Food;
import IECA.logic.Restaurant;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;

public class FoodPartyDataset extends DatasetManager{
    @Override
    public void addToDataset(String datasetInString) throws IOException {
        ObjectMapper restaurantMapper = new ObjectMapper();
        ArrayList<Restaurant> restaurants = restaurantMapper.readValue(datasetInString, new TypeReference<ArrayList<Restaurant>>(){});
        ArrayList<Food> foods = new ArrayList<Food>();
        for(Restaurant res :restaurants){
            for(Food f:res.getMenu()){
                f.setRestaurantId(res.getId());
                foods.add(f);
            }
        }
        setRestaurants(restaurants);
        setFoods(foods);
    }
}
