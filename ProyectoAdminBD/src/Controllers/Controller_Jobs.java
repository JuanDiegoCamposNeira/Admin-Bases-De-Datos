package Controllers;

import Main.Main;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.io.IOException;

public class Controller_Jobs {
    // Constructor
    public Controller_Jobs() {}

    /**
     * Get the elements in the view
     */
    @FXML private Button enviar;
    @FXML private TextArea console;
    @FXML private TextField input;

    /**
     * Methods for the elements
     */
    public void onClick_Atras(ActionEvent actionEvent) throws IOException {
        // Create instance of main
        Main main = new Main();
        // Launch main activity
        main.changeScene("/views/Pantalla_Inicio.fxml");
    }

    // Handle onClick in 'enviar' button
    public void onClick_Enviar(ActionEvent event) {
        // Get the text of the user input
        String inputText = input.getText();
        // Validate input
        if (inputText.equals("")) return;
        // Update text area with user input
        if (!console.getText().equals(""))
            console.setText(console.getText() + "\n$ " + inputText);
        else console.setText("$ " + inputText);
        // Clear text on input field
        input.clear();
    }
}
