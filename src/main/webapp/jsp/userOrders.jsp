<%@ page import="IECA.logic.RestaurantManager" %>
<%@ page import="IECA.servlets.UserOrders" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="IECA.logic.Food" %>
<%@ page import="IECA.logic.SaleFood" %>
<%@ page import="IECA.logic.Cart" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<jsp:include page="../../../../target/IECA/header.jsp" />

<%  RestaurantManager restaurantManager = RestaurantManager.getInstance();
    Integer index = (Integer) request.getAttribute("i");
    String restaurantName = (String) request.getAttribute("restaurantName");
    ArrayList<Food> foods = restaurantManager.getCurrentUser().getOrders().get(index).getFoods();
    ArrayList<Integer> numberOfFoods = restaurantManager.getCurrentUser().getOrders().get(index).getNumberOfFood();
    ArrayList<SaleFood> saleFoods = restaurantManager.getCurrentUser().getOrders().get(index).getSaleFoods();
    ArrayList<Integer> numberOfSaleFoods = restaurantManager.getCurrentUser().getOrders().get(index).getNumberOfSaleFood();
    Cart order = restaurantManager.getCurrentUser().getOrders().get(index);
%>
    <div><%=restaurantName%></div>
<ul>
    <%for (int i=0;i< foods.size();i++){%>
    <li ><%=numberOfFoods.get(i)%> : ‌ <%=foods.get(i).getName()%></li>
    <%}%>
    <%for (int i=0;i< saleFoods.size();i++){%>
    <li ><%=numberOfSaleFoods.get(i)%> : ‌ <%=saleFoods.get(i).getName()%></li>
    <%}%>
</ul>
<div >your order has been successfully finalized<br></div>
<div>status: <%=order.getStatus()%><br></div>
<%if(order.getStatus().equals("delivering")){%>
<div>remained time : <%=(int)(restaurantManager.getBestTime()/60)%> : <%=(int)(restaurantManager.getBestTime()%60)%>s<br></div>
<%}%>
<form action="UserOrders" method="post">
    <script>setInterval(function () {document.getElementById("cart").click();}, 1000);</script>

    <button type="submit" hidden="hidden" id="cart" name="cart" value="<%=index%>">update</button>
</form>

<form action="../../../../target/IECA/index.jsp">
    <button type="submit" name="home" value="">Home</button>
</form>
<%%>
<jsp:include page="../../../../target/IECA/footer.jsp" />
