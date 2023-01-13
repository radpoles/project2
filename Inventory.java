package model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Inventory {
    private static ObservableList<Part> allParts = FXCollections.observableArrayList();
    private static ObservableList<Product> allProducts = FXCollections.observableArrayList();

    /**
     * adds a part to the list of all parts
     */
    public static void addPart(Part newPart) {
        allParts.add(newPart);
    }

    /**
     * returns the list of all parts
     * @return allParts the list of all parts
     */
    public static ObservableList<Part> getAllParts() {

        return allParts;
    }

    /**
     * returns the parts that match the part ID search
     * @return nParts the parts that match the part ID search. If no ID matches, returns nothing
     */
    public static Part lookupPart(int partId) {

        for (Part nParts : allParts) {
            if (nParts.getId() == partId) {
                return nParts;
            }
        }
        return null;
    }

    /**
     * returns the parts that match the part name search
     * @return namedParts the parts that match the part name search. If no name matches, returns nothing
     */
    public static ObservableList<Part> lookupPart(String partialPartName) {

        ObservableList<Part> namedParts = FXCollections.observableArrayList();

        for (Part nParts : allParts) {
            if (nParts.getName().toLowerCase().contains(partialPartName.toLowerCase())) {
                namedParts.add(nParts);
            }
        }
        return namedParts;
    }

    /**
     * updates a part
     */
         public static void updatePart(int index, Part selectedPart) {
                allParts.set(index, selectedPart);
             }

    /**
     * deletes a part
     */
    public static void deletePart(Part part) {
            allParts.remove(part);
        }

    /**
     * adds a Product to the list of all products
     */
    public static void addProduct(Product newProduct) {
        allProducts.add(newProduct);
    }

    /**
     * returns the list of all products
     * @return allProducts the list of all products
     */
    public static ObservableList<Product> getAllProducts() {
        return allProducts;
    }

    /**
     * returns the products that match the product ID search
     * @return nProducts the products that match the product ID search. If no ID matches, returns nothing
     */
    public static Product lookupProduct(int Id) {

        for (Product nProduct : allProducts) {
            if (nProduct.getId() == Id) {
                return nProduct;
            }
        }
        return null;
    }

    /**
     * returns the products that match the product name search
     * @return namedProducts the products that match the product name search. If no name matches, returns nothing
     */
    public static ObservableList<Product> lookupProduct(String partialProductName) {

        ObservableList<Product> namedProducts = FXCollections.observableArrayList();

        for (Product nProduct : allProducts) {
            if (nProduct.getName().toLowerCase().contains(partialProductName.toLowerCase())) {
                namedProducts.add(nProduct);
            }
        }
        return namedProducts;
    }
    /**
     * updates a product
     */
    public static void updateProduct(int index, Product newProduct) {
        Product selected = Inventory.lookupProduct(index);
        Inventory.deleteProduct(selected);
        Inventory.addProduct(newProduct);
    }

    /**
     * deletes a product
     */
    public static void deleteProduct(Product product) {
           allProducts.remove(product);
            }
}
