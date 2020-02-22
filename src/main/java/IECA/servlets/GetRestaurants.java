package IECA.servlets;

import IECA.logic.Restaurant;
import IECA.logic.RestaurantManager;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/GetRestaurants")
public class GetRestaurants extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//        String value = request.getParameter("restaurantInfo");
//        boolean notFound = true;
//        for (Restaurant restaurant : RestaurantManager.getInstance().getRestaurants()) {
//            if (restaurant.getId().equals(value)) {
//                notFound = false;
//                break;
//            }
//        }
//        if (value == null || notFound == true) {
//            String indexPageName = "index.jsp";
//            RequestDispatcher requestDispatcher = request.getRequestDispatcher(indexPageName);
//            requestDispatcher.forward(request, response);
//        }
//        else {
//            String srPageName = "SpecificRestaurant.jsp";
//            RequestDispatcher requestDispatcher = request.getRequestDispatcher(srPageName);
//            requestDispatcher.forward(request, response);
//            }

        }


    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //response.setContentType("GetRestaurants.jsp");
        String value = request.getParameter("restaurantInfo");
        boolean notFound = true;
        for (Restaurant restaurant : RestaurantManager.getInstance().getRestaurants()) {
            if (restaurant.getId().equals(value)) {
                notFound = false;
                break;
            }
        }
        if (value == null || notFound == true) {
            String indexPageName = "index.jsp";
            RequestDispatcher requestDispatcher = request.getRequestDispatcher(indexPageName);
            requestDispatcher.forward(request, response);
        }
        else {
            String srPageName = "SpecificRestaurant.jsp";
            RequestDispatcher requestDispatcher = request.getRequestDispatcher(srPageName);
            requestDispatcher.forward(request, response);
        }
    }
}
