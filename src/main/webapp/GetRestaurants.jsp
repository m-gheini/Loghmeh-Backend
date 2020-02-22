<%@ page import="IECA.logic.RestaurantManager" %>
<%@ page import="IECA.logic.Restaurant" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<jsp:include page="header.jsp" />

<table>
    <tr>
        <th>id</th>
        <th>logo</th>
        <th>name</th>
        <th>location</th>
    </tr>

    <%for (Restaurant res: RestaurantManager.getInstance().getRestaurants()){%>
    <tr>
        <td><%= res.getId()%></td>
        <td><img class="logo" src=" <%= res.getLogo()%>" alt="logo"></td>
        <td><%= res.getName()%></td>
        <td>(<%= res.getLocation().getX()%>,
            <%= res.getLocation().getY()%>)</td>
        <td>
            <form action="SpecificRestaurant" method="post">
                <button type="submit" name="restaurantInfo" value="<%=res.getId()%>">more info</button>
            </form>
        </td>
    </tr>
    <%}%>
</table>
<div>
<form action="index.jsp">
    <button type="submit" name="home" value="">Home</button>
</form>
</div>
<jsp:include page="footer.jsp" />
