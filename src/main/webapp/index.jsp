<%@ page import="IECA.logic.RestaurantManager" %>
<jsp:include page="header.jsp" />
<%RestaurantManager.getInstance();%>
<a href="IECA_war_exploded/users">userInfo </a>
<ul>
    <form action="GetRestaurants" method="post">
        <button type="submit" name="getRestaurants" value="">See Available Restaurants In Radius</button>
    </form>
    <form action="FoodParty"  method="post">
        <button type="submit" name="foodPartyButton" value="">Food Party</button>
    </form>
    <form action="IECA_war_exploded/users">
        <button type="submit" name="userInfo" value="">Profile</button>
    </form>
    <form action="Cart"  method="post">
        <button type="submit" name="cartFromHome" value="">Cart</button>
    </form>
</ul>
<jsp:include page="footer.jsp" />
<%--<%@ page language="java" contentType="text/html; charset=UTF-8"--%>
<%--         pageEncoding="UTF-8"%>--%>
<%--<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">--%>
<%--<html>--%>
<%--<head>--%>
<%--    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">--%>
<%--    <title>Insert title here</title>--%>
<%--</head>--%>
<%--<body>--%>

<%--<h1> Click below link and check console for finding execution time </h1>--%>
<%--<a href="ServletController1">Call servlet 1</a> <br>--%>
<%--<a href="ServletController2">Call servlet 2</a>--%>
<%--</body>--%>
<%--</html>--%>