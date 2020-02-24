package IECA.logic;

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
    public double getTime(Restaurant restaurant){
        return  (Math.sqrt(Math.pow(location.getX()-restaurant.getLocation().getX(), 2) +
                Math.pow(location.getY()-restaurant.getLocation().getY(), 2))+
                (Math.sqrt(Math.pow(restaurant.getLocation().getX(), 2) +
                        Math.pow(restaurant.getLocation().getY(), 2))))/velocity;
    }
}
