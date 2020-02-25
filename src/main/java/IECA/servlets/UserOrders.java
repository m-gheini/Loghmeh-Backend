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
        request.setAttribute("restaurantId",RestaurantManager.getInstance().getCurrentUser().getOrders().get(i).getFoods().get(0).getRestaurantId());
        request.setAttribute("i",i);
        RequestDispatcher requestDispatcher = request.getRequestDispatcher("userOrders.jsp");
        requestDispatcher.forward(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
