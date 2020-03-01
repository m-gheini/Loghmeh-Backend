<%@ page import="IECA.logic.RestaurantManager" %>
<jsp:include page="header.jsp" />
<%RestaurantManager.getInstance();%>
<ul>
    <form action="GetRestaurants" method="post">
        <button type="submit" name="getRestaurants" value="">See Available Restaurants In Radius</button>
    </form>
    <form action="FoodParty"  method="post">
        <button type="submit" name="foodPartyButton" value="">Food Party</button>
    </form>
    <form action="userInfo.jsp">
        <button type="submit" name="userInfo" value="">Profile</button>
    </form>
    <form action="Cart"  method="post">
        <button type="submit" name="cartFromHome" value="">Cart</button>
    </form>
</ul>
<jsp:include page="footer.jsp" />
