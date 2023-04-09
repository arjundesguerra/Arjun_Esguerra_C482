import Models.InHouse;
import Models.Inventory;
import Models.Outsourced;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("Controllers/main_view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1100, 625);
        stage.setTitle("Inventory Management System");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        InHouse inhouse1 = new InHouse(1, "Brakes", 200.00, 6, 1, 6, 1);
        InHouse inhouse2 = new InHouse(2, "Fuel", 50.00, 20, 1, 20, 2);

        Outsourced outsource1 = new Outsourced(3, "Alternator", 100.00, 5, 1, 5, "Honda");
        Outsourced outsource2 = new Outsourced(4, "Wheel", 250.00, 12, 1, 12, "Toyota");

        Inventory.addPart(inhouse1);
        Inventory.addPart(inhouse2);
        Inventory.addPart(outsource1);
        Inventory.addPart(outsource2);

        launch();
    }
}