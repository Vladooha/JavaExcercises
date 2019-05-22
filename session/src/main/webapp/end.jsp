<%@ page contentType="text/html; charset=UTF-8" %>
<!DOCTYPE html>
<html lang="ru">
    <head>

    </head>
    <body>
        <h2>Ваши данные: <%=session.getAttribute("name") + " " + session.getAttribute("surname")%></h2>
        <a href=<%=request.getContextPath()%>><input type="button" value="New iteration"></a>
        <%
            session.invalidate();
        %>
    </body>
</html>