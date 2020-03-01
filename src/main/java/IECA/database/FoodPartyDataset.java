package IECA.database;

import IECA.logic.Delivery;
import IECA.logic.Food;
import IECA.logic.Restaurant;
import IECA.logic.SaleFood;
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
        ArrayList<SaleFood> foods = new ArrayList<SaleFood>();
        for(Restaurant res :restaurants){
            for(SaleFood f:res.getSaleMenu()){
                f.setRestaurantId(res.getId());
                foods.add(f);
            }
        }
        setRestaurantsOnSale(restaurants);
        setFoodsOnSale(foods);
        System.out.println(restaurants.toString());
        System.out.println(foods.toString());
    }
}
