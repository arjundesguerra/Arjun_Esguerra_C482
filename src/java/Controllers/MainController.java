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
import java.util.Optional;
import java.util.ResourceBundle;
import static Models.Inventory.getAllParts;
import static Models.Inventory.getAllProducts;

public class MainController implements Initializable {

    @FXML private Button partModifyButton;
    @FXML private Button partDeleteButton;
    @FXML private Button exitButton;
    @FXML private TextField partSearch;
    @FXML private TextField productSearch;
    @FXML private TableView<Part> partTable;
    @FXML private TableColumn<Part, Integer> partIdColumn;
    @FXML private TableColumn<Part, String> partNameColumn;
    @FXML private TableColumn<Part, Integer> partInventoryColumn;
    @FXML private TableColumn<Part, Double> partPriceColumn;
    @FXML private TableView<Product> productTable;
    @FXML private TableColumn<Part, Integer> productIdColumn;
    @FXML private TableColumn<Part, String> productNameColumn;
    @FXML private TableColumn<Part, Integer> productInventoryColumn;
    @FXML private TableColumn<Part, Double> productPriceColumn;
    private Stage stage;
    private Scene scene;
    private static int selectedPartIndex = -1;
    private int selectedProductIndex = -1;

    /**
     * Initializes the Main Controller class.
     * Sets up table columns and populates table with data.
     * Moves focus to the save button.
     * Binds enter to trigger searchParts() and searchProducts()
     */
    public void initialize(URL location, ResourceBundle resourceBundle) {
        // moves focus to exit button
        exitButton.setFocusTraversable(true);
        Platform.runLater(() -> exitButton.requestFocus());

        partTable.setItems(getAllParts());
        partIdColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        partNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        partInventoryColumn.setCellValueFactory(new PropertyValueFactory<>("stock"));
        partPriceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));

        productTable.setItems(getAllProducts());
        productIdColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        productNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        productInventoryColumn.setCellValueFactory(new PropertyValueFactory<>("stock"));
        productPriceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));

        partTable.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);

        partSearch.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                searchParts(partSearch.getText());
            }
        });

        productSearch.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                searchProducts(productSearch.getText());
            }
        });

    }

    public void deletePart() {
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

    public void deleteProduct() {
        selectedProductIndex = productTable.getSelectionModel().getSelectedIndex();
        if (selectedProductIndex != -1) {
            Product selectedProduct = productTable.getItems().get(selectedProductIndex);
            if (selectedProduct.getAllAssociatedParts().size() > 0) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText(null);
                alert.setContentText("This product can't be deleted because it has associated part(s).");
                alert.showAndWait();
            } else {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Confirmation Dialog");
                alert.setHeaderText("Are you sure you want to delete this product?");
                alert.setContentText("Press OK to confirm or Cancel to go back.");
                Optional<ButtonType> result = alert.showAndWait();
                if (result.get() == ButtonType.OK) {
                    Inventory.deleteProduct(selectedProduct);
                    productTable.getItems().remove(selectedProduct);
                    selectedPartIndex = -1;
                }
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Please select a product to delete.");
            alert.showAndWait();
        }
    }

    /**
     * Searches for parts and displays the search results in the all parts table.
     * If the search query is empty or null, all parts are displayed in the all parts table.
     * If no parts are found for the search query, an error alert is displayed.
     * @param searchQuery the search query to use for the search
     */
    private void searchParts(String searchQuery) {
        if (searchQuery == null || searchQuery.trim().isEmpty()) {
            partTable.setItems(getAllParts());
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
            partTable.setItems(searchResult);
        }
    }

    /**
     * Searches for products and displays the search results in the products table.
     * If the search query is empty or null, all products are displayed in the products table.
     * If no products are found for the search query, an error alert is displayed.
     * @param searchQuery the search query to use for the search
     */
    private void searchProducts(String searchQuery) {
        if (searchQuery == null || searchQuery.trim().isEmpty()) {
            productTable.setItems(getAllProducts());
            return;
        }

        String lowercaseSearchQuery = searchQuery.toLowerCase();
        ObservableList<Product> searchResult;
        try {
            int productId = Integer.parseInt(searchQuery);
            Product product = Inventory.lookupProduct(productId);
            if (product != null) {
                searchResult = FXCollections.observableArrayList(product);
            } else {
                searchResult = FXCollections.emptyObservableList();
            }
        } catch (NumberFormatException e) {
            searchResult = Inventory.lookupProduct(lowercaseSearchQuery);
        }

        if (searchResult.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("No parts found for search query: " + searchQuery);
            alert.showAndWait();
        } else {
            productTable.setItems(searchResult);
        }
    }

    /**
     * Switches to the add_part view.
     * Called when the "Add" button is clicked.
     * @param event The action event.
     * @throws IOException if an error occurs when loading the FXML file.
     */
    public void switchToAddPartScene(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("add_part.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();

    }

    /**
     * Switches to the modify_part view.
     * Called when the "Modify" button is clicked.
     * Displays error if no part is selected
     * @param event The action event.
     * @throws IOException if an error occurs when loading the FXML file.
     */
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

    /**
     * Switches to the add_product view.
     * Called when the "Add" button is clicked.
     * @param event The action event.
     * @throws IOException if an error occurs when loading the FXML file.
     */
    public void switchToAddProductScene(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("add_product.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();

    }

    /**
     * Switches to the modify_product view.
     * Called when the "Modify" button is clicked.
     * Displays error if no part is selected
     * @param event The action event.
     * @throws IOException if an error occurs when loading the FXML file.
     */
    public void switchToModifyProductScene(ActionEvent event) throws IOException {
        selectedProductIndex = productTable.getSelectionModel().getSelectedIndex();
        if (selectedProductIndex == -1) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Please select a product to modify.");
            alert.showAndWait();
            return;
        }
        FXMLLoader loader = new FXMLLoader(getClass().getResource("modify_product.fxml"));
        Parent root = loader.load();
        ModifyProductController modifyProductController = loader.getController();
        modifyProductController.setProduct(productTable.getItems().get(selectedProductIndex), selectedProductIndex);

        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Handles the action of the Exit button by closing the application.
     */
    @FXML
    private void handleExitButtonAction() {
        Platform.exit();
    }

}





