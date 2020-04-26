package IECA.logic;

import java.io.IOException;
import java.sql.SQLException;

public class Delivery {
    private String id;
    private int velocity;
    private Location location;

    public int getVelocity() {
        return velocity;
    }

    public Location getLocation() {
        return location;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setVelocity(int velocity) {
        this.velocity = velocity;
    }

    public void setLocation(Location location) {
        this.location = location;
    }
    public double getTime(String restaurantId) throws IOException, SQLException {
        Location restaurantLocation = new Location();
        String jsonString = "{\"id\":\""+restaurantId+"\"}";
        if(RestaurantManager.getInstance().getCurrentUser().getMyCart().getFoods().size()>0)
            restaurantLocation = RestaurantManager.getInstance().searchForRestaurant(jsonString).getLocation();
        else if(RestaurantManager.getInstance().getCurrentUser().getMyCart().getSaleFoods().size()>0)
            restaurantLocation = RestaurantManager.getInstance().getCurrentUser().getMyCart().getSaleFoods().get(0).getRestaurantLocation();
        return  (Math.sqrt(Math.pow(location.getX()-restaurantLocation.getX(), 2) +
                Math.pow(location.getY()-restaurantLocation.getY(), 2))+
                (Math.sqrt(Math.pow(restaurantLocation.getX(), 2) +
                        Math.pow(restaurantLocation.getY(), 2))))/velocity;
    }
}
