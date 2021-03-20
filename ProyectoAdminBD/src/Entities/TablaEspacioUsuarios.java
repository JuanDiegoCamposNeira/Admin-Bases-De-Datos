package Entities;

import javafx.beans.property.SimpleStringProperty;

public class TablaEspacioUsuarios {
    private SimpleStringProperty nombre;
    private SimpleStringProperty espacioUtilizado;

    public TablaEspacioUsuarios(String nombre, String espacioUtilizado) {
        this.nombre = new SimpleStringProperty(nombre);
        this.espacioUtilizado = new SimpleStringProperty(espacioUtilizado);
    }

    public String getNombre() {
        return nombre.get();
    }

    public String getEspacioUtilizado() {
        return espacioUtilizado.get();
    }

}
