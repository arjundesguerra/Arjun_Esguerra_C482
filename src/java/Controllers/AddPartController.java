package Controllers;

import Models.InHouse;
import Models.Inventory;
import Models.Part;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;

public class AddPartController {
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
    @FXML
    private TextField machineId;

    @FXML private ToggleGroup group1;

    @FXML private RadioButton inhouse;

    @FXML private RadioButton outsourced;

    @FXML private Text machineOrCompany;

    private Stage stage;
    private Scene scene;


    public void initialize() {
        group1 = new ToggleGroup();
        inhouse.setToggleGroup(group1);
        outsourced.setToggleGroup(group1);
    }

    public void radioSwitch() {
        if (inhouse.isSelected()) {
            machineOrCompany.setText("Machine ID");
        } else {
            machineOrCompany.setText("Company Name");
        }
    }

    public void switchToMainScene(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("main_view.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
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

    public void savePart(ActionEvent event) {
        String partName = name.getText();
        int partStock = Integer.valueOf(stock.getText());
        double partPrice = Double.valueOf(price.getText());
        int partMax = Integer.valueOf(max.getText());
        int partMin = Integer.valueOf(min.getText());
        int partMachineId = Integer.valueOf(machineId.getText());

        if(inhouse.isSelected()) {
            InHouse newPart = new InHouse(0, null, 0, 0, 0, 0, 0);
            newPart.setId(autoId());
            newPart.setName(partName);
            newPart.setPrice(partPrice);
            newPart.setStock(partStock);
            newPart.setMin(partMin);
            newPart.setMax(partMax);
            newPart.setMachineId(partMachineId);

        }

    }
}
