package Controllers;

import Models.InHouse;
import Models.Outsourced;
import Models.Part;
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

public class ModifyPartController {

    @FXML
    private TextField id;
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
    private TextField source;

    @FXML private ToggleGroup group1;

    @FXML private RadioButton inhouse;

    @FXML private RadioButton outsourced;

    @FXML private Text machineOrCompany;

    private Stage stage;
    private Scene scene;

    private Part part;

    private InHouse inHouse;


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

    public void setPart(Part part) {
        this.part = part;
        name.setText(part.getName());
        stock.setText(Integer.toString(part.getStock()));
        price.setText(Double.toString(part.getPrice()));
        max.setText(Integer.toString(part.getMax()));
        min.setText(Integer.toString(part.getMin()));

        if (part instanceof InHouse) {
            inhouse.setSelected(true);
            machineOrCompany.setText("Machine ID");
            source.setText(Integer.toString(((InHouse) part).getMachineId()));
        } else if (part instanceof Outsourced) {
            outsourced.setSelected(true);
            machineOrCompany.setText("Company Name");
            source.setText(((Outsourced) part).getCompanyName());
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
