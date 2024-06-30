<%--
  Created by IntelliJ IDEA.
  User: user
  Date: 29.06.2024
  Time: 22:36
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Brand List</title>
</head>
<body>
<h1>Brand List</h1>
<a href="brands?action=new">Add New Brand</a>
<table border="1">
    <tr>
        <th>ID</th>
        <th>Name</th>
        <th>Actions</th>
    </tr>
    <c:forEach var="brand" items="${listBrand}">
        <tr>
            <td>${brand.id}</td>
            <td>${brand.name}</td>
            <td>
                <a href="brands?action=edit&id=${brand.id}">Edit</a>
                <a href="brands?action=delete&id=${brand.id}">Delete</a>
            </td>
        </tr>
    </c:forEach>
</table>
</body>
</html>
