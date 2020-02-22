<%@ page import="IECA.logic.RestaurantManager" %>
<%@ page import="IECA.logic.Cart" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<jsp:include page="header.jsp" />
<%String restaurantName ="";
System.out.println("AAAAAAAAA"+request.getAttribute("i"));
int index = (int) request.getAttribute("i");
String restaurantId= (String) request.getAttribute("restaurantId");
    if (request.getAttribute("cart") != null){
        restaurantId = (String) request.getAttribute("restaurantId");
    }
    if(RestaurantManager.getInstance().getCurrentUser().getMyCart().getFoods().size()>0 ||
            !(request.getAttribute("cart").equals(null))) {
        restaurantName = RestaurantManager.getInstance().searchForRestaurant("{\"id\":\""+restaurantId+"\"}").getName();
    }
%>
<div><%=restaurantName%></div>
<ul>
<%if((request.getAttribute("cart")==(null))){
    for (int i=0;i< RestaurantManager.getInstance().getCurrentUser().getMyCart().getFoods().size();i++){%>
    <li ><%=RestaurantManager.getInstance().getCurrentUser().getMyCart().getNumberOfFood().get(i)%> : ‌ <%=RestaurantManager.getInstance().getCurrentUser().getMyCart().getFoods().get(i).getName()%></li>
    <%}}
    else{
        for (int i=0;i< RestaurantManager.getInstance().getCurrentUser().getOrders().get(index).getFoods().size();i++){%>
    <li ><%=RestaurantManager.getInstance().getCurrentUser().getOrders().get(index).getNumberOfFood().get(i)%> : ‌ <%=RestaurantManager.getInstance().getCurrentUser().getOrders().get(index).getFoods().get(i).getName()%></li>
    <%}%>
    <%}%>
</ul>
<%if(!(restaurantId.equals(""))){%>
<form action="SpecificRestaurant.jsp">
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