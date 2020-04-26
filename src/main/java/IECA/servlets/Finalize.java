package IECA.servlets;
import IECA.logic.SaleFood;
import IECA.logic.schedulers.*;
import IECA.database.DeliveryDataset;
import IECA.logic.Food;
import IECA.logic.RestaurantManager;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

@WebServlet("/Finalize")
public class Finalize extends HttpServlet {
    private int total;
    private String restaurantName;
    private String restaurantId;
    private ServletHandler servletHandler;

    private IECA.logic.Cart previousCart;
    private ArrayList<Food> foods;
    private ArrayList<SaleFood> saleFoods;
    private ArrayList<Integer> counts;
    private ArrayList<Integer> saleCounts;
    private HashMap<String,String> strAttr;

    public void initial() throws IOException, SQLException {
        total = RestaurantManager.getInstance().makeTotal();
        restaurantId = "";
        restaurantName =RestaurantManager.getInstance().getCurrentUser().getMyCart().getRestaurantName();;
        servletHandler = new ServletHandler();
    }
    public void initForFinalize() throws IOException, SQLException {
        previousCart = new IECA.logic.Cart();
        foods = new ArrayList<>(RestaurantManager.getInstance().getCurrentUser().getMyCart().getFoods());
        saleFoods = new ArrayList<>(RestaurantManager.getInstance().getCurrentUser().getMyCart().getSaleFoods());
        counts = new ArrayList<>(RestaurantManager.getInstance().getCurrentUser().getMyCart().getNumberOfFood());
        saleCounts = new ArrayList<>(RestaurantManager.getInstance().getCurrentUser().getMyCart().getNumberOfSaleFood());
        previousCart.setFoods(foods);
        previousCart.setSaleFoods(saleFoods);
        previousCart.setNumberOfFood(counts);
        previousCart.setNumberOfSaleFood(saleCounts);
        previousCart.setRestaurantName(restaurantName);
        RestaurantManager.getInstance().getCurrentUser().addOrder(previousCart);

    }

    public void initialForErrors(HttpServletRequest request){
        strAttr = new HashMap<>();
        strAttr.put("restaurantId", restaurantId);
        strAttr.put("restaurantName", restaurantName);
        servletHandler.setStrAttributes(strAttr,request);

    }

    public void dispatch(HttpServletRequest request, HttpServletResponse response,String address,int errorCode) throws ServletException, IOException {
        response.setStatus(errorCode);
        servletHandler.dispatchTo(request,response,address);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            initial();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            restaurantId =  RestaurantManager.getInstance().setRestaurantId(restaurantId);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        try {
            if(RestaurantManager.getInstance().getCurrentUser().getCredit()>=total && total!=0){
                RestaurantManager.getInstance().getCurrentUser().addCredit(-total);
                initForFinalize();
                DeliveryScheduler deliveryScheduler = new DeliveryScheduler();
                deliveryScheduler.setRestaurant(restaurantId);

                dispatch(request,response,"finalize.jsp",200);
            }
            else{
                try {
                    if(RestaurantManager.getInstance().getCurrentUser().getCredit()<total) {
                        restaurantName = RestaurantManager.getInstance().setRestaurantName(restaurantName,restaurantId);
                        initialForErrors(request);
                        dispatch(request,response,"enoughCreditError.jsp",400);
                    }
                    else
                        dispatch(request,response,"emptyCartError.jsp",400);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        response.setContentType("finalize.jsp");
    }
}
