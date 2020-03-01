package IECA.database;

import IECA.logic.*;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.math.BigDecimal;

public class FoodPartyDataset extends DatasetManager{
    public ArrayList<SaleFood> convertObjToSaleFood(HashMap<String,Object> object){
        ArrayList<SaleFood> saleFoods = new ArrayList<SaleFood>();
        String restaurantImage = (String) object.get("logo");
        String restaurantId = (String) object.get("id");
        String restaurantName = (String) object.get("name");
        Location restaurantLocation = new Location();
        restaurantLocation.setX((int)(((HashMap<String ,Object>)object.get("location")).get("x")));
        restaurantLocation.setY((int)(((HashMap<String ,Object>)object.get("location")).get("y")));

        for(HashMap<String,Object> obj:(ArrayList<HashMap<String,Object>>)object.get("menu")){
            SaleFood saleFood = new SaleFood();
            saleFood.setRestaurantImage(restaurantImage);
            saleFood.setRestaurantLocation(restaurantLocation);
            saleFood.setRestaurantName(restaurantName);
            saleFood.setRestaurantId(restaurantId);
            saleFood.setCount((int) obj.get("count"));
            saleFood.setOldPrice((int) obj.get("oldPrice"));
            saleFood.setName((String) obj.get("name"));
            saleFood.setDescription((String) obj.get("description"));
            saleFood.setPrice((int) obj.get("price"));
            saleFood.setPopularity(BigDecimal.valueOf((Double) obj.get("popularity")).floatValue());
            saleFood.setImage((String) obj.get("image"));
            saleFoods.add(saleFood);
        }
        return saleFoods;
    }
    @Override
    public void addToDataset(String datasetInString) throws IOException {
        ObjectMapper foodMapper = new ObjectMapper();
        ArrayList<HashMap<String,Object>> foodsObj = foodMapper.readValue(datasetInString, new TypeReference<ArrayList<HashMap<String,Object>>>(){});
        ArrayList<SaleFood> foods = new ArrayList<SaleFood>();
        for (HashMap<String,Object> o:foodsObj){
            foods.addAll(convertObjToSaleFood(o));
        }
        setFoodsOnSale(foods);
        System.out.println("##############################################");
        for(Object s:foods){
            System.out.println(s.toString());
        }
    }
}
