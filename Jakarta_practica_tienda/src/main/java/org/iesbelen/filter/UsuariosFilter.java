package org.iesbelen.filter;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.annotation.WebInitParam;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.iesbelen.model.Usuario;

import java.io.IOException;

@WebFilter(
        urlPatterns = {"/tienda/usuarios/", "/tienda/usuarios/crear", "/tienda/usuarios/editar/*", "/tienda/usuarios/borrar/*"},
        initParams = @WebInitParam(name = "acceso-concedido-a-rol", value = "Administrador")
)
public class UsuariosFilter implements Filter {

    private String rolAcceso;

    public void init(FilterConfig filterConfig) throws ServletException {
        this.rolAcceso = filterConfig.getInitParameter("acceso-concedido-a-rol");
    }

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;

        HttpSession session = httpRequest.getSession();

        //Obtención de la url
        String url = httpRequest.getRequestURL().toString();

        Usuario usuario = null;

        if (session != null
                && (usuario = (Usuario) session.getAttribute("usuario-logado")) != null
                && rolAcceso.equals(usuario.getRol())) {

            //Si eres administrador, acceso a cualquier página del filtro
            chain.doFilter(request, response);

        } else if (url.endsWith("/usuarios/")
                || url.endsWith("/usuarios/crear")
                || url.contains("/usuarios/editar")
                || url.contains("/usuarios/borrar")) {

            // Usuario no administrador trata de acceder, y el filtro lo redirige a login
            httpResponse.sendRedirect(httpRequest.getContextPath() + "/tienda/login");

        } else{
            // Otras rutas se dan paso a cualquier rol
            chain.doFilter(request, response);
        }
    }

    public void destroy() {
    }
}