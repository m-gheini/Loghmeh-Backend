<%@ page import="IECA.logic.RestaurantManager" %>
<%@ page import="IECA.logic.Cart" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="IECA.logic.Food" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<jsp:include page="header.jsp" />
<%String restaurantName = (String) request.getAttribute("restaurantName");
    String restaurantId= (String) request.getAttribute("restaurantId");
    ArrayList<Food> foods = RestaurantManager.getInstance().getCurrentUser().getMyCart().getFoods();
    ArrayList<Integer> numberOfFoods = RestaurantManager.getInstance().getCurrentUser().getMyCart().getNumberOfFood();
%>
<div>Your credit is not enough!</div>

<div><%=restaurantName%></div>
<ul>
    <%for (int i=0;i< foods.size();i++){%>
    <li ><%=numberOfFoods.get(i)%> : ‌ <%=foods.get(i).getName()%></li>
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