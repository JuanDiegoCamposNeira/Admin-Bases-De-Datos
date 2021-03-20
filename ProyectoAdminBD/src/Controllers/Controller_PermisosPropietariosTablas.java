package Controllers;

import Entities.TablaPermisosPropietarios;
import Main.Main;
import acceso_datos.conexion;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class Controller_PermisosPropietariosTablas {

    // Permisos de la tabla
    @FXML private TableView<TablaPermisosPropietarios> tablaPermisosTablas;
    @FXML private TableColumn<TablaPermisosPropietarios, String> tablasNombreTabla;
    @FXML private TableColumn<TablaPermisosPropietarios, String> tablasPermisos;
    @FXML private TableColumn<TablaPermisosPropietarios, String> tablasPropietario;
    // Permisos de las columnas
    @FXML private TableView<TablaPermisosPropietarios> tablaPermisosColumnas;
    @FXML private TableColumn<TablaPermisosPropietarios, String> columnasNombreColumna;
    @FXML private TableColumn<TablaPermisosPropietarios, String> columnasPermisosColumnas;
    @FXML private TableColumn<TablaPermisosPropietarios, String> columnasPropietarioColumna;

    @FXML private ComboBox<String> seleccioneUsuario;
    
    private List<String> usernames = new ArrayList<>();
    private ArrayList<TablaPermisosPropietarios> permisosTablas;
    private ArrayList<TablaPermisosPropietarios> permisosColumnas;
    private String selectedUser = "";
    
    
    /**
     * Constructor
     */
    public Controller_PermisosPropietariosTablas() throws SQLException{
        Connection conex = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try{
            conex = conexion.getConnection();
            ps = conex.prepareStatement("SELECT USERNAME FROM all_users");
            rs = ps.executeQuery();
            while(rs.next()){
                usernames.add(rs.getString("USERNAME"));
            }
        }catch (SQLException ex){
            System.out.println(ex.toString());
        }finally{
            if(rs != null) rs.close();
            if(ps != null) ps.close();
            if(conex != null) conex.close();
        }
    }

    // Method to initialice view
    @FXML
    public void initialize() {
        // Add users to the combobox
        addUsers();
        // Asssign the types to the columns in 'tablaPermisosTablas'
        tablasNombreTabla.setCellValueFactory( new PropertyValueFactory<>("Nombre") );
        tablasPermisos.setCellValueFactory( new PropertyValueFactory<>("Permiso") );
        tablasPropietario.setCellValueFactory( new PropertyValueFactory<>("Propietario") );
        // Asssign the types to the columns in 'tablaPermisosColumnas'
        columnasNombreColumna.setCellValueFactory( new PropertyValueFactory<>("Nombre") );
        columnasPermisosColumnas.setCellValueFactory( new PropertyValueFactory<>("Permiso") );
        columnasPropietarioColumna.setCellValueFactory( new PropertyValueFactory<>("Propietario") );
    }

    @FXML
    private void onClick_Atras(ActionEvent event) throws IOException {
        // Create instance of main
        Main main = new Main();
        // Launch main activity
        main.changeScene("/views/Pantalla_Inicio.fxml");
    }

    public void addUsers() {
        seleccioneUsuario.getItems().clear();
        seleccioneUsuario.getItems().addAll(usernames);
    }

    @FXML
    private void selectUser(ActionEvent event) throws SQLException {
        // Create parameters to the connection
        selectedUser = seleccioneUsuario.getValue();
        Connection conex = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        /**
         * Query to get the permissions of the tables
         */
        try{
            conex = conexion.getConnection();
            ps = conex.prepareStatement("SELECT TABLE_NAME, PRIVILEGE, OWNER FROM DBA_TAB_PRIVS WHERE GRANTEE = ? ");
            ps.setString(1, selectedUser);
            // Execute query
            rs = ps.executeQuery();
            // Create List to store results
            permisosTablas = new ArrayList<TablaPermisosPropietarios>();
            // Traverse results
            while(rs.next()){
                // Get the information
                String nombreTabla = rs.getString("TABLE_NAME");
                String privilegio = rs.getString("PRIVILEGE");
                String propietario = rs.getString("OWNER");
                // Create class and save it in 'permisosTablas'
                permisosTablas.add( new TablaPermisosPropietarios(nombreTabla, privilegio, propietario) );
            }
        }catch (SQLException ex){
            ex.printStackTrace();
        }finally{
            if(rs != null) rs.close();
            if(ps != null) ps.close();
            if(conex != null)conex.close();
        }

        /**
         * Query to get the permissions on the columns
         */
        try{
            conex = conexion.getConnection();
            ps = conex.prepareStatement("SELECT COLUMN_NAME, PRIVILEGE, OWNER FROM DBA_COL_PRIVS WHERE GRANTEE = ? ");
            ps.setString(1, selectedUser);
            // Execute query
            rs = ps.executeQuery();
            // Create List to store results
            permisosColumnas = new ArrayList<TablaPermisosPropietarios>();
            // Traverse results
            while(rs.next()) {
                // Get the information
                String nombreColumnas = rs.getString("COLUMN_NAME");
                String privilegio = rs.getString("PRIVILEGE");
                String propietario = rs.getString("OWNER");
                // Create class and save it in 'permisosColumnas'
                permisosColumnas.add(new TablaPermisosPropietarios(nombreColumnas, privilegio, propietario));
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            if(rs != null) rs.close();
            if(ps != null) ps.close();
            if(conex != null)conex.close();
        }

        // Clear previous elements in the view
        tablaPermisosTablas.getItems().clear();
        tablaPermisosColumnas.getItems().clear();
        System.out.println("Cleared");

        // Add results of the queries to the view
        permisosTablas.forEach( elemento -> tablaPermisosTablas.getItems().add(elemento) );
        permisosColumnas.forEach( elemento -> tablaPermisosColumnas.getItems().add(elemento) );
    }
}

 
