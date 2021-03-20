package Entities;

import javafx.beans.property.SimpleStringProperty;

public class TablaPermisosPropietarios {
    // Attributes
    private SimpleStringProperty nombre;
    private SimpleStringProperty permiso;
    private SimpleStringProperty propietario;

    // Constructors
    public TablaPermisosPropietarios(String nombre, String permiso, String propietario) {
        this.nombre = new SimpleStringProperty(nombre);
        this.permiso = new SimpleStringProperty(permiso);
        this.propietario = new SimpleStringProperty(propietario);
    }

    // Methods
    public String getNombre() {
        return nombre.get();
    }

    public String getPermiso() {
        return permiso.get();
    }

    public String getPropietario() {
        return propietario.get();
    }
}
