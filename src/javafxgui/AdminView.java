package javafxgui;

import java.io.IOException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Launches the admin panel of the application, serving as the main entry point for the JavaFX UI.
 * It loads the admin panel layout from an FXML file and displays it in a new window.
 */
public class AdminView extends Application {

    /**
     * The main method that launches the JavaFX application.
     * 
     * @param args Command line arguments passed to the application.
     */
    public static void main(String[] args) {
        launch(args);
    }

    /**
     * Starts the JavaFX application, setting up the primary stage with the admin panel scene.
     * It loads the admin panel layout from 'adminpanel.fxml'.
     * 
     * @param primaryStage The primary window for this application.
     * @throws IOException If there's an issue loading the FXML file.
     */
    @Override
    public void start(Stage primaryStage) throws IOException {
        try {
            // Load the layout for the admin panel from FXML.
            Parent root = FXMLLoader.load(getClass().getResource("adminpanel.fxml"));
            Scene scene = new Scene(root);

            // Set the scene on the primary stage and show it.
            primaryStage.setScene(scene);
            primaryStage.setTitle("Admin Panel"); // Setting a title for the window.
            primaryStage.show();
        } catch (Exception e) {
            e.printStackTrace(); // Print the stack trace if there's an error during loading.
        }
    }
}
