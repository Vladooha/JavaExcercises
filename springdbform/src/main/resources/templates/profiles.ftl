<#import "/spring.ftl" as spring/>
<#import "macro.ftl" as m>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="utf-8">
        <title>Training portal</title>
        <link rel="stylesheet" href="../static/css/tables.css">
        <style>
        table, th, td {
            border: 1px solid black;
            width: 30%;
        }
        </style>
    </head>
    <body>
        <div>
            <div>
                <label>Список людей:</label>
                <#if profiles??>
                    <table>
                        <tr>
                            <th>Имя:</th>
                            <th>Фамилия:</th>
                        </tr>
                        <#list profiles as profile>
                            <tr>
                                <td>${profile.name}</td>
                                <td>${profile.surname}</td>
                            </tr>
                        </#list>
                    </table>
                </#if>
            </div>
        </div>
        <#if insertedProfile??>
            <b>Житель '${newProfile.name} ${newProfile.surname}' успешно добавлен!</b>
        </#if>
        <h2>Новый житель:</h2>
        <div>
            <form action="/profile" method="post">
                Имя:<br>
                <@spring.bind "newProfile.name"/>
                <input id="name"
                       name="${spring.status.expression}"
                       value="${spring.status.value?default("")}"/>
                <#list spring.status.errorMessages as error> <b>${error}</b></#list>
                <br>

                Фамилия:<br>
                <@spring.bind "newProfile.surname"/>
                <input id="surname"
                       name="${spring.status.expression}"
                       value="${spring.status.value?default("")}"/>
                <#list spring.status.errorMessages as error> <b>${error}</b></#list>
                <br>
                <br>

                <input type="submit" value="Отправить">
            </form>
        </div>

        <@m.index/>
    </body>
</html>