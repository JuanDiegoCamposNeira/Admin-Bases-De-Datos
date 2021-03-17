package Controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import Main.Main;

import java.io.IOException;

public class Controller_Inicio {

    @FXML
    private Button tablasUsuario;
    // Constructor
    public Controller_Inicio() {}

    @FXML
    private Button tablasPermisosDeUsuario;
    @FXML
    private Button jobs;
    @FXML
    private Button tablespace;
    @FXML
    private Button paquetesFuncionesProcedimientos;
    @FXML
    private Button espacioUtilizado;

    /****************************************
     *  Methods for the events of the buttons
     ***************************************/
    // Tablas de Usuario
    @FXML
    public void onClick_TablasUsuario(ActionEvent event) throws IOException {
        Main m = new Main();
        m.changeScene("/views/Pantalla_TablasUsuario.fxml");
    }
    // Tablas y permisos de usuario
    @FXML
    public void onClick_TablasPermisosDeUsuario(ActionEvent event) throws IOException {
        Main m = new Main();
        m.changeScene("/views/Pantalla_PermisosPropietariosTablas.fxml");
    }
    // Jobs
    @FXML
    public void onClick_Jobs(ActionEvent event) throws IOException {
        Main m = new Main();
        m.changeScene("/views/Pantalla_Jobs.fxml");
    }
    // TableSpace
    @FXML
    public void onClick_Tablespace(ActionEvent event) throws IOException {
        Main m = new Main();
        m.changeScene("/views/Pantalla_Tablespace.fxml");
    }
    // Paquetes, funciones y procedimientos
    @FXML
    public void onClick_PaquetesFuncionesProcedimientos(ActionEvent event) throws IOException {
        Main m = new Main();
        m.changeScene("/views/Pantalla_ProcedimientosFuncionesPaquetes.fxml");
    }
    // Espacio utilizado por el usuario
    @FXML
    public void onClick_EspacioUtilizado(ActionEvent event) throws IOException {
        Main m = new Main();
        m.changeScene("/views/Pantalla_EspacioUtilizadoUsuario.fxml");
    }
}
