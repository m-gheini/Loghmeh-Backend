package IECA.servlets;

import IECA.logic.Food;
import IECA.logic.Restaurant;
import IECA.logic.RestaurantManager;
import IECA.logic.SaleFood;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

@WebServlet("/Cart")
public class Cart extends HttpServlet {
    private HashMap<String,String> strAttr;
    private String foodName;
    private String restaurantId ;
    private String restaurantName;
    private String value;
    private ServletHandler servletHandler;
    private ArrayList<Food> foods ;
    private ArrayList<SaleFood> saleFoods;

    private void initial(HttpServletRequest request, String from) throws IOException, SQLException {
        strAttr = new HashMap<>();
        foodName = "";
        restaurantId = "";
        restaurantName = "";
        servletHandler = new ServletHandler();
        value = request.getParameter(from);
        foods = RestaurantManager.getInstance().getCurrentUser().getMyCart().getFoods();
        saleFoods = RestaurantManager.getInstance().getCurrentUser().getMyCart().getSaleFoods();
    }
    private void dispatch(HttpServletRequest request, HttpServletResponse response, String address,int statusCode) throws ServletException, IOException {
        response.setStatus(statusCode);
        strAttr.put("restaurantId", restaurantId);
        strAttr.put("restaurantName", restaurantName);
        strAttr.put("foodName", foodName);
        servletHandler.setStrAttributes(strAttr,request);
        servletHandler.dispatchTo(request,response,address);
    }

    private String createJsonForm(){
        return "{\"foodName\":\""+foodName+"\","+"\"restaurantId\":\""+restaurantId+"\"}";
    }

    private void goToCorrectPage(HttpServletRequest request, HttpServletResponse response, int condition) throws ServletException, IOException {
        if(condition == 0){
            dispatch(request,response,"differentRestaurantError.jsp",400);
        }
        else {
            dispatch(request,response,"cart.jsp",200);
        }
    }
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String foodInJson="";
        if(request.getParameter("foodInfo")!=null) {
            try {
                initial(request, "foodInfo");
            } catch (SQLException e) {
                e.printStackTrace();
            }
            foodName =new String(value.split(",")[0].getBytes(StandardCharsets.ISO_8859_1),StandardCharsets.UTF_8);
            restaurantId = value.split(",")[1];
            try {
                restaurantName = RestaurantManager.getInstance().searchForRestaurant("{\"id\":\""+restaurantId+"\"}").getName();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            foodInJson = createJsonForm();
            try {
                goToCorrectPage(request,response,RestaurantManager.getInstance().addToCart(foodInJson));
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        else if(request.getParameter("cartFromHome")!=null){
            try {
                initial(request,"cartFromHome");
            } catch (SQLException e) {
                e.printStackTrace();
            }
            try {
                restaurantId = RestaurantManager.getInstance().setRestaurantId(restaurantId);
            } catch (SQLException e) {
                e.printStackTrace();
            }
            try {
                restaurantName = RestaurantManager.getInstance().setRestaurantName(restaurantName,restaurantId);
            } catch (SQLException e) {
                e.printStackTrace();
            }
            dispatch(request,response,"cart.jsp",200);
        }
        else if(request.getParameter("cartFromFoodParty")!=null){
            try {
                initial(request,"cartFromFoodParty");
            } catch (SQLException e) {
                e.printStackTrace();
            }
            foodName =new String(value.split(",")[0].getBytes(StandardCharsets.ISO_8859_1),StandardCharsets.UTF_8);
            restaurantId = value.split(",")[1];
            restaurantName =new String(value.split(",")[3].getBytes(StandardCharsets.ISO_8859_1),StandardCharsets.UTF_8);
            foodInJson = createJsonForm();
            try {
                goToCorrectPage(request,response,RestaurantManager.getInstance().addToCartSaleFood(foodInJson));
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
