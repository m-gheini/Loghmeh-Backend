<%@ page import="IECA.logic.RestaurantManager" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Restaurants</title>
    <style>
        table {
            text-align: center;
            margin: auto;
        }
        th, td {
            padding: 5px;
            text-align: center;
        }
        .logo{
            width: 100px;
            height: 100px;
        }
    </style>
</head>
<body>
<table>
    <tr>
        <th>id</th>
        <th>logo</th>
        <th>name</th>
        <th>location</th>
    </tr>

        <%int size = RestaurantManager.getInstance().getRestaurants().size();%>
        <%for (int i =0;i<size;i++){%>
    <tr>
        <td><%= RestaurantManager.getInstance().getRestaurants().get(i).getId()%></td>
        <td><img class="logo" src=" <%= RestaurantManager.getInstance().getRestaurants().get(i).getLogo()%>" alt="logo"></td>
        <td><%= RestaurantManager.getInstance().getRestaurants().get(i).getName()%><%System.out.println(RestaurantManager.getInstance().getRestaurants().get(i).getName());%></td>
        <td>(<%= RestaurantManager.getInstance().getRestaurants().get(i).getLocation().getX()%>,
            <%= RestaurantManager.getInstance().getRestaurants().get(i).getLocation().getY()%>)</td>
    </tr>
    <%}%>
</table>
</body>
</html>