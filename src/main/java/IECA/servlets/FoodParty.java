package IECA.servlets;

import IECA.database.FoodPartyDataset;
import IECA.logic.RestaurantManager;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/FoodParty")
public class FoodParty extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        FoodPartyDataset foodPartyDataset = new FoodPartyDataset();
        foodPartyDataset.addToDataset(foodPartyDataset.readFromWeb("http://138.197.181.131:8080/foodparty"));
        RestaurantManager.getInstance().setSaleFoods(foodPartyDataset.getFoodsOnSale());
    }
}
