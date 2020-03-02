package IECA.servlets;

import IECA.logic.RestaurantManager;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/UserOrders")
public class UserOrders extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int i = Integer.parseInt(request.getParameter("cart"));
        System.out.println(i);
        IECA.logic.Cart previousCart = RestaurantManager.getInstance().getCurrentUser().getOrders().get(i);
        request.setAttribute("cart",previousCart);
        String restaurantId = "";
        String restaurantName = "";
        if(RestaurantManager.getInstance().getCurrentUser().getOrders().get(i).getFoods().size()>0) {
            restaurantId = RestaurantManager.getInstance().getCurrentUser().getOrders().get(i).getFoods().get(0).getRestaurantId();
            restaurantName = RestaurantManager.getInstance().searchForRestaurant("{\"id\":\""+restaurantId+"\"}").getName();
        }
        else if(RestaurantManager.getInstance().getCurrentUser().getOrders().get(i).getSaleFoods().size()>0) {
            restaurantId = RestaurantManager.getInstance().getCurrentUser().getOrders().get(i).getSaleFoods().get(0).getRestaurantId();
            restaurantName = RestaurantManager.getInstance().getCurrentUser().getOrders().get(i).getSaleFoods().get(0).getRestaurantName();
        }
        request.setAttribute("restaurantId", restaurantId);
        request.setAttribute("restaurantName",restaurantName);
        request.setAttribute("i",i);
        RequestDispatcher requestDispatcher = request.getRequestDispatcher("userOrders.jsp");
        requestDispatcher.forward(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
