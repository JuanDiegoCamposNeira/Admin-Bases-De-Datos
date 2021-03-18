package Entities;

import javafx.beans.property.SimpleStringProperty;
import javafx.scene.control.TableColumn;

public class Tabla {
    /**
     * Attributes
     */
    private SimpleStringProperty index;
    private SimpleStringProperty field;

    /**
     * Constructors
     */
    // Constructor for index and associated field
    public Tabla(String index, String field) {
        this.index = new SimpleStringProperty(index);
        this.field = new SimpleStringProperty(field);
    }

    /**
     * Getters and setters
     */
    public String getIndex() { return index.get(); }
    public String getField() { return field.get(); }
}
