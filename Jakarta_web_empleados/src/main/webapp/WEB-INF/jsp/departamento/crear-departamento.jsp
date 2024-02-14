<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Crear Departamento</title>
</head>

<body>

<div id="contenedora" style="float:none; margin: 0 auto;width: 900px;">
    <form action="${pageContext.request.contextPath}/departamentos/crear/" method="post">
        <div style="float: left; width: 50%">
            <h1>Crear Departamento</h1>
        </div>
        <div style="float: none;width: auto;overflow: hidden;min-height: 80px;position: relative;">

            <div style="position: absolute; left: 39%; top : 39%;">
                <input type="submit" value="Crear"/>
            </div>

            <div style="position: absolute; left: 28%; top : 39%;">
                <input type="button" value="Atrás" onclick="history.back()"/>
            </div>
        </div>

        <div style="float: left;width: 50%">
            Nombre
        </div>
        <div style="float: none;width: auto;overflow: hidden;">
            <input name="nombre"/>
        </div>

    </form>
</div>

</body>
</html>