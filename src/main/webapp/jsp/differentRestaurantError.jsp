<%@ page import="IECA.logic.RestaurantManager" %>
<%@ page import="IECA.logic.Cart" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<jsp:include page="../../../../target/IECA/header.jsp" />
<%  Cart userCart = RestaurantManager.getInstance().getCurrentUser().getMyCart();
    String restaurantName ="";
    String restaurantId = "";
    if(userCart.getFoods().size() != 0) {
        restaurantId = userCart.getFoods().get(0).getRestaurantId();
        restaurantName = RestaurantManager.getInstance().searchForRestaurant("{\"id\":\"" + restaurantId + "\"}").getName();
    }
    else {
        restaurantId = userCart.getSaleFoods().get(0).getRestaurantId();
        restaurantName = RestaurantManager.getInstance().getSaleFoods().get(0).getRestaurantName();
    }
%>

<div>You already have some order from different restaurant!</div>

<div><h4><%=restaurantName%><br></div>
<ul>
    <%for (int i=0;i< userCart.getFoods().size();i++){%>
    <li ><%=userCart.getNumberOfFood().get(i)%> : ‌ <%=userCart.getFoods().get(i).getName()%></li>
    <%}%>
</ul>
<%if(userCart.getFoods().size() != 0){%>
<form action="../../../../target/IECA/SpecificRestaurant.jsp">
    <button type="submit" name="restaurantInfo" value="<%=restaurantId%>">Go Back To Restaurant To Order More</button>
</form>
<%}%>
<form action="Finalize" method="POST">
    <button type="submit">finalize</button>
</form>

<form action="../../../../target/IECA/index.jsp">
    <button type="submit" name="home" value="">Home</button>
</form>
<jsp:include page="../../../../target/IECA/footer.jsp" />