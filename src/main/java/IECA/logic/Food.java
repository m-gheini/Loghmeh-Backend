package IECA.logic;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class Food {
    private String name;
    private String description;
    private float popularity;
    private String restaurantId;
    private int price;
    private String image;
    public String getName(){
        return name;
    }
    public String getDescription(){
        return description;
    }
    public float getPopularity(){
        return  popularity;
    }
    public String getRestaurantId(){
        return restaurantId;
    }
    public int getPrice(){
        return price;
    }
    public String getImage(){
        return image;
    }
    public void setName(String _name){name = _name;}
    public void setDescription(String _description){description = _description;}
    public void setPopularity(float _populariry){popularity = _populariry;}
    public void setRestaurantId(String _restaurantId){restaurantId = _restaurantId;}
    public void setPrice(int _price){price = _price;}
    public void setImage(String _image){image = _image;}
//    @Override
//    public String toString(){
//        return "{ \"name\" : " + name + ", \"description\" : " + description + ",\"popularity\" : " + popularity + ", \"price\" : " + price + ", \"image\" : " + image +" } "  ;
//    }
}
