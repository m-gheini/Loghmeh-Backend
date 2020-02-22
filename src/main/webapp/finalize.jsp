<%@ page import="IECA.logic.RestaurantManager" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<jsp:include page="header.jsp" />
<%String restaurantName ="";
    String restaurantId="";
    if(RestaurantManager.getInstance().getCurrentUser().getMyCart().getFoods().size()>0 ) {
        restaurantId = RestaurantManager.getInstance().getCurrentUser().getMyCart().getFoods().get(0).getRestaurantId();
        restaurantName = RestaurantManager.getInstance().searchForRestaurant("{\"id\":\""+restaurantId+"\"}").getName();
    }
%>
<div><%=restaurantName%></div>
<ul>
    <%for (int i=0;i< RestaurantManager.getInstance().getCurrentUser().getMyCart().getFoods().size();i++){%>
    <li ><%=RestaurantManager.getInstance().getCurrentUser().getMyCart().getNumberOfFood().get(i)%> : ‌ <%=RestaurantManager.getInstance().getCurrentUser().getMyCart().getFoods().get(i).getName()%></li>
    <%}%>
</ul>
<div align="center">your order has been successfully finalized<br></div>
<%RestaurantManager.getInstance().getCurrentUser().getMyCart().clearCart();%>
<form action="index.jsp">
    <button type="submit" name="home" value="">Home</button>
</form>
<jsp:include page="footer.jsp" />
