package edu.itu.mat335e.model;

public class Product {
    private final String name;
    private final String id;
    private final double price;
    private final int quantity;
    private final String supplier;
    private final String expireDate;
    private final String category;

    public Product(String name, String id, double price, int quantity, String supplier, String expireDate, String category) {
        this.name = name;
        this.id = id;
        this.price = price;
        this.quantity = quantity;
        this.supplier = supplier;
        this.expireDate = expireDate;
        this.category = category;
    }

    public String getId() {
        return id;
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
    public String getSupplier() {
        return supplier;
    }
    public String getExpireDate() {
        return expireDate;
    }
    public String getCategory() {
        return category;
    }
}