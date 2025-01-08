package edu.itu.mat335e.model;

public class Product {
    private String name;
    private String id;
    private double price;
    private int quantity;
    private String supplier;
    private String expireDate;
    private String category;

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
    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }
    public void setPrice(double price) {
        this.price = price;
    }

    public int getQuantity() {
        return quantity;
    }
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getSupplier() {
        return supplier;
    }
    public void setSupplier(String supplier) {
        this.supplier = supplier;
    }

    public String getExpireDate() {
        return expireDate;
    }
    public void setExpireDate(String expireDate) {
        this.expireDate = expireDate;
    }

    public String getCategory() {
        return category;
    }
    public void setCategory(String category) {
        this.category = category;
    }
}