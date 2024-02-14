<%@ page import="java.util.List" %>
<%@ page import="org.iesbelen.model.Empleado" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>

<head>
    <title>Empleados</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet"
          integrity="sha384-T3c6CoIi6uLrA9TneNEoa7RxnatzjcDSCmG1MXxSR1GAsXEV/Dwwykc2MPK8M2HN" crossorigin="anonymous">
    <link rel="stylesheet" href="style.css">
</head>

<body>

<!-- Header Section -->
<%@ include file="/WEB-INF/jsp/fragmentos/header.jspf" %>

<!-- Menu Navigation Bar -->
<%@ include file="/WEB-INF/jsp/fragmentos/nav.jspf" %>

<!-- Body section -->
<div class="content">
    <div style="float:none; margin: 0 auto;width: 900px;">
        <div style="float: left; width: 50%">
            <h2>Empleados</h2>
        </div>
        <div style="float: none;width: auto;overflow: hidden;min-height: 80px;position: relative;">
            <div style="position: absolute; left: 49%; top : 49%;">
                <form>
                    <input type="submit" value="Crear">
                </form>
            </div>
        </div>
        <div style="float: left;width: 16.66%">Código</div>
        <div style="float: left;width: 16.66%">NIF</div>
        <div style="float: left;width: 16.66%">Nombre</div>
        <div style="float: left;width: 16.66%">Apellido 1</div>
        <div style="float: left;width: 16.66%">Apellido 2</div>
        <div style="float: none;width: auto;overflow: hidden;">Acción</div>

        <% if (request.getAttribute("listaEmpleados") != null) {
            List<Empleado> listaEmpleados = (List<Empleado>) request.getAttribute("listaEmpleados");
            for (Empleado empleado : listaEmpleados) {
        %>
        <div style="margin-top: 6px;" class="clearfix">
            <div style="float: left;width: 16.66%"><%= empleado.getId()%>
            </div>
            <div style="float: left;width: 16.66%"><%= empleado.getNif()%>
            </div>
            <div style="float: left;width: 16.66%"><%= empleado.getNombre()%>
            </div>
            <div style="float: left;width: 16.66%"><%= empleado.getApellido1()%>
            </div>
            <div style="float: left;width: 16.66%"><%= empleado.getApellido2()%>
            </div>
            <div style="float: none;width: auto;overflow: hidden;">
                <form
                        style="display: inline;"><input type="submit" value="Detalles"/>
                </form>
                <form  action="${pageContext.request.contextPath}/empleados/editar/<%= empleado.getId()%>"
                        style="display: inline;"><input type="submit" value="Editar"/>
                </form>
                <form
                        style="display: inline;"><input type="submit" value="Eliminar"/>
                </form>
            </div>
        </div>
        <%
            }
        } else {
        %>
        No hay ningún empleado
        <% } %>
    </div>
</div>

<!-- Footer Section -->
<%@ include file="/WEB-INF/jsp/fragmentos/footer.jspf" %>

</body>

</html>