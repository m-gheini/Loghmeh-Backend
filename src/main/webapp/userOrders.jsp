<%@ page import="IECA.logic.RestaurantManager" %>
<%@ page import="IECA.servlets.UserOrders" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<jsp:include page="header.jsp" />

<%      String restaurantId= (String) request.getAttribute("restaurantId");
    Integer index = (Integer) request.getAttribute("i");
    String restaurantName ="";
    restaurantName = RestaurantManager.getInstance().searchForRestaurant("{\"id\":\""+restaurantId+"\"}").getName();
%>
    <div><%=restaurantName%></div>
<ul>
<%for (int i=0;i< RestaurantManager.getInstance().getCurrentUser().getOrders().get(index).getFoods().size();i++){%>
<li ><%=RestaurantManager.getInstance().getCurrentUser().getOrders().get(index).getNumberOfFood().get(i)%> : ‌ <%=RestaurantManager.getInstance().getCurrentUser().getOrders().get(index).getFoods().get(i).getName()%></li>
<%}%>
</ul>
<div >your order has been successfully finalized<br></div>
<div>status: <%=RestaurantManager.getInstance().getCurrentUser().getOrders().get(index).getStatus()%><br></div>
<%if(RestaurantManager.getInstance().getCurrentUser().getOrders().get(index).getStatus().equals("delivering")){%>
<div>remained time : <%=(int)(RestaurantManager.getInstance().getBestTime()/60)%> :
    <%=(int)(RestaurantManager.getInstance().getBestTime()%60)%>s<br></div>
<%}%>
<form action="UserOrders" method="post">
    <script>setInterval(function () {document.getElementById("cart").click();}, 1000);</script>

    <button type="submit" hidden="hidden" id="cart" name="cart" value="<%=index%>">update</button>
</form>

<form action="index.jsp">
    <button type="submit" name="home" value="">Home</button>
</form>
<%%>
<jsp:include page="footer.jsp" />
