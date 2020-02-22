<%@ page import="IECA.logic.RestaurantManager" %><%--
  Created by IntelliJ IDEA.
  User: leilafakheri
  Date: 2/22/2020 AD
  Time: 11:19 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<jsp:include page="header.jsp" />
<%String restaurantName ="";
    if(RestaurantManager.getInstance().getCurrentUser().getMyCart().getFoods().size()>0)
        restaurantName = request.getParameter("foodInfo").split(",")[1];%>
<div><%=restaurantName%></div>
<ul>
<%for (int i=0;i< RestaurantManager.getInstance().getCurrentUser().getMyCart().getFoods().size();i++){%>
    <li><%=RestaurantManager.getInstance().getCurrentUser().getMyCart().getFoods().get(i).getName()%> : ‌ <%=RestaurantManager.getInstance().getCurrentUser().getMyCart().getNumberOfFood().get(i)%></li>
    <%}%>
</ul>
<form action="SpecificRestaurant.jsp">
    <button type="submit" name="restaurantInfo" value="<%=restaurantName%>">Go Back To Restaurant To Order More</button>
</form>
<form action="" method="POST">
    <button type="submit">finalize</button>
</form>
<form action="index.jsp">
    <button type="submit" name="home" value="">Home</button>
</form>
<jsp:include page="footer.jsp" />