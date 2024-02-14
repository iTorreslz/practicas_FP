package org.iesbelen.dao;

import org.iesbelen.model.Empleado;

import java.util.List;
import java.util.Optional;

public interface EmpleadoDAO {
		
	public void create(Empleado empleado);
	public List<Empleado> getAll();
	public List<Empleado> getAllFilterName(String filter);
	public Optional<Empleado>  find(int id);
	public void update(Empleado empleado);
	public void delete(int id);

}