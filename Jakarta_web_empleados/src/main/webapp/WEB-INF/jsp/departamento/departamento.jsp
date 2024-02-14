<%@ page import="org.iesbelen.model.Departamento" %>
<%@ page import="java.util.List" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>

<head>
    <title>Departamentos</title>
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
            <h2>Departamentos</h2>
        </div>
        <div style="float: none;width: auto;overflow: hidden;min-height: 80px;position: relative;">
            <div style="position: absolute; left: 49%; top : 49%;">
                <form action="${pageContext.request.contextPath}/departamentos/crear">
                    <input type="submit" value="Crear">
                </form>
            </div>
        </div>
        <div style="float: left;width: 20%">Código</div>
        <div style="float: left;width: 20%">Nombre</div>
        <div style="float: left;width: 20%">Presupuesto</div>
        <div style="float: left;width: 20%">Gastos</div>
        <div style="float: none;width: auto;overflow: hidden;">Acción</div>
        <% if (request.getAttribute("listaDepartamentos") != null) {
            List<Departamento> listaDepartamentos = (List<Departamento>) request.getAttribute("listaDepartamentos");
            for (Departamento departamento : listaDepartamentos) {
        %>
        <div style="margin-top: 6px;" class="clearfix">
            <div style="float: left;width: 20%"><%= departamento.getId()%>
            </div>
            <div style="float: left;width: 20%"><%= departamento.getNombre()%>
            </div>
            <div style="float: left;width: 20%"><%= departamento.getPresupuesto()%>
            </div>
            <div style="float: left;width: 20%"><%= departamento.getGastos()%>
            </div>
            <div style="float: none;width: auto;overflow: hidden;">
                <form
                        style="display: inline;"><input type="submit" value="Detalles"/>
                </form>
                <form
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
        No hay ningún departamento
        <% } %>
    </div>
</div>

<!-- Footer Section -->
<%@ include file="/WEB-INF/jsp/fragmentos/footer.jspf" %>

</body>

</html>