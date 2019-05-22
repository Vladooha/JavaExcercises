<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page import="com.vladooha.data.dao.ProfileDAO" %>
<%@ page import="com.vladooha.data.entity.Profile" %>
<!DOCTYPE html>
<html lang="ru">
    <head>

    </head>
    <body>
        <%
            request.setCharacterEncoding("UTF-8");
            Profile profile = new Profile(
                request.getParameter("name"),
                request.getParameter("surname"));

            ProfileDAO profileDAO = ProfileDAO.getInstance();
            if (profileDAO != null) {
                profileDAO.addProfile(profile);
            }
            //session.setAttribute("name", request.getParameter("name"));
            //session.setAttribute("surname", request.getParameter("surname"));

        %>
        <h2>Здесь что-то происходит</h2>
        <a href=<%=request.getContextPath() + "/end_process"%>><input type="button" value="Look data"></a>
    </body>
</html>