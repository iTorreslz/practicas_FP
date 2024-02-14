package org.iesbelen.dao;

import org.iesbelen.model.Fabricante;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class FabricanteDAOImpl extends AbstractDAOImpl implements FabricanteDAO {
    public synchronized void create(Fabricante fabricante) {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        ResultSet rsGenKeys = null;

        try {
            conn = connectDB();
            ps = conn.prepareStatement("INSERT INTO fabricantes (nombre) VALUES (?)", Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, fabricante.getNombre());

            int rows = ps.executeUpdate();
            if (rows == 0)
                System.out.println("INSERT de fabricante con 0 filas insertadas.");

            rsGenKeys = ps.getGeneratedKeys();
            if (rsGenKeys.next())
                fabricante.setIdFabricante(rsGenKeys.getInt(1));

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            closeDb(conn, ps, rs);
        }
    }

    public List<Fabricante> getAll() {
        Connection conn = null;
        Statement s = null;
        ResultSet rs = null;
        List<Fabricante> listFab = new ArrayList<>();

        try {
            conn = connectDB();
            s = conn.createStatement();
            rs = s.executeQuery("SELECT * FROM fabricantes");

            while (rs.next()) {
                Fabricante fab = new Fabricante();
                fab.setIdFabricante(rs.getInt(1));
                fab.setNombre(rs.getString(2));
                listFab.add(fab);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            closeDb(conn, s, rs);
        }
        return listFab;
    }

    public Optional<Fabricante> find(int id) {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            conn = connectDB();

            ps = conn.prepareStatement("SELECT * FROM fabricantes WHERE idFabricante = ?");
            ps.setInt(1, id);
            rs = ps.executeQuery();

            if (rs.next()) {
                Fabricante fab = new Fabricante();
                fab.setIdFabricante(rs.getInt(1));
                fab.setNombre(rs.getString(2));

                return Optional.of(fab);
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

    public void update(Fabricante fabricante) {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            conn = connectDB();

            ps = conn.prepareStatement("UPDATE fabricantes SET nombre = ?  WHERE idFabricante = ?");
            ps.setString(1, fabricante.getNombre());
            ps.setInt(2, fabricante.getIdFabricante());

            int rows = ps.executeUpdate();

            if (rows == 0)
                System.out.println("Update de fabricante con 0 registros actualizados.");

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

            ps = conn.prepareStatement("DELETE FROM fabricantes WHERE idFabricante = ?");
            ps.setInt(1, id);

            int rows = ps.executeUpdate();

            if (rows == 0)
                System.out.println("Delete de fabricante con 0 registros eliminados.");

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            closeDb(conn, ps, rs);
        }
    }

    public Optional<Integer> getCountProductos(int id) {
        Connection conn = null;
        Statement s = null;
        ResultSet rs = null;
        int contador = 0;

        try {
            conn = connectDB();
            s = conn.createStatement();
            rs = s.executeQuery("SELECT COUNT(*) FROM productos WHERE idFabricante = " + id);

            while (rs.next()) {
                contador = rs.getInt(1);
                return Optional.of(contador);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            closeDb(conn, s, rs);
        }
        return Optional.empty();
    }
}