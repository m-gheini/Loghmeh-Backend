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

        <%int size = RestaurantManager.getInstance().getRestaurants().size();%>
        <%for (int i =0;i<size;i++){%>
    <tr>
        <td><%= RestaurantManager.getInstance().getRestaurants().get(i).getId()%></td>
        <td><img class="logo" src=" <%= RestaurantManager.getInstance().getRestaurants().get(i).getLogo()%>" alt="logo"></td>
        <td><%= RestaurantManager.getInstance().getRestaurants().get(i).getName()%></td>
        <td>(<%= RestaurantManager.getInstance().getRestaurants().get(i).getLocation().getX()%>,
            <%= RestaurantManager.getInstance().getRestaurants().get(i).getLocation().getY()%>)</td>
        <td>
            <form action="SpecificRestaurant.jsp">
                <button type="submit" name="restaurantInfo" value="<%=RestaurantManager.getInstance().getRestaurants().get(i).getId()%>">more info</button>
            </form>
        </td>
    </tr>
    <%}%>
</table>
<jsp:include page="footer.jsp" />
