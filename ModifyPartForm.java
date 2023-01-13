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
import model.Part;

import java.io.IOException;
import java.util.Objects;

public class ModifyPartForm {


    Stage stage;
    Parent scene;

    public ToggleGroup toggleModify;
    public RadioButton modifyPartInRadio;
    public RadioButton modifyPartOutRadio;
    public Text modifyPartLabel;
    public Text modifyPartIDLabel;
    public Text modifyPartNameLabel;
    public Text modifyPartInvLabel;
    public Text modifyPartPriceLabel;
    public Text modifyPartMaxLabel;
    public Text modifyMachineIDCompanyNameLabel;
    public Text modifyPartMinLabel;
    public Button modifyPartSaveButton;
    public Button modifyPartCancelButton;
    public TextField modifyPartIDText;
    public TextField modifyPartNameText;
    public TextField modifyPartInvText;
    public TextField modifyPartPriceText;
    public TextField modifyMachineIDCompanyNameText;
    public TextField modifyPartMaxText;
    public TextField modifyPartMinText;

    Part partData = null;

    /**
     * sets text fields with selected part info
     */
    public void sendPart(Part part) {
        partData = part;
        modifyPartIDText.setText(String.valueOf(part.getId()));
        modifyPartNameText.setText(part.getName());
        modifyPartInvText.setText(String.valueOf(part.getStock()));
        modifyPartPriceText.setText(String.valueOf(part.getPrice()));
        modifyPartMaxText.setText(String.valueOf(part.getMax()));
        modifyPartMinText.setText(String.valueOf(part.getMin()));


        if(part instanceof InHouse partInHouse) {
            modifyMachineIDCompanyNameText.setText(String.valueOf(partInHouse.getMachineId()));
            modifyPartInRadio.setSelected(true);
            modifyMachineIDCompanyNameLabel.setText("Machine ID");
        } else if(part instanceof Outsourced partOutsourced) {
            modifyMachineIDCompanyNameText.setText(partOutsourced.getCompanyName());
            modifyPartOutRadio.setSelected(true);
            modifyMachineIDCompanyNameLabel.setText("Company Name");
        }
    }

    /**
     * saves updated part info
     */
    public void onModifyPartSaveButton(ActionEvent event) {
        try {
            int partId = Integer.parseInt(modifyPartIDText.getText());
            String partName = modifyPartNameText.getText();
            int partInv = Integer.parseInt(modifyPartInvText.getText());
            double partPrice = Double.parseDouble(modifyPartPriceText.getText());
            int partMax = Integer.parseInt(modifyPartMaxText.getText());
            int partMin = Integer.parseInt(modifyPartMinText.getText());

            if (partMin < 1 || partMin > partInv || partMax < partInv) {
                Alert error = new Alert(Alert.AlertType.INFORMATION);
                error.setTitle("Inventory Error");
                error.setContentText("Inventory needs to be between min and max, min must be greater than 0, max must be greater than min");
                error.show();
                return;
            }

            if (partName.isBlank()) {
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

            if (modifyPartInRadio.isSelected()) {
                int partMachineID = Integer.parseInt(modifyMachineIDCompanyNameText.getText());
                    Inventory.addPart(new InHouse(partId, partName, partPrice, partInv, partMin, partMax, partMachineID));
                }
             else {
                String companyName = modifyMachineIDCompanyNameText.getText();
                Inventory.addPart(new Outsourced(partId, partName, partPrice, partInv, partMin, partMax, companyName));
                }
            Inventory.getAllParts().remove(partData);

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
     * cancels part modification
     */
    public void onModifyPartCancelButton(ActionEvent event) throws IOException {
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
    public void onModifyPartIn() {
        modifyMachineIDCompanyNameLabel.setText("Machine ID");
    }

    /**
     * sets text label to Company Name if Outsourced radio button is selected
     */
    public void onModifyPartOut() {
        modifyMachineIDCompanyNameLabel.setText("Company Name");
    }
}