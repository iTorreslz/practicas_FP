package org.iesbelen.model;

import java.util.Objects;

public class Fabricante {
    private int idFabricante;
    private String nombre;

    public int getIdFabricante() {
        return idFabricante;
    }

    public void setIdFabricante(int idFabricante) {
        this.idFabricante = idFabricante;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Fabricante that = (Fabricante) o;
        return idFabricante == that.idFabricante;
    }

    @Override
    public int hashCode() {
        return Objects.hash(idFabricante);
    }
}
