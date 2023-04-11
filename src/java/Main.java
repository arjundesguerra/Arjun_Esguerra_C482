import Models.InHouse;
import Models.Inventory;
import Models.Outsourced;
import Models.Product;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;

/**
 * @author Arjun Esguerra
 * C482 Software 1
 * Javadoc folder location: /Arjun_Esguerra_C482/javadoc
 *
 * The main class for the Inventory Management System application.
 * Launches the application and initializes the inventory with some sample data.
 *
 * FUTURE ENHANCEMENT - User Authentication:
 * Implement a feature that allows users to create accounts and log in with their credentials to access the application.
 * External Database that allows for information to be saved for later access.
 *
 * RUNTIME ERROR - More information found in AddPartController
 */
public class Main extends Application {

    /**
     * Starts the GUI of the application and loads the main view.
     * @param stage The primary stage for the application.
     * @throws IOException If an error occurs while loading the main view FXML file.
     */
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("Controllers/main_view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1100, 625);
        stage.setTitle("Inventory Management System");
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Launches the application
     * @param args Command line arguments (not used).
     */
    public static void main(String[] args) {
        launch();
    }
}