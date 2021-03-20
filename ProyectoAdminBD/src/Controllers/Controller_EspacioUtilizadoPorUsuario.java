package Controllers;

import Entities.TablaEspacioUsuarios;
import Entities.TablaPermisosPropietarios;
import Main.Main;
import acceso_datos.conexion;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.awt.image.AreaAveragingScaleFilter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class Controller_EspacioUtilizadoPorUsuario {


    /**
     * Get the elements in the view
     */
    @FXML TableView<TablaEspacioUsuarios> tablaEspacioUsuarios = new TableView<TablaEspacioUsuarios>();
    @FXML TableColumn<TablaEspacioUsuarios, String> nombreUsuario;
    @FXML TableColumn<TablaEspacioUsuarios, String> espacioUtilizado;

    private ArrayList<TablaEspacioUsuarios> listaUsuarios;

    /**
     * Methods for the elements
     */
    // Constructor
    public Controller_EspacioUtilizadoPorUsuario() {}

    @FXML
    private void initialize() throws SQLException {
        // Set column types
        nombreUsuario.setCellValueFactory( new PropertyValueFactory<>("Nombre") );
        espacioUtilizado.setCellValueFactory( new PropertyValueFactory<>("EspacioUtilizado") );

        // Create parameters to the connection
        Connection conex = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            conex = conexion.getConnection();
            ps = conex.prepareStatement("SELECT OWNER, SUM(BYTES) FROM DBA_EXTENTS GROUP BY OWNER");
            // Execute query
            rs = ps.executeQuery();
            // Create List to store results
            listaUsuarios = new ArrayList<TablaEspacioUsuarios>();
            // Traverse results
            while(rs.next()){
                // Get the information
                String nombreUsr = rs.getString("OWNER");
                String espacioUsr = rs.getString("sum(BYTES)");
                // Create class and save it in 'permisosTablas'
                listaUsuarios.add( new TablaEspacioUsuarios(nombreUsr, espacioUsr) );
            }
        } catch (SQLException ex){
            ex.printStackTrace();
        } finally{
            if(rs != null) rs.close();
            if(ps != null) ps.close();
            if(conex != null)conex.close();
        }

        // Clear previous data
        tablaEspacioUsuarios.getItems().clear();
        // Add data to table view
        listaUsuarios.forEach( elemento -> tablaEspacioUsuarios.getItems().add(elemento) );
    }

    // Funtion to go back to the main view
    public void onClick_Atras(ActionEvent actionEvent) throws IOException {
        // Create instance of main
        Main main = new Main();
        // Launch main activity
        main.changeScene("/views/Pantalla_Inicio.fxml");
    }

}
