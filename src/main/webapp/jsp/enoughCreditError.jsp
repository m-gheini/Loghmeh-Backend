<%@ page import="IECA.logic.RestaurantManager" %>
<%@ page import="IECA.logic.Cart" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="IECA.logic.Food" %>
<%@ page import="IECA.logic.SaleFood" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<jsp:include page="../../../../target/IECA/header.jsp" />
<%
    String restaurantName = (String) request.getAttribute("restaurantName");
    String restaurantId= (String) request.getAttribute("restaurantId");
    ArrayList<Food> foods = RestaurantManager.getInstance().getCurrentUser().getMyCart().getFoods();
    ArrayList<Integer> numberOfFoods = RestaurantManager.getInstance().getCurrentUser().getMyCart().getNumberOfFood();
    ArrayList<SaleFood> saleFoods = RestaurantManager.getInstance().getCurrentUser().getMyCart().getSaleFoods();
    ArrayList<Integer> numberOfSaleFoods = RestaurantManager.getInstance().getCurrentUser().getMyCart().getNumberOfSaleFood();
%>
<div>Your credit is not enough!</div>

<div><%=restaurantName%></div>
<ul>
    <%for (int i=0;i< foods.size();i++){%>
    <li ><%=numberOfFoods.get(i)%> : ‌ <%=foods.get(i).getName()%></li>
    <%}%>
    <%for (int i=0;i< saleFoods.size();i++){%>
    <li ><%=numberOfSaleFoods.get(i)%> : ‌ <%=saleFoods.get(i).getName()%></li>
    <%}%>
</ul>
<%if(!(restaurantId.equals(""))){%>
<form action="../../../../target/IECA/SpecificRestaurant.jsp">
    <button type="submit" name="restaurantInfo" value="<%=restaurantId%>">Go Back To Restaurant To Order More</button>
</form>
<form action="Finalize" method="POST">
    <button type="submit">finalize</button>
</form>
<%}%>

<form action="index.jsp">
    <button type="submit" name="home" value="">Home</button>
</form>
<jsp:include page="../../../../target/IECA/footer.jsp" />