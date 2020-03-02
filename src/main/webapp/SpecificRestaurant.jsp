<%@ page import="IECA.logic.Restaurant" %>
<%@ page import="IECA.logic.RestaurantManager" %>
<%@ page import="IECA.logic.Food" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%String value = request.getParameter("restaurantInfo");%>
<%Restaurant thisRes = new Restaurant();%>
<%for(Restaurant restaurant : RestaurantManager.getInstance().getRestaurants()){
    if (restaurant.getId().equals(value)) {
        thisRes = restaurant;
        break;
    }
}%>
<jsp:include page="header.jsp" />
<ul>
    <li>id: <%=thisRes.getId()%></li>
    <li>name: <%=thisRes.getName()%></li>
    <li>location: (<%=thisRes.getLocation().getX()%>,<%=thisRes.getLocation().getY()%>)</li>
    <li>
        menu:
        <ul>
            <%for (Food foodInMenu:thisRes.getMenu()){%>
            <li>
                <img src="<%=foodInMenu.getImage()%>" alt="logo">
                <div><%=foodInMenu.getName()%></div>
                <div><%=foodInMenu.getPrice()%> Toman</div>
                <form action="Cart" method="post">
                    <button type="submit" name="foodInfo" value="<%=foodInMenu.getName()%>,<%=foodInMenu.getRestaurantId()%>,<%=foodInMenu.getPrice()%>">add To Cart</button>
                </form>
            </li>
            <%}%>
        </ul>
    </li>
</ul>
<form action="index.jsp">
    <button type="submit" name="home" value="">Home</button>
</form>
<jsp:include page="footer.jsp" />

