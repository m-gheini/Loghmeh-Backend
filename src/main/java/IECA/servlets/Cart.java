package IECA.servlets;

import IECA.logic.Food;
import IECA.logic.RestaurantManager;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

@WebServlet("/Cart")
public class Cart extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String foodInJson="";
        if(request.getParameter("foodInfo")!=null) {
            String value = request.getParameter("foodInfo");
            String foodName =new String(value.split(",")[0].getBytes(StandardCharsets.ISO_8859_1),StandardCharsets.UTF_8);
            String restaurantId = value.split(",")[1];
            int price = Integer.parseInt(value.split(",")[2]);
            foodInJson = "{\"foodName\":\""+foodName+"\","+"\"restaurantId\":\""+restaurantId+"\"}";
            RestaurantManager.getInstance().addToCart(foodInJson);
            request.setAttribute("foodName",foodName);
            request.setAttribute("restaurantId",restaurantId);
            request.setAttribute("cart",null);
            request.setAttribute("i",-9);


        }
        else if(request.getParameter("cart")!=null){
            int i = Integer.parseInt(request.getParameter("cart"));
            IECA.logic.Cart previousCart = RestaurantManager.getInstance().getCurrentUser().getOrders().get(i);
            request.setAttribute("cart",previousCart);
            request.setAttribute("restaurantId",RestaurantManager.getInstance().getCurrentUser().getOrders().get(i).getFoods().get(0).getRestaurantId());
            request.setAttribute("i",i);
        }

        RequestDispatcher requestDispatcher = request.getRequestDispatcher("cart.jsp");
        requestDispatcher.forward(request, response);

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
