package org.iesbelen.servlet;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.iesbelen.dao.ProductoDAO;
import org.iesbelen.dao.ProductoDAOImpl;
import org.iesbelen.model.Producto;

import java.io.IOException;
import java.util.Comparator;
import java.util.List;

@WebServlet(name = "productosServlet", value = "/tienda/productos/*")
public class ProductosServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        RequestDispatcher dispatcher = null;
        String pathInfo = request.getPathInfo(); //

        if (pathInfo == null || "/".equals(pathInfo)) {
            ProductoDAO prodDAO = new ProductoDAOImpl();

            //GET
            //	/productos/
            //	/productos

            List<Producto> listaProductos = prodDAO.getAll();

            if (request.getParameter("filtrar-por-nombre") == null) {

                request.setAttribute("listaProductos", listaProductos);

            } else {
                String filtro = request.getParameter("filtrar-por-nombre");
                List<Producto> listaFiltrada = listaProductos.stream()
                        .filter(producto -> producto.getNombre().toLowerCase().contains(filtro.toLowerCase()))
                        .toList();
                request.setAttribute("listaProductos", listaFiltrada);
            }

            dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/productos/productos.jsp");

        } else {
            // GET
            // 		/productos/{id}
            // 		/productos/{id}/
            // 		/productos/edit/{id}
            // 		/productos/edit/{id}/
            // 		/productos/crear
            // 		/productosv/crear/

            pathInfo = pathInfo.replaceAll("/$", "");
            String[] pathParts = pathInfo.split("/");

            if (pathParts.length == 2 && "crear".equals(pathParts[1])) {

                // GET
                // /productos/crear
                dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/productos/crear-producto.jsp");

            } else if (pathParts.length == 2) {
                ProductoDAO productoDAO = new ProductoDAOImpl();
                // GET
                // /productos/{id}
                try {
                    request.setAttribute("producto", productoDAO.find(Integer.parseInt(pathParts[1])));
                    dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/productos/detalle-producto.jsp");

                } catch (NumberFormatException nfe) {
                    nfe.printStackTrace();
                    dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/productos/productos.jsp");
                }
            } else if (pathParts.length == 3 && "editar".equals(pathParts[1])) {
                ProductoDAO productoDAO = new ProductoDAOImpl();

                // GET
                // /productos/editar/{id}
                try {
                    request.setAttribute("producto", productoDAO.find(Integer.parseInt(pathParts[2])));
                    dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/productos/editar-producto.jsp");

                } catch (NumberFormatException nfe) {
                    nfe.printStackTrace();
                    dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/productos/productos.jsp");
                }
            } else {
                System.out.println("Opción POST no soportada.");
                dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/productos/productos.jsp");

            }
        }

        dispatcher.forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        RequestDispatcher dispatcher;
        String __method__ = request.getParameter("__method__");

        if (__method__ == null) {
            // Crear uno nuevo
            ProductoDAO productoDAO = new ProductoDAOImpl();
            String nombre = request.getParameter("nombre");
            String precioStr = request.getParameter("precio");
            String idFabStr = request.getParameter("idFabricante");
            Producto nuevoProd = new Producto();
            try {
                ;
                double precio = Double.parseDouble(precioStr);
                int idFabricante = Integer.parseInt(idFabStr);
                nuevoProd.setNombre(nombre);
                nuevoProd.setPrecio(precio);
                nuevoProd.setIdFabricante(idFabricante);
                productoDAO.create(nuevoProd);

            } catch (NumberFormatException nfe) {
                nfe.printStackTrace();
            }

        } else if (__method__ != null && "put".equalsIgnoreCase(__method__)) {
            doPut(request, response);

        } else if (__method__ != null && "delete".equalsIgnoreCase(__method__)) {
            doDelete(request, response);
        } else {
            System.out.println("Opción POST no soportada.");
        }

        response.sendRedirect(request.getContextPath() + "/tienda/productos");
    }

    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        ProductoDAO productoDAO = new ProductoDAOImpl();
        String codigo = request.getParameter("codigo");
        String nombre = request.getParameter("nombre");
        String precioStr = request.getParameter("precio");
        String idFabStr = request.getParameter("idFabricante");
        Producto producto = new Producto();

        try {
            int id = Integer.parseInt(codigo);
            double precio = Double.parseDouble(precioStr);
            int idFabricante = Integer.parseInt(idFabStr);
            producto.setIdProducto(id);
            producto.setNombre(nombre);
            producto.setPrecio(precio);
            producto.setIdFabricante(idFabricante);
            productoDAO.update(producto);

        } catch (NumberFormatException nfe) {
            nfe.printStackTrace();
        }

    }

    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) {
        RequestDispatcher dispatcher;
        ProductoDAO productoDAO = new ProductoDAOImpl();
        String codigo = request.getParameter("codigo");

        try {
            int id = Integer.parseInt(codigo);

            productoDAO.delete(id);

        } catch (NumberFormatException nfe) {
            nfe.printStackTrace();
        }
    }
}