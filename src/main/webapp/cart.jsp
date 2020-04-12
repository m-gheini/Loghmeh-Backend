<%@ page import="IECA.logic.RestaurantManager" %>
<%@ page import="IECA.logic.Cart" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    String restaurantId= (String) request.getAttribute("restaurantId");
    String restaurantName = (String) request.getAttribute("restaurantName");
    Cart userCart = RestaurantManager.getInstance().getCurrentUser().getMyCart();
%>
<jsp:include page="header.jsp" />
<%if(userCart.getFoods().size()==0 &&
        userCart.getSaleFoods().size()==0){%>
<div>Your cart is empty!</div>
<%}else{%>
<div><h4><%=restaurantName%><br></div>
<ul>
    <%for (int i=0;i< userCart.getFoods().size();i++){%>
    <li ><%=userCart.getNumberOfFood().get(i)%> : ‌ <%=userCart.getFoods().get(i).getName()%></li>
    <%}%>
    <%for (int i=0;i< userCart.getSaleFoods().size();i++){%>
    <li ><%=userCart.getNumberOfSaleFood().get(i)%> : ‌ <%=userCart.getSaleFoods().get(i).getName()%></li>
    <%}%>
</ul>
<%if(!(restaurantId.equals("")) && userCart.getSaleFoods().size()==0){%>
<form action="SpecificRestaurant" method="post">
    <button type="submit" name="restaurantInfo" value="<%=restaurantId%>">Go Back To Restaurant To Order More</button>
</form>
<%}%>
<form action="Finalize" method="POST">
    <button type="submit">finalize</button>
</form>
<%}%>
<form action="index.jsp">
    <button type="submit" name="home" value="">Home</button>
</form>

<jsp:include page="footer.jsp" />