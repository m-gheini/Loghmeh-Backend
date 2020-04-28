package IECA.logic;

import IECA.database.mappers.*;
import IECA.database.mappers.order.SaleOrderMapper;
import IECA.database.mappers.cart.CartMapper;
import IECA.database.mappers.cart.SaleCartMapper;
import IECA.database.mappers.order.OrderMapper;
import IECA.database.mappers.user.UserMapper;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
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
        name = "لیلا";
        familyName = "قینی";
        email ="fakheri90@gmail.com";
        credit = 5000;
        phoneNumber = "09121111111";
        myCart = new Cart();
        myCart.setUserId(id);
        orders = new ArrayList<>();
    }
    public void setAllParameters(int id,String name,String familyName,String email,int credit,String phoneNumber){
        this.id = id;
        this.name = name;
        this.familyName = familyName;
        this.email = email;
        this.credit = credit;
        this.phoneNumber = phoneNumber;
        this.myCart = new Cart();
        myCart.setUserId(id);
        this.orders = new ArrayList<Cart>();
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
    public void addOrder(Cart newOrder) throws IOException, SQLException {
        CartMapper cartMapper = new CartMapper(false);
        OrderMapper orderMapper  = new OrderMapper(false);
        SaleCartMapper saleCartMapper = new SaleCartMapper(false);
        SaleOrderMapper saleOrderMapper = new SaleOrderMapper(false);
        Connection connection =  ConnectionPool.getConnection();
        ArrayList<Integer> idKey = new ArrayList<Integer>();
        idKey.add(this.id);
        ArrayList<Cart> carts = cartMapper.findByForeignKey(idKey);
        ArrayList<Cart> saleCarts = saleCartMapper.findByForeignKey(idKey);
        if(carts.size() > 0) {
            for (Cart c : carts) {
                c.setIndex(RestaurantManager.getInstance().getCurrentUser().getOrders().size());
                orderMapper.insert(c);
            }
            cartMapper.delete(idKey);
        }
        if(saleCarts.size() > 0) {
            for (Cart c : saleCarts) {
                c.setIndex(RestaurantManager.getInstance().getCurrentUser().getOrders().size());
                saleOrderMapper.insert(c);
            }
            saleCartMapper.delete(idKey);
        }
        connection.close();
        System.out.println("ADD ORDER IN  USER SIDE!!!!");
        newOrder.setIndex(RestaurantManager.getInstance().getCurrentUser().getOrders().size());
        orders.add(newOrder);
        System.out.println(orders.size());
    }
    public void addCredit(int value) throws SQLException {
        credit = credit+value;
        UserMapper userMapper = new UserMapper(false);
        Connection connection = ConnectionPool.getConnection();
        ArrayList<Integer> key = new ArrayList<Integer>();
        key.add(id);
        userMapper.insert(this);
        connection.close();
    }

}
