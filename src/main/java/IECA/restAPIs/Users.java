package IECA.restAPIs;

import IECA.database.mappers.ConnectionPool;
import IECA.database.mappers.user.UserMapper;
import IECA.logic.*;
import IECA.logic.Error;
import IECA.logic.schedulers.DeliveryScheduler;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.web.bind.annotation.*;


import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Map;

@RestController
public class Users {
//    @RequestMapping(value = "/users", method = RequestMethod.GET)
//    public @ResponseBody
//    ArrayList<User> allUsers() throws IOException, InterruptedException, SQLException {
//        UserMapper userMapper = new UserMapper(false);
//        Connection connection = ConnectionPool.getConnection();
//        ArrayList<User> users = userMapper.convertAllResultToObject();
//        connection.close();
//        return users;
//    }

    @RequestMapping(value = "/user", method = RequestMethod.GET)
    public @ResponseBody
    Object specificUser(@RequestHeader Map<String, String> headers) throws IOException, SQLException {
        Integer id  = null;
        try {
            Algorithm algorithm = Algorithm.HMAC256("loghme");
            JWTVerifier verifier = JWT.require(algorithm)
                    .build();
            DecodedJWT jwt = verifier.verify(headers.get("authorization"));
            if(!(jwt.getClaim("id").asInt()==RestaurantManager.getInstance().getCurrentUser().getId()
                    && jwt.getClaim("email").asString().equals(RestaurantManager.getInstance().getCurrentUser().getEmail())
                    && jwt.getClaim("iss").asString().equals("user"))){
                Error error=new Error(300,"error in jwt");
                return error;
            }
            id = jwt.getClaim("id").asInt();
        } catch (JWTVerificationException exception){
            exception.printStackTrace();
            Error error=new Error(300,"error in jwt");
            return error;
        }
        UserMapper userMapper = new UserMapper(false);
        Connection connection = ConnectionPool.getConnection();
        ArrayList<Integer> key = new ArrayList<Integer>();
        key.add(id);
        User result = userMapper.find(key);
        connection.close();
        if(result != null)
            return result;
        Error error = new Error(404,"no such id");
        return error;

    }

//    @RequestMapping(value = "/users/{id}/credit", method = RequestMethod.GET)
//    public @ResponseBody
//    Object specificUserCredit(@PathVariable(value = "id") Integer id) throws IOException, SQLException {
////        User result = RestaurantManager.getInstance().findSpecUser(id);
//        UserMapper userMapper = new UserMapper(false);
//        Connection connection = ConnectionPool.getConnection();
//        ArrayList<Integer> key = new ArrayList<Integer>();
//        key.add(id);
//        User result = userMapper.find(key);
//        connection.close();
//
//        if(result != null)
//            return result.getCredit();
//        Error error = new Error(404,"no such id");
//        return error;
//
//    }

//    @RequestMapping(value = "/users/{id}", method = RequestMethod.DELETE)
//    public @ResponseBody
//    Object deleteUser(@PathVariable(value = "id") Integer id) throws IOException, SQLException {
////        for(User u:RestaurantManager.getInstance().getUsers()){
////            if(u.getId()==id){
////                RestaurantManager.getInstance().getUsers().remove(u);
////                Error error = new Error(202,"user deleted successfully");
////                return error;
////            }
////        }
////        Error error = new Error(404,"no such id");
////        return error;
//        UserMapper userMapper = new UserMapper(false);
//        Connection connection = ConnectionPool.getConnection();
//        Integer prevSize = userMapper.convertAllResultToObject().size();
//        ArrayList<Integer> key = new ArrayList<Integer>();
//        key.add(id);
//        userMapper.delete(key);
//        if (userMapper.convertAllResultToObject().size()==prevSize) {
//            connection.close();
//            return new Error(404, "no such id");
//        }
//        connection.close();
//        return new Error(202,"user deleted successfully");
//
//    }

    @RequestMapping(value = "/users", method = RequestMethod.POST)
    public @ResponseBody
    Object addUser(
            @RequestParam(value = "password") String password,
            @RequestParam(value = "name") String name,
            @RequestParam(value = "familyName") String familyName,
            @RequestParam(value = "email") String email,
            @RequestParam(value = "phoneNumber") String phoneNumber) throws IOException, SQLException {
        User user = new User();
        user.setAllParameters(password,name,familyName,email,phoneNumber);
        Integer prevSize=RestaurantManager.getInstance().getUsers().size();
        RestaurantManager.getInstance().addUser(user);
        UserMapper userMapper = new UserMapper(false);
        Connection connection = ConnectionPool.getConnection();
        userMapper.insert(user);
        connection.close();
        if(prevSize==RestaurantManager.getInstance().getUsers().size()){
            Error error = new Error(403,"already existed");
            return error;
        }
        else {
            Error error = new Error(201,"added successfully");
            return error;
        }
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public @ResponseBody
    Object authenticate(@RequestParam(value = "email") String email,
                        @RequestParam(value = "password") String password) throws SQLException {
        MD5 hash = new MD5();
        String hashPass = hash.getMd5(password);
        UserMapper userMapper = new UserMapper(false);
        Connection connection = ConnectionPool.getConnection();
        User found = userMapper.findForLogin(email,hashPass);
        connection.close();
        if(found!=null) {
//            RsaJsonWebKey rsaJsonWebKey = RsaJwkGenerator.generateJwk(2048);
//            System.out.println("type : "+rsaJsonWebKey.getKeyType());
//            // Give the JWK a Key ID (kid), which is just the polite thing to do
//            rsaJsonWebKey.setKeyId("loghme");
//
//            // Create the Claims, which will be the content of the JWT
//            JwtClaims claims = new JwtClaims();
//            claims.setIssuer("user");  // who creates the token and signs it
//            claims.setExpirationTimeMinutesInTheFuture(10); // time when the token will expire (10 minutes from now)
//            claims.setIssuedAtToNow();  // when the token was issued/created (now)
//            claims.setClaim("email",email); // additional claims/attributes about the subject can be added
//            claims.setClaim("id",found.getId()); // additional claims/attributes about the subject can be added
//            JsonWebSignature jws = new JsonWebSignature();
//            jws.setPayload(claims.toJson());
//            System.out.println(rsaJsonWebKey.getPrivateKey());
//            jws.setKey(rsaJsonWebKey.getPrivateKey());
//            jws.setKeyIdHeaderValue(rsaJsonWebKey.getKeyId());
//            jws.setAlgorithmHeaderValue(AlgorithmIdentifiers.RSA_USING_SHA256);
//            String jwt = jws.getCompactSerialization();
//            System.out.println("JWT: " + jwt);
            String token="";
            try {
                Algorithm algorithm = Algorithm.HMAC256("loghme");
                Date now = new Date();
                Date expire = new Date();
                long nowMillis = System.currentTimeMillis();
                expire.setTime(nowMillis+600000);
                token = JWT.create()
                        .withIssuer("user")
                        .withClaim("email", email)
                        .withClaim("id", found.getId())
                        .withIssuedAt(now)
                        .withExpiresAt(expire)
                        .sign(algorithm);
                System.out.println(token);
                RestaurantManager.getInstance().setCurrentUser(found);
            } catch (JWTCreationException | UnsupportedEncodingException exception){
                //Invalid Signing configuration / Couldn't convert Claims.
            } catch (IOException e) {
                e.printStackTrace();
            }
            return new Error(200, token);
        }
        return new Error(403, "no such user");
    }

    @RequestMapping(value = "/user", method = RequestMethod.PUT)
    public @ResponseBody
    Object updateUserCredit(
            @RequestHeader Map<String, String> headers,
            @RequestParam(value = "credit") Integer credit) throws IOException, SQLException {
        try {
            Algorithm algorithm = Algorithm.HMAC256("loghme");
            JWTVerifier verifier = JWT.require(algorithm)
                    .build(); //Reusable verifier instance
            DecodedJWT jwt = verifier.verify(headers.get("authorization"));
            if(!(jwt.getClaim("id").asInt()==RestaurantManager.getInstance().getCurrentUser().getId()
            && jwt.getClaim("email").asString().equals(RestaurantManager.getInstance().getCurrentUser().getEmail())
            && jwt.getClaim("iss").asString().equals("user"))){
                Error error=new Error(300,"error in jwt");
                return error;

            }
        } catch (JWTVerificationException exception){
            exception.printStackTrace();
            Error error=new Error(300,"error in jwt");
            return error;
        }
        if(credit<0 )
            return new Error(403,"please enter a positive number");
        RestaurantManager.getInstance().getCurrentUser().addCredit(credit);
        Error error=new Error(200,"successful");
        return error;

    }

    @RequestMapping(value = "/user/orders",method = RequestMethod.GET)
    public @ResponseBody
    Object allOrders(@RequestHeader Map<String, String> headers) throws IOException, SQLException {
        Integer id = null;
        try {
            Algorithm algorithm = Algorithm.HMAC256("loghme");
            JWTVerifier verifier = JWT.require(algorithm)
                    .build(); //Reusable verifier instance
            DecodedJWT jwt = verifier.verify(headers.get("authorization"));
            if(!(jwt.getClaim("id").asInt()==RestaurantManager.getInstance().getCurrentUser().getId()
                    && jwt.getClaim("email").asString().equals(RestaurantManager.getInstance().getCurrentUser().getEmail())
                    && jwt.getClaim("iss").asString().equals("user"))){
                Error error=new Error(300,"error in jwt");
                return error;

            }
            id = jwt.getClaim("id").asInt();
        } catch (JWTVerificationException exception){
            exception.printStackTrace();
            Error error=new Error(300,"error in jwt");
            return error;
        }

        UserMapper userMapper = new UserMapper(false);
        Connection connection = ConnectionPool.getConnection();
        ArrayList<Integer> key = new ArrayList<Integer>();
        key.add(id);
        User user = userMapper.find(key);
        connection.close();
        if(user!=null){
            return user.getOrders();
        }
        else{
            Error error = new Error(404,"no such id");
            return error;
        }
    }

    @RequestMapping(value = "/user/orders/{index}",method = RequestMethod.GET)
    public @ResponseBody
    Object allOrders(@RequestHeader Map<String, String> headers,
                   @PathVariable(value = "index") Integer index) throws IOException, SQLException {
        Integer id = null;
        try {
            Algorithm algorithm = Algorithm.HMAC256("loghme");
            JWTVerifier verifier = JWT.require(algorithm)
                    .build(); //Reusable verifier instance
            DecodedJWT jwt = verifier.verify(headers.get("authorization"));
            if(!(jwt.getClaim("id").asInt()==RestaurantManager.getInstance().getCurrentUser().getId()
                    && jwt.getClaim("email").asString().equals(RestaurantManager.getInstance().getCurrentUser().getEmail())
                    && jwt.getClaim("iss").asString().equals("user"))){
                Error error=new Error(300,"error in jwt");
                return error;

            }
            id = jwt.getClaim("id").asInt();
        } catch (JWTVerificationException exception){
            exception.printStackTrace();
            Error error=new Error(300,"error in jwt");
            return error;
        }
        UserMapper userMapper = new UserMapper(false);
        Connection connection = ConnectionPool.getConnection();
        ArrayList<Integer> key = new ArrayList<Integer>();
        key.add(id);
        User user = userMapper.find(key);
        connection.close();
        if(user!=null){
            Cart result = RestaurantManager.getInstance().findSpecOrder(index,user);
            if(result!=null){
                return result;
            }
            return new Error(404,"no such index of orders");
        }
        else
            return new Error(404,"no such user id");
    }

    @RequestMapping(value = "/user/cart",method = RequestMethod.GET)
    public @ResponseBody
    Object userCart(@RequestHeader Map<String, String> headers) throws IOException, SQLException {
        Integer id = null;
        try {
            Algorithm algorithm = Algorithm.HMAC256("loghme");
            JWTVerifier verifier = JWT.require(algorithm)
                    .build(); //Reusable verifier instance
            DecodedJWT jwt = verifier.verify(headers.get("authorization"));
            if(!(jwt.getClaim("id").asInt()==RestaurantManager.getInstance().getCurrentUser().getId()
                    && jwt.getClaim("email").asString().equals(RestaurantManager.getInstance().getCurrentUser().getEmail())
                    && jwt.getClaim("iss").asString().equals("user"))){
                Error error=new Error(300,"error in jwt");
                return error;

            }
            id = jwt.getClaim("id").asInt();
        } catch (JWTVerificationException exception){
            exception.printStackTrace();
            Error error=new Error(300,"error in jwt");
            return error;
        }
        UserMapper userMapper = new UserMapper(false);
        Connection connection = ConnectionPool.getConnection();
        ArrayList<Integer> key = new ArrayList<Integer>();
        key.add(id);
        User user = userMapper.find(key);
        connection.close();
        if(user!=null){
            Cart userCart = user.getMyCart();
            if(userCart.getFoods().size()==0 && userCart.getSaleFoods().size()==0)
                return new Error(404,"Your cart is empty!");
            return userCart;
        }
        else
            return new Error(404,"No such user!");
    }

    @RequestMapping(value = "/users/{id}/cart", params = "foodName",method = RequestMethod.PUT)
    public @ResponseBody
    Object addToCart(
            @PathVariable(value = "id") Integer id,
            @RequestParam(value = "restaurantId") String restaurantId,
            @RequestParam(value = "foodName") String foodName) throws IOException, SQLException {
        boolean restaurantNotFound = RestaurantManager.getInstance().searchInProperResById(restaurantId);
        if(restaurantNotFound)
            return new Error(404,"no such restaurantId");
        User u = RestaurantManager.getInstance().findSpecUser(id);
        if(u==null)
            return new Error(404,"no such id");
        String jsonInString = "{\"foodName\":\""+foodName+"\",\"restaurantId\":\""+restaurantId+"\"}";
        Food orderFood = RestaurantManager.getInstance().searchForFood(jsonInString);
        if(orderFood.getName()==null)
            return new Error(404,"no such foodName");
        int status = u.getMyCart().addFood(jsonInString,RestaurantManager.getInstance().getFoods());
        if (status==0)
            return new Error(403,"you can not choose from different restaurants");
        return new Error(201,"ok");
    }

    @RequestMapping(value = "/users/{id}/cart", params = "saleFoodName",method = RequestMethod.PUT)
    public @ResponseBody
    Object addSaleFoodToCart(
            @PathVariable(value = "id") Integer id,
            @RequestParam(value = "restaurantId") String restaurantId,
            @RequestParam(value = "saleFoodName") String foodName) throws IOException, SQLException {
        boolean restaurantFound = RestaurantManager.getInstance().restaurantIdOfSaleFoodFound(restaurantId);
        if(!restaurantFound)
            return new Error(404,"no such restaurantId");
        boolean foodFound = false;
        User u = RestaurantManager.getInstance().findSpecUser(id);
        if(u==null)
            return new Error(404,"no such id");
        String jsonInString = "{\"foodName\":\""+foodName+"\",\"restaurantId\":\""+restaurantId+"\"}";
        SaleFood orderFood = RestaurantManager.getInstance().findSpecialSaleFood(restaurantId,foodName);
        if(orderFood==null)
            return new Error(404,"no such foodName");
        int status = u.getMyCart().addSaleFood(jsonInString,RestaurantManager.getInstance().getSaleFoods());
        if (status==0)
            return new Error(403,"you can not choose from different restaurants");
        return new Error(201,"successful");
    }

    @RequestMapping(value = "/users/{id}/cart", params = "foodName",method = RequestMethod.DELETE)
    public @ResponseBody
    Object deleteFromCart(
            @PathVariable(value = "id") Integer id,
            @RequestParam(value = "restaurantId") String restaurantId,
            @RequestParam(value = "foodName") String foodName) throws IOException, SQLException {
        boolean restaurantNotFound = RestaurantManager.getInstance().searchInProperResById(restaurantId);
        if(restaurantNotFound)
            return new Error(404,"no such restaurantId");
        User u = RestaurantManager.getInstance().findSpecUser(id);
        if(u==null)
            return new Error(404,"no such id");
        String jsonInString = "{\"foodName\":\""+foodName+"\",\"restaurantId\":\""+restaurantId+"\"}";
        Food orderFood = RestaurantManager.getInstance().searchForFood(jsonInString);
        if(orderFood.getName()==null)
            return new Error(404,"no such foodName");
        u.getMyCart().deleteSpecificFood(orderFood);
        return new Error(202,"successful");
    }

    @RequestMapping(value = "/users/{id}/cart", params = "saleFoodName",method = RequestMethod.DELETE)
    public @ResponseBody
    Object deleteSaleFoodFromCart(
            @PathVariable(value = "id") Integer id,
            @RequestParam(value = "restaurantId") String restaurantId,
            @RequestParam(value = "saleFoodName") String foodName) throws IOException, SQLException {
        boolean restaurantFound = RestaurantManager.getInstance().restaurantIdOfSaleFoodFound(restaurantId);
        if(!restaurantFound)
            return new Error(404,"no such restaurantId");
        boolean foodFound = false;
        User u = RestaurantManager.getInstance().findSpecUser(id);
        if(u==null)
            return new Error(404,"no such id");
        String jsonInString = "{\"foodName\":\""+foodName+"\",\"restaurantId\":\""+restaurantId+"\"}";
        SaleFood orderFood = RestaurantManager.getInstance().findSpecialSaleFood(restaurantId,foodName);
        if(orderFood==null)
            return new Error(404,"no such foodName");
        u.getMyCart().deleteSpecificSaleFood(orderFood);
        return new Error(201,"no such foodName");
    }

    @RequestMapping(value = "/users/{id}/cart",method = RequestMethod.POST)
    public @ResponseBody
    Object finalizeCart(@PathVariable(value = "id") Integer id) throws IOException, SQLException {
        User u = RestaurantManager.getInstance().findSpecUser(id);
        if(u!=null){
            Cart userCart = u.getMyCart();
            if(userCart.getFoods().size()==0 && userCart.getSaleFoods().size()==0)
                return new Error(404,"Your cart is empty!");
            else {
                Integer total = RestaurantManager.getInstance().makeTotal();
                String restaurantId = "";
                if(u.getMyCart().getFoods().size()>0)
                    restaurantId = u.getMyCart().getFoods().get(0).getRestaurantId();
                else
                    restaurantId = u.getMyCart().getSaleFoods().get(0).getRestaurantId();
                if(u.getCredit()>=total && total!=0){
                    Cart previousCart = new Cart();
                    u.addCredit(-total);
                    DeliveryScheduler deliveryScheduler = new DeliveryScheduler();
                    deliveryScheduler.setRestaurant(restaurantId);
                    previousCart.setAllParameters(u);
                    u.addOrder(previousCart);
                    u.getMyCart().clearCart();
                    return u.getMyCart();
                }
                else{
                    if(u.getCredit()<total)
                        return new Error(403,"not enough credit");
                    else
                        return new Error(404,"empty cart");
                }
            }
        }
        return new Error(404,"No such user!");
    }
}