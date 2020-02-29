<%@ page import="IECA.logic.RestaurantManager" %>
<%@ page import="IECA.logic.Cart" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<jsp:include page="header.jsp" />
<%String restaurantName ="";
    String restaurantId= (String) request.getAttribute("restaurantId");
%>
<div>Your credit is not enough!</div>

<div><%=RestaurantManager.getInstance().searchForRestaurant("{\"id\":\""+restaurantId+"\"}").getName()%></div>
<ul>
    <%for (int i=0;i< RestaurantManager.getInstance().getCurrentUser().getMyCart().getFoods().size();i++){%>
    <li ><%=RestaurantManager.getInstance().getCurrentUser().getMyCart().getNumberOfFood().get(i)%> : ‌ <%=RestaurantManager.getInstance().getCurrentUser().getMyCart().getFoods().get(i).getName()%></li>
    <%}%>
</ul>
<%if(!(restaurantId.equals(""))){%>
<form action="SpecificRestaurant.jsp">
    <button type="submit" name="restaurantInfo" value="<%=restaurantId%>">Go Back To Restaurant To Order More</button>
</form>
<form action="Finalize" method="POST">
    <button type="submit">finalize</button>
</form>
<%}%>

<form action="index.jsp">
    <button type="submit" name="home" value="">Home</button>
</form>
<jsp:include page="footer.jsp" />