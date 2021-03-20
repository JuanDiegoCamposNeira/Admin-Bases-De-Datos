package Controllers;

import Entities.Tabla;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import Main.Main;
import acceso_datos.conexion;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

public class Controller_TablasUsuario {

    @FXML private AnchorPane tablasDeUsuario;
    @FXML private ComboBox<String> seleccionarTabla;
    @FXML private Pane restriccionesTabla;
    @FXML private VBox restriccionesLista;
    @FXML private Pane comentariosTabla;
    @FXML private VBox comentariosLista;
    @FXML private ComboBox<String> seleccionarColumna;
    @FXML private TableView<Tabla> tablaIndices;
    @FXML private TableColumn<Tabla, String> columnIndex;
    @FXML private TableColumn<Tabla, String> columnField;
    @FXML private Pane tipoDatoAtributos;
    @FXML private VBox tipoDatoAtributosLista;
    @FXML private Pane comentariosColumna;
    @FXML private VBox comentariosColumnaLista;
    @FXML private ComboBox<String> seleccionarUsuario;
    @FXML private Button atras;
    
    private List<String> usernames = new ArrayList<>();
    private List<String> tableNames = new ArrayList<>();
    private List<String> columnNames = new ArrayList<>();
    private String selectedUser = "";
    private String selectedTable = "";
    private String selectedColumn = "";
    
    // Constructor
    public Controller_TablasUsuario() throws SQLException {
        Connection conex = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try{
            conex = conexion.getConnection();
            ps = conex.prepareStatement("SELECT USERNAME FROM DBA_USERS");
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

    @FXML
    public void initialize() {
        addUsers();
    }

    /**
     * Methods for the elements
     */
    @FXML
    public void onClick_Atras(ActionEvent actionEvent) throws IOException {
        // Create instance of main
        Main main = new Main();
        // Launch main activity
        main.changeScene("../Views/Pantalla_Inicio.fxml");
    }

    public void addUsers(){
        if(seleccionarUsuario.getItems().size() > 0){
            seleccionarUsuario.getItems().clear();
        }
        seleccionarUsuario.getItems().addAll(usernames);
    }

    public void addTables(){
        if(seleccionarTabla.getItems().size() > 0){
            seleccionarTabla.getItems().clear();
        }
        seleccionarTabla.getItems().addAll(tableNames);
    }
    
    public void addColumns(){
        if(seleccionarColumna.getItems().size() > 0){
            seleccionarColumna.getItems().clear();
        }
        seleccionarColumna.getItems().addAll(columnNames);
    }

    @FXML
    private void selectUser(ActionEvent event) throws SQLException {
        System.out.println("Method triggered");
        selectedUser = seleccionarUsuario.getValue();
        Connection conex = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        tableNames.clear();
        try{
            conex = conexion.getConnection();
            ps = conex.prepareStatement("SELECT table_name FROM DBA_TABLES WHERE owner = ?");
            ps.setString(1, selectedUser);
            rs = ps.executeQuery();
            while(rs.next()){
                tableNames.add(rs.getString("TABLE_NAME"));
            }
            addTables();
        }catch (SQLException ex){
            System.out.println(ex.toString());
        }finally{
            if(rs != null) rs.close();
            if(ps != null) ps.close();
            if(conex != null)conex.close();
        }
    }

    @FXML
    private void selectTable(ActionEvent event) throws SQLException {
        selectedTable = seleccionarTabla.getValue();
        Connection conex = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        columnNames.clear();
        try{
            conex = conexion.getConnection();
            ps = conex.prepareStatement("SELECT COLUMN_NAME FROM DBA_TAB_COLUMNS WHERE owner = ? AND TABLE_NAME = ?");
            ps.setString(1, selectedUser);
            ps.setString(2, selectedTable);
            rs = ps.executeQuery();
            while(rs.next()){
                columnNames.add(rs.getString("COLUMN_NAME"));
            }
            addColumns();
            /**
             * Query to get the constraints of the table
             */
            ps = conex.prepareStatement("SELECT SEARCH_CONDITION FROM DBA_CONSTRAINTS WHERE OWNER = ? AND TABLE_NAME = ? AND SEARCH_CONDITION IS NOT NULL");
            ps.setString(1, selectedUser);
            ps.setString(2, selectedTable);
            rs = ps.executeQuery();
            ListView restricciones = new ListView();
            // Clear the items if exists
            if (restriccionesLista.getChildren().size() > 0) {
                restriccionesLista.getChildren().clear();
            }
            // Traverse the results
            while(rs.next()){
                restricciones.getItems().add(rs.getString("SEARCH_CONDITION"));
            }
            // Assign the new items to the list
            restriccionesLista.getChildren().add(restricciones);
            /**
             * Query to get the comments of the table
             */
            ps = conex.prepareStatement("SELECT COMMENTS FROM dba_tab_comments WHERE OWNER= ? AND TABLE_NAME= ? AND COMMENTS IS NOT NULL");
            ps.setString(1, selectedUser);
            ps.setString(2, selectedTable);
            rs = ps.executeQuery();
            // List view to save the results of the query
            ListView tableComments = new ListView();
            // Clear the items if exists
            if (comentariosLista.getChildren().size() > 0) {
                comentariosLista.getChildren().clear();
            }
            // Traverse results object
            while (rs.next()) {
                tableComments.getItems().add(rs.getString("COMMENTS"));
            }
            // Assign the nre items to the list
            comentariosLista.getChildren().add(tableComments);

        }catch (SQLException ex){
            System.out.println(ex.toString());
        }finally{
            // Close connections
            if(rs != null) rs.close();
            if(ps != null) ps.close();
            if(conex != null)conex.close();
        }

        /**
         * Query to get the index of the table selected
         */
        // Set the type for column 'Index'
        columnIndex.setCellValueFactory(new PropertyValueFactory<>("Index"));
        // Set the type for column 'Field'
        columnField.setCellValueFactory(new PropertyValueFactory<>("Field"));
        try {
            // Stablish connection
            conex = conexion.getConnection();
            // Prepare sql query
            ps = conex.prepareStatement("SELECT INDEX_NAME, TABLE_NAME, COLUMN_NAME  FROM DBA_IND_COLUMNS WHERE INDEX_OWNER = ? AND TABLE_NAME = ?");
            // Set query arguments
            ps.setString(1, selectedUser);
            ps.setString(2, selectedTable);
            // Execute query
            rs = ps.executeQuery();
            // Create Array to store results
            ArrayList<Tabla> indices = new ArrayList<Tabla>();
            // Traverse query results
            while (rs.next()) {
                indices.add(new Tabla(rs.getString("INDEX_NAME"), rs.getString("COLUMN_NAME")));
            }
            // Clear previous items in table
            tablaIndices.getItems().clear();
            // Add query results in the table
            for (Tabla row : indices) {
                tablaIndices.getItems().add(row);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            // Close connections
            if(rs != null) rs.close();
            if(ps != null) ps.close();
            if(conex != null)conex.close();
        }
    }

    @FXML
    private void selectColumn(ActionEvent event) throws SQLException {
        // Stablish connection
        Connection conex = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        /**
         * Query to get the data type of the selected column
         */
        // Get the selected column
        selectedColumn = seleccionarColumna.getValue();
        try {
            /**
             * Query to get  the type of the attributes of the table
             */
            conex = conexion.getConnection();
            ps = conex.prepareStatement("SELECT DATA_TYPE FROM dba_tab_columns WHERE OWNER = ? AND TABLE_NAME = ? AND COLUMN_NAME = ?");
            ps.setString(1, selectedUser);
            ps.setString(2, selectedTable);
            ps.setString(3, selectedColumn);
            rs = ps.executeQuery();
            // Object to store results
            ListView tipoDato = new ListView();
            // Clear the items if exists
            if (tipoDatoAtributosLista.getChildren().size() > 0) {
                tipoDatoAtributosLista.getChildren().clear();
            }
            // Traverse results object
            while (rs.next()) {
                tipoDato.getItems().add(rs.getString("DATA_TYPE"));
            }
            // Assign items to the list
            tipoDatoAtributosLista.getChildren().add(tipoDato);
        } catch (SQLException ex) {
            System.out.println(ex.toString());
        } finally {
            // Close connections
            if (rs != null) rs.close();
            if (ps != null) ps.close();
            if (conex != null) conex.close();
        }

        /**
         * Query to get the comments of the selected column
         */
        try {
            // Get the connection
            conex = conexion.getConnection();
            // Prepare SQL statement
            ps = conex.prepareStatement("SELECT COMMENTS FROM dba_col_comments WHERE OWNER = ? AND TABLE_NAME = ? AND COLUMN_NAME = ? AND COMMENTS IS NOT NULL");
            // Add parameters to the query
            ps.setString(1, selectedUser);
            ps.setString(2, selectedTable);
            ps.setString(3, selectedColumn);
            // Execute query
            rs = ps.executeQuery();
            // Create variable to store query results
            ListView comentariosColumna = new ListView();
            // Traverse results object
            while (rs.next()) comentariosColumna.getItems().add(rs.getString("COMMENTS"));
            // Clear items in VBox
            comentariosColumnaLista.getChildren().clear();
            // Add the new comments
            comentariosColumnaLista.getChildren().add(comentariosColumna);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            // Close connections
            if (rs != null) rs.close();
            if (ps != null) ps.close();
            if (conex != null) conex.close();
        }
    }
}
