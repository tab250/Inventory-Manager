/**
 * @author Tyler Brown
 */
package model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * This class is the backbone for the program.
 * All other classes are connected to this class.
 * And any manipulations to the information in the program are manipulated through this class, or a subclass.
 */
public class Inventory {

    //Creates an Observable Array List for all the Parts and Products in the Inventory
    private static ObservableList<Part> allParts = FXCollections.observableArrayList();
    private static ObservableList<Product> allProducts = FXCollections.observableArrayList();

    /**
     * This method adds a new Part to the Parts list.
     */
    public static void addPart(Part newPart) {

        allParts.add(newPart);
    }

    /**
     * This method adds a new Product to the Products list.
     */
    public static void addProduct(Product newProduct) {

        allProducts.add(newProduct);
    }

    /**
     * This method searches the Parts list via part Id.
     * It returns either the Part with the Id that matches the desired part, or it returns null.
     */
    public static Part lookupPart(int partId) {
        for (Part part : getAllParts()) {
            if (part.getId() == partId) {
                return part;
            }
        }
        return null;
    }

    /**
     * This method searches the Products list via product Id.
     * It returns either the Product with the Id that matches the desired product, or it returns null.
     */
    public static Product lookupProduct(int productId) {
        for (Product product : getAllProducts()) {
            if (product.getId() == productId) {
                return product;
            }
        }
        return null;
    }

    /**
     * This method searches the Parts list via part name.
     * It returns Parts with partial or complete name matches.
     */
    public static ObservableList<Part> lookupPart(String partName) {
        ObservableList<Part> matchedParts = FXCollections.observableArrayList();

        for (Part part : allParts) {
            if (part.getName().contains(partName)) {
                matchedParts.add(part);
            }
        }

        return matchedParts;
    }

    /**
     * This method searches the Products list via product name.
     * It returns Products with partial or complete name matches.
     */
    public static ObservableList<Product> lookupProduct(String productName) {
        ObservableList<Product> matchedProducts = FXCollections.observableArrayList();

        for (Product product : allProducts) {
            if (product.getName().contains(productName)) {
                matchedProducts.add(product);
            }
        }

        return matchedProducts;
    }

    /**
     * This method updates a selected Part with altered information.
     */
    public static void updatePart(int index, Part selectedPart) {
        for (int i = 0; i < allParts.size(); i++) {
            if (i == index) {
                allParts.set(i, selectedPart);
            }
        }
    }

    /**
     * This method updates a selected Product with altered information.
     */
    public static void updateProduct(int index, Product newProduct) {
        for (int i = 0; i < allProducts.size(); i++) {
            if (i == index) {
                allProducts.set(i, newProduct);
            }
        }
    }

    /**
     * This method removes an existing Part from the Parts list.
     */
    public static boolean deletePart(Part selectedPart) {

        for (Part part : allParts) {
            if (part == selectedPart) {
                allParts.remove(selectedPart);
                return true;
            }
        }
        return false;
    }

    /**
     * This method removes an existing Product from the Products list.
     */
    public static boolean deleteProduct(Product selectedProduct) {
        boolean isDeleted = false;

        for (Product product : allProducts) {
            if (product == selectedProduct) {
                allProducts.remove(product);
                return true;
            }
        }
        return false;
    }

    /**
     * This method returns all the Parts in the Parts list.
     */
    public static ObservableList<Part> getAllParts() {

        return allParts;
    }

    /**
     * This method returns all the Products in the Products list.
     */
    public static ObservableList<Product> getAllProducts() {

        return allProducts;
    }
}
