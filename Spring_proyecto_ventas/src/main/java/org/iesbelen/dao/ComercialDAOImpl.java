package org.iesbelen.dao;

import java.sql.PreparedStatement;
import java.util.List;
import java.util.Optional;

import org.iesbelen.modelo.Cliente;
import org.iesbelen.modelo.Comercial;
import org.iesbelen.service.ComercialService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

//Anotación lombok para logging (traza) de la aplicación
@Slf4j
@Repository
//Utilizo lombok para generar el constructor
@AllArgsConstructor
public class ComercialDAOImpl implements ComercialDAO {

    //JdbcTemplate se inyecta por el constructor de la clase automáticamente
    @Autowired
    private JdbcTemplate jdbcTemplate;

    /**
     * Inserta en base de datos el nuevo Comercial, actualizando el id en el bean Comercial.
     */
    @Override
    public synchronized void create(Comercial comercial) {

        String sqlInsert = "INSERT INTO comercial (nombre, apellido1, apellido2, comisión) VALUES  (?,?,?,?)";

        KeyHolder keyHolder = new GeneratedKeyHolder();
        //Con recuperación de id generado
        int rows = jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sqlInsert, new String[]{"id"});
            ps.setString(1, comercial.getNombre());
            ps.setString(2, comercial.getApellido1());
            ps.setString(3, comercial.getApellido2());
            ps.setFloat(4, comercial.getComision());
            return ps;
        }, keyHolder);

        comercial.setId(keyHolder.getKey().intValue());
        log.info("Insertados {} registros.", rows);
    }

    /**
     * Devuelve lista con todos los Comerciales.
     */
    @Override
    public List<Comercial> getAll() {

        List<Comercial> listComercial = jdbcTemplate.query(
                "SELECT * FROM comercial",
                (rs, rowNum) -> new Comercial(rs.getInt("id"),
                        rs.getString("nombre"),
                        rs.getString("apellido1"),
                        rs.getString("apellido2"),
                        rs.getFloat("comisión"))

        );

        log.info("Devueltos {} registros.", listComercial.size());

        return listComercial;
    }

    /**
     * Devuelve Optional del total de Pedidos del Comercial con el ID dado.
     */
    @Override
    public Optional<Integer> getTotalPedidos(int id) {

        Integer contador = jdbcTemplate.
                queryForObject("SELECT COUNT(*) FROM pedido WHERE id_comercial = ?"
                        , new Object[]{id}
                        , Integer.class
                );

        if (contador != null) {
            return Optional.of(contador);
        } else {
            log.info("ID de comercial no existe.");
            return Optional.empty();
        }
    }

    /**
     * Devuelve lista de clientes propios.
     */
    @Override
    public Optional<List<Cliente>> getClientes(int id) {

        String consulta = "SELECT * FROM cliente WHERE id IN" +
                "(SELECT id_cliente FROM pedido WHERE id_comercial = " + id + ")";

        List<Cliente> listaClientes = jdbcTemplate.query(consulta,
                (rs, rowNum) -> new Cliente(rs.getLong("id"),
                        rs.getString("nombre"),
                        rs.getString("apellido1"),
                        rs.getString("apellido2"),
                        rs.getString("ciudad"),
                        rs.getInt("categoría")
                )
        );

        log.info("Devueltos {} registros.", listaClientes.size());

        if (!listaClientes.isEmpty()) {
            return Optional.of(listaClientes);
        } else {
            log.info("ID de comercial no existe.");
            return Optional.empty();
        }
    }

    /**
     * Devuelve Optional de Comerciales con el ID dado.
     */
    @Override
    public Optional<Comercial> find(int id) {

        Comercial com = jdbcTemplate
                .queryForObject("SELECT * FROM comercial WHERE id = ?"
                        , (rs, rowNum) -> new Comercial(rs.getInt("id"),
                                rs.getString("nombre"),
                                rs.getString("apellido1"),
                                rs.getString("apellido2"),
                                rs.getFloat("comisión"))
                        , id
                );

        if (com != null) {
            return Optional.of(com);
        } else {
            log.info("Comercial no encontrado.");
            return Optional.empty();
        }
    }

    /**
     * Actualiza Comercial con campos del bean Comercial según ID del mismo.
     */
    @Override
    public void update(Comercial comercial) {

        int rows = jdbcTemplate.update("""
                        UPDATE comercial SET 
                        				nombre = ?, 
                        				apellido1 = ?, 
                        				apellido2 = ?,
                        				comisión = ?  
                        		WHERE id = ?
                        """, comercial.getNombre()
                , comercial.getApellido1()
                , comercial.getApellido2()
                , comercial.getComision()
                , comercial.getId());

        log.info("Update de Comercial con {} registros actualizados.", rows);

    }

    @Override
    public void delete(long id) {

        int rows = jdbcTemplate.update("DELETE FROM comercial WHERE id = ?", id);

        log.info("Delete de Comercial con {} registros eliminados.", rows);

    }
}