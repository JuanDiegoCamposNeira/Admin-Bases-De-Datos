package Main;

import acceso_datos.conexion;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

public class Main extends Application {

    // Attribute to create a fixed stage
    private static Stage stage;

    @Override
    public void start(Stage primaryStage) throws Exception {
        Connection conex = null;
        try{
            conex = conexion.getConnection();
        }catch (SQLException ex){
            System.out.println(ex.toString());
        }finally{
            if(conex != null)conex.close();
        }
        stage = primaryStage;
        Parent root = FXMLLoader.load(getClass().getResource("/views/Pantalla_Inicio.fxml"));
        primaryStage.setScene(new Scene(root, 650, 400));
        primaryStage.show();
    }

    public void changeScene(String FXML_scene) throws IOException {
        // Create a parent to set the scene
        Parent scene = FXMLLoader.load(getClass().getResource(FXML_scene));
        // Load the stage
        stage.getScene().setRoot(scene);
    }

    public static void main(String[] args) {
        launch(args);
    }

}
