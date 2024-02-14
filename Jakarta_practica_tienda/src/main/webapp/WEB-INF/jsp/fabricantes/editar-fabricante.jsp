<%@ page import="org.iesbelen.model.Fabricante" %>
<%@ page import="java.util.Optional" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<!DOCTYPE html>
<html lang="es">

<head>
    <meta charset="UTF-8">
    <title>Editar fabricante</title>
</head>

<body class="bg-gray-200">
<div class="p-12">
    <div class="mx-auto w-full">
        <% Optional<Fabricante> optFab = (Optional<Fabricante>) request.getAttribute("fabricante");
            if (optFab.isPresent()) {
        %>
        <form action="<%=application.getContextPath()%>/tienda/fabricantes/editar" method="POST">
            <input type="hidden" name="__method__" value="put"/>
            <div class="mb-5">
                <label for="codigo" class="mb-3 block text-base font-medium text-[#07074D]">
                    Código
                </label>
                <input type="text" name="codigo" id="codigo" value="<%= optFab.get().getIdFabricante()%>" readonly
                       class="w-full rounded-md border border-[#e0e0e0] bg-white py-3 px-6 text-base font-medium text-[#6B7280] outline-none focus:border-[#6A64F1] focus:shadow-md"/>
            </div>
            <div class="mb-5">
                <label for="nombre" class="mb-3 block text-base font-medium text-[#07074D]">
                    Nombre
                </label>
                <input type="text" name="nombre" id="nombre" value="<%= optFab.get().getNombre()%>"
                       class="w-full rounded-md border border-[#e0e0e0] bg-white py-3 px-6 text-base font-medium text-[#6B7280] outline-none focus:border-[#6A64F1] focus:shadow-md"/>
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