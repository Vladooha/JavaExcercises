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
                <label>Список домов:</label>
                <#if houses??>
                    <table>
                        <tr>
                            <th>Адрес:</th>
                            <th>Кол-во этажей:</th>
                        </tr>
                        <#list houses as home>
                            <tr>
                                <td>${home.adress}</td>
                                <td>${home.stageCount}</td>
                            </tr>
                        </#list>
                    </table>
                </#if>
            </div>
        </div>
        <#if insertedHome??>
            <b>Дом по адресу '${insertedHome.adress}' успешно добавлен!</b>
        </#if>
        <h2>Новый дом:</h2>
        <div>
            <form action="/home" method="post">
                Адрес:<br>
                <@spring.bind "newHome.adress"/>
                <input id="adress"
                       name="${spring.status.expression}"
                       value="${spring.status.value?default("")}"/>
                <#list spring.status.errorMessages as error> <b>${error}</b></#list>
                <br>

                Кол-во этажей:<br>
                <@spring.bind "newHome.stageCount"/>
                <input id="stageCount"
                       name="${spring.status.expression}"
                       value="${spring.status.value?default(2)}"/>
                <#list spring.status.errorMessages as error> <b>${error}</b></#list>
                <br>
                <br>

                <input type="submit" value="Отправить">
            </form>
        </div>

        <@m.index/>
    </body>
</html>