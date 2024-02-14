<%@ page import="org.iesbelen.model.Producto" %>
<%@ page import="java.util.Optional" %>
<%@ page import="org.iesbelen.model.Fabricante" %>
<%@ page import="org.iesbelen.dao.FabricanteDAO" %>
<%@ page import="org.iesbelen.dao.FabricanteDAOImpl" %>
<%@ page import="java.util.List" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<%
    FabricanteDAO fabDTO = new FabricanteDAOImpl();
    List<Fabricante> listaFab = fabDTO.getAll();
%>
<!DOCTYPE html>
<html lang="es">

<head>
    <meta charset="UTF-8">
    <title>Editar producto</title>
</head>

<body class="bg-gray-200">
<div class="p-12">
    <div class="mx-auto w-full">
        <% Optional<Producto> optProd = (Optional<Producto>) request.getAttribute("producto");
            if (optProd.isPresent()) {
        %>
        <form action="<%=application.getContextPath()%>/tienda/productos/editar" method="POST">
            <input type="hidden" name="__method__" value="put"/>
            <div class="mb-5">
                <label for="codigo" class="mb-3 block text-base font-medium text-[#07074D]">
                    Código
                </label>
                <input type="text" name="codigo" id="codigo" value="<%= optProd.get().getIdProducto()%>" readonly
                       class="w-full rounded-md border border-[#e0e0e0] bg-white py-3 px-6 text-base font-medium text-[#6B7280] outline-none focus:border-[#6A64F1] focus:shadow-md"/>
            </div>
            <div class="mb-5">
                <label for="nombre" class="mb-3 block text-base font-medium text-[#07074D]">
                    Nombre
                </label>
                <input type="text" name="nombre" id="nombre" value="<%= optProd.get().getNombre()%>"
                       class="w-full rounded-md border border-[#e0e0e0] bg-white py-3 px-6 text-base font-medium text-[#6B7280] outline-none focus:border-[#6A64F1] focus:shadow-md"/>
            </div>
            <div class="mb-5">
                <label for="precio" class="mb-3 block text-base font-medium text-[#07074D]">
                    Precio
                </label>
                <input type="text" name="precio" id="precio" value="<%= optProd.get().getPrecio()%>"
                       class="w-full rounded-md border border-[#e0e0e0] bg-white py-3 px-6 text-base font-medium text-[#6B7280] outline-none focus:border-[#6A64F1] focus:shadow-md"/>
            </div>
            <div class="mb-5">
                <label for="fab" class="mb-3 block text-base font-medium text-[#07074D]">
                    Fabricante
                </label>
                <select class="rounded-md border border-[#e0e0e0] bg-white py-3 px-6 text-base font-medium text-[#6B7280] outline-none focus:border-[#6A64F1] focus:shadow-md"
                        name="idFabricante" id="fab">
                    <% for (Fabricante fabricante : listaFab) { %>
                    <option value="<%=fabricante.getIdFabricante()%>"
                            <% if (fabricante.getIdFabricante() == optProd.get().getIdFabricante()) { %>
                            selected
                            <% } %>
                    >
                        <%=fabricante.getNombre()%>
                    </option>
                    <% } %>
                </select>
            </div>
            <div>
                <button
                        class="hover:shadow-form rounded-md bg-[#6A64F1] py-3 px-8 text-base font-semibold text-white outline-none">
                    Completar
                </button>
            </div>
        </form>
        <% } %>
    </div>
</div>
<script src="https://cdn.tailwindcss.com"></script>
</body>

</html>