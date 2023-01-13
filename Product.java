package model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Product {
    private int id;
    private String name;
    private double price;
    private int stock;
    private int min;
    private int max;
    private ObservableList<Part> associatedParts = FXCollections.observableArrayList();

    public Product(int id, String name, double price, int stock, int min, int max) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.stock = stock;
        this.min = min;
        this.max = max;
    }
    /**
     * returns the id
     * @return the id
     */
    public int getId() {
        return id;
    }

    /**
     * sets the id
     * @param id the id to set
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * returns the name
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * sets the name
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * returns the price
     * @return the price
     */
    public double getPrice() {
        return price;
    }

    /**
     * sets the price
     * @param price the price to set
     */
    public void setPrice(double price) {
        this.price = price;
    }

    /**
     * returns the inventory
     * @return the stock
     */
    public int getStock() {
        return stock;
    }

    /**
     * sets the inventory
     * @param stock the stock to set
     */
    public void setStock(int stock) {
        this.stock = stock;
    }

    /**
     * returns the min
     * @return the min
     */
    public int getMin() {
        return min;
    }

    /**
     * sets the min
     * @param min the min to set
     */
    public void setMin(int min) {
        this.min = min;
    }

    /**
     * returns the max
     * @return the max
     */
    public int getMax() {
        return max;
    }

    /**
     * sets the max
     * @param max the max to set
     */
    public void setMax(int max) {
        this.max = max;
    }

    /** Adds parts to a product */
    public void addAssociatedPart(Part part) {
        associatedParts.add(part);
    }

    /** Removes parts from a product */
    public boolean deleteAssociatedPart(Part part) {
        return associatedParts.remove(part);
    }

    /** Displays all parts associated with a product
     * @return the product's associated parts */
    public ObservableList<Part> getAllAssociatedParts() {
        return associatedParts;
    }

}

