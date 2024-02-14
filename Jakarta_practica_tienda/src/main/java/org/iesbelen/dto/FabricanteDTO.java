package org.iesbelen.dto;

import org.iesbelen.model.Fabricante;

public class FabricanteDTO extends Fabricante {
    private int numeroProductos;

    public FabricanteDTO(Fabricante fabricante) {
        this.setIdFabricante(fabricante.getIdFabricante());
        this.setNombre(fabricante.getNombre());
    }

    public int getNumeroProductos() {
        return numeroProductos;
    }

    public void setNumeroProductos(int numeroProductos) {
        this.numeroProductos = numeroProductos;
    }
}
