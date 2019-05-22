<%@ page contentType="text/html; charset=UTF-8" %>
<!DOCTYPE html>
<html lang="ru">
    <head>

    </head>
    <body>
        <%
            request.setCharacterEncoding("UTF-8");
            session.setAttribute("name", request.getParameter("name"));
            session.setAttribute("surname", request.getParameter("surname"));
        %>
        <h2>Здесь что-то происходит</h2>
        <a href=<%=request.getContextPath() + "/end"%>><input type="button" value="Look data"></a>
    </body>
</html>