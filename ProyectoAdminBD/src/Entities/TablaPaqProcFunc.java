package Entities;

import javafx.beans.property.SimpleStringProperty;

public class TablaPaqProcFunc {
    private SimpleStringProperty nombre;
    private SimpleStringProperty estado;

    public TablaPaqProcFunc(String nombre, String estado) {
        this.nombre = new SimpleStringProperty(nombre);
        this.estado = new SimpleStringProperty(estado);
    }

    public String getNombre() {
        return nombre.get();
    }

    public String getEstado() {
        return estado.get();
    }

}
