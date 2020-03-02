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
import java.util.Iterator;

@WebServlet("/Cart")
public class Cart extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String foodInJson="";
        if(request.getParameter("foodInfo")!=null) {
            String value = request.getParameter("foodInfo");
            String foodName =new String(value.split(",")[0].getBytes(StandardCharsets.ISO_8859_1),StandardCharsets.UTF_8);
            String restaurantId = value.split(",")[1];
            String restaurantName = RestaurantManager.getInstance().searchForRestaurant("{\"id\":\""+restaurantId+"\"}").getName();
            foodInJson = "{\"foodName\":\""+foodName+"\","+"\"restaurantId\":\""+restaurantId+"\"}";
            if(RestaurantManager.getInstance().addToCart(foodInJson) == 0){
                request.setAttribute("foodName",foodName);
                request.setAttribute("restaurantId",restaurantId);
                RequestDispatcher requestDispatcher = request.getRequestDispatcher("differentRestaurantError.jsp");
                requestDispatcher.forward(request, response);
            }
            else {
                request.setAttribute("foodName", foodName);
                request.setAttribute("restaurantId", restaurantId);
                request.setAttribute("restaurantName", restaurantName);
            }
        }
        else if(request.getParameter("cartFromHome")!=null){
            request.setAttribute("foodName",null);
            String restaurantId = "";
            if(RestaurantManager.getInstance().getCurrentUser().getMyCart().getFoods().size()>0) {
                restaurantId = RestaurantManager.getInstance().getCurrentUser().getMyCart().getFoods().get(0).getRestaurantId();
                request.setAttribute("restaurantId", restaurantId);
                request.setAttribute("restaurantName", RestaurantManager.getInstance().searchForRestaurant("{\"id\":\"" + restaurantId + "\"}").getName());
            }
            if(RestaurantManager.getInstance().getCurrentUser().getMyCart().getSaleFoods().size()>0) {
                request.setAttribute("restaurantId", RestaurantManager.getInstance().getCurrentUser().getMyCart().getSaleFoods().get(0).getRestaurantId());
                request.setAttribute("restaurantName", RestaurantManager.getInstance().getCurrentUser().getMyCart().getSaleFoods().get(0).getRestaurantName());
            }
            else
                request.setAttribute("restaurantId","");
        }
        else if(request.getParameter("cartFromFoodParty")!=null){
            String value = request.getParameter("cartFromFoodParty");
            String foodName =new String(value.split(",")[0].getBytes(StandardCharsets.ISO_8859_1),StandardCharsets.UTF_8);
            String restaurantId = value.split(",")[1];
            String restaurantName =new String(value.split(",")[3].getBytes(StandardCharsets.ISO_8859_1),StandardCharsets.UTF_8);
            foodInJson = "{\"foodName\":\""+foodName+"\","+"\"restaurantId\":\""+restaurantId+"\"}";
            if(RestaurantManager.getInstance().addToCartSaleFood(foodInJson) == 0 ){
                request.setAttribute("restaurantId",restaurantId);
                RequestDispatcher requestDispatcher = request.getRequestDispatcher("differentRestaurantError.jsp");
                requestDispatcher.forward(request, response);
            }
            else {
                request.setAttribute("restaurantId", restaurantId);
                request.setAttribute("restaurantName", restaurantName);
            }
        }
        RequestDispatcher requestDispatcher = request.getRequestDispatcher("cart.jsp");
        requestDispatcher.forward(request, response);

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
