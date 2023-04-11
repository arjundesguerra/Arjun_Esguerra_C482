package Controllers;

import Models.Inventory;
import Models.Part;
import Models.Product;
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
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;
import java.io.IOException;
import java.util.Optional;
import static Models.Inventory.getAllParts;

public class ModifyProductController {

    @FXML private TextField id;
    @FXML private TextField name;
    @FXML private TextField stock;
    @FXML private TextField price;
    @FXML private TextField max;
    @FXML private TextField min;
    @FXML private TextField partSearch;
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
    @FXML private Button saveButton;
    private Stage stage;
    private Scene scene;
    private Product product;
    private int selectedProductIndex;
    private ObservableList<Part> associatedPartList = FXCollections.observableArrayList();

    /**
     * Initializes the controller class.
     * Sets up table columns and populates table with data.
     * Moves focus to the save button.
     * Binds enter to trigger searchParts()
     */
    public void initialize() {
        // moves focus to save button
        saveButton.setFocusTraversable(true);
        Platform.runLater(() -> saveButton.requestFocus());

        allPartsTable.setItems(getAllParts());
        allPartsIdColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        allPartsNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        allPartsInventoryColumn.setCellValueFactory(new PropertyValueFactory<>("stock"));
        allPartsPriceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
    }

    /**
     * Sets the product details and associated parts in the UI when a product is selected.
     * Sets up table columns and populates table with data.
     * Binds enter to trigger searchParts()
     * @param product The product object to set the details for.
     * @param selectedProductIndex The index of the selected product in the products list.
     *
     */
    public void setProduct(Product product, int selectedProductIndex) {
        this.product = product;
        id.setText(Integer.toString(product.getId()));
        name.setText(product.getName());
        stock.setText(Integer.toString(product.getStock()));
        price.setText(Double.toString(product.getPrice()));
        max.setText(Integer.toString(product.getMax()));
        min.setText(Integer.toString(product.getMin()));
        this.selectedProductIndex = selectedProductIndex;

        associatedPartsTable.setItems(product.getAllAssociatedParts());
        associatedPartsIdColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        associatedPartsNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        associatedPartsInventoryColumn.setCellValueFactory(new PropertyValueFactory<>("stock"));
        associatedPartsPriceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));

        //adds all associated parts to the part list
        for (Part part : product.getAllAssociatedParts()) {
            associatedPartList.add(part);
        }

        partSearch.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                searchParts(partSearch.getText());
            }
        });
    }

    /**
     * Searches for parts and displays the search results in the all parts table.
     * If the search query is empty or null, all parts are displayed in the all parts table.
     * If no parts are found for the search query, an error alert is displayed.
     * @param searchQuery the search query to use for the search
     */
    private void searchParts(String searchQuery) {
        if (searchQuery == null || searchQuery.trim().isEmpty()) {
            allPartsTable.setItems(getAllParts());
            return;
        }

        String lowercaseSearchQuery = searchQuery.toLowerCase();
        ObservableList<Part> searchResult;
        try {
            int partId = Integer.parseInt(searchQuery);
            Part part = Inventory.lookupPart(partId);
            if (part != null) {
                searchResult = FXCollections.observableArrayList(part);
            } else {
                searchResult = FXCollections.emptyObservableList();
            }
        } catch (NumberFormatException e) {
            searchResult = Inventory.lookupPart(lowercaseSearchQuery);
        }

        if (searchResult.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("No parts found for search query: " + searchQuery);
            alert.showAndWait();
        } else {
            allPartsTable.setItems(searchResult);
        }
    }

    /**
     * Adds the selected part to the associated part table.
     * Displays an error message if no part is selected or if the selected part is already in the associated part list.
     * @param event the action event that triggered this method
     */
    public void addToAssociatedPartTable(ActionEvent event) {
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

    /**
     * Removes the selected part from the associated part table.
     * Displays a confirmation dialog before removing the part and an error message if no part is selected.
     * @param event the action event that triggered this method
     */
    public void removeFromAssociatedPartTable(ActionEvent event) {
        Part selected = (Part) associatedPartsTable.getSelectionModel().getSelectedItem();
        if (selected != null) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmation Dialog");
            alert.setHeaderText("Are you sure you want to remove this part?");
            alert.setContentText("Press OK to confirm or Cancel to go back.");
            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK) {
                associatedPartList.remove(selected);
                product.deleteAssociatedPart(selected);
                associatedPartsTable.setItems(associatedPartList);

            } else {
                Alert nullError = new Alert(Alert.AlertType.ERROR);
                nullError.setTitle("Error");
                nullError.setHeaderText(null);
                nullError.setContentText("Please select a part to remove");
                nullError.showAndWait();
            }
        }
    }

    /**
     * Saves the product after validating user input for name, stock, price, min, and max.
     * Overwrites the previous product to the Inventory and switches the scene back to the main scene.
     * @param event The ActionEvent triggered by the save button.
     * @throws IOException if an error occurs while loading the main scene.
     */
    public void saveProduct(ActionEvent event) throws IOException {
        int productId = 0;
        String productName = null;
        int productStock = 0;
        double productPrice = 0.0;
        int productMax = 0;
        int productMin = 0;

        try {
            productId = Integer.parseInt(id.getText());
            productName = name.getText();
            productStock = Integer.parseInt(stock.getText());
            productPrice = Double.parseDouble(price.getText());
            productMax = Integer.parseInt(max.getText());
            productMin = Integer.parseInt(min.getText());
        } catch (NumberFormatException e) {
            String errorMessage = "Please enter a valid value for ";
            if (!productName.matches("^[a-zA-Z ]+$")) {
                errorMessage += "name (a string), ";
            }
            if (!stock.getText().matches("\\d+")) {
                errorMessage += "stock (an integer), ";
            }
            if (!price.getText().matches("\\d+(\\.\\d+)?")) {
                errorMessage += "price (a double), ";
            }
            if (!max.getText().matches("\\d+")) {
                errorMessage += "max (an integer), ";
            }
            if (!min.getText().matches("\\d+")) {
                errorMessage += "min (an integer), ";
            }
            // removes comma at last error
            errorMessage = errorMessage.substring(0, errorMessage.length() - 2);
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Invalid Input");
            alert.setHeaderText("Please enter valid input values.");
            alert.setContentText(errorMessage);
            alert.showAndWait();
            return;
        }
        // check if max is less than min
        if (productMax < productMin) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Invalid Input");
            alert.setHeaderText("Input Error");
            alert.setContentText("Max should be greater than or equal to Min.");
            alert.showAndWait();
            return;
        }
        // check if stock is between min and max values
        if (productStock < productMin || productStock > productMax) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Invalid Input");
            alert.setHeaderText("Input Error");
            alert.setContentText("Inventory must be between minimum and max values.");
            alert.showAndWait();
            return;
        }
        Product updatedProduct = new Product(0, null, 0, 0, 0, 0);
        updatedProduct.setId(productId);
        updatedProduct.setName(productName);
        updatedProduct.setPrice(productPrice);
        updatedProduct.setStock(productStock);
        updatedProduct.setMin(productMin);
        updatedProduct.setMax(productMax);
        for (Part part : associatedPartList) {
            updatedProduct.addAssociatedPart(part);
        }
        Inventory.updateProduct(selectedProductIndex, updatedProduct);
        switchToMainScene(event);
    }

    /**
     * Switches the scene back to the main view.
     * Called when the "Cancel" button is clicked.
     * @param event The action event.
     * @throws IOException if an error occurs when loading the FXML file.
     */
    public void switchToMainScene(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("main_view.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();

    }

}
