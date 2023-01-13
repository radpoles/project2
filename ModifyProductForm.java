package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import model.*;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

public class ModifyProductForm implements Initializable {

    Stage stage;
    Parent scene;

    public TableColumn productCol1;
    public TableColumn productCol2;
    public TableColumn productCol3;
    public TableColumn productCol4;
    public TableColumn productCol5;
    public TableColumn productCol6;
    public TableColumn productCol7;
    public TableColumn productCol8;
    public Text modifyProductLabel;
    public Text modifyProductIDLabel;
    public Text modifyProductNameLabel;
    public Text modifyProductInvLabel;
    public Text modifyProductPriceLabel;
    public Text modifyProductMaxLabel;
    public Text modifyProductMinLabel;
    public Button modifyProductSaveButton;
    public Button modifyProductCancelButton;
    public TextField modifyProductIDText;
    public TextField modifyProductNameText;
    public TextField modifyProductInvText;
    public TextField modifyProductPriceText;
    public TextField modifyProductMaxText;
    public TextField modifyProductMinText;
    public TableView modifyProductTable1;
    public Button modifyProductRAPButton;
    public Button modifyProductAddButton;
    public TableView modifyProductTable2;
    public TextField modifyProductSearchBar;

    Product selectedProduct = new Product(0, "Scooter", 10.00, 10, 1, 10);
    ObservableList<Part> associatedParts = FXCollections.observableArrayList();


    /**
     * sets text fields and tables with selected product info and associated parts
     */
    public void sendProduct(Product product) {

        selectedProduct = product;
        associatedParts = selectedProduct.getAllAssociatedParts();
        modifyProductIDText.setText(String.valueOf(selectedProduct.getId()));
        modifyProductNameText.setText(selectedProduct.getName());
        modifyProductInvText.setText(String.valueOf(selectedProduct.getStock()));
        modifyProductPriceText.setText(String.valueOf(selectedProduct.getPrice()));
        modifyProductMaxText.setText(String.valueOf(selectedProduct.getMax()));
        modifyProductMinText.setText(String.valueOf(selectedProduct.getMin()));
        modifyProductTable2.setItems(selectedProduct.getAllAssociatedParts());
    }

    /**
     * saves updated product info
     */
    public void onModifyProductSaveButton(ActionEvent event) {
        try {
            int productId = Integer.parseInt(modifyProductIDText.getText());
            String productName = modifyProductNameText.getText();
            int productInv = Integer.parseInt(modifyProductInvText.getText());
            double productPrice = Double.parseDouble(modifyProductPriceText.getText());
            int productMax = Integer.parseInt(modifyProductMaxText.getText());
            int productMin = Integer.parseInt(modifyProductMinText.getText());

            if (productMin < 1 || productMin > productInv || productMax < productInv) {
                Alert error = new Alert(Alert.AlertType.INFORMATION);
                error.setTitle("Inventory Error");
                error.setContentText("Inventory needs to be between min and max, min must be greater than 0, max must be greater than min");
                error.show();
                return;
            }

            if (productPrice < 0 || productPrice == 0) {
                Alert error = new Alert(Alert.AlertType.INFORMATION);
                error.setTitle("Price Error");
                error.setContentText("Price must be greater than zero");
                error.show();
                return;
            }

            if (productName.isBlank()) {
                Alert error = new Alert(Alert.AlertType.INFORMATION);
                error.setTitle("Name Error");
                error.setContentText("Name must be entered");
                error.show();
                return;
            }

            if (productName.contains("0") ||
                    productName.contains("1") ||
                    productName.contains("2") ||
                    productName.contains("3") ||
                    productName.contains("4") ||
                    productName.contains("5") ||
                    productName.contains("6") ||
                    productName.contains("7") ||
                    productName.contains("8") ||
                    productName.contains("9")) {
                Alert error = new Alert(Alert.AlertType.INFORMATION);
                error.setTitle("Product Name Error");
                error.setContentText("Product Name must be a string");
                error.show();
                return;
            }

            selectedProduct.setId(Integer.parseInt(modifyProductIDText.getText()));
            selectedProduct.setName(modifyProductNameText.getText());
            selectedProduct.setStock(Integer.parseInt(modifyProductInvText.getText()));
            selectedProduct.setPrice(Double.parseDouble(modifyProductPriceText.getText()));
            selectedProduct.setMin(Integer.parseInt(modifyProductMinText.getText()));
            selectedProduct.setMax(Integer.parseInt(modifyProductMaxText.getText()));

            Inventory.updateProduct(Integer.parseInt(modifyProductIDText.getText()), selectedProduct);

            stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
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
     * cancels product modification
     */
    public void onModifyProductCancelButton(ActionEvent event) throws IOException {
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
     * removes associated parts from product
     */
    public void onModifyProductRAPButton() {
        Part selectedPart = (Part) modifyProductTable2.getSelectionModel().getSelectedItem();
        Alert warning = new Alert(Alert.AlertType.CONFIRMATION);
        warning.setTitle("Caution");
        warning.setContentText("Are you sure you want to remove this part?");
        warning.showAndWait();
        if (warning.getResult() == ButtonType.OK) {
            selectedProduct.deleteAssociatedPart(selectedPart);
        }
    }

    /**
     * adds part to a product
     */
    public void onModifyProductAddButton() {
        Part selectedPart = (Part) modifyProductTable1.getSelectionModel().getSelectedItem();
        selectedProduct.addAssociatedPart(selectedPart);
        modifyProductTable2.setItems(selectedProduct.getAllAssociatedParts());
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        productCol1.setCellValueFactory(new PropertyValueFactory<>("id"));
        productCol2.setCellValueFactory(new PropertyValueFactory<>("name"));
        productCol3.setCellValueFactory(new PropertyValueFactory<>("stock"));
        productCol4.setCellValueFactory(new PropertyValueFactory<>("price"));

        modifyProductTable1.setItems(Inventory.getAllProducts());
        modifyProductTable2.setItems(associatedParts);

        productCol5.setCellValueFactory(new PropertyValueFactory<>("id"));
        productCol6.setCellValueFactory(new PropertyValueFactory<>("name"));
        productCol7.setCellValueFactory(new PropertyValueFactory<>("stock"));
        productCol8.setCellValueFactory(new PropertyValueFactory<>("price"));
    }
}
