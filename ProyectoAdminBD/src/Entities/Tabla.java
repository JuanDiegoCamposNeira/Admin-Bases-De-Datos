package Entities;

import javafx.beans.property.SimpleStringProperty;

public class Tabla {
    /**
     * Attributes
     */
    // Attributes for view 'TablasUsuario'
    private SimpleStringProperty index;
    private SimpleStringProperty field;
    // Attributes for view 'Tablespace'
    private SimpleStringProperty tablespaceName;
    private SimpleStringProperty tablespaceTotalSpace;
    private SimpleStringProperty tablespaceFreeSpace;

    /**
     * Constructors
     */
    // Constructor for view 'TablasUsuario'
    public Tabla(String index, String field) {
        this.index = new SimpleStringProperty(index);
        this.field = new SimpleStringProperty(field);
    }
    // Constructor for view 'Tablespace'
    public Tabla(String name, String totalSpace, String freeSpace) {
        this.tablespaceName = new SimpleStringProperty(name);
        this.tablespaceTotalSpace = new SimpleStringProperty(totalSpace);
        this.tablespaceFreeSpace = new SimpleStringProperty(freeSpace);
    }

    /**
     * Getters and setters
     */
    // Getters for view 'TablasUsuario'
    public String getIndex() { return index.get(); }
    public String getField() { return field.get(); }
    // Getters for view 'Tablespace'
    public String getTablespaceName() { return tablespaceName.get(); }
    public String getTablespaceTotalSpace() { return tablespaceTotalSpace.get(); }
    public String getTablespaceFreeSpace() { return tablespaceFreeSpace.get(); }
}
