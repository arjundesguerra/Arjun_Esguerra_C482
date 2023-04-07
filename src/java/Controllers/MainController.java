package Controllers;


import Models.Part;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import static Models.Inventory.getAllParts;

public class MainController {

    private Stage stage;
    private Scene scene;

    @FXML private TableView<Part> partTable;

    public void switchToAddPartScene(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("add_part.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();

    }

    public void switchToModifyPartScene(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("modify_part.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();

    }

    public void switchToAddProductScene(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("add_product.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();

    }

    public void switchToModifyProductScene(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("modify_product.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void initialize(URL location, ResourceBundle resourceBundle) {
        partTable.setItems(getAllParts());

    }

    @FXML
    private void handleExitButtonAction() {
        Platform.exit();
    }

}





