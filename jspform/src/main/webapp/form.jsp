<%@ page contentType="text/html; charset=UTF-8" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Title</title>
</head>
<body>
    <h2>Введите ваши данные:</h2>
    <form method="post" action=<%=request.getContextPath() + "/send" %>>
        <label>Имя:</label>
        <input type="text" name="name">
        <br>
        <label>Фамилия:</label>
        <input type="text" name="surname">
        <br>
        <input type="submit">
    </form>
</body>
</html>