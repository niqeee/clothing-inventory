package com.inventory;

import java.util.List;

public interface InventoryDAO {
    void addProduct(ClothingItem item);
    ClothingItem getProduct(int id);
    List<ClothingItem> getAllProducts();
    void updateProduct(ClothingItem item);
    void deleteProduct(int id);
}
