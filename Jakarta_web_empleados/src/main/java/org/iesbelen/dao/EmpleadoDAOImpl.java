package org.iesbelen.dao;

import org.iesbelen.model.Departamento;
import org.iesbelen.model.Empleado;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class EmpleadoDAOImpl extends AbstractDAOImpl implements EmpleadoDAO {

    @Override
    public void create(Empleado empleado) {

    }

    @Override
    public List<Empleado> getAll() {
        Connection conn = null;
        Statement s = null;
        ResultSet rs = null;

        List<Empleado> listaEmpl = new ArrayList<>();
        try {
            conn = connectDB();
            s = conn.createStatement();
            rs = s.executeQuery("SELECT * FROM empleado");
            while (rs.next()) {
                Empleado empl = new Empleado();
                empl.setId(rs.getInt(1));
                empl.setNif(rs.getString(2));
                empl.setNombre(rs.getString(3));
                empl.setApellido1(rs.getString(4));
                empl.setApellido2(rs.getString(5));
                listaEmpl.add(empl);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            closeDb(conn, s, rs);
        }
        return listaEmpl;
    }

    @Override
    public List<Empleado> getAllFilterName(String filter) {

        return null;
    }

    @Override
    public Optional<Empleado> find(int id) {

        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            conn = connectDB();

            ps = conn.prepareStatement("SELECT * FROM empleado WHERE id = ?");

            int idx = 1;
            ps.setInt(idx, id);

            rs = ps.executeQuery();

            if (rs.next()) {
                Empleado empl = new Empleado();
                idx = 1;
                empl.setId(rs.getInt(idx++));
                empl.setNombre(rs.getString(idx));

                return Optional.of(empl);
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

    @Override
    public void update(Empleado empleado) {

        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            conn = connectDB();

            ps = conn.prepareStatement("UPDATE empleado SET nombre = ?, apellido1 = ?, apellido2 = ?  WHERE idEmpleado = ?");

            ps.setString(1, empleado.getNombre());
            ps.setString(2, empleado.getApellido1());
            ps.setString(3, empleado.getApellido1());

            int rows = ps.executeUpdate();

            if (rows == 0)
                System.out.println("Update de empleado con 0 registros actualizados.");

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            closeDb(conn, ps, rs);
        }
    }

    @Override
    public void delete(int id) {

    }
}
