package edu.itu.mat335e.database;

import edu.itu.mat335e.model.Product;
import java.sql.*;
import java.util.List;
import java.util.ArrayList;

public class ProductDAO {
    private Connection connection;
    private PreparedStatement stmt;

    public ProductDAO() {
        connectToDatabase();
        createTable();
    }
    public void connectToDatabase() {
        try {
            connection = DatabaseConnection.connection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void closeConnection() {
        try {
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void createTable() {
        try {
            stmt = connection.prepareStatement( "CREATE TABLE IF NOT EXISTS products " +
                                                    "(name TEXT, id TEXT, price REAL, quantity INTEGER," +
                                                    " supplier TEXT, expire_date TEXT, category TEXT)");
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void addItem(Product product) {
        try {
            stmt = connection.prepareStatement( "INSERT INTO products " +
                                                    "(name, id, price, quantity, supplier," +
                                                    "expire_date, category) VALUES (?, ?, ?, ?, ?, ?, ?)");
            stmt.setString(1, product.getName());
            stmt.setString(2, product.getId());
            stmt.setDouble(3, product.getPrice());
            stmt.setInt(4, product.getQuantity());
            stmt.setString(5, product.getSupplier());
            stmt.setString(6, product.getExpireDate());
            stmt.setString(7, product.getCategory());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateItem(Product product) {
        String query =  "UPDATE products SET name = ?, price = ?, quantity = ?," +
                        "supplier = ?, expire_date = ?, category = ? WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, product.getName());
            stmt.setDouble(2, product.getPrice());
            stmt.setInt(3, product.getQuantity());
            stmt.setString(4, product.getSupplier());
            stmt.setString(5, product.getExpireDate());
            stmt.setString(6, product.getCategory());
            stmt.setString(7, product.getId());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void removeItem(String id) {
        try {
            stmt = connection.prepareStatement("DELETE FROM products WHERE id = ?");
            stmt.setString(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Product> getAllItems() {
        List<Product> products = new ArrayList<>();
        try {
            stmt = connection.prepareStatement("SELECT * FROM products");
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Product product = new Product(rs.getString("name"), rs.getString("id"), rs.getDouble("price"), rs.getInt("quantity"), rs.getString("supplier"), rs.getString("expire_date"), rs.getString("category"));
                products.add(product);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return products;
    }

    public List<Product> getItemsByCategory(String category) {
        List<Product> products = new ArrayList<>();
        try {
            stmt = connection.prepareStatement("SELECT * FROM products WHERE category = ?");
            stmt.setString(1, category);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Product product = new Product(rs.getString("name"), rs.getString("id"), rs.getDouble("price"), rs.getInt("quantity"), rs.getString("supplier"), rs.getString("expire_date"), rs.getString("category"));
                products.add(product);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return products;
    }

    public List<String> getUniqueCategories() {
        List<String> categories = new ArrayList<>();
        try {
            stmt = connection.prepareStatement("SELECT DISTINCT category FROM products");
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                categories.add(rs.getString("category"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return categories;
    }
}
