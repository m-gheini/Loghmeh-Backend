package IECA.logic;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;
import IECA.logic.schedulers.*;
public class Cart {
    private ArrayList<Food> foods ;
    private ArrayList<Integer> numberOfFood ;
    private ArrayList<Food> saleFoods ;
    private ArrayList<Integer> numberOfSaleFood ;
    private String status;
    int index;
    public Cart() {
        foods = new ArrayList<Food>();
        numberOfFood = new ArrayList<Integer>();
        saleFoods = new ArrayList<Food>();
        numberOfSaleFood = new ArrayList<Integer>();
        status = "finding delivery";
        index = 0;
    }
    public int addSaleFood(String jsonString, ArrayList<SaleFood> allSaleFoods) throws IOException {
        System.out.println("+*+*+*+*+*+*+*+*+()()()()()FoodSaleB------>"+saleFoods.size());
        boolean found = false;
        ObjectMapper mapper = new ObjectMapper();
        Map<String, Object> jsonMap = mapper.readValue(jsonString, new TypeReference<Map<String, Object>>() {
        });
        String foodName = (String) jsonMap.get("foodName");
        String restaurantId = (String) jsonMap.get("restaurantId");
        int index = 0;
        System.out.println("+*+*+*+*+*+*+*+*+()()()()()FoodSaleC------>"+saleFoods.size());
        for (int i = 0; i < saleFoods.size(); i++) {
            if (!(restaurantId.equals(saleFoods.get(i).getRestaurantId())))
                return 0;
            else {
                if (foodName.equals(saleFoods.get(i).getName()) && restaurantId.equals(saleFoods.get(i).getRestaurantId())) {
                    found = true;
                    index = i;
                }
            }
        }
        for (SaleFood allSaleFood : allSaleFoods) {
            if (foodName.equals(allSaleFood.getName()) && restaurantId.equals(allSaleFood.getRestaurantId())) {
                if(allSaleFood.getCount() > 0) {
                    if (found) {
                        numberOfSaleFood.set(index, numberOfSaleFood.get(index) + 1);
                        allSaleFood.updateCount();
                    } else {
                        saleFoods.add(allSaleFood);
                        numberOfSaleFood.add(1);
                        allSaleFood.updateCount();
                    }
                    return 1;
                }
                else{
                    System.out.println("NOT ENOUGH COUNT!!!");
                }
            }
        }
        return 0;
    }
    public int getIndex() {
        return index;
    }

    public ArrayList<Food> getSaleFoods() {
        return saleFoods;
    }

    public ArrayList<Integer> getNumberOfSaleFood() {
        return numberOfSaleFood;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public String getStatus() {
        return status;
    }

    public ArrayList<Food> getFoods(){
        return foods;
    }
    public ArrayList<Integer> getNumberOfFood() {
        return numberOfFood;
    }

    public void setSaleFoods(ArrayList<Food> saleFoods) {
        this.saleFoods = saleFoods;
    }

    public void setNumberOfSaleFood(ArrayList<Integer> numberOfSaleFood) {
        this.numberOfSaleFood = numberOfSaleFood;
    }

    public void setFoods(ArrayList<Food> foods) {
        this.foods = foods;
    }

    public void setNumberOfFood(ArrayList<Integer> numberOfFood) {
        this.numberOfFood = numberOfFood;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void clearCart(){
        foods.clear();
        numberOfFood.clear();
    }
    public int addFood(String jsonString, ArrayList<Food> allFoods) throws IOException {
        boolean found = false;
        boolean totallyFound = false;
        ObjectMapper mapper = new ObjectMapper();
        Map<String, Object> jsonMap = mapper.readValue(jsonString, new TypeReference<Map<String,Object>>(){});
        String foodName = (String) jsonMap.get("foodName");
        String  restaurantId= (String) jsonMap.get("restaurantId");
        int index = 0;
        for (int i = 0;i<foods.size();i++) {
            if (!(restaurantId.equals(foods.get(i).getRestaurantId()))) {
                System.out.println("YOU CAN NOT CHOOSE FOOD FROM DIFFERENT RESTAURANTS");
                return 0;
            } else {
                if (foodName.equals(foods.get(i).getName()) && restaurantId.equals(foods.get(i).getRestaurantId())) {
                    found = true;
                    index = i;
                }
            }
        }
        for (int j = 0; j < allFoods.size(); j++) {
            if (foodName.equals(allFoods.get(j).getName()) && restaurantId.equals(allFoods.get(j).getRestaurantId())) {

                if (found == true) {
                    numberOfFood.set(index, numberOfFood.get(index) + 1);
                } else {
                    foods.add(allFoods.get(j));
                    numberOfFood.add(1);
                }
                return 1;
            }
        }
        System.out.println("THIS FOOD HAS NOT BEEN EXISTED");
        return 0;
    }
}
