<%@ page import="IECA.logic.Restaurant" %>
<%@ page import="IECA.logic.RestaurantManager" %>
<%@ page import="IECA.logic.Food" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%String value = request.getParameter("restaurantInfo");%>
<%Restaurant thisRes = new Restaurant();%>
<%for(Restaurant restaurant : RestaurantManager.getInstance().getRestaurants()){
    if (restaurant.getId().equals(value)) {
        thisRes = restaurant;
        break;
    }
}%>
<html>
<head>
    <title>RestaurantInfo</title>
    <style>
        img{
            width: 100px;
            height: 100px;
        }
        li{
            display: flex;
            flex-direction: row;
            padding: 0 0 5px;
        }
        div, form{
            padding: 0 5px;
        }
    </style>
</head>
<body>
<ul>
    <li>id: <%=thisRes.getId()%></li>
    <li>name: <%=thisRes.getName()%></li>
    <li>location: (<%=thisRes.getLocation().getX()%>,<%=thisRes.getLocation().getY()%>)</li>
    <li>
        menu:
        <ul>
            <%for (Food foodInMenu:thisRes.getMenu()){%>
            <li>
                <img src="<%=foodInMenu.getImage()%>" alt="logo">
                <div><%=foodInMenu.getName()%></div>
                <div><%=foodInMenu.getPrice()%> Toman</div>
                <form action="" method="post">
                    <button type="submit" name="foodInfo" value="<%=foodInMenu.getName()%>,<%=foodInMenu.getRestaurantId()%>">add To Cart</button>
                </form>
            </li>
            <%}%>
        </ul>
    </li>
</ul>
</body>
</html>
