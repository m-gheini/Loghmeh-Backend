<%@ page import="IECA.logic.RestaurantManager" %><%--
  Created by IntelliJ IDEA.
  User: leilafakheri
  Date: 2/22/2020 AD
  Time: 11:19 AM
  To change this template use File | Settings | File Templates.
--%>
<jsp:include page="header.jsp" />
<div>restaurant name</div>
<ul>
<%for (int i=0;i< RestaurantManager.getInstance().getCurrentUser().getMyCart().getFoods().size();i++){%>
    <li><%=RestaurantManager.getInstance().getCurrentUser().getMyCart().getFoods().get(i)%> : ‌ <%=RestaurantManager.getInstance().getCurrentUser().getMyCart().getNumberOfFood().get(i)%></li>
    <%}%>
</ul>
<form action="" method="POST">
    <button type="submit">finalize</button>
</form>
<jsp:include page="footer.jsp" />