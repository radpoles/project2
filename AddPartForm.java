package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import model.InHouse;
import model.Inventory;
import model.Outsourced;

import java.io.IOException;
import java.util.Objects;

public class AddPartForm {

    Stage stage;
    Parent scene;

    public ToggleGroup toggleAdd;
    public RadioButton addPartInRadio;
    public RadioButton addPartOutRadio;
    public Text addPartLabel;
    public Text addPartIDLabel;
    public Text addPartNameLabel;
    public Text addPartInvLabel;
    public Text addPartPriceLabel;
    public Text addPartMaxLabel;
    public Text addPartMachineIDCompanyNameLabel;
    public Text addPartMinLabel;
    public Button addPartSaveButton;
    public Button addPartCancelButton;
    public TextField addPartIDText;
    public TextField addPartNameText;
    public TextField addPartInvText;
    public TextField addPartPriceText;
    public TextField addPartMachineIDText;
    public TextField addPartMaxText;
    public TextField addPartMinText;

    private static int partID = 0;

    /**
     * returns the part ID
     * @return partID returns the part ID
     */
    public static int getPartID() {
        return ++partID;
    }

    /**
     * saves new part to list of all parts
     */
    public void onAddPartSaveButton(ActionEvent event) {

        try {
            String partName = addPartNameText.getText();
            int partInv = Integer.parseInt(addPartInvText.getText());
            double partPrice = Double.parseDouble(addPartPriceText.getText());
            int partMax = Integer.parseInt(addPartMaxText.getText());
            int partMin = Integer.parseInt(addPartMinText.getText());

            if(partMin < 1 || partMin > partInv || partMax < partInv) {
                Alert error = new Alert(Alert.AlertType.INFORMATION);
                error.setTitle("Inventory Error");
                error.setContentText("Inventory needs to be between min and max, min must be greater than 0, max must be greater than min");
                error.show();
                return;
            }

            if(partName.isBlank()) {
                Alert error = new Alert(Alert.AlertType.INFORMATION);
                error.setTitle("Name Error");
                error.setContentText("Name must be entered");
                error.show();
                return;
            }

            if(partPrice < 0 || partPrice == 0) {
                Alert error = new Alert(Alert.AlertType.INFORMATION);
                error.setTitle("Price Error");
                error.setContentText("Price must be greater than zero");
                error.show();
                return;
            }

            if(addPartInRadio.isSelected()) {
                int partMachineID = Integer.parseInt(addPartMachineIDText.getText());
                Inventory.addPart(new InHouse(getPartID(), partName, partPrice, partInv, partMin, partMax, partMachineID));
            } else if(addPartOutRadio.isSelected()) {
                String companyName = addPartMachineIDText.getText();
                Inventory.addPart(new Outsourced(getPartID(), partName, partPrice, partInv, partMin, partMax, companyName));
            }

            stage = (Stage) ((Button)event.getSource()).getScene().getWindow();
            scene = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/view/MainForm.fxml")));
            stage.setScene(new Scene(scene));
            stage.show();
        } catch (NumberFormatException | IOException e) {
            Alert error = new Alert(Alert.AlertType.INFORMATION);
            error.setTitle("Input Error");
            error.setContentText("Check values entered for correct input type");
            error.show();
        }
    }

    /**
     * cancels new part creation
     */
    public void onAddPartCancelButton(ActionEvent event) throws IOException {
        Alert warning = new Alert(Alert.AlertType.CONFIRMATION);
        warning.setTitle("Cancel");
        warning.setContentText("Are you sure you want to leave without saving?");
        warning.showAndWait();
        if (warning.getResult() == ButtonType.OK) {
            stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            scene = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/view/MainForm.fxml")));
            stage.setScene(new Scene(scene));
            stage.show();
        }
    }

    /**
     * sets text label to Machine ID if InHouse radio button is selected
     */
    public void onAddPartIn() {
        addPartMachineIDCompanyNameLabel.setText("Machine ID");
    }

    /**
     * sets text label to Company Name if Outsourced radio button is selected
     */
    public void onAddPartOut() {
        addPartMachineIDCompanyNameLabel.setText("Company Name");
    }
}