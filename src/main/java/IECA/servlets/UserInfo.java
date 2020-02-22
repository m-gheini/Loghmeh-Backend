package IECA.servlets;

import IECA.logic.RestaurantManager;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/UserInfo")
public class UserInfo extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String value = request.getParameter("credit");
        if(!(value.equals(""))){
            RestaurantManager.getInstance().getCurrentUser().addCredit(Integer.valueOf(value));
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String value = request.getParameter("credit");
        if(!(value.equals(""))){
            RestaurantManager.getInstance().getCurrentUser().addCredit(Integer.valueOf(value));
        }
        System.out.println(value);
        response.setContentType("userInfo.jsp");
    }
}
