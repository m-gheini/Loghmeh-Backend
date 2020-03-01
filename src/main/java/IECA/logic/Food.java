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


    public void setDescription(String description) {
        this.description = description;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPopularity(float popularity) {
        this.popularity = popularity;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public void setRestaurantId(String restaurantId) {
        this.restaurantId = restaurantId;
    }

        @Override
    public String toString(){
        return "{ \"name\" : " + name + ", \"description\" : " + description + ",\"popularity\" : " + popularity + ", \"price\" : " + price + ", \"image\" : " + image +" } "  ;
    }
}
