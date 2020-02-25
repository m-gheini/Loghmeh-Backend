<%@ page import="IECA.logic.RestaurantManager" %>
<%@ page import="IECA.logic.User" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%User currentUser = RestaurantManager.getInstance().getCurrentUser();%>
<jsp:include page="header.jsp" />

    <ul>
        <li>id: <%=currentUser.getId()%></li>
        <li>full name: <%=currentUser.getName()+" "+currentUser.getFamilyName()%></li>
        <li>phone number: <%=currentUser.getPhoneNumber()%></li>
        <li>email: <%=currentUser.getEmail()%></li>
        <li>credit: <%=currentUser.getCredit()%></li>
        <form action="UserInfo" method="post">
            <button type="submit">increase</button>
            <input type="text" name="credit" value="">
        </form>
    </ul>
<%for (int i=0;i<RestaurantManager.getInstance().getCurrentUser().getOrders().size();i++){%>
<form action="UserOrders" method="post">
    <button type="submit" name="cart" value="<%=i%>">order <%=i+1%></button>
</form>
<%}%>
<form action="index.jsp">
    <button type="submit" name="home" value="">Home</button>
</form>
<jsp:include page="footer.jsp" />



