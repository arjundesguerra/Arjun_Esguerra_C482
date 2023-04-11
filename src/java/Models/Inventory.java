package Models;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * The Inventory class represents the inventory of the store. It provides methods to add, update, and delete parts and
 * products in the inventory, as well as look up parts and products by ID or name.
 */
public class Inventory {

    /**
     * The list of all parts in the inventory.
     */
    private static ObservableList<Part> allParts = FXCollections.observableArrayList();;

    /**
     * The list of all products in the inventory.
     */
    private static ObservableList<Product> allProducts = FXCollections.observableArrayList();;

    /**
     * Adds a new part to the inventory.
     * @param newPart the part to add to the inventory
     */
    public static void addPart(Part newPart) {
        allParts.add(newPart);
    }

    /**
     * Adds a new product to the inventory.
     * @param newProduct the product to add to the inventory
     */
    public static void addProduct(Product newProduct) {
        allProducts.add(newProduct);
    }

    /**
     * Looks up a part in the inventory by ID.
     * @param partId the ID of the part to look up
     * @return the part with the specified ID, or null if no such part exists
     */
    public static Part lookupPart(int partId) {
        ObservableList<Part> allParts = getAllParts();
        for(Part part: allParts) {
            if(part.getId() == partId) {
                return part;
            }
        }
        return null;
    }

    /**
     * Looks up a product in the inventory by ID.
     * @param productId the ID of the product to look up
     * @return the product with the specified ID, or null if no such product exists
     */
    public static Product lookupProduct(int productId) {
        ObservableList<Product> allProducts = getAllProducts();

        for(Product product: allProducts) {
            if(product.getId() == productId) {
                return product;
            }
        }
        return null;
    }

    /**
     * Looks up parts in the inventory whose names contain the specified string.
     * @param partName the string to search for
     * @return a list of parts whose names contain the specified string
     */
    public static ObservableList<Part> lookupPart(String partName) {
        ObservableList<Part> parts = FXCollections.observableArrayList();
        ObservableList<Part> allParts = getAllParts();
        for(Part part: allParts) {
            if(part.getName().toLowerCase().contains(partName)) {
                parts.add(part);
            }
        }
        return parts;
    }

    /**
     * Looks up products in the inventory whose names contain the specified string.
     * @param productName the string to search for
     * @return a list of products whose names contain the specified string
     */
    public static ObservableList<Product> lookupProduct(String productName) {
        ObservableList<Product> products = FXCollections.observableArrayList();
        ObservableList<Product> allProducts = getAllProducts();
        for(Product product: allProducts) {
            if(product.getName().toLowerCase().contains(productName)) {
                products.add(product);
            }
        }
        return products;
    }

    /**
     * Updates a part in the inventory at the specified index.
     * @param index the index of the part to update
     * @param selectedPart the new part to replace the old part with
     */
    public static void updatePart(int index, Part selectedPart) {
        getAllParts().set(index, selectedPart);
    }

    /**
     * Updates a product at a given index in the inventory with the provided product object.
     * @param index the index of the product to update
     * @param newProduct the new product to replace the old product with
     */
    public static void updateProduct(int index, Product newProduct) {
        getAllProducts().set(index, newProduct);
    }

    /**
     * Deletes a part from the inventory.
     * @param selectedPart the part object to be deleted
     * @return true if the part was successfully deleted, false otherwise
     */
    public static boolean deletePart(Part selectedPart) {
        if (allParts.contains(selectedPart)) {
            allParts.remove(selectedPart);
            return true;
        } else {
            return false;
        }
    }

    /**
     * Deletes a product from the inventory.
     * @param selectedProduct the product object to be deleted
     * @return true if the product was successfully deleted, false otherwise
     */
    public static boolean deleteProduct(Product selectedProduct) {
        if (allProducts.contains(selectedProduct)) {
            allProducts.remove(selectedProduct);
            return true;
        } else {
            return false;
        }
    }

    /**
     * Retrieves all parts in the inventory.
     * @return an observable list of all parts in the inventory
     */
    public static ObservableList<Part> getAllParts() { return allParts; }

    /**
     * Retrieves all products in the inventory.
     * @return an observable list of all products in the inventory
     */
    public static ObservableList<Product> getAllProducts() {
        return allProducts;
    }


}
