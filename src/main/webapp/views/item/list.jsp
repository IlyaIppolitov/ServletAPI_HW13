<%--
  Created by IntelliJ IDEA.
  User: user
  Date: 29.06.2024
  Time: 22:37
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Item List</title>
</head>
<body>
<h1>Item List</h1>
<a href="items?action=new">Add New Item</a>
<table border="1">
    <tr>
        <th>ID</th>
        <th>Name</th>
        <th>Brand</th>
        <th>Actions</th>
    </tr>
    <c:forEach var="item" items="${listItem}">
        <tr>
            <td>${item.id}</td>
            <td>${item.name}</td>
            <td>${item.brand.name}</td>
            <td>
                <a href="items?action=edit&id=${item.id}">Edit</a>
                <a href="items?action=delete&id=${item.id}">Delete</a>
            </td>
        </tr>
    </c:forEach>
</table>
</body>
</html>
