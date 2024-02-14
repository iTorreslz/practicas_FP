<%@ page import="java.util.List" %>
<%@ page import="org.iesbelen.model.Fabricante" %>
<%@ page import="org.iesbelen.dto.FabricanteDTO" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="es">

<head>
    <meta charset="UTF-8">
    <title>Fabricantes</title>
</head>

<body class="bg-gray-200">
<%@ include file="/WEB-INF/jsp/fragmentos/nav.jspf" %>

<div class="bg-white p-8 rounded-md w-full">
    <div class=" flex items-center justify-between pb-6">
        <div>
            <h2 class="text-gray-600 font-semibold">Fabricantes</h2>
            <span class="text-xs">Lista de fabricantes</span>
        </div>
        <div class="flex items-center justify-between">
            <div class="flex bg-gray-50 items-center p-2 rounded-md">
                <svg xmlns="http://www.w3.org/2000/svg" class="h-5 w-5 text-gray-400" viewBox="0 0 20 20"
                     fill="currentColor">
                    <path fill-rule="evenodd"
                          d="M8 4a4 4 0 100 8 4 4 0 000-8zM2 8a6 6 0 1110.89 3.476l4.817 4.817a1 1 0 01-1.414 1.414l-4.816-4.816A6 6 0 012 8z"
                          clip-rule="evenodd"/>
                </svg>
                <input class="bg-gray-50 outline-none ml-1 block " type="text" name="" id=""
                       placeholder="search...">
            </div>
            <div class="flex bg-gray-50 items-center p-2 rounded-md ml-10">
                <form action="<%=application.getContextPath()%>/tienda/fabricantes/" method="get">
                    <label for="orden" class="mb-3 block text-base font-medium text-[#07074D]">
                        Ordenar por
                    </label>
                    <select class="rounded-md border border-[#e0e0e0] bg-white py-3 px-6 text-base font-medium text-[#6B7280] outline-none focus:border-[#6A64F1] focus:shadow-md"
                            name="ordenar-por" id="orden">
                        <option value="nombre">Nombre</option>
                        <option value="codigo">Codigo</option>
                    </select>
                    <select class="rounded-md border border-[#e0e0e0] bg-white py-3 px-6 text-base font-medium text-[#6B7280] outline-none focus:border-[#6A64F1] focus:shadow-md"
                            name="modo">
                        <option value="asc">Ascendente</option>
                        <option value="desc">Descendente</option>
                    </select>
                    <button class="bg-indigo-600 px-4 py-2 rounded-md text-white font-semibold tracking-wide cursor-pointer">
                        Aceptar
                    </button>
                </form>
            </div>
            <div class="lg:ml-40 ml-10 space-x-8">
                <form action="<%=application.getContextPath()%>/tienda/fabricantes/crear">
                    <button
                            class="bg-indigo-600 px-4 py-2 rounded-md text-white font-semibold tracking-wide cursor-pointer">
                        Nuevo
                        fabricante
                    </button>
                </form>
            </div>
        </div>
    </div>
    <div>
        <div class="-mx-4 sm:-mx-8 px-4 sm:px-8 py-4 overflow-x-auto">
            <div class="inline-block min-w-full shadow rounded-lg overflow-hidden">
                <table class="min-w-full leading-normal">
                    <thead>
                    <tr>
                        <th
                                class="px-5 py-3 border-b-2 border-gray-200 bg-gray-100 text-left text-xs font-semibold text-gray-600 uppercase tracking-wider">
                            Codigo
                        </th>
                        <th
                                class="px-5 py-3 border-b-2 border-gray-200 bg-gray-100 text-left text-xs font-semibold text-gray-600 uppercase tracking-wider">
                            Nombre
                        </th>
                        <th
                                class="px-5 py-3 border-b-2 border-gray-200 bg-gray-100 text-left text-xs font-semibold text-gray-600 uppercase tracking-wider">
                            Cantidad productos
                        </th>
                        <th
                                class="px-5 py-3 border-b-2 border-gray-200 bg-gray-100 text-left text-xs font-semibold text-gray-600 uppercase tracking-wider">
                        </th>
                        <th
                                class="px-5 py-3 border-b-2 border-gray-200 bg-gray-100 text-left text-xs font-semibold text-gray-600 uppercase tracking-wider">
                        </th>
                    </tr>
                    </thead>
                    <%
                        if (request.getAttribute("listaFabricantesDTO") != null) {
                    %>
                    <tbody>
                    <%
                        List<FabricanteDTO> listaFabricantes = (List<FabricanteDTO>) request.getAttribute("listaFabricantesDTO");

                        for (FabricanteDTO fabricante : listaFabricantes) {
                    %>
                    <tr>
                        <td class="px-5 py-5 border-b border-gray-200 bg-white text-sm">
                            <p class="text-gray-900 whitespace-no-wrap"><%= fabricante.getIdFabricante() %>
                            </p>
                        </td>
                        <td class="px-5 py-5 border-b border-gray-200 bg-white text-sm">
                            <p class="text-gray-900 whitespace-no-wrap"><%= fabricante.getNombre() %>
                            </p>
                        </td>
                        <td class="px-5 py-5 border-b border-gray-200 bg-white text-sm">
                            <p class="text-gray-900 whitespace-no-wrap"><%= fabricante.getNumeroProductos() %>
                            </p>
                        </td>
                        <td class="px-5 py-5 border-b border-gray-200 bg-white text-sm">
                            <form action="<%=application.getContextPath()%>/tienda/fabricantes/editar/<%= fabricante.getIdFabricante() %>">
                                <button class="text-green-600 whitespace-no-wrap">
                                    Editar fabricante
                                </button>
                            </form>
                        </td>
                        <td class="px-5 py-5 border-b border-gray-200 bg-white text-sm">
                            <form action="<%=application.getContextPath()%>/tienda/fabricantes/borrar" method="POST">
                                <input type="hidden" name="__method__" value="delete"/>
                                <input type="hidden" name="codigo" value="<%= fabricante.getIdFabricante()%>"/>
                                <button class="text-red-600 whitespace-no-wrap">
                                    Eliminar fabricante
                                </button>
                            </form>
                        </td>
                    </tr>
                    <%
                        }
                    %>
                    </tbody>
                    <%
                        }
                    %>
                </table>
            </div>
        </div>
    </div>
</div>

<script src="https://cdn.tailwindcss.com"></script>
</body>

</html>