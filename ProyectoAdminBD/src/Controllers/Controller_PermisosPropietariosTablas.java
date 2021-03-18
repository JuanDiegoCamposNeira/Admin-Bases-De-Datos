package Controllers;

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

public class Controller_PermisosPropietariosTablas {

    @FXML private Label label;
    @FXML private Button atras;
    @FXML private TableView<?> TablaPPT;
    @FXML private TableColumn<?, ?> NombreTabla;
    @FXML private TableColumn<?, ?> ColumnaTabla;
    @FXML private TableColumn<?, ?> PermisosTabla;
    @FXML private TableColumn<?, ?> PropietarioTabla;
    @FXML private ComboBox<String> seleccioneUsuario;
    
    private List<String> usernames = new ArrayList<>();
    private List<String> tableNames = new ArrayList<>();
    private List<String> columnNames = new ArrayList<>();
    private List<String> permisoName = new ArrayList<>();
    private List<String> propietarioName = new ArrayList<>();
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
        addUsers();
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
        selectedUser = seleccioneUsuario.getValue();
        Connection conex = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try{
            conex = conexion.getConnection();
            ps = conex.prepareStatement("SELECT TABLE_NAME, PRIVILEGE, OWNER FROM USER_TAB_PRIVS WHERE GRANTEE = ?");
            // ps = conex.prepareStatement("SELECT table_name FROM user_tab_privs");
            ps.setString(1, selectedUser);
            rs = ps.executeQuery();
            int res = 0;
            while(rs.next()){
                res++;
                tableNames.add(rs.getString("TABLE_NAME"));
            }
            System.out.println(res + " results");
        }catch (SQLException ex){
            ex.printStackTrace();
        }finally{
            if(rs != null) rs.close();
            if(ps != null) ps.close();
            if(conex != null)conex.close();
        }
    }
}

 
