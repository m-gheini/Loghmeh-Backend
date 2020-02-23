package IECA.servlets;

import IECA.database.DeliveryDataset;
import IECA.logic.RestaurantManager;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "Delivery")
public class Delivery extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        DeliveryDataset deliveryDataset = new DeliveryDataset();
        deliveryDataset.addToDataset(deliveryDataset.readFromWeb("http://138.197.181.131:8080/deliveries"));
        RestaurantManager.getInstance().setDeliveries(deliveryDataset.getDeliveries());

    }
}
