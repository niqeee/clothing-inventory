package com.inventory;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class CSVInventoryDAO implements InventoryDAO {

    private static final String CSV_FILE = "inventory.csv";

    @Override
    public List<ClothingItem> getAllProducts() {
        List<ClothingItem> products = new ArrayList<>();
        File file = new File(CSV_FILE);

        if (!file.exists()) {
            return products;
        }

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            boolean isFirstLine = true; 

            while ((line = br.readLine()) != null) {
                if (isFirstLine) {
                    isFirstLine = false;
                    continue;
                }

                String[] data = line.split(",");
                if (data.length == 6) {
                    int id = Integer.parseInt(data[0]);
                    String name = data[1];
                    double price = Double.parseDouble(data[2]);
                    int quantity = Integer.parseInt(data[3]);
                    String size = data[4];
                    String color = data[5];

                    ClothingItem item = new ClothingItem(id, name, price, quantity, size, color);
                    products.add(item);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return products;
    }

    @Override
    public void addProduct(ClothingItem item) {
        List<ClothingItem> currentList = getAllProducts();
        currentList.add(item);
        saveToFile(currentList); 
    }

    @Override
    public ClothingItem getProduct(int id) {
        List<ClothingItem> products = getAllProducts();
        for (ClothingItem item : products) {
            if (item.getID() == id) {
                return item;
            }
        }
        return null;
    }

    @Override
    public void updateProduct(ClothingItem updatedItem) {
        List<ClothingItem> products = getAllProducts();
        for (int i = 0; i < products.size(); i++) {
            if (products.get(i).getID() == updatedItem.getID()) {
                products.set(i, updatedItem); 
                break;
            }
        }
        saveToFile(products); 
    }

    @Override
    public void deleteProduct(int id) {
        List<ClothingItem> products = getAllProducts();
        products.removeIf(item -> item.getID() == id); 
        saveToFile(products); 
    }

    private void saveToFile(List<ClothingItem> products) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(CSV_FILE))) {
            bw.write("ID,Name,Price,Quantity,Size,Material,Color");
            bw.newLine();

            for (ClothingItem item : products) {
                String line = String.format("%d,%s,%.2f,%d,%s,%s",
                        item.getID(),
                        item.getName(),
                        item.getPrice(),
                        item.getQuantity(),
                        item.getSize(),
                        item.getColor()
                );
                bw.write(line);
                bw.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}