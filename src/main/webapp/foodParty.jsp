<%@ page import="IECA.logic.RestaurantManager" %>
<%@ page import="IECA.logic.Restaurant" %>
<%@ page import="IECA.logic.SaleFood" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<meta http-equiv="refresh" content="30;URL='FoodParty'">

<jsp:include page="header.jsp" />
<table>
    <tr>
        <th>restaurant id</th>
        <th>restaurant logo</th>
        <th>restaurant name</th>
        <th>restaurant location</th>
        <th>food name</th>
        <th>food image</th>
        <th>description</th>
        <th>count</th>
        <th>old price</th>
        <th>price</th>
        <th>popularity</th>
    </tr>

    <%for (SaleFood saleFood: RestaurantManager.getInstance().getSaleFoods()){%>
    <tr>
        <td><%= saleFood.getRestaurantId()%></td>
        <td><img class="logo" src=" <%= saleFood.getRestaurantImage()%>" alt="logo"></td>
        <td><%= saleFood.getRestaurantName()%></td>
        <td>(<%= saleFood.getRestaurantLocation().getX()%>,
            <%= saleFood.getRestaurantLocation().getY()%>)</td>
        <td><%=saleFood.getName()%></td>
        <td><img class="logo" src=" <%= saleFood.getImage()%>" alt="logo"></td>
        <td><%=saleFood.getDescription()%></td>
        <%if(saleFood.getCount()>0){%>
        <td><%=saleFood.getCount()%></td>
        <%}else{%>
        <td> finished </td>
        <%}%>
        <td class="old-price"><%=saleFood.getOldPrice()%></td>
        <td><%=saleFood.getPrice()%></td>
        <td><%=saleFood.getPopularity()%></td>
        <%if(saleFood.getCount()>0){%>
        <td>
            <form action="Cart" method="post">
                <button type="submit" name="cartFromFoodParty"
                        value="<%=saleFood.getName()%>,<%=saleFood.getRestaurantId()%>,<%=saleFood.getPrice()%>">add To Cart</button>
            </form>
        </td>
        <%}%>
    </tr>
    <%}%>
</table>
<div>
    <form action="index.jsp">
        <button type="submit" name="home" value="">Home</button>
    </form>
</div>
<jsp:include page="footer.jsp" />

