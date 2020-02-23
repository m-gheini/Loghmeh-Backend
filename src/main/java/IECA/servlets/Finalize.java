package IECA.servlets;

import IECA.logic.Food;
import IECA.logic.RestaurantManager;
import IECA.logic.Runner;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;

@WebServlet("/Finalize")
public class Finalize extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int total = 0;
        for (int i =0;i<RestaurantManager.getInstance().getCurrentUser().getMyCart().getFoods().size();i++){
            total+=RestaurantManager.getInstance().getCurrentUser().getMyCart().getNumberOfFood().get(i)*
                    RestaurantManager.getInstance().getCurrentUser().getMyCart().getFoods().get(i).getPrice();
        }
        if(RestaurantManager.getInstance().getCurrentUser().getCredit()>=total && RestaurantManager.getInstance().getCurrentUser().getMyCart().getFoods().size()>0){
            RestaurantManager.getInstance().getCurrentUser().addCredit(-total);
            IECA.logic.Cart previousCart = new IECA.logic.Cart();
            ArrayList<Food> foods = new ArrayList<>(RestaurantManager.getInstance().getCurrentUser().getMyCart().getFoods());
            previousCart.setFoods(foods);
            ArrayList<Integer> counts = new ArrayList<>(RestaurantManager.getInstance().getCurrentUser().getMyCart().getNumberOfFood());
            previousCart.setNumberOfFood(counts);
            RestaurantManager.getInstance().getCurrentUser().addOrder(previousCart);
            Runner runner = new Runner();
            ServletContextEvent sce = new ServletContextEvent(getServletContext("/Delivery"));
            runner.contextInitialized(sce);
            RequestDispatcher requestDispatcher = request.getRequestDispatcher("finalize.jsp");
            requestDispatcher.forward(request, response);
        }
        else{
            request.setAttribute("foodName",null);
            request.setAttribute("restaurantId",RestaurantManager.getInstance().getCurrentUser().getMyCart().getFoods().get(0).getRestaurantId());
            request.setAttribute("cart",null);
            request.setAttribute("i",-9);
            RequestDispatcher requestDispatcher = request.getRequestDispatcher("cart.jsp");
            requestDispatcher.forward(request, response);
        }

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
