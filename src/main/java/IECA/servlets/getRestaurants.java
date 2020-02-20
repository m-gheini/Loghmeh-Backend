package IECA.servlets;

import IECA.database.DatasetManager;
import IECA.logic.Restaurant;

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;

@WebServlet("/getRestaurants")
public class getRestaurants extends HttpServlet {
    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        DatasetManager dataset = new DatasetManager();
        PrintWriter out = response.getWriter();
        out.println("<!DOCTYPE html>");
        out.println("<html lang=\"en\">");
        out.println("<head>");
        out.println("<meta charset=\"UTF-8\">");
        out.println("<title>"+"getRestaurants"+"</title>");
        out.println("        table {");
        out.println("            text-align: center;");
        out.println("            margin: auto;");
        out.println("        }");
        out.println("        th, td {");
        out.println("            padding: 5px;");
        out.println("            text-align: center;");
        out.println("        }");
        out.println( "        .logo{");
        out.println("            width: 100px;");
        out.println("            height: 100px;");
        out.println("        }");
        out.println("    </style>");
        out.println("</head>");
        out.println("<body>");
        out.println("    <table>");
        out.println("        <tr>");
        out.println("            <th>id</th>");
        out.println("            <th>logo</th>");
        out.println("            <th>name</th>");
        out.println("        </tr>");
        for (Restaurant res: dataset.getRestaurants()){
            out.println("<tr>");
            out.println("<td>"+res.getId()+"</td>");
            out.println("<td><img class=\"logo\" src=\""+res.getLogo()+"\" alt=\"logo\"></td>");
            out.println("<td>"+res.getName()+"</td>");
            out.println("</tr>");
        }
        out.println("    </table>");
        out.println("</body>");
        out.println("</html>");
    }
}
