package com.inventory;

public class ClothingItem {

    private int ID;
    private String name;
    private double price;
    private int quantity;
    private String size;
    private String color;

    public ClothingItem(int ID, String name, double price, int quantity, String size, String color) {
        this.ID = ID;
        this.name = name;
        this.price = price;
        this.quantity = quantity;
        this.size = size;
        this.color = color;
    }

    public int getID() {
        return ID;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

    public int getQuantity() {
        return quantity;
    }

    public String getSize() {
        return size;
    }

    public String getColor() {
        return color;
    }


    public void setName(String name) {
        this.name = name;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public void setColor(String color) {
        this.color = color;
    }
    
    @Override
    public String toString() {
        return "ID = " + ID + ", name = '" + name + '\'' + ", price = " + price + ", quantity = " + quantity + '}';
    }

}
