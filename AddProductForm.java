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

public class AddProductForm implements Initializable {

    public TableColumn partIDCol1;
    public TableColumn nameCol1;
    public TableColumn invCol1;
    public TableColumn priceCol1;
    public TableColumn partIDCol2;
    public TableColumn nameCol2;
    public TableColumn invCol2;
    public TableColumn priceCol2;
    Stage stage;
    Parent scene;

    public Text addProductLabel;
    public Text addProductIDLabel;
    public Text addProductNameLabel;
    public Text addProductInvLabel;
    public Text addProductPriceLabel;
    public Text addProductMaxLabel;
    public Text addProductMinLabel;
    public Button addProductSaveButton;
    public Button addProductCancelButton;
    public TextField addProductIDText;
    public TextField addProductNameText;
    public TextField addProductInvText;
    public TextField addProductPriceText;
    public TextField addProductMaxText;
    public TextField addProductMinText;
    public TableView addProductTable1;
    public Button addProductRAPButton;
    public Button addProductAddButton;
    public TableView addProductTable2;
    public TextField addProductSearchBar;

    ObservableList<Part> associatedParts = FXCollections.observableArrayList();


    private int productId = 0;

    Product productData = new Product(0, "Scooter", 10.00, 10, 1, 10);

    /**
     * adds product to list of all products
     */
    public void onAddProductSaveButton(ActionEvent event) {
        try {
            String productName = addProductNameText.getText();
            int productInv = Integer.parseInt(addProductInvText.getText());
            double productPrice = Double.parseDouble(addProductPriceText.getText());
            int productMax = Integer.parseInt(addProductMaxText.getText());
            int productMin = Integer.parseInt(addProductMinText.getText());

            if(productMin < 1 || productMin > productInv || productMax < productInv) {
                Alert error = new Alert(Alert.AlertType.INFORMATION);
                error.setTitle("Inventory Error");
                error.setContentText("Inventory needs to be between min and max, min must be greater than 0.");
                error.show();
                return;
            }

            if(productName.isBlank()) {
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

            if(productPrice < 0 || productPrice == 0) {
                Alert error = new Alert(Alert.AlertType.INFORMATION);
                error.setTitle("Price Error");
                error.setContentText("Price must be greater than zero");
                error.show();
                return;
            }

            productData.setId(Integer.parseInt(addProductIDText.getText()));
            productData.setName(addProductNameText.getText());
            productData.setStock(Integer.parseInt(addProductInvText.getText()));
            productData.setMin(Integer.parseInt(addProductMinText.getText()));
            productData.setMax(Integer.parseInt(addProductMaxText.getText()));

            Inventory.addProduct(productData);


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
     * cancels new product creation
     */
    public void onAddProductCancelButton(ActionEvent event) throws IOException {
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
    public void onAddProductRAPButton() {
        Part selectedPart = (Part) addProductTable2.getSelectionModel().getSelectedItem();
        Alert warning = new Alert(Alert.AlertType.CONFIRMATION);
        warning.setTitle("Caution");
        warning.setContentText("Are you sure you want to remove this part?");
        warning.showAndWait();
        if (warning.getResult() == ButtonType.OK) {
            associatedParts.remove(selectedPart);
        }
    }

    /**
     * adds part to a product
     */
    public void onAddProductAddButton() {
        Part selectedPart = (Part) addProductTable1.getSelectionModel().getSelectedItem();
        productData.addAssociatedPart(selectedPart);
        addProductTable2.setItems(productData.getAllAssociatedParts());
    }

    /**
     * searches for parts with matching name or id. shows parts in table if a match is found
     */
    public void onAddProductsSearchBar() {
        String partSB = addProductSearchBar.getText();

        if (partSB.isBlank()) {
            addProductTable1.setItems(Inventory.getAllParts());
            return;
        }
        try {
            Part nPart = Inventory.lookupPart(Integer.parseInt(partSB));

            if(nPart == null) {
                Alert error = new Alert(Alert.AlertType.INFORMATION);
                error.setTitle("Search Error");
                error.setContentText("Part Not Found");
                error.show();
            } else {
                addProductTable1.getSelectionModel().select(nPart);
            }

        } catch (NumberFormatException e) {
            ObservableList<Part> namedParts = Inventory.lookupPart(partSB);
            if(namedParts.size() > 0) {
                addProductTable1.setItems(namedParts);
            } else {
                Alert error = new Alert(Alert.AlertType.INFORMATION);
                error.setTitle("Search Error");
                error.setContentText("Part Not Found");
                error.show();
            }
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        partIDCol1.setCellValueFactory(new PropertyValueFactory<>("id"));
        nameCol1.setCellValueFactory(new PropertyValueFactory<>("name"));
        invCol1.setCellValueFactory(new PropertyValueFactory<>("stock"));
        priceCol1.setCellValueFactory(new PropertyValueFactory<>("price"));

        addProductTable2.setItems(associatedParts);
        addProductIDText.setText(String.valueOf(Inventory.getAllProducts().size()+1));

        partIDCol2.setCellValueFactory(new PropertyValueFactory<>("id"));
        nameCol2.setCellValueFactory(new PropertyValueFactory<>("name"));
        invCol2.setCellValueFactory(new PropertyValueFactory<>("stock"));
        priceCol2.setCellValueFactory(new PropertyValueFactory<>("price"));

        ObservableList<Part> allParts = Inventory.getAllParts();
        addProductTable1.setItems(allParts);
    }
}