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
    private ServletHandler servletHandler;

    public void initial(){
        servletHandler = new ServletHandler();
    }

    public void dispatch(HttpServletRequest request, HttpServletResponse response,String address,int errorCode) throws ServletException, IOException {
        response.setStatus(errorCode);
        servletHandler.dispatchTo(request,response,address);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String value = request.getParameter("restaurantInfo");
        initial();
//        boolean totallyNotFound = RestaurantManager.getInstance().searchForResInAllRes(value);
//        if (totallyNotFound)
//            dispatch(request,response,"validIdError.jsp",404);
//        boolean notFound = RestaurantManager.getInstance().searchInProperResById(value);
//        if (notFound)
//            dispatch(request,response,"outOfBindError.jsp",403);
//        dispatch(request,response,"SpecificRestaurant.jsp",200);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
