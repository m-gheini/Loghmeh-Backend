package IECA.database;

import IECA.logic.Delivery;
import IECA.logic.Restaurant;
import IECA.logic.Food;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;

public class DatasetManager {
    private ArrayList<Restaurant> restaurants;
    private ArrayList<Food> foods;
    private ArrayList<Food> foodsOnSale;
    private ArrayList<Delivery> deliveries;


    public ArrayList<Restaurant> getRestaurants() {
        return restaurants;
    }

    public ArrayList<Delivery> getDeliveries() {
        return deliveries;
    }

    public ArrayList<Food> getFoods() {
        return foods;
    }

    public ArrayList<Food> getFoodsOnSale() {
        return foodsOnSale;
    }

    public void setDeliveries(ArrayList<Delivery> deliveries) {
        this.deliveries = deliveries;
    }

    public void setFoods(ArrayList<Food> foods) {
        this.foods = foods;
    }

    public void setFoodsOnSale(ArrayList<Food> foodsOnSale) {
        this.foodsOnSale = foodsOnSale;
    }

    public void setRestaurants(ArrayList<Restaurant> restaurants) {
        this.restaurants = restaurants;
    }

    public String readFromWeb(String urlString) throws IOException {
        URL url = new URL(urlString);
        BufferedReader br = new BufferedReader(new InputStreamReader(url.openStream()));
        StringBuilder sb = new StringBuilder();
        sb.append(br.readLine());
        return sb.toString();
    }

    public void addToDataset(String datasetInString) throws IOException {}
    public static void main(String args[]) throws IOException {
        RestaurantDataset d = new RestaurantDataset();
        d.readFromWeb("http://138.197.181.131:8080/restaurants");
        d.addToDataset(d.readFromWeb("http://138.197.181.131:8080/restaurants"));
        System.out.println(d.getRestaurants().get(0).getId());
    }
}
