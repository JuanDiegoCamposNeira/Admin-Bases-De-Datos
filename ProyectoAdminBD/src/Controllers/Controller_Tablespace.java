package Controllers;

import Entities.Tabla;
import Main.Main;
import acceso_datos.conexion;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class Controller_Tablespace {
    // Constructor
    public Controller_Tablespace() {}

    /**
     * Get the elements in the view
     */
    @FXML TableView<Tabla> tableTablespace;
    @FXML TableColumn<Tabla, String> nombreTablespace;
    @FXML TableColumn<Tabla, String> espacioLibre;
    @FXML TableColumn<Tabla, String> espacioTotal;

    /**
     * Methods for the elements
     */
    public void onClick_Atras(ActionEvent actionEvent) throws IOException {
        // Create instance of main
        Main main = new Main();
        // Launch main activity
        main.changeScene("/views/Pantalla_Inicio.fxml");
    }

    // Method to initialize the view
    @FXML
    public void initialize() throws SQLException {
        // Define connections parameters
        Connection conex = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        // Create object to store results
        ArrayList<ArrayList<String>> tablespace = new ArrayList<ArrayList<String>>();

        /**
         * Query to get the total space of all tablespaces in the DB
         */
        try{
            // Make DB connection
            conex = conexion.getConnection();
            // Prepare SQL statement
            ps = conex.prepareStatement("SELECT TABLESPACE_NAME, sum(BYTES) FROM DBA_DATA_FILES group by TABLESPACE_NAME");
            // Execute query
            rs = ps.executeQuery();
            // Traverse results
            while(rs.next()){
                // Create ArrayList to store the name and the occupied space
                ArrayList<String> totalSpace = new ArrayList<String>();
                // Get the results
                totalSpace.add(rs.getString("TABLESPACE_NAME"));
                totalSpace.add(String.valueOf(rs.getInt("sum(BYTES)")));
                // Add total space to tablespace Array
                tablespace.add(totalSpace);
            }
        } catch (SQLException ex) {
            System.out.println(ex.toString());
        } finally {
            // Close DB connections
            if(rs != null) rs.close();
            if(ps != null) ps.close();
            if(conex != null) conex.close();
        }

        /**
         * Query to get the free space occupied by all tablespaces in the DB
         */
        try{
            // Make DB connection
            conex = conexion.getConnection();
            // Prepare SQL statement
            ps = conex.prepareStatement("SELECT TABLESPACE_NAME, sum(BYTES) FROM dba_free_space group by TABLESPACE_NAME");
            // Execute query
            rs = ps.executeQuery();
            // Traverse results
            while(rs.next()){
                // Get the table name
                String name = rs.getString("TABLESPACE_NAME");
                // Find the table in 'tablespace' ArrayList
                for (ArrayList<String> t : tablespace) {
                    if (name.equals(t.get(0))) {
                        t.add(String.valueOf(rs.getInt("sum(BYTES)")));
                    }
                }
            }
        } catch (SQLException ex) {
            System.out.println(ex.toString());
        } finally {
            // Close DB connections
            if(rs != null) rs.close();
            if(ps != null) ps.close();
            if(conex != null) conex.close();
        }
        for (ArrayList<String> t : tablespace) System.out.println(t.get(0) + " ... " + t.get(1) + " ... " + t.get(2));
        // Assign the type of value to get
        nombreTablespace.setCellValueFactory( new PropertyValueFactory<>("TablespaceName") );
        espacioLibre.setCellValueFactory( new PropertyValueFactory<>("TablespaceFreeSpace") );
        espacioTotal.setCellValueFactory( new PropertyValueFactory<>("TablespaceTotalSpace") );
        // Clear items int the table
        tableTablespace.getItems().clear();
        // Add items to the table
        for (ArrayList<String> t : tablespace) {
            String tablespaceName = t.get(0);
            String tablespaceTotalSpace = t.get(1);
            String tablespaceFreeSpace = t.get(2);
            Tabla tabla = new Tabla(tablespaceName, tablespaceFreeSpace, String.valueOf(Integer.parseInt(tablespaceTotalSpace) - Integer.parseInt(tablespaceFreeSpace)));
            tableTablespace.getItems().add(tabla);
        }
    }

}