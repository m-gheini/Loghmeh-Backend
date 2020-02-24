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
    private String status;
    public Cart(){
        foods = new ArrayList<Food>();
        numberOfFood = new ArrayList<Integer>();
        status = "finding delivery";
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
