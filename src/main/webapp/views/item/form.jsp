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
    <title>${item != null ? 'Edit Item' : 'New Item'}</title>
</head>
<body>
<h1>${item != null ? 'Edit Item' : 'New Item'}</h1>
<form action="items" method="get">
    <input type="hidden" name="action" value="${item != null ? 'update' : 'insert'}">
    <c:if test="${item != null}">
        <input type="hidden" name="id" value="${item.id}">
    </c:if>
    Name: <input type="text" name="name" value="${item != null ? item.name : ''}">
    Brand:
    <select name="brand">
        <c:forEach var="brand" items="${listBrand}">
            <option value="${brand.id}" ${item != null && brand.id == item.brand.id ? 'selected' : ''}>${brand.name}</option>
        </c:forEach>
    </select>
    <input type="submit" value="Save">
</form>
<a href="items">Back to List</a>
</body>
</html>
