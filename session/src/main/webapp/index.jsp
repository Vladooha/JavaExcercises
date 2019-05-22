<%@ page contentType="text/html; charset=UTF-8" %>
<!DOCTYPE html>
<html lang="ru">
    <head>
        <meta charset="UTF-8">
        <title>Title</title>
        <style>
            div.field {
              padding-bottom: 5px;
            }

            div.field label {
              display: block;
              float: left;
              width: 120px;
              height: 15px;
            }
        </style>
    </head>
    <body>
        <h2>Введите ваши данные:</h2>
        <form method="post" action=<%=request.getContextPath() + "/process" %>>
            <div class="field">
                <label>Имя:</label>
                <input type="text" name="name">
            </div>
            <div class="field">
                <label>Фамилия:</label>
                <input type="text" name="surname">
            </div>
            </br>
            <input type="submit">
        </form>
    </body>
</html>