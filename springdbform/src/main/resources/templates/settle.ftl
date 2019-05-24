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
                <label>Список жильцов:</label>
                <#if settlements??>
                    <table>
                        <tr>
                            <th>Имя:</th>
                            <th>Фамилия:</th>
                            <th>Адрес:</th>
                        </tr>
                        <#list settlements as settlement>
                            <tr>
                                <td>${settlement.profileDTO.name}</td>
                                <td>${settlement.profileDTO.surname}</td>
                                <td>${settlement.homeDTO.adress}</td>
                            </tr>
                        </#list>
                    </table>
                </#if>
            </div>
        </div>
        <div>
            <div>
                <label>Список домов:</label>
                <#if houses??>
                    <table>
                        <tr>
                            <th>ID:</th>
                            <th>Адрес:</th>
                            <th>Кол-во этажей:</th>
                        </tr>
                        <#list houses as home>
                            <tr>
                                <td>${home.id}</td>
                                <td>${home.adress}</td>
                                <td>${home.stageCount}</td>
                            </tr>
                        </#list>
                    </table>
                </#if>
            </div>
        </div>
        <div>
            <div>
                <label>Список людей:</label>
                <#if profiles??>
                    <table>
                        <tr>
                            <th>ID:</th>
                            <th>Имя:</th>
                            <th>Фамилия:</th>
                        </tr>
                        <#list profiles as profile>
                            <tr>
                                <td>${profile.id}</td>
                                <td>${profile.name}</td>
                                <td>${profile.surname}</td>
                            </tr>
                        </#list>
                    </table>
                </#if>
            </div>
        </div>
        <h2>Заселение жителя:</h2>
        <div>
            <form action="/settlement" method="post">
                ID дома:<br>
                <@spring.bind "newSettle.homeId"/>
                <input id="adress"
                       name="${spring.status.expression}"
                       value="${spring.status.value?default("")}"/>
                <#list spring.status.errorMessages as error> <b>${error}</b></#list>
                <br>

                ID жителя:<br>
                <@spring.bind "newSettle.profileId"/>
                <input id="stageCount"
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