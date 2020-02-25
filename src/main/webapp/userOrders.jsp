<%@ page import="IECA.logic.RestaurantManager" %>
<%@ page import="IECA.servlets.UserOrders" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<jsp:include page="header.jsp" />

<meta http-equiv="refresh" content="1;URL='userOrders.jsp'">
<%String restaurantName ="";
<%int index = i;%>
    <%int index = (int) request.getAttribute("i");%>
    String restaurantId= (String) request.getAttribute("restaurantId");
    if (request.getAttribute("cart") != null){
        restaurantId = (String) request.getAttribute("restaurantId");
    }
    if(RestaurantManager.getInstance().getCurrentUser().getMyCart().getFoods().size()>0 ||
            request.getAttribute("cart") != null) {
        restaurantName = RestaurantManager.getInstance().searchForRestaurant("{\"id\":\""+restaurantId+"\"}").getName();
    }
%>
<div><%=restaurantName%></div>
<%for (int i=0;i< RestaurantManager.getInstance().getCurrentUser().getOrders().get(index).getFoods().size();i++){%>
<li ><%=RestaurantManager.getInstance().getCurrentUser().getOrders().get(index).getNumberOfFood().get(i)%> : ‌ <%=RestaurantManager.getInstance().getCurrentUser().getOrders().get(index).getFoods().get(i).getName()%></li>
<%}%>
<div>status: <%=RestaurantManager.getInstance().getCurrentUser().getOrders().get(index).getStatus()%><br></div>
<%if(RestaurantManager.getInstance().getCurrentUser().getOrders().get(index).getStatus().equals("delivering")){%>
<div>remained time : <%=(int)(RestaurantManager.getInstance().getBestTime()/60)%> :
    <%=(int)(RestaurantManager.getInstance().getBestTime()%60)%>s<br></div>
<%}%>
<form action="index.jsp">
    <button type="submit" name="home" value="">Home</button>
</form>
<jsp:include page="footer.jsp" />
