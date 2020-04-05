package IECA.servlets;

import IECA.logic.RestaurantManager;

//import javax.servlet.RequestDispatcher;
//import javax.servlet.ServletException;
//import javax.servlet.annotation.WebServlet;
//import javax.servlet.http.HttpServlet;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import java.io.IOException;
//
//@WebServlet("/UserInfo")
//public class UserInfo extends HttpServlet {
//    private ServletHandler servletHandler;
//
//    public void initial(){
//        servletHandler = new ServletHandler();
//    }
//
//    public void dispatch(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//        servletHandler.dispatchTo(request,response,"userInfo.jsp");
//    }
//
//    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//        String value = request.getParameter("credit");
//        initial();
//        if(!(value.equals(""))){
//            RestaurantManager.getInstance().getCurrentUser().addCredit(Integer.valueOf(value));
//        }
//        dispatch(request,response);
//    }
//
//    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//
//    }
//}

import IECA.logic.User;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
public class UserInfo {

    @RequestMapping(value = "/UserInfo", method = RequestMethod.GET)
    public @ResponseBody
    User getAvailableSeats() throws IOException {
        User user = RestaurantManager.getInstance().getCurrentUser();
        return user;
    }

//    @RequestMapping(value = "/bookTheFlight", method = RequestMethod.POST)
//    public void bookTheFlight(
//            @RequestParam(value = "destination") String destination,
//            @RequestParam(value = "numberOfTickets") int numberOfTickets,
//            @RequestParam(value = "firstName") String firstName,
//            @RequestParam(value = "lastName") String lastName) {
//        FlightManager.getInstance().bookFlight(destination, numberOfTickets);
//    }
}

//@Controller
//@RequestMapping("/kfc/brands")
//public class JSONController {
//
//    @RequestMapping(value="{name}", method = RequestMethod.GET)
//    public @ResponseBody Shop getShopInJSON(@PathVariable String name) {
//
//        Shop shop = new Shop();
//        shop.setName(name);
//        shop.setStaffName(new String[]{"mkyong1", "mkyong2"});
//
//        return shop;
//
//    }
//
//}