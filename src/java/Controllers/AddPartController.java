package Controllers;

import Models.InHouse;
import Models.Inventory;
import Models.Outsourced;
import Models.Part;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import java.io.IOException;

/**
 * The AddPartController class manages the functionality for adding a new part to the inventory.
 *
 * RUNTIME ERROR
 * Previously, the savePart() method was crashing and throwing a NumberFormatException error when invalid values
 * were submitted to TextFields. (For example, String in min and max TextFields).
 * To prevent these crashes, the savePart() method was updated to include a try-catch block that catches
 * the NumberFormatException error and displays an alert to the user that the troubled TextField requires
 * a different data type. This allows users to identify the problem with their inputs, and prevents
 * the app from crashing.
 */
public class AddPartController {

    @FXML private AnchorPane mainPane;
    @FXML private TextField name;
    @FXML private TextField stock;
    @FXML private TextField price;
    @FXML private TextField max;
    @FXML private TextField min;
    @FXML private TextField source;
    @FXML private ToggleGroup group1;
    @FXML private RadioButton inhouse;
    @FXML private RadioButton outsourced;
    @FXML private Text machineOrCompany;
    @FXML private Button saveButton;
    private Stage stage;
    private Scene scene;

    /**
     * Initializes the controller class.
     * Moves focus to the save button and sets up the radio buttons.
     */
    public void initialize() {
        // moves focus to save button
        saveButton.setFocusTraversable(true);
        Platform.runLater(() -> saveButton.requestFocus());

        group1 = new ToggleGroup();
        inhouse.setToggleGroup(group1);
        outsourced.setToggleGroup(group1);
    }

    /**
     * Changes the text of the machineOrCompany field based on the selected radio button.
     * Called when one of the radio buttons is clicked.
     */
    public void radioSwitch() {
        if (inhouse.isSelected()) {
            machineOrCompany.setText("Machine ID");
        } else {
            machineOrCompany.setText("Company Name");
        }
    }

    /**
     * Generates a new auto-generated ID for the new part.
     * @return The new auto-generated ID.
     */
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

    /**
     * Saves the new part to the inventory.
     * Called when the "Save" button is clicked.
     * @param event The action event.
     * @throws IOException if an error occurs when loading the FXML file.
     */
    public void savePart(ActionEvent event) throws IOException {
        String partName = null;
        int partStock = 0;
        double partPrice = 0.0;
        int partMax = 0;
        int partMin = 0;
        Object partSource = null;

        try {
            partName = name.getText();
            partStock = Integer.parseInt(stock.getText());
            partPrice = Double.parseDouble(price.getText());
            partMax = Integer.parseInt(max.getText());
            partMin = Integer.parseInt(min.getText());

            // check radio button selection
            if (inhouse.isSelected()) {
                partSource = Integer.parseInt(source.getText());
            } else if (outsourced.isSelected()) {
                if (source.getText().matches(".*\\d.*")) {
                    throw new NumberFormatException();
                }
                partSource = source.getText(); // store as a string
            }
        } catch (NumberFormatException e) {
            String errorMessage = "Please enter a valid value for ";
            if (!partName.matches("^[a-zA-Z ]+$")) {
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
            if (inhouse.isSelected() && !source.getText().matches("\\d+")) {
                errorMessage += "machine ID (an integer), ";
            } else if (outsourced.isSelected() && source.getText().matches(".*\\d.*")) {
                errorMessage += "company name (a string without digits), ";
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
        if (partMax < partMin) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Invalid Input");
            alert.setHeaderText("Input Error");
            alert.setContentText("Max should be greater than or equal to Min.");
            alert.showAndWait();
            return;
        }
        // check if stock is between min and max values
        if (partStock < partMin || partStock > partMax) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Invalid Input");
            alert.setHeaderText("Input Error");
            alert.setContentText("Inventory must be between minimum and max values.");
            alert.showAndWait();
            return;
        }
        // check radio button selection
        if (inhouse.isSelected()) {
            // cast partSource to int
            int machineId = (int) partSource;
            InHouse newPart = new InHouse(0, null, 0, 0, 0, 0, 0);
            newPart.setId(autoId());
            newPart.setName(partName);
            newPart.setPrice(partPrice);
            newPart.setStock(partStock);
            newPart.setMin(partMin);
            newPart.setMax(partMax);
            newPart.setMachineId(machineId);
            Inventory.addPart(newPart);
        } else if (outsourced.isSelected()) {
            // cast partSource to String
            String companyName = (String) partSource;
            Outsourced newPart = new Outsourced(0, null, 0, 0, 0, 0, null);
            newPart.setId(autoId());
            newPart.setName(partName);
            newPart.setPrice(partPrice);
            newPart.setStock(partStock);
            newPart.setMin(partMin);
            newPart.setMax(partMax);
            newPart.setCompanyName(companyName);
            Inventory.addPart(newPart);
        }
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
        stage.setTitle("Inventory Management System");
        stage.setScene(scene);
        stage.show();
    }


}
