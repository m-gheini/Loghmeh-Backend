package IECA.servlets;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class ServletHandler {
    HashMap<String,String> strAttributes;
    HashMap<String,Integer> intAttributes;
    public ServletHandler(){
        strAttributes = new HashMap<String,String>();
        intAttributes = new HashMap<String,Integer>();
    }

    public HashMap<String, Integer> getIntAttributes() {
        return intAttributes;
    }

    public HashMap<String, String> getStrAttributes() {
        return strAttributes;
    }

    public void setIntAttributes(HashMap<String, Integer> intAttributes,HttpServletRequest request) {
        this.intAttributes = intAttributes;
        Iterator iterator = intAttributes.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry mapElement = (Map.Entry)iterator.next();
            request.setAttribute((String) mapElement.getKey(),mapElement.getValue());
        }
    }
    public void addIntAttributes(String key, Integer value,HttpServletRequest request){
        intAttributes.put(key,value);
        request.setAttribute(key, value);
    }
    public void setStrAttributes(HashMap<String, String> strAttributes,HttpServletRequest request) {
        this.strAttributes = strAttributes;
        Iterator iterator = strAttributes.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry mapElement = (Map.Entry)iterator.next();
            request.setAttribute((String) mapElement.getKey(),mapElement.getValue());
        }
    }
    public void addStrAttributes(String key,String value,HttpServletRequest request){
        strAttributes.put(key,value);
        request.setAttribute(key, value);
    }
    public void dispatchTo(HttpServletRequest request, HttpServletResponse response, String address) throws ServletException, IOException {
        RequestDispatcher requestDispatcher = request.getRequestDispatcher(address);
        requestDispatcher.forward(request, response);
    }
}
