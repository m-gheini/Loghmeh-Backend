package IECA.logic;

public class SaleFood extends Food{
    private int oldPrice;
    private int count;
    private String restaurantName;
    private String restaurantImage;
    private Location restaurantLocation;

    public Location getRestaurantLocation() {
        return restaurantLocation;
    }

    public String getRestaurantName() {
        return restaurantName;
    }

    public String getRestaurantImage() {
        return restaurantImage;
    }

    public int getOldPrice() {
        return oldPrice;
    }

    public int getCount() {
        return count;
    }

    public void setOldPrice(int oldPrice) {
        this.oldPrice = oldPrice;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public void setRestaurantName(String restaurantName) {
        this.restaurantName = restaurantName;
    }

    public void setRestaurantImage(String restaurantImage) {
        this.restaurantImage = restaurantImage;
    }

    public void setRestaurantLocation(Location restaurantLocation) {
        this.restaurantLocation = restaurantLocation;
    }

    public void updateCount(){
        this.count--;
    }

    public String toString(){
        return "{ "+"\"count\" : " + getCount() +"\",oldPrice\" : " + getOldPrice() +"\",name\" : " + getName() + ", \"description\" : " + getDescription() + ",\"popularity\" : " + getPopularity() + ", \"price\" : " + getPrice() + ", \"image\" : " + getImage() +" } "  ;
    }
}
