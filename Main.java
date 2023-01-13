package main;

import controller.AddPartForm;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import model.InHouse;
import model.Inventory;
import model.Product;

import java.util.Objects;

/**RUNTIME/LOGICAL ERROR.
 * I had a difficult time populating the 2nd part table on the add and modify product forms.
 * By setting the items with the associated parts list instead of all parts I was able to get it to work.
 * FUTURE ENHANCEMENTS
 * I think in the future I would add a chart that lists all the products and their associated parts so that it is easier to visualize
 * */

public class Main extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/view/MainForm.fxml")));
        stage.setTitle("Main Form");
        stage.setScene(new Scene(root, 800, 600));
        stage.show();
    }

    /**
     * sets parts and products
     */
    public static void main(String[] args){

            InHouse inHouse1 = new InHouse(AddPartForm.getPartID(), "Brakes", 10.00, 10,2,100, 1234);
            InHouse inHouse2 = new InHouse(AddPartForm.getPartID(), "Wheel", 11.00, 10,2,100, 4567);
            InHouse inHouse3 = new InHouse(AddPartForm.getPartID(), "Seat", 12.00, 10,1,100 , 6789);

            Product outsourced1 = new Product(1, "Mini Bike", 99.99, 1,1,100);
            Product outsourced2 = new Product(2, "Giant Bike", 199.99, 2,1,100);
            Product outsourced3 = new Product(3, "Tricycle", 299.99, 3,1,100);

        Inventory.addPart(inHouse1);
        Inventory.addPart(inHouse2);
        Inventory.addPart(inHouse3);
        Inventory.addProduct(outsourced1);
        Inventory.addProduct(outsourced2);
        Inventory.addProduct(outsourced3);

        launch(args);
    }
}
