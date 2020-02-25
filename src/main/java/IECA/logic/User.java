package IECA.logic;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class User {
    private int id;
    private String name;
    private String familyName;
    private String email;
    private int credit;
    private Cart myCart;
    private String phoneNumber;
    private ArrayList<Cart> orders;
    public User(){
        id = 1;
        name = "leila";
        familyName = "fakheri";
        email ="fakheri90@gmail.com";
        credit = 5000;
        phoneNumber = "09121111111";
        myCart = new Cart();
        orders = new ArrayList<>();
    }

    public ArrayList<Cart> getOrders() {
        return orders;
    }

    public Cart getMyCart(){
        return myCart;
    }
    public int getId(){
        return id;
    }
    public String getName(){
        return name;
    }
    public String getFamilyName(){
        return familyName;
    }
    public String getEmail(){
        return email;
    }
    public String getPhoneNumber(){
        return phoneNumber;
    }
    public int getCredit(){
        return credit;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setFamilyName(String familyName) {
        this.familyName = familyName;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setCredit(int credit) {
        this.credit = credit;
    }

    public void setMyCart(Cart myCart) {
        this.myCart = myCart;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void setOrders(ArrayList<Cart> orders) {
        this.orders = orders;
    }
    public void addOrder(Cart newOrder) throws IOException {
        newOrder.setIndex(RestaurantManager.getInstance().getCurrentUser().getOrders().size());
        orders.add(newOrder);
    }
    public void addCredit(int value){credit = credit+value;}

}
