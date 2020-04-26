package IECA.servlets;

import IECA.logic.Food;
import IECA.logic.RestaurantManager;
import IECA.logic.SaleFood;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

@WebServlet("/UserOrders")
public class UserOrders extends HttpServlet {
    private HashMap<String,String> strAttr;
    private HashMap<String,Integer> intAtr;
    private String restaurantId = "";
    private String restaurantName = "";
    private int i=0;
    private ServletHandler servletHandler;
    private ArrayList<Food> foods ;
    private ArrayList<SaleFood> saleFoods;

    private void dispatch(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        strAttr.put("restaurantId", restaurantId);
        strAttr.put("restaurantName", restaurantName);
        intAtr.put("i",i);
        servletHandler.setIntAttributes(intAtr,request);
        servletHandler.setStrAttributes(strAttr,request);
        servletHandler.dispatchTo(request,response,"userOrders.jsp");
    }

    private void initial(HttpServletRequest request) throws IOException, SQLException {
        strAttr = new HashMap<>();
        intAtr = new HashMap<>();
        restaurantId = "";
        restaurantName = "";
        i=0;
        servletHandler = new ServletHandler();
        i = Integer.parseInt(request.getParameter("cart"));
        foods = RestaurantManager.getInstance().getCurrentUser().getOrders().get(i).getFoods();
        saleFoods = RestaurantManager.getInstance().getCurrentUser().getOrders().get(i).getSaleFoods();
    }



    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            initial(request);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        if(foods.size()>0) {
            restaurantId = foods.get(0).getRestaurantId();
            String jsonInString = "{\"id\":\""+restaurantId+"\"}";
            try {
                restaurantName = RestaurantManager.getInstance().searchForRestaurant(jsonInString).getName();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        else if(saleFoods.size()>0) {
            restaurantId = saleFoods.get(0).getRestaurantId();
            restaurantName = saleFoods.get(0).getRestaurantName();
        }
        dispatch(request,response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
