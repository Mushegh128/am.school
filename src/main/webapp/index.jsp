<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>Welcome</title>
</head>
<body style="background-image: url('../resources/pictures/body_pic.jpg')">
<div style="background-image: url('../resources/pictures/body_pic.jpg'); text-align: center">
    <h1 style="color: teal"> Welcome SCHOOL.am </h1>
    <h3 style="color: teal"> please login </h3>

    <form action="/login" method="post">
        email: <input type="text" name="email">
        password: <input type="password" name="password">
        <input type="submit" value="login">
    </form>

</div>

</body>
</html>