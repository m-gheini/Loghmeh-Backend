package IECA.servlets;

import IECA.logic.Restaurant;
import IECA.logic.RestaurantManager;

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
        boolean notFound = true;
        for(Restaurant restaurant : RestaurantManager.getInstance().getRestaurants()){
            if (restaurant.getId().equals(value)) {
                notFound = false;
                break;
            }
        }
        if (!notFound) {
            response.setContentType("SpecificRestaurant.jsp");
            response.setStatus(200);
        }
        else{
            response.setContentType("index.jsp");
            response.setStatus(404);
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}