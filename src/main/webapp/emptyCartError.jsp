<%@ page import="IECA.logic.RestaurantManager" %>
<%@ page import="IECA.logic.Cart" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<jsp:include page="header.jsp" />
<%response.setStatus(400);%>
<div>Your cart is empty!</div>

<form action="index.jsp">
    <button type="submit" name="home" value="">Home</button>
</form>
<jsp:include page="footer.jsp" />