<%@ page import="IECA.logic.RestaurantManager" %>
<%@ page import="IECA.logic.Cart" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<jsp:include page="header.jsp" />
<%
String restaurantId= (String) request.getAttribute("restaurantId");
%>
<%if(RestaurantManager.getInstance().getCurrentUser().getMyCart().getFoods().size()==0 &&
    RestaurantManager.getInstance().getCurrentUser().getMyCart().getSaleFoods().size()==0){%>
<div>Your cart is empty!</div>
<%}%>
<%if(!(restaurantId.equals("")) && request.getParameter("cartFromFoodParty")==null){%>
<div><h4><%=RestaurantManager.getInstance().searchForRestaurant("{\"id\":\""+restaurantId+"\"}").getName()%><br></div>
<%}%>
<%if(request.getParameter("cartFromFoodParty")!=null){%>
<div><h4><%=RestaurantManager.getInstance().getCurrentUser().getMyCart().getSaleFoods().get(0).getRestaurantName()%></h4></div>
<%}%>
<ul>
    <%for (int i=0;i< RestaurantManager.getInstance().getCurrentUser().getMyCart().getFoods().size();i++){%>
    <li ><%=RestaurantManager.getInstance().getCurrentUser().getMyCart().getNumberOfFood().get(i)%> : ‌ <%=RestaurantManager.getInstance().getCurrentUser().getMyCart().getFoods().get(i).getName()%></li>
    <%}%>
    <%for (int i=0;i< RestaurantManager.getInstance().getCurrentUser().getMyCart().getSaleFoods().size();i++){%>
    <li ><%=RestaurantManager.getInstance().getCurrentUser().getMyCart().getNumberOfSaleFood().get(i)%> : ‌ <%=RestaurantManager.getInstance().getCurrentUser().getMyCart().getSaleFoods().get(i).getName()%></li>
    <%}%>
</ul>
<%if(!(restaurantId.equals("")) && request.getParameter("cartFromFoodParty")==null){%>
<form action="SpecificRestaurant" method="post">
    <button type="submit" name="restaurantInfo" value="<%=restaurantId%>">Go Back To Restaurant To Order More</button>
</form>
<%}%>
<form action="Finalize" method="POST">
    <button type="submit">finalize</button>
</form>

<form action="index.jsp">
    <button type="submit" name="home" value="">Home</button>
</form>
<jsp:include page="footer.jsp" />