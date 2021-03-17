package Controllers;

import Main.Main;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

import java.io.IOException;

public class Controller_ProcedimientosFuncionesPaquetes {
    // Constructor
    public Controller_ProcedimientosFuncionesPaquetes() {}

    /**
     * Get the elements in the view
     */
    @FXML
    private Button atras;

    /**
     * Methods for the elements
     */
    public void onClick_Atras(ActionEvent actionEvent) throws IOException {
        // Create instance of main
        Main main = new Main();
        // Launch main activity
        main.changeScene("/views/Pantalla_Inicio.fxml");
    }
}
