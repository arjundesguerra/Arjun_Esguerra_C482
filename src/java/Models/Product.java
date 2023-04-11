package Models;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * The Product class represents a company's product.
 * It contains information such as the product's ID, name, price, stock level,
 * minimum and maximum stock levels, and the parts associated with the product.
 */
public class Product {

    /**
     * The parts associated with the product.
     */
    private ObservableList<Part> associatedParts = FXCollections.observableArrayList();

    private int id;

    private String name;

    private double price;

    private int stock;

    private int min;

    private int max;

    /**
     * Creates a new Product with the specified parameters.
     * @param id the ID of the product
     * @param name the name of the product
     * @param price the price of the product
     * @param stock the current inventory level of the product
     * @param min the minimum inventory level of the product
     * @param max the maximum inventory level of the product
     */
    public Product(int id, String name, double price, int stock, int min, int max) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.stock = stock;
        this.min = min;
        this.max = max;
    }

    /**
     * Sets the ID of the product.
     * @param id The unique identifier of the product.
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Sets the name of the product.
     * @param name The name of the product.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Sets the price of the product.
     * @param price The price of the product.
     */
    public void setPrice(double price) {
        this.price = price;
    }

    /**
     * Sets the current stock level of the product.
     * @param stock The current stock level of the product.
     */

    public void setStock(int stock) {
        this.stock = stock;
    }

    /**
     * Sets the minimum stock level of the product.
     * @param min The minimum stock level of the product.
     */
    public void setMin(int min) {
        this.min = min;
    }

    /**
     * Sets the maximum stock level of the product.
     * @param max The maximum stock level of the product.
     */
    public void setMax(int max) {
        this.max = max;
    }

    /**
     * @return The unique identifier of the product.
     */
    public int getId() {
        return id;
    }

    /**
     * @return The name of the product.
     */
    public String getName() {
        return name;
    }

    /**
     * @return The price of the product.
     */
    public double getPrice() {
        return price;
    }

    /**
     * @return The current stock level of the product.
     */
    public int getStock() {
        return stock;
    }

    /**
     * @return The minimum stock level of the product.
     */
    public int getMin() {
        return min;
    }

    /**
     * @return The maximum stock level of the product.
     */
    public int getMax() {
        return max;
    }

    /**
     * Adds a part to the list of associated parts for the product.
     * @param part The part to add to the list of associated parts.
     */
    public void addAssociatedPart(Part part) {
        this.associatedParts.add(part);
    }

    /**
     * Deletes an associated part from the product.
     * @param selectedAssociatedPart The associated part to delete from the product.
     * @return Returns true if the associated part was successfully deleted, false otherwise.
     */
    public boolean deleteAssociatedPart(Part selectedAssociatedPart) {
        if (this.associatedParts.contains(selectedAssociatedPart)) {
            this.associatedParts.remove(selectedAssociatedPart);
            return true;
        } else {
            return false;
        }
    }

    /**
     * @return An observable list of all associated parts for the product.
     */
    public ObservableList<Part> getAllAssociatedParts() { return associatedParts; }
}
