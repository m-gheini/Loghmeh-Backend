package IECA.logic;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class Restaurant {
    private String name;
    private String description;
    private Location location;
    private ArrayList<Food> menu ;
    private String id;
    private String logo;

    public String getName(){
        return name;
    }
    public String getDescription(){
        return description;
    }
    public Location getLocation(){
        return location;
    }
    public ArrayList<Food> getMenu(){
        return  menu;
    }
    public String getId(){return id;}
    public String getLogo(){return logo;}

    public void addFood(Food _food){
        menu.add(_food);
    }
    public void setName(String _name){name = _name;}
    public void setDescription(String _description){description = _description;}
    public void setLocation(Location _address){
        location = _address;
    }
    public void setMenu(ArrayList<Food> _menu){
        menu = new ArrayList<Food>();
        menu.addAll(_menu);
    }
    public void setId(String _id){id = _id;}
    public void setLogo(String _logo){logo = _logo;}
    public int calculateApproximationArrival(){
        int result = 0;
        int distanceResCustomer = (int) Math.sqrt(Math.pow(location.getX(), 2) + Math.pow(location.getY(), 2));
        int distanceDeliveryRes = distanceResCustomer/2;
        result += (distanceResCustomer/5) + (distanceDeliveryRes/5) + 60;
        return result;
    }
    @Override
    public String toString(){
        String restaurantInString = "{ ";
        restaurantInString = restaurantInString + "\"id\" : " + id + ",\"name\" : " + name +"\"location\" : " +location.toString()
           +",\"logo\" : "+logo + "\"menu\" :[\\n";
        for(Food current:menu)
            restaurantInString+=current.toString();

        return restaurantInString+"]\\n}";
    }
}
