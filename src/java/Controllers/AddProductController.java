package Controllers;

import Models.Inventory;
import Models.Part;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;

import static Models.Inventory.getAllParts;

public class AddProductController {

    @FXML
    private TextField name;
    @FXML
    private TextField stock;
    @FXML
    private TextField price;
    @FXML
    private TextField max;
    @FXML
    private TextField min;
    @FXML private TableView allPartsTable;
    @FXML private TableColumn<Part, Integer> allPartsIdColumn;
    @FXML private TableColumn<Part, String> allPartsNameColumn;
    @FXML private TableColumn<Part, Integer> allPartsInventoryColumn;
    @FXML private TableColumn<Part, Double> allPartsPriceColumn;
    @FXML private TableView associatedPartsTable;
    @FXML private TableColumn<Part, Integer> associatedPartsIdColumn;
    @FXML private TableColumn<Part, String> associatedPartsNameColumn;
    @FXML private TableColumn<Part, Integer> associatedPartsInventoryColumn;
    @FXML private TableColumn<Part, Double> associatedPartsPriceColumn;
    @FXML private Button addButton;
    @FXML private Button saveButton;

    private ObservableList<Part> associatedPartList = FXCollections.observableArrayList();

    private Stage stage;
    private Scene scene;


    public void initialize() {
        // moves focus to save button
        saveButton.setFocusTraversable(true);
        Platform.runLater(() -> saveButton.requestFocus());

        allPartsTable.setItems(getAllParts());
        allPartsIdColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        allPartsNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        allPartsInventoryColumn.setCellValueFactory(new PropertyValueFactory<>("stock"));
        allPartsPriceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));

        associatedPartsTable.setItems(associatedPartList);
        associatedPartsIdColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        associatedPartsNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        associatedPartsInventoryColumn.setCellValueFactory(new PropertyValueFactory<>("stock"));
        associatedPartsPriceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
    }

    public static int autoId() {
        ObservableList<Part> allParts = Inventory.getAllParts();
        int maxId = 0;
        for(Part part: allParts) {
            int partId = part.getId();
            if (partId > maxId) {
                maxId = partId;
            }
        }
        return maxId + 1;
    }

    public void addAssociatedPart(ActionEvent event) {
        Part selected = (Part) allPartsTable.getSelectionModel().getSelectedItem();
        if (selected == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Please select a part");
            alert.showAndWait();
        } else if (!associatedPartList.contains(selected)) {
            associatedPartList.add(selected);
            associatedPartsTable.setItems(associatedPartList);
        }
    }


    public void switchToMainScene(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("main_view.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }


}
