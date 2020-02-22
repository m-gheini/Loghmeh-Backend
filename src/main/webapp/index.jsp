<jsp:include page="header.jsp" />

<ul>
    <form action="GetRestaurants.jsp">
        <button type="submit" name="getRestaurants" value="">See Available Restaurants In Radius</button>
    </form>
    <form action="userInfo.jsp">
        <button type="submit" name="userInfo" value="">Profile</button>
    </form>
    <form action="Cart" , method="post">
        <button type="submit" name="cartFromHome" value="">Cart</button>
    </form>
</ul>
<jsp:include page="footer.jsp" />
