<%@ page import="org.iesbelen.dao.FabricanteDAOImpl" %>
<%@ page import="org.iesbelen.dao.FabricanteDAO" %>
<%@ page import="java.util.List" %>
<%@ page import="org.iesbelen.model.Fabricante" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<%
    FabricanteDAO fabDTO = new FabricanteDAOImpl();
    List<Fabricante> listaFab = fabDTO.getAll();
%>
<!DOCTYPE html>
<html lang="es">

<head>
    <meta charset="UTF-8">
    <title>Crear producto</title>
</head>

<body class="bg-gray-200">
<div class="p-12">
    <div class="mx-auto w-full">
        <form action="<%=application.getContextPath()%>/tienda/productos/crear" method="POST">
            <div class="mb-5">
                <label for="name" class="mb-3 block text-base font-medium text-[#07074D]">
                    Nombre
                </label>
                <input type="text" name="nombre" id="name"
                       class="rounded-md border border-[#e0e0e0] bg-white py-3 px-6 text-base font-medium text-[#6B7280] outline-none focus:border-[#6A64F1] focus:shadow-md"/>
            </div>
            <div class="mb-5">
                <label for="precio" class="mb-3 block text-base font-medium text-[#07074D]">
                    Precio
                </label>
                <input type="text" name="precio" id="precio"
                       class="rounded-md border border-[#e0e0e0] bg-white py-3 px-6 text-base font-medium text-[#6B7280] outline-none focus:border-[#6A64F1] focus:shadow-md"/>
            </div>
            <div class="mb-5">
                <label for="fab" class="mb-3 block text-base font-medium text-[#07074D]">
                    Fabricante
                </label>
                <select class="rounded-md border border-[#e0e0e0] bg-white py-3 px-6 text-base font-medium text-[#6B7280] outline-none focus:border-[#6A64F1] focus:shadow-md"
                        name="idFabricante" id="fab">
                    <% for (Fabricante fabricante : listaFab) { %>
                    <option value="<%=fabricante.getIdFabricante()%>">
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
    </div>
</div>
<script src="https://cdn.tailwindcss.com"></script>
</body>

</html>