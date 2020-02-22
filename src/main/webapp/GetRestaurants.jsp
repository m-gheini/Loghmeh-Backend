<%@ page import="IECA.logic.RestaurantManager" %>
<%@ page import="IECA.logic.Restaurant" %>
<jsp:include page="header.jsp" />

<table>
    <tr>
        <th>id</th>
        <th>logo</th>
        <th>name</th>
        <th>location</th>
    </tr>

    <%System.out.println( RestaurantManager.getInstance().getRestaurants());%>
    <%for (Restaurant res: RestaurantManager.getInstance().getRestaurants()){%>
    <tr>
        <td><%= res.getId()%></td>
        <td><img class="logo" src=" <%= res.getLogo()%>" alt="logo"></td>
        <td><%= res.getName()%></td>
        <td>(<%= res.getLocation().getX()%>,
            <%= res.getLocation().getY()%>)</td>
        <td>
            <form action="SpecificRestaurant.jsp">
                <button type="submit" name="restaurantInfo" value="<%=res.getId()%>">more info</button>
            </form>
        </td>
    </tr>
    <%}%>
</table>
<jsp:include page="footer.jsp" />
