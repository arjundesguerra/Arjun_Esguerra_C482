package Controllers;


import Models.Inventory;
import Models.Part;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

import static Models.Inventory.getAllParts;

public class MainController implements Initializable {

    private Stage stage;
    private Scene scene;

    @FXML private Button partModifyButton;
    @FXML private Button partDeleteButton;
    @FXML private Button exitButton;
    @FXML private TextField partSearch;
    @FXML private TableView<Part> partTable;
    @FXML private TableColumn<Part, Integer> partIdColumn;
    @FXML private TableColumn<Part, String> partNameColumn;
    @FXML private TableColumn<Part, Integer> partInventoryColumn;
    @FXML private TableColumn<Part, Double> partPriceColumn;

    private int selectedPartIndex = -1;

    public void initialize(URL location, ResourceBundle resourceBundle) {
        // moves focus to exit button
        exitButton.setFocusTraversable(true);
        Platform.runLater(() -> exitButton.requestFocus());

        partTable.setItems(getAllParts());
        partIdColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        partNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        partInventoryColumn.setCellValueFactory(new PropertyValueFactory<>("stock"));
        partPriceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));

        partTable.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);

        partDeleteButton.setOnAction(e -> deletePart());

        partSearch.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                searchParts(partSearch.getText());
            }
        });

    }

    private void deletePart() {
        selectedPartIndex = partTable.getSelectionModel().getSelectedIndex();
        if (selectedPartIndex != -1) {
            Part selectedPart = partTable.getItems().get(selectedPartIndex);
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmation Dialog");
            alert.setHeaderText("Are you sure you want to delete this part?");
            alert.setContentText("Press OK to confirm or Cancel to go back.");
            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK) {
                Inventory.deletePart(selectedPart);
                partTable.getItems().remove(selectedPart);
                selectedPartIndex = -1;
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Please select a part to delete.");
            alert.showAndWait();
        }
    }

    private void searchParts(String searchQuery) {
        if (searchQuery == null || searchQuery.trim().isEmpty()) {
            partTable.setItems(getAllParts());
            return;
        }

        String lowercaseSearchQuery = searchQuery.toLowerCase();
        List<Part> searchResult = Inventory.getAllParts()
                .stream()
                .filter(part ->
                        part.getName().toLowerCase().contains(lowercaseSearchQuery) ||
                                Integer.toString(part.getId()).contains(searchQuery))
                .collect(Collectors.toList());

        if (searchResult.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("No parts found for search query: " + searchQuery);
            alert.showAndWait();
        } else {
            ObservableList<Part> searchResultList = FXCollections.observableArrayList(searchResult);
            partTable.setItems(searchResultList);
        }
    }

    public void switchToAddPartScene(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("add_part.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();

    }

    public void switchToModifyPartScene(ActionEvent event) throws IOException {
        selectedPartIndex = partTable.getSelectionModel().getSelectedIndex();
        if (selectedPartIndex == -1) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Please select a part to modify.");
            alert.showAndWait();
            return;
        }
        FXMLLoader loader = new FXMLLoader(getClass().getResource("modify_part.fxml"));
        Parent root = loader.load();
        ModifyPartController modifyPartController = loader.getController();
        modifyPartController.setPart(partTable.getItems().get(selectedPartIndex), selectedPartIndex);

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





