package IECA.servlets;

import IECA.logic.RestaurantManager;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/Finalize")
public class Finalize extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int total = 0;
        for (int i =0;i<RestaurantManager.getInstance().getCurrentUser().getMyCart().getFoods().size();i++){
            total+=RestaurantManager.getInstance().getCurrentUser().getMyCart().getNumberOfFood().get(i)*
                    RestaurantManager.getInstance().getCurrentUser().getMyCart().getFoods().get(i).getPrice();
        }
        if(RestaurantManager.getInstance().getCurrentUser().getCredit()>=total){
            RestaurantManager.getInstance().getCurrentUser().addCredit(-total);
            RequestDispatcher requestDispatcher = request.getRequestDispatcher("finalize.jsp");
            requestDispatcher.forward(request, response);
        }
        else{
            RequestDispatcher requestDispatcher = request.getRequestDispatcher("cart.jsp");
            requestDispatcher.forward(request, response);
        }

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
