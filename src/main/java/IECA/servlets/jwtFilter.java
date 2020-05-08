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
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Enumeration;

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
        HttpServletResponse httpResponse = (HttpServletResponse) resp;
        HttpServletRequest httpRequest = (HttpServletRequest) req;
        Enumeration<String> headerNames = httpRequest.getHeaderNames();
        String jwtString = null;
        if (headerNames != null) {
            while (headerNames.hasMoreElements()) {
                String name = headerNames.nextElement();
                String value = httpRequest.getHeader(name);
                if(name.equals("authorization"))
                    jwtString = value;
                System.out.println("Header: " + name + value);
            }
        }
        try {
            System.out.println("------------------------"+jwtString+"--------------------------");
            if(jwtString==null){
                httpResponse.setStatus(401);
                System.out.println("--------------------------------------------401");
            }
            else {
                Algorithm algorithm = Algorithm.HMAC256("loghmeh loghmeh loghmeh loghmeh loghmeh");
                JWTVerifier verifier = JWT.require(algorithm)
                        .build();
                DecodedJWT jwt = verifier.verify(jwtString);

                if (!(jwt.getClaim("id").asInt() == RestaurantManager.getInstance().getCurrentUser().getId()
                        && jwt.getClaim("email").asString().equals(RestaurantManager.getInstance().getCurrentUser().getEmail())
                        && jwt.getClaim("iss").asString().equals("user"))) {
                    httpResponse.setStatus(403);
                    System.out.println("--------------------------------------------403");
                }
            }
        }
        catch (JWTVerificationException | SQLException exception){
            exception.printStackTrace();
            httpResponse.setStatus(401);
            System.out.println("--------------------------------------------401");

        }
//
        chain.doFilter(req, resp);
    }

    public void init(FilterConfig config) throws ServletException {

    }

}
