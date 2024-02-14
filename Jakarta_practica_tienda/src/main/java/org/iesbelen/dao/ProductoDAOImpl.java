package org.iesbelen.dao;

import org.iesbelen.model.Fabricante;
import org.iesbelen.model.Producto;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ProductoDAOImpl extends AbstractDAOImpl implements ProductoDAO {
    public synchronized void create(Producto producto) {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        ResultSet rsGenKeys = null;

        try {
            conn = connectDB();
            ps = conn.prepareStatement("INSERT INTO productos (nombre,precio,idFabricante) VALUES (?,?,?)", Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, producto.getNombre());
            ps.setDouble(2, producto.getPrecio());
            ps.setInt(3, producto.getIdFabricante());

            int rows = ps.executeUpdate();
            if (rows == 0)
                System.out.println("INSERT de producto con 0 filas insertadas.");

            rsGenKeys = ps.getGeneratedKeys();
            if (rsGenKeys.next())
                producto.setIdProducto(rsGenKeys.getInt(1));

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            closeDb(conn, ps, rs);
        }
    }

    public List<Producto> getAll() {
        Connection conn = null;
        Statement s = null;
        ResultSet rs = null;
        List<Producto> listProd = new ArrayList<>();

        try {
            conn = connectDB();
            s = conn.createStatement();
            rs = s.executeQuery("SELECT * FROM productos");

            while (rs.next()) {
                Producto prod = new Producto();
                prod.setIdProducto(rs.getInt(1));
                prod.setNombre(rs.getString(2));
                prod.setPrecio(rs.getDouble(3));
                prod.setIdFabricante(rs.getInt(4));
                listProd.add(prod);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            closeDb(conn, s, rs);
        }
        return listProd;
    }

    public Optional<Producto> find(int id) {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            conn = connectDB();

            ps = conn.prepareStatement("SELECT * FROM productos WHERE idProducto = ?");
            ps.setInt(1, id);
            rs = ps.executeQuery();

            if (rs.next()) {
                Producto prod = new Producto();
                prod.setIdProducto(rs.getInt(1));
                prod.setNombre(rs.getString(2));
                prod.setPrecio(rs.getDouble(3));
                prod.setIdFabricante(rs.getInt(4));

                return Optional.of(prod);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            closeDb(conn, ps, rs);
        }

        return Optional.empty();
    }

    public void update(Producto producto) {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            conn = connectDB();

            ps = conn.prepareStatement("UPDATE productos SET nombre = ?,precio = ?  WHERE idProducto = ?");
            ps.setString(1, producto.getNombre());
            ps.setDouble(2, producto.getPrecio());
            ps.setInt(3, producto.getIdProducto());

            int rows = ps.executeUpdate();

            if (rows == 0)
                System.out.println("Update de producto con 0 registros actualizados.");

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            closeDb(conn, ps, rs);
        }
    }

    public void delete(int id) {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            conn = connectDB();

            ps = conn.prepareStatement("DELETE FROM productos WHERE idProducto = ?");
            ps.setInt(1, id);

            int rows = ps.executeUpdate();

            if (rows == 0)
                System.out.println("Delete de producto con 0 registros eliminados.");

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            closeDb(conn, ps, rs);
        }
    }
}