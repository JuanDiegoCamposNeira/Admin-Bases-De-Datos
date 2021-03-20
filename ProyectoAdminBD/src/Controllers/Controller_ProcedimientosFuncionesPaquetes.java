package Controllers;

import Entities.TablaPaqProcFunc;
import Entities.TablaPermisosPropietarios;
import Main.Main;
import acceso_datos.conexion;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Controller_ProcedimientosFuncionesPaquetes {
    /**
     * Get the elements in the view
     */
    @FXML TableView<TablaPaqProcFunc> tablaPaquetes;
    @FXML TableColumn<TablaPaqProcFunc, String> estadoPaquetes;
    @FXML TableColumn<TablaPaqProcFunc, String> paquetes;

    @FXML TableView<TablaPaqProcFunc> tablaFunciones;
    @FXML TableColumn<TablaPaqProcFunc, String> estadoFunciones;
    @FXML TableColumn<TablaPaqProcFunc, String> funciones;

    @FXML TableView<TablaPaqProcFunc> tablaProcedimientos;
    @FXML TableColumn<TablaPaqProcFunc, String> estadoProcedimientos;
    @FXML TableColumn<TablaPaqProcFunc, String> procedimientos;

    @FXML private ComboBox<String> seleccioneUsuario;

    private ArrayList<String> usernames = new ArrayList<>();
    private String selectedUser = "";

    private ArrayList<TablaPaqProcFunc> listaPaquetes;
    private ArrayList<TablaPaqProcFunc> listaFunciones;
    private ArrayList<TablaPaqProcFunc> listaProcedimientos;

    // Constructor
    public Controller_ProcedimientosFuncionesPaquetes() throws SQLException {
        Connection conex = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            conex = conexion.getConnection();
            ps = conex.prepareStatement("SELECT USERNAME FROM all_users");
            rs = ps.executeQuery();
            while(rs.next()){
                usernames.add(rs.getString("USERNAME"));
            }
        } catch (SQLException ex) {
            System.out.println(ex.toString());
        } finally {
            if(rs != null) rs.close();
            if(ps != null) ps.close();
            if(conex != null) conex.close();
        }
    }

    /**
     * Methods for the elements
     */
    public void onClick_Atras(ActionEvent actionEvent) throws IOException {
        // Create instance of main
        Main main = new Main();
        // Launch main activity
        main.changeScene("/views/Pantalla_Inicio.fxml");
    }

    @FXML
    public void initialize() {
        // Add users to the combobox
        addUsers();
        // Set type to 'Paquetes' table
        paquetes.setCellValueFactory( new PropertyValueFactory<>("Nombre") );
        estadoPaquetes.setCellValueFactory( new PropertyValueFactory<>("Estado") );
        // Set types to 'Funciones' table
        funciones.setCellValueFactory( new PropertyValueFactory<>("Nombre") );
        estadoFunciones.setCellValueFactory( new PropertyValueFactory<>("Estado") );
        // Set types to 'Procedimientos' table
        procedimientos.setCellValueFactory( new PropertyValueFactory<>("Nombre") );
        estadoProcedimientos.setCellValueFactory( new PropertyValueFactory<>("Estado") );
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
         * Query to get the packets of the user
         */
        try{
            // Make connection
            conex = conexion.getConnection();
            ps = conex.prepareStatement("SELECT OBJECT_NAME, STATUS FROM DBA_OBJECTS WHERE OWNER = ? AND OBJECT_TYPE = 'PACKAGE'");
            ps.setString(1, selectedUser);
            // Execute query
            rs = ps.executeQuery();
            // Create List to store results
            listaPaquetes = new ArrayList<TablaPaqProcFunc>();
            // Traverse results
            while(rs.next()){
                // Get the information
                String nombrePaquete = rs.getString("OBJECT_NAME");
                String status = rs.getString("STATUS");
                // Create class and save it in 'listaPaquetes'
                listaPaquetes.add( new TablaPaqProcFunc(nombrePaquete, status) );
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            if(rs != null) rs.close();
            if(ps != null) ps.close();
            if(conex != null)conex.close();
        }

        /**
         * Query to get the functions of the user
         */
        try{
            // Make connection
            conex = conexion.getConnection();
            ps = conex.prepareStatement("SELECT OBJECT_NAME, STATUS FROM DBA_OBJECTS WHERE OWNER = ? AND OBJECT_TYPE = 'FUNCTION'");
            ps.setString(1, selectedUser);
            // Execute query
            rs = ps.executeQuery();
            // Create List to store results
            listaFunciones = new ArrayList<TablaPaqProcFunc>();
            // Traverse results
            while(rs.next()){
                // Get the information
                String nombreFuncion = rs.getString("OBJECT_NAME");
                String status = rs.getString("STATUS");
                // Create class and save it in 'listaFunciones'
                listaFunciones.add( new TablaPaqProcFunc(nombreFuncion, status) );
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            if(rs != null) rs.close();
            if(ps != null) ps.close();
            if(conex != null)conex.close();
        }

        /**
         * Query to get the procedures of the user
         */
        try{
            // Make connection
            conex = conexion.getConnection();
            ps = conex.prepareStatement("SELECT OBJECT_NAME, STATUS FROM DBA_OBJECTS WHERE OWNER = ? AND OBJECT_TYPE = 'PROCEDURE'");
            ps.setString(1, selectedUser);
            // Execute query
            rs = ps.executeQuery();
            // Create List to store results
            listaProcedimientos = new ArrayList<TablaPaqProcFunc>();
            // Traverse results
            while(rs.next()){
                // Get the information
                String nombreProcedimiento = rs.getString("OBJECT_NAME");
                String status = rs.getString("STATUS");
                // Create class and save it in 'listaProcedimientos'
                listaProcedimientos.add( new TablaPaqProcFunc(nombreProcedimiento, status) );
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            if(rs != null) rs.close();
            if(ps != null) ps.close();
            if(conex != null)conex.close();
        }

        // Clear previous elements in the view
        tablaPaquetes.getItems().clear();
        tablaFunciones.getItems().clear();
        tablaProcedimientos.getItems().clear();

        // Add results of the queries to the view
        listaPaquetes.forEach( elemento -> tablaPaquetes.getItems().add(elemento) );
        listaFunciones.forEach( elemento -> tablaFunciones.getItems().add(elemento) );
        listaProcedimientos.forEach( elemento -> tablaProcedimientos.getItems().add(elemento) );
    }

}