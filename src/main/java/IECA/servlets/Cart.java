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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

@WebServlet("/Cart")
public class Cart extends HttpServlet {
    private HashMap<String,String> strAttr;
    private String foodName = "";
    private String restaurantId = "";
    private String restaurantName = "";
    private String value;
    private ServletHandler servletHandler;
    private ArrayList<Food> foods ;
    private ArrayList<SaleFood> saleFoods;
    private void initial(HttpServletRequest request, String from) throws IOException {
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
        RequestDispatcher requestDispatcher = request.getRequestDispatcher(address);
        requestDispatcher.forward(request, response);
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
            initial(request, "foodInfo");
            foodName =new String(value.split(",")[0].getBytes(StandardCharsets.ISO_8859_1),StandardCharsets.UTF_8);
            restaurantId = value.split(",")[1];
            restaurantName = RestaurantManager.getInstance().searchForRestaurant("{\"id\":\""+restaurantId+"\"}").getName();
            foodInJson = createJsonForm();
            goToCorrectPage(request,response,RestaurantManager.getInstance().addToCart(foodInJson));
        }
        else if(request.getParameter("cartFromHome")!=null){
            initial(request,"cartFromHome");
            if(foods.size()>0) {
                restaurantId = foods.get(0).getRestaurantId();
                restaurantName = RestaurantManager.getInstance().searchForRestaurant("{\"id\":\"" + restaurantId + "\"}").getName();
            }
            if(saleFoods.size()>0) {
                restaurantId = saleFoods.get(0).getRestaurantId();
                restaurantName = saleFoods.get(0).getRestaurantName();
            }
            dispatch(request,response,"cart.jsp",200);
        }
        else if(request.getParameter("cartFromFoodParty")!=null){
            initial(request,"cartFromFoodParty");
            foodName =new String(value.split(",")[0].getBytes(StandardCharsets.ISO_8859_1),StandardCharsets.UTF_8);
            restaurantId = value.split(",")[1];
            restaurantName =new String(value.split(",")[3].getBytes(StandardCharsets.ISO_8859_1),StandardCharsets.UTF_8);
            foodInJson = createJsonForm();
            goToCorrectPage(request,response,RestaurantManager.getInstance().addToCartSaleFood(foodInJson));
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
