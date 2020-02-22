<jsp:include page="header.jsp" />
<html>
<head>
    <title>Home</title>
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
    <form action="GetRestaurants.jsp">
        <button type="submit" name="getRestaurants" value="">See Available Restaurants In Radius</button>
    </form>
    <form action="userInfo.jsp">
        <button type="submit" name="userInfo" value="">Profile</button>
    </form>
</ul>
</body>
</html>