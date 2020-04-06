package IECA.servlets;

import IECA.logic.RestaurantManager;
import IECA.logic.User;
import IECA.logic.Error;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.ArrayList;

@RestController
public class Users {
    @RequestMapping(value = "/users", method = RequestMethod.GET)
    public @ResponseBody
    ArrayList<User> allUsers() throws IOException {
        ArrayList<User> users = new ArrayList<User>();
        User user = RestaurantManager.getInstance().getCurrentUser();
        users.add(user);
        return users;
    }
    @RequestMapping(value = "/users/{id}", method = RequestMethod.GET)
    public @ResponseBody
    Object specificUser(@PathVariable(value = "id") Integer id) throws IOException {
        if (id==RestaurantManager.getInstance().getCurrentUser().getId()){
            System.out.println(id);
            return RestaurantManager.getInstance().getCurrentUser();
        }
        else{
            Error error = new Error();
            error.setErrorCode(404);
            error.setErrorMassage("no such id");
            return error;
        }
    }


}