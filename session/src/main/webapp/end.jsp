<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page import="com.vladooha.data.dao.ProfileDAO" %>
<%@ page import="com.vladooha.data.entity.Profile" %>
<!DOCTYPE html>
<html lang="ru">
    <head>

    </head>
    <body>
        <%
            Profile profile = new Profile();
            ProfileDAO profileDAO = ProfileDAO.getInstance();
            if (profileDAO != null) {
                profile = profileDAO.getLastProfile();
                profileDAO.removeProfile(profile);
            }
        %>
        <h2>Ваши данные: <%=profile.getName() + " " + profile.getSurname()%></h2>
        <a href=<%=request.getContextPath()%>><input type="button" value="New iteration"></a>
        <%
            session.invalidate();
        %>
    </body>
</html>