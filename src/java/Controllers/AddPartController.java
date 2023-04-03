package Controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;

public class AddPartController {

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
}
