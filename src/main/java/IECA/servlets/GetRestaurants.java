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
import java.net.InetAddress;

@WebServlet("/GetRestaurants")
public class GetRestaurants extends HttpServlet {
    private ServletHandler servletHandler;

    public void initial(){
        servletHandler = new ServletHandler();
    }

    public void dispatch(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        servletHandler.dispatchTo(request, response,"GetRestaurants.jsp");
    }
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        initial();
        dispatch(request,response);
        }


    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    }
}
