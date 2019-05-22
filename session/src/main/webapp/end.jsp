<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page import="com.vladooha.data.dao.ProfileDAO" %>
<%@ page import="com.vladooha.data.entity.Profile" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html lang="ru">
    <head>

    </head>
    <body>

        <c:choose>
            <c:when test="${empty profile}">
                <h2>База данных пуста =(/h2>
            </c:when>
            <c:otherwise>
                 <h2>Ваши данные: <c:out value="${profile.name}"/> <c:out value="${profile.surname}"/></h2>
            </c:otherwise>
        </c:choose>

        <a href=<%=request.getContextPath()%>><input type="button" value="New iteration"></a>
        <%
            session.invalidate();
        %>
    </body>
</html>