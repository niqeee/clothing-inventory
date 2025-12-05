package com.inventory;

import java.util.List;

public class ConsoleTest {
    public static void main(String[] args) {
        // 1. Create our Tool (The DAO)
        InventoryDAO dao = new CSVInventoryDAO();

        System.out.println("--- STARTING INVENTORY TEST ---");

        // 2. Test: Adding Items
        // REMEMBER: The order is ID, Name, Price, Qty, Size, Color
        // (I removed the "Material" argument here)
        ClothingItem item1 = new ClothingItem(1, "Summer T-Shirt", 19.99, 50, "M", "Red");
        ClothingItem item2 = new ClothingItem(2, "Winter Jacket", 99.99, 10, "L", "Black");

        dao.addProduct(item1);
        dao.addProduct(item2);
        System.out.println("✅ Added 2 items.");

        // 3. Test: Reading All Items
        System.out.println("\n--- CURRENT INVENTORY ---");
        List<ClothingItem> inventory = dao.getAllProducts();
        for (ClothingItem item : inventory) {
            System.out.println(item);
        }

        // 4. Test: Updating an Item
        System.out.println("\n--- UPDATING ITEM ID 1 ---");
        ClothingItem toUpdate = dao.getProduct(1);
        if (toUpdate != null) {
            toUpdate.setPrice(15.99); // Sale price!
            toUpdate.setQuantity(49); // Sold one!
            dao.updateProduct(toUpdate);
            System.out.println("✅ Item 1 updated (Price dropped to $15.99).");
        }

        // 5. Test: Deleting an Item
        System.out.println("\n--- DELETING ITEM ID 2 ---");
        dao.deleteProduct(2);
        System.out.println("✅ Item 2 deleted.");

        // 6. Final Check
        System.out.println("\n--- FINAL INVENTORY ---");
        inventory = dao.getAllProducts();
        for (ClothingItem item : inventory) {
            System.out.println(item);
        }
        
        System.out.println("--- TEST COMPLETE ---");
    }
}