package IECA.servlets;

import IECA.logic.Error;
import IECA.logic.RestaurantManager;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import java.io.IOException;

@WebFilter(filterName = "jwtFilter")
public class jwtFilter implements Filter {
    public void destroy() {
    }

    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException {
//        resp.
//        Integer id  = null;
//        try {
//            Algorithm algorithm = Algorithm.HMAC256("loghme");
//            JWTVerifier verifier = JWT.require(algorithm)
//                    .build();
//            DecodedJWT jwt = verifier.verify(headers.get("authorization"));
//            if(!(jwt.getClaim("id").asInt()== RestaurantManager.getInstance().getCurrentUser().getId()
//                    && jwt.getClaim("email").asString().equals(RestaurantManager.getInstance().getCurrentUser().getEmail())
//                    && jwt.getClaim("iss").asString().equals("user"))){
//                Error error=new Error(300,"error in jwt");
//                return error;
//            }
//            id = jwt.getClaim("id").asInt();
//        } catch (JWTVerificationException exception){
//            exception.printStackTrace();
//            Error error=new Error(300,"error in jwt");
//            return error;
//        }

        chain.doFilter(req, resp);
    }

    public void init(FilterConfig config) throws ServletException {

    }

}
