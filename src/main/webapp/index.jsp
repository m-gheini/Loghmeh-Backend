<%@ page import="IECA.logic.RestaurantManager" %>
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
        <td><%= RestaurantManager.getInstance().getRestaurants().get(i).getName().toString()%></td>
        <td>(<%= RestaurantManager.getInstance().getRestaurants().get(i).getLocation().getX()%>,
            <%= RestaurantManager.getInstance().getRestaurants().get(i).getLocation().getY()%>)</td>
    </tr>
    <%}%>
</table>
<jsp:include page="footer.jsp" />
