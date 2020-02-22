<%@ page import="IECA.logic.RestaurantManager" %>
<%@ page import="IECA.logic.User" %><%--
  Created by IntelliJ IDEA.
  User: mahya
  Date: 2/22/2020
  Time: 11:16 AM
  To change this template use File | Settings | File Templates.
--%>
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
<jsp:include page="footer.jsp" />



