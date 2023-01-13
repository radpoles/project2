package controller;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import model.Inventory;
import model.Part;
import model.Product;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

public class MainForm implements Initializable {

    Stage stage;
    Parent scene;

    public Button exitButton;
    public Button productsAddButton;
    public Button productsModifyButton;
    public Button productsDeleteButton;
    public Button partsAddButton;
    public Button partsModifyButton;
    public Button partsDeleteButton;
    public Label imsTitle;
    public Pane partsPane;
    public Label partsLabel;
    public TableView<Part> partsTable;
    public TableColumn<Part, Integer> partsIDCol;
    public TableColumn<Part, String> partsNameCol;
    public TableColumn<Part, Integer> partsInventoryCol;
    public TableColumn<Part, Double> partsPriceCol;
    public Pane productsPane;
    public Label productsLabel;
    public TableView<Product> productsTable;
    public TableColumn<Product, Integer> productsIDCol;
    public TableColumn<Product, String> productsNameCol;
    public TableColumn<Product, Integer> productsInventoryCol;
    public TableColumn<Product, Double> productsPriceCol;
    public TextField partsSearchBar;
    public TextField productsSearchBar;


    /**
     * sets tables with parts and products
     */
    @Override
    public void initialize(URL url, ResourceBundle resources) {

        partsIDCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        partsNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        partsInventoryCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        partsPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));
        productsIDCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        productsNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        productsInventoryCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        productsPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));

        ObservableList<Part> allParts = Inventory.getAllParts();
        partsTable.setItems(allParts);

        ObservableList<Product> allProducts = Inventory.getAllProducts();
        productsTable.setItems(allProducts);
    }

    /**
     * searches for parts with matching name or id. shows parts in table if a match is found
     */
    public void onMainPartsSearch() {
        String partSB = partsSearchBar.getText();

        if (partSB.isBlank()) {
            partsTable.setItems(Inventory.getAllParts());
            return;
        }
        try {
            Part nPart = Inventory.lookupPart(Integer.parseInt(partSB));

            if (nPart == null) {
                Alert error = new Alert(Alert.AlertType.INFORMATION);
                error.setTitle("Search Error");
                error.setContentText("Part Not Found");
                error.show();
            } else {
                partsTable.getSelectionModel().select(nPart);
            }

        } catch (NumberFormatException e) {
            ObservableList<Part> namedParts = Inventory.lookupPart(partSB);
            if (namedParts.size() > 0) {
                partsTable.setItems(namedParts);
            } else {
                Alert error = new Alert(Alert.AlertType.INFORMATION);
                error.setTitle("Search Error");
                error.setContentText("Part Not Found");
                error.show();
            }
        }
    }

    /**
     * opens Add Part Form
     */
    public void onPartsAddButton(ActionEvent event) throws IOException {


        stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/view/AddPartForm.fxml")));
        stage.setScene(new Scene(scene));
        stage.show();
    }

    /**
     * opens Modify Part Form if a part is selected
     */
    public void onPartsModifyButton(ActionEvent event) throws IOException {

        if (partsTable.getSelectionModel().getSelectedItem() == null) {
            Alert error = new Alert(Alert.AlertType.INFORMATION);
            error.setTitle("Error");
            error.setContentText("Part must be selected");
            error.show();
        } else {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/view/ModifyPartForm.fxml"));
            loader.load();

            ModifyPartForm MPFController = loader.getController();
            MPFController.sendPart(partsTable.getSelectionModel().getSelectedItem());

            stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            Parent scene = loader.getRoot();
            stage.setScene(new Scene(scene));
            stage.show();
        }
    }

    /**
     * deletes a selected part
     */
    public void onPartsDeleteButton() {
        Part selectedParts = partsTable.getSelectionModel().getSelectedItem();
        if (selectedParts == null) {
            Alert error = new Alert(Alert.AlertType.INFORMATION);
            error.setTitle("Error");
            error.setContentText("Part must be selected");
            error.show();
        } else {
            Alert warning = new Alert(Alert.AlertType.CONFIRMATION);
            warning.setTitle("Caution");
            warning.setContentText("Are you sure you want to delete this part?");
            warning.showAndWait();
            if (warning.getResult() == ButtonType.OK) {
                Inventory.getAllParts().remove(selectedParts);
            }
        }
    }

    /**
     * opens Add Product Form
     */
    public void onProductsAddButton(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/view/AddProductForm.fxml"));
        loader.load();

        AddProductForm APFController = loader.getController();
        APFController.addProductTable1.setItems(Inventory.getAllParts());

        stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        Parent scene = loader.getRoot();
        stage.setScene(new Scene(scene));
        stage.show();
    }

    /**
     * opens Modify Product Form if a product is selected
     */
    public void onProductsModifyButton(ActionEvent event) throws IOException {

        if (productsTable.getSelectionModel().getSelectedItem() == null) {
            Alert error = new Alert(Alert.AlertType.INFORMATION);
            error.setTitle("Error");
            error.setContentText("Product must be selected");
            error.show();
        } else {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/view/ModifyProductForm.fxml"));
            loader.load();

            ModifyProductForm MPFController = loader.getController();
            MPFController.sendProduct(productsTable.getSelectionModel().getSelectedItem());
            MPFController.modifyProductTable1.setItems(Inventory.getAllParts());
            MPFController.modifyProductTable2.setItems(MPFController.associatedParts);

            stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            Parent scene = loader.getRoot();
            stage.setScene(new Scene(scene));
            stage.show();
        }
    }

    /**
     * deletes a selected product if no parts are associated with it
     */
    public void onProductsDeleteButton() {
        Product selectedProducts = productsTable.getSelectionModel().getSelectedItem();
        if (selectedProducts == null) {
            Alert error = new Alert(Alert.AlertType.INFORMATION);
            error.setTitle("Error");
            error.setContentText("Product must be selected");
            error.show();
        } else {
            if (selectedProducts.getAllAssociatedParts().isEmpty()) {
                Alert warning = new Alert(Alert.AlertType.CONFIRMATION);
                warning.setTitle("Caution");
                warning.setContentText("Are you sure you want to delete this product?");
                warning.showAndWait();
                if (warning.getResult() == ButtonType.OK) {
                    Inventory.getAllProducts().remove(selectedProducts);
                }
            } else {
                Alert error = new Alert(Alert.AlertType.INFORMATION);
                error.setTitle("Error");
                error.setContentText("Product has associated parts and cannot be deleted");
                error.show();
            }
        }
    }

    /**
     * searches for products with matching name or id. shows products in table if a match is found
     */
    public void onMainProductsSearch() {
        String productsB = productsSearchBar.getText();

        if (productsB.isBlank()) {
            productsTable.setItems(Inventory.getAllProducts());
            return;
        }
        try {
            Product nProduct = Inventory.lookupProduct(Integer.parseInt(productsB));

            if (nProduct == null) {
                Alert error = new Alert(Alert.AlertType.INFORMATION);
                error.setTitle("Search Error");
                error.setContentText("Product Not Found");
                error.show();
            } else {
                productsTable.getSelectionModel().select(nProduct);
            }

        } catch (NumberFormatException e) {
            ObservableList<Product> namedProducts = Inventory.lookupProduct(productsB);
            if (namedProducts.size() > 0) {
                productsTable.setItems(namedProducts);
            } else {
                Alert error = new Alert(Alert.AlertType.INFORMATION);
                error.setTitle("Search Error");
                error.setContentText("Product Not Found");
                error.show();
            }
        }
    }

    /**
     * exits the application
     */
    public void onExitButton() {
        Alert warning = new Alert(Alert.AlertType.CONFIRMATION);
        warning.setTitle("Exit");
        warning.setContentText("Are you sure you want to exit?");
        warning.showAndWait();
        if (warning.getResult() == ButtonType.OK) {
            System.exit(0);
        }
    }
}