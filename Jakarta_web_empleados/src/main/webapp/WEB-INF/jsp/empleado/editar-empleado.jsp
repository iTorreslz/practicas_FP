<%@ page import="org.iesbelen.model.Empleado" %>
<%@ page import="java.util.Optional" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Detalle Empleado</title>
</head>
<body>

<div id="contenedora" style="float:none; margin: 0 auto;width: 900px;">
    <form action="${pageContext.request.contextPath}/empleados/editar/" method="post">
        <input type="hidden" name="__method__" value="put"/>
            <div style="float: left; width: 50%">
                <h1>Editar Empleado</h1>
            </div>
            <div style="float: none;width: auto;overflow: hidden;min-height: 80px;position: relative;">

                <div style="position: absolute; left: 39%; top : 39%;">
                    <input type="submit" value="Guardar"/>
                </div>

            </div>

        <% Optional<Empleado> optEmpl = (Optional<Empleado>) request.getAttribute("empleado");
            if (optEmpl.isPresent()) {
        %>

            <div style="float: left;width: 50%">
                <label>Nombre</label>
            </div>
            <div style="float: none;width: auto;overflow: hidden;">
                <input name="nombre" value="<%= optEmpl.get().getNombre() %>" readonly="readonly"/>
            </div>
            <div style="float: left;width: 50%">
                <label>Apellido1</label>
            </div>
            <div style="float: none;width: auto;overflow: hidden;">
                <input name="apellido1" value="<%= optEmpl.get().getApellido1() %>"/>
            </div>
        <div style="float: left;width: 50%">
            <label>Apellido2</label>
        </div>
        <div style="float: none;width: auto;overflow: hidden;">
            <input name="apellido2" value="<%= optEmpl.get().getApellido2() %>"/>
        </div>

        <% } else { %>

        request.sendRedirect("empleados/");

        <% } %>
    </form>
</div>

</body>
</html>