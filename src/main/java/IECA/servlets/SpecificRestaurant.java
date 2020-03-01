package IECA.servlets;

import IECA.logic.Food;
import IECA.logic.Restaurant;
import IECA.logic.RestaurantManager;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/SpecificRestaurant")
public class SpecificRestaurant extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String value = request.getParameter("restaurantInfo");
        boolean totallyNotFound = true;
        for(Restaurant restaurant : RestaurantManager.getInstance().getAllRestaurants()){
            if (restaurant.getId().equals(value)) {
                totallyNotFound = false;
                break;
            }
        }
        if (totallyNotFound) {
            response.setStatus(404);
            RequestDispatcher requestDispatcher = request.getRequestDispatcher("validIdError.jsp");
            requestDispatcher.forward(request, response);
        }
        boolean notFound = true;
        for (Restaurant restaurant : RestaurantManager.getInstance().getRestaurants()) {
            if (restaurant.getId().equals(value)) {
                notFound = false;
                break;
            }
        }
        if (notFound) {
            response.setStatus(403);
            RequestDispatcher requestDispatcher = request.getRequestDispatcher("outOfBindError.jsp");
            requestDispatcher.forward(request, response);
        }
        RequestDispatcher requestDispatcher = request.getRequestDispatcher("SpecificRestaurant.jsp");
        requestDispatcher.forward(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
