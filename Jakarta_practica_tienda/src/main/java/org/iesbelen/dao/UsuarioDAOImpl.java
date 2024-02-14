package org.iesbelen.dao;

import org.iesbelen.model.Usuario;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class UsuarioDAOImpl extends AbstractDAOImpl implements UsuarioDAO {
    public synchronized void create(Usuario usuario) {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        ResultSet rsGenKeys = null;

        try {
            conn = connectDB();
            ps = conn.prepareStatement("INSERT INTO usuarios (usuario,password,rol) VALUES (?,?,?)", Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, usuario.getUsuario());
            ps.setString(2, usuario.getPassword());
            ps.setString(3, usuario.getRol());

            int rows = ps.executeUpdate();
            if (rows == 0)
                System.out.println("INSERT de usuario con 0 filas insertadas.");

            rsGenKeys = ps.getGeneratedKeys();
            if (rsGenKeys.next())
                usuario.setIdUsuario(rsGenKeys.getInt(1));

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            closeDb(conn, ps, rs);
        }
    }

    public List<Usuario> getAll() {
        Connection conn = null;
        Statement s = null;
        ResultSet rs = null;
        List<Usuario> listUser = new ArrayList<>();

        try {
            conn = connectDB();
            s = conn.createStatement();
            rs = s.executeQuery("SELECT * FROM usuarios");

            while (rs.next()) {
                Usuario user = new Usuario();
                user.setIdUsuario(rs.getInt(1));
                user.setUsuario(rs.getString(2));
                user.setPassword(rs.getString(3));
                user.setRol(rs.getString(4));
                listUser.add(user);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            closeDb(conn, s, rs);
        }
        return listUser;
    }

    public Optional<Usuario> find(int id) {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            conn = connectDB();

            ps = conn.prepareStatement("SELECT * FROM usuarios WHERE idUsuario = ?");
            ps.setInt(1, id);
            rs = ps.executeQuery();

            if (rs.next()) {
                Usuario fab = new Usuario();
                fab.setIdUsuario(rs.getInt(1));
                fab.setUsuario(rs.getString(2));
                fab.setPassword(rs.getString(3));
                fab.setRol(rs.getString(4));

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

    public void update(Usuario usuario) {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            conn = connectDB();

            ps = conn.prepareStatement("UPDATE usuarios SET usuario = ?,password = ?,rol = ?  WHERE idUsuario = ?");
            ps.setString(1, usuario.getUsuario());
            ps.setString(2, usuario.getPassword());
            ps.setString(3, usuario.getRol());
            ps.setInt(4, usuario.getIdUsuario());

            int rows = ps.executeUpdate();

            if (rows == 0)
                System.out.println("Update de usuario con 0 registros actualizados.");

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

            ps = conn.prepareStatement("DELETE FROM usuarios WHERE idUsuario = ?");
            ps.setInt(1, id);

            int rows = ps.executeUpdate();

            if (rows == 0)
                System.out.println("Delete de usuario con 0 registros eliminados.");

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            closeDb(conn, ps, rs);
        }
    }
}