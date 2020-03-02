<%@ page import="IECA.logic.RestaurantManager" %>
<%@ page import="IECA.logic.Cart" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<jsp:include page="header.jsp" />

<meta http-equiv="refresh" content="1;URL='finalize.jsp'">
<%String restaurantName ="";
    String restaurantId="";
    int finalIndex = RestaurantManager.getInstance().getCurrentUser().getOrders().size()-1;
    Cart order = RestaurantManager.getInstance().getCurrentUser().getOrders().get(finalIndex);
    if(order.getFoods().size()>0 ) {
        restaurantId = order.getFoods().get(0).getRestaurantId();
        restaurantName = RestaurantManager.getInstance().searchForRestaurant("{\"id\":\""+restaurantId+"\"}").getName();
    }
    else if(RestaurantManager.getInstance().getCurrentUser().getOrders().get(finalIndex).getSaleFoods().size()>0 ) {
        restaurantName = order.getSaleFoods().get(0).getRestaurantName();
    }
%>
<div><%=restaurantName%></div>
<ul>
    <%for (int i=0;i< order.getFoods().size();i++){%>
    <li ><%=order.getNumberOfFood().get(i)%> : ‌ <%=order.getFoods().get(i).getName()%></li>
    <%}%>
    <%for (int i=0;i< order.getSaleFoods().size();i++){%>
    <li ><%=order.getNumberOfSaleFood().get(i)%> : ‌ <%=order.getSaleFoods().get(i).getName()%></li>
    <%}%>
</ul>
<div >your order has been successfully finalized<br></div>
<div>status: <%=order.getStatus()%><br></div>
<%if(order.getStatus().equals("delivering")){%>
<div>remained time : <%=(int)(RestaurantManager.getInstance().getBestTime()/60)%> :
    <%=(int)(RestaurantManager.getInstance().getBestTime()%60)%>s<br></div>
<%}%>
<%RestaurantManager.getInstance().getCurrentUser().getMyCart().clearCart();%>
<form action="index.jsp">
    <button type="submit" name="home" value="">Home</button>
</form>
<jsp:include page="footer.jsp" />
