package Controllers;


import Models.Inventory;
import Models.Part;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

import static Models.Inventory.getAllParts;

public class MainController implements Initializable {

    private Stage stage;
    private Scene scene;
    @FXML
    private Button deleteButton;
    @FXML private TableView<Part> partTable;
    @FXML private TableColumn<Part, Integer> partIdColumn;
    @FXML private TableColumn<Part, String> partNameColumn;
    @FXML private TableColumn<Part, Integer> partInventoryColumn;
    @FXML private TableColumn<Part, Double> partPriceColumn;

    public void initialize(URL location, ResourceBundle resourceBundle) {
        partTable.setItems(getAllParts());
        partIdColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        partNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        partInventoryColumn.setCellValueFactory(new PropertyValueFactory<>("stock"));
        partPriceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));

        partTable.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        deleteButton.setOnAction(e -> {
            Part selectedPart = partTable.getSelectionModel().getSelectedItem();
            if (selectedPart != null) {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Confirmation Dialog");
                alert.setHeaderText("Are you sure you want to delete this part?");
                alert.setContentText("Press OK to confirm or Cancel to go back.");
                Optional<ButtonType> result = alert.showAndWait();
                if (result.get() == ButtonType.OK) {
                    Inventory.deletePart(selectedPart);
                    partTable.getItems().remove(selectedPart);
                }
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText(null);
                alert.setContentText("Please select a part to delete.");
                alert.showAndWait();
            }
        });
    }


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

    @FXML
    private void handleExitButtonAction() {
        Platform.exit();
    }

}





