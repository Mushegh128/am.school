<%@ page import="java.util.List" %>
<%@ page import="model.Rate" %>
<%@ page import="java.util.ArrayList" %><%--
  Created by IntelliJ IDEA.
  User: Mushegh Gazaryan
  Date: 09.08.2021
  Time: 19:37
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Student</title>
    <style>
        bodyStyle{
            background: url("../resources/pictures/body_pic.jpg");
            text-align: center;
            text-align-all: center;
            align-items: center;
        }
    </style>
</head>
<body class="bodyStyle">
<div style="text-align: center">
    <table style="border: 1px solid black; text-align: center">
        <tr>
            <th>lesson</th>
            <th>rating</th>
        </tr>

        <%
            List<Rate> userRates = (ArrayList) request.getAttribute("userRates");
            if (userRates != null && !userRates.isEmpty()) {
                for (Rate rate : userRates) {
        %>
        <tr>
            <td><%= rate.getLesson().getTitle() %></td>
            <td><%= rate.getRating() %></td>
        <%
                }
            }
        %>


    </table>
    <h3>Result of All <%=(double) request.getAttribute("sumResultOfRates")%>
    </h3>
</div>

</body>
</html>
