package IECA.servlets;

import IECA.logic.RestaurantManager;
import IECA.servlets.ServletHandler;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/UserInfo")
public class UserInfo extends HttpServlet {
    private ServletHandler servletHandler;

    public void initial(){
        servletHandler = new ServletHandler();
    }

    public void dispatch(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        servletHandler.dispatchTo(request,response,"userInfo.jsp");
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String value = request.getParameter("credit");
        initial();
        if(!(value.equals(""))){
            try {
                RestaurantManager.getInstance().getCurrentUser().addCredit(Integer.valueOf(value));
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        dispatch(request,response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}

