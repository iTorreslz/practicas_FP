<%@ page import="org.iesbelen.model.Usuario" %>
<%@ page import="java.util.Optional" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<!DOCTYPE html>
<html lang="es">

<head>
    <meta charset="UTF-8">
    <title>Editar usuario</title>
</head>

<body class="bg-gray-200">
<div class="p-12">
    <div class="mx-auto w-full">
        <% Optional<Usuario> optUser = (Optional<Usuario>) request.getAttribute("usuario");
            if (optUser.isPresent()) {
        %>
        <form action="<%=application.getContextPath()%>/tienda/usuarios/editar" method="POST">
            <input type="hidden" name="__method__" value="put"/>
            <div class="mb-5">
                <label for="codigo" class="mb-3 block text-base font-medium text-[#07074D]">
                    Código
                </label>
                <input type="text" name="codigo" id="codigo" value="<%= optUser.get().getIdUsuario()%>" readonly
                       class="w-full rounded-md border border-[#e0e0e0] bg-white py-3 px-6 text-base font-medium text-[#6B7280] outline-none focus:border-[#6A64F1] focus:shadow-md"/>
            </div>
            <div class="mb-5">
                <label for="usuario" class="mb-3 block text-base font-medium text-[#07074D]">
                    Nombre
                </label>
                <input type="text" name="usuario" id="usuario" value="<%= optUser.get().getUsuario()%>"
                       class="w-full rounded-md border border-[#e0e0e0] bg-white py-3 px-6 text-base font-medium text-[#6B7280] outline-none focus:border-[#6A64F1] focus:shadow-md"/>
            </div>
            <div class="mb-5">
                <label for="password" class="mb-3 block text-base font-medium text-[#07074D]">
                    Password
                </label>
                <input type="password" name="password" id="password" value="<%= optUser.get().getPassword()%>"
                       class="w-full rounded-md border border-[#e0e0e0] bg-white py-3 px-6 text-base font-medium text-[#6B7280] outline-none focus:border-[#6A64F1] focus:shadow-md"/>
            </div>
            <div class="mb-5">
                <label for="rol" class="mb-3 block text-base font-medium text-[#07074D]">
                    Rol
                </label>
                <select class="rounded-md border border-[#e0e0e0] bg-white py-3 px-6 text-base font-medium text-[#6B7280] outline-none focus:border-[#6A64F1] focus:shadow-md"
                        name="rol" id="rol">
                    <option value="Cliente"
                            <% if (optUser.get().getRol().equals("Cliente")) { %>
                            selected
                            <% } %>
                    >Cliente</option>
                    <option value="Vendedor"
                            <% if (optUser.get().getRol().equals("Vendedor")) { %>
                            selected
                            <% } %>
                    >Vendedor</option>
                    <option value="Administrador"
                            <% if (optUser.get().getRol().equals("Administrador")) { %>
                            selected
                            <% } %>
                    >Administrador</option>
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