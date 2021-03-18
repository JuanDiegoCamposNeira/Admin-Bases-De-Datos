package Controllers;

import Entities.Tabla;
import Main.Main;
import acceso_datos.conexion;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
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
    @FXML TableView<Object> tableTablespace;
    @FXML TableColumn<Object, String> nombreTablespace;
    @FXML TableColumn<Object, String> espacioLibre;
    @FXML TableColumn<Object, String> espacioOcupado;

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
         * Query to get the space occupied by all tablespaces in the DB
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
                ArrayList<String> occupiedSpace = new ArrayList<String>();
                // Get the results
                occupiedSpace.add(rs.getString("TABLESPACE_NAME"));
                occupiedSpace.add(String.valueOf(rs.getInt("sum(BYTES)")));
                // Add occupied space to tablespace Array
                tablespace.add(occupiedSpace);
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
         * Query to get the free space by all tablespaces in the DB
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

        // Assign the type of value to get
        nombreTablespace.setCellValueFactory( new PropertyValueFactory<>("TablespaceName") );
        espacioLibre.setCellValueFactory( new PropertyValueFactory<>("TablespaceFreeSpace") );
        espacioOcupado.setCellValueFactory( new PropertyValueFactory<>("TablespaceOccupiedSpace") );
        // Clear items int the table
        tableTablespace.getItems().clear();
        // Add items to the table
        for (ArrayList<String> t : tablespace) {
            tableTablespace.getItems().add( new Tabla(t.get(0), t.get(1), t.get(2)) );
        }
    }

}