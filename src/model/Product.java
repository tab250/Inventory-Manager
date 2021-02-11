/**
 * @author Tyler Brown
 */
package model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * This class creates Product objects and adds them to the Inventory.
 */
public class Product {

    //Observable ArrayList of Parts associated with Product
    private static ObservableList<Part> associatedParts = FXCollections.observableArrayList();

    private int id;
    private String name;
    private double price;
    private int stock;
    private int min;
    private int max;

    public Product(int id, String name, double price, int stock, int min, int max) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.stock = stock;
        this.min = min;
        this.max = max;
    }

    /**
     * @return the id
     */
    public int getId() {

        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(int id) {

        this.id = id;
    }

    /**
     * @return the name
     */
    public String getName() {

        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {

        this.name = name;
    }

    /**
     * @return the price
     */
    public double getPrice() {

        return price;
    }

    /**
     * @param price the price to set
     */
    public void setPrice(double price) {

        this.price = price;
    }

    /**
     * @return the stock
     */
    public int getStock() {

        return stock;
    }

    /**
     * @param stock the stock to set
     */
    public void setStock(int stock) {

        this.stock = stock;
    }

    /**
     * @return the minimum for stock level
     */
    public int getMin() {

        return min;
    }

    /**
     * @param min the minimum for stock level to set
     */
    public void setMin(int min) {

        this.min = min;
    }

    /**
     * @return the maximum for the Product stock
     */
    public int getMax() {

        return max;
    }

    /**
     * @param max the maximum for stock level to set
     */
    public void setMax(int max) {

        this.max = max;
    }

    /**
     * This method adds an Associated Part to the list connected to the Product.
     */
    public static void addAssociatedPart(Part part) {

        associatedParts.add(part);
    }

    /**
     * This method removes an Associated Part to the list connected to the Product.
     */
    public static boolean deleteAssociatedPart(Part selectedAssociatedPart) {
        boolean isDeleted = false;

        for (Part part : Product.getAllAssociatedParts()) {
            if (part == selectedAssociatedPart) {
                isDeleted = true;
                associatedParts.remove(part);
            }
        }
        return isDeleted;
    }

    /**
     * This method returns all Associated Parts connected to the Product.
     */
    public static ObservableList<Part> getAllAssociatedParts() {

        return associatedParts;
    }
}
