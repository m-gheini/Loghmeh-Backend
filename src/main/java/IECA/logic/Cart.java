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
    private ArrayList<SaleFood> saleFoods ;
    private ArrayList<Integer> numberOfSaleFood ;
    private String status;
    int index;
    public Cart() {
        foods = new ArrayList<Food>();
        numberOfFood = new ArrayList<Integer>();
        saleFoods = new ArrayList<SaleFood>();
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
        System.out.println("ID---:>"+restaurantId);
        int index = 0;
        System.out.println("+*+*+*+*+*+*+*+*+()()()()()FoodSaleC------>"+saleFoods.size());
        if((foods.size()>0 && !foods.get(0).getRestaurantId().equals(restaurantId)))
            return 0;
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
                    } else {
                        saleFoods.add(allSaleFood);
                        numberOfSaleFood.add(1);
                    }
                    allSaleFood.updateCount();
                    return 1;
                }
                else{
                    System.out.println("NOT ENOUGH COUNT!!!");
                    //TODO:error for count of sale food
                }
            }
        }
        return 0;
    }
    public int getIndex() {
        return index;
    }

    public ArrayList<SaleFood> getSaleFoods() {
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

    public void setSaleFoods(ArrayList<SaleFood> saleFoods) {
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
        saleFoods.clear();
        numberOfSaleFood.clear();
    }
    public void deleteSaleFood(SaleFood saleFood){
        for (int i=0;i<saleFoods.size();i++){
            if(saleFood.getName().equals(saleFoods.get(i).getName()) &&
                    saleFood.getRestaurantId().equals(saleFoods.get(i).getRestaurantId())){
                saleFoods.remove(i);
                numberOfSaleFood.remove(i);
                break;
            }
        }
    }
    public void deleteSpecificFood(Food food){
        for (int i=0;i<foods.size();i++){
            if(foods.get(i).getRestaurantId().equals(food.getRestaurantId()) &&
            foods.get(i).getName().equals(food.getName())){
                if(numberOfFood.get(i)>1) {
                    numberOfFood.set(i, numberOfFood.get(i) - 1);
                }
                else{
                    foods.remove(i);
                    numberOfFood.remove(i);
                }
            }
        }
    }
    public int addFood(String jsonString, ArrayList<Food> allFoods) throws IOException {
        boolean found = false;
        boolean totallyFound = false;
        ObjectMapper mapper = new ObjectMapper();
        Map<String, Object> jsonMap = mapper.readValue(jsonString, new TypeReference<Map<String,Object>>(){});
        String foodName = (String) jsonMap.get("foodName");
        String  restaurantId= (String) jsonMap.get("restaurantId");
        int index = 0;
        if((saleFoods.size()>0 && !saleFoods.get(0).equals(restaurantId)))
        return 0;
        for (int i = 0;i<foods.size();i++) {
            if (!(restaurantId.equals(foods.get(i).getRestaurantId()))){
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
