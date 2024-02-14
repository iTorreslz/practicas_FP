package org.iesbelen.dao;

import org.iesbelen.model.Departamento;

import javax.swing.plaf.nimbus.State;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class DepartamentoDAOImpl extends AbstractDAOImpl implements DepartamentoDAO {
    @Override
    public void create(Departamento departamento) {


        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rsGenKeys = null;

        try {
            conn = connectDB();

            ps = conn.prepareStatement("INSERT INTO departamento (nombre,presupuesto,gastos) VALUES (?, ?, ?)", Statement.RETURN_GENERATED_KEYS);

            ps.setString(1, departamento.getNombre());
            ps.setDouble(2, departamento.getPresupuesto());
            ps.setDouble(3, departamento.getPresupuesto());

            int rows = ps.executeUpdate();
            if (rows == 0)
                System.out.println("INSERT de departamento con 0 filas insertadas.");

            rsGenKeys = ps.getGeneratedKeys();
            if (rsGenKeys.next())
                departamento.setId(rsGenKeys.getInt(1));

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            closeDb(conn, ps, rsGenKeys);
        }
    }

    @Override
    public List<Departamento> getAll() {
        Connection conn = null;
        Statement s = null;
        ResultSet rs = null;

        List<Departamento> listaDep = new ArrayList<>();
        try {
            conn = connectDB();
            s = conn.createStatement();
            rs = s.executeQuery("SELECT * FROM departamento");
            while (rs.next()) {
                Departamento dep = new Departamento();
                dep.setId(rs.getInt(1));
                dep.setNombre(rs.getString(2));
                dep.setPresupuesto(rs.getDouble(3));
                dep.setGastos(rs.getDouble(4));
                listaDep.add(dep);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            closeDb(conn, s, rs);
        }
        return listaDep;
    }

    @Override
    public Optional<Departamento> find(int id) {

        return null;
    }

    @Override
    public void update(Departamento departamento) {

    }

    @Override
    public void delete(int id) {

    }
}
