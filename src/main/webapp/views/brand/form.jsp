<%--
  Created by IntelliJ IDEA.
  User: user
  Date: 29.06.2024
  Time: 22:38
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>${brand != null ? 'Edit Brand' : 'New Brand'}</title>
</head>
<body>
<h1>${brand != null ? 'Edit Brand' : 'New Brand'}</h1>
<form action="brands" method="get">
    <input type="hidden" name="action" value="${brand != null ? 'update' : 'insert'}">
    <c:if test="${brand != null}">
        <input type="hidden" name="id" value="${brand.id}">
    </c:if>
    Name: <input type="text" name="name" value="${brand != null ? brand.name : ''}">
    <input type="submit" value="Save">
</form>
<a href="brands">Back to List</a>
</body>
</html>
