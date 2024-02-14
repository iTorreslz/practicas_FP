package org.iesbelen.dao;

import org.iesbelen.model.Fabricante;
import java.util.List;
import java.util.Optional;

public interface FabricanteDAO {
    public void create(Fabricante fabricante);
    public List<Fabricante> getAll();
    public Optional<Fabricante> find(int id);
    public void update(Fabricante fabricante);
    public void delete(int id);
    public Optional<Integer> getCountProductos(int id);
}
