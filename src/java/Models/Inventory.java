package Models;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Inventory {

    private static ObservableList<Part> allParts = FXCollections.observableArrayList();;
    private static ObservableList<Product> allProducts = FXCollections.observableArrayList();;

    public static void addPart(Part newPart) {
        allParts.add(newPart);
    }

    public static void addProduct(Product newProduct) {
        allProducts.add(newProduct);
    }

    public static Part lookupPart(int partId) {
        ObservableList<Part> allParts = getAllParts();
        for(Part part: allParts) {
            if(part.getId() == partId) {
                return part;
            }
        }
        return null;
    }

    public static Product lookupProduct(int productId) {
        ObservableList<Product> allProducts = getAllProducts();

        for(Product product: allProducts) {
            if(product.getId() == productId) {
                return product;
            }
        }
        return null;
    }

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

    public static void updatePart(int index, Part selectedPart) {
        getAllParts().set(index, selectedPart);
    }

    public static void updateProduct(int index, Product newProduct) {
        getAllProducts().set(index, newProduct);
    }

    public static boolean deletePart(Part selectedPart) {
        if (allParts.contains(selectedPart)) {
            allParts.remove(selectedPart);
            return true;
        } else {
            return false;
        }
    }

    public static boolean deleteProduct(Part selectedProduct) {
        if (allProducts.contains(selectedProduct)) {
            allProducts.remove(selectedProduct);
            return true;
        } else {
            return false;
        }
    }

    public static ObservableList<Part> getAllParts() {
        return allParts;
    }

    public static ObservableList<Product> getAllProducts() {
        return allProducts;
    }



}
