<%@ page import="IECA.logic.RestaurantManager" %>
<%@ page import="IECA.logic.Restaurant" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<jsp:include page="header.jsp" />

<meta http-equiv="refresh" content="1;URL='finalize.jsp'">
<body><%String restaurantName ="";
    String restaurantId="";
    Restaurant restaurant = new Restaurant();
    int finalIndex = RestaurantManager.getInstance().getCurrentUser().getOrders().size()-1;
    if(RestaurantManager.getInstance().getCurrentUser().getOrders().get(finalIndex).getFoods().size()>0 ) {
        restaurantId = RestaurantManager.getInstance().getCurrentUser().getOrders().get(finalIndex).getFoods().get(0).getRestaurantId();
        restaurantName = RestaurantManager.getInstance().searchForRestaurant("{\"id\":\""+restaurantId+"\"}").getName();
        restaurant = RestaurantManager.getInstance().searchForRestaurant("{\"id\":\""+restaurantId+"\"}");
    }
%>
<div><%=restaurantName%></div>
<ul>
    <%for (int i=0;i< RestaurantManager.getInstance().getCurrentUser().getOrders().get(finalIndex).getFoods().size();i++){%>
    <li ><%=RestaurantManager.getInstance().getCurrentUser().getOrders().get(finalIndex).getNumberOfFood().get(i)%> : ‌ <%=RestaurantManager.getInstance().getCurrentUser().getOrders().get(finalIndex).getFoods().get(i).getName()%></li>
    <%}%>
</ul>
<div >your order has been successfully finalized<br></div>
<div>status: <%=RestaurantManager.getInstance().getCurrentUser().getOrders().get(finalIndex).getStatus()%><br></div>
<%if(RestaurantManager.getInstance().getCurrentUser().getOrders().get(finalIndex).getStatus().equals("delivering")){%>
<div>remained time : <%=(int)(RestaurantManager.getInstance().getBestTime()/60)%> :
    <%=(int)(RestaurantManager.getInstance().getBestTime()%60)%>s<br></div>
<%}%>
<%RestaurantManager.getInstance().getCurrentUser().getMyCart().clearCart();%>
<form action="index.jsp">
    <button type="submit" name="home" value="">Home</button>
</form>
<jsp:include page="footer.jsp" />
