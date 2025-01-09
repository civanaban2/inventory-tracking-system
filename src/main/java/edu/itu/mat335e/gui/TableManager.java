package edu.itu.mat335e.gui;

import edu.itu.mat335e.database.ProductDAO;
import edu.itu.mat335e.model.Product;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.List;

public class TableManager {
    private DefaultTableModel tableModel;
    private final JTable productTable;
    private final JScrollPane scrollPane;
    private final ProductDAO productDAO;

    public TableManager() {
        productDAO = new ProductDAO();
        productTable = createTable();
        scrollPane = new JScrollPane(productTable);
    }

    public void loadTable(List<Product> products) {
        tableModel.setRowCount(0);
        for (Product product : products) {
            Object[] row = {product.getName(), product.getId(), product.getPrice(),
                    product.getQuantity(), product.getSupplier(),
                    product.getExpireDate(), product.getCategory()};
            tableModel.addRow(row);
        }
    }

    public JTable createTable() {
        String[] columnNames = {"Name", "ID", "Price", "Quantity", "Supplier", "Expire Date", "Category"};
        tableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        return new JTable(tableModel);
    }

    public DefaultTableModel getTableModel() {
        return tableModel;
    }
    public JTable getProductTable() {
        return productTable;
    }
    public JScrollPane getScrollPane() {
        return scrollPane;
    }
    public ProductDAO getProductDAO() {
        return productDAO;
    }
}
