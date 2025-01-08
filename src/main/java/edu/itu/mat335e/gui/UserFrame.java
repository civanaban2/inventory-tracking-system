package edu.itu.mat335e.gui;

import edu.itu.mat335e.database.ProductDAO;
import edu.itu.mat335e.model.Product;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class UserFrame extends Frame{
    JPanel listPanel;
    JComboBox<String> btnCategory;
    JComboBox<String> btnSort;
    TableManager tableManager;

    public UserFrame() {
        super();
    }

    @Override
    public void setFrameSettings() {
        setTitle("User Panel - Product Management");
        setMaximumSize(new Dimension(800, 350));
        setMinimumSize(new Dimension(800, 350));
        setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
    }

    @Override
    public void initialize() {
        listPanel = new JPanel();
        btnCategory = new JComboBox<String>();
        btnSort = new JComboBox<String>();
        tableManager = new TableManager();
        initializeCategoryList();
        initializeSortList();
        tableManager.loadProducts();
    }

    @Override
    public void addSettings() {
    }

    @Override
    public void addListeners() {
        categoryBtnListener(btnCategory);
        sortBtnListener(btnSort);
    }

    @Override
    public void setLayout() {
        btnCategory.setMinimumSize(new Dimension(150, 30));
        btnCategory.setMaximumSize(new Dimension(150, 30));
        btnSort.setMinimumSize(new Dimension(150, 30));
        btnSort.setMaximumSize(new Dimension(150, 30));
        tableManager.getScrollPane().setMinimumSize(new Dimension(750, 200));
        tableManager.getScrollPane().setMaximumSize(new Dimension(750, 200));
        listPanel.setLayout(new BoxLayout(listPanel, BoxLayout.Y_AXIS));
    }

    @Override
    public void addComponents() {
        listPanel.add(btnCategory);
        listPanel.add(btnSort);
        listPanel.add(tableManager.getScrollPane());

        getContentPane().setFocusable(false);
        add(listPanel);
        getContentPane().setFocusable(true);
    }

    public void initializeCategoryList() {
        btnCategory.removeAllItems();
        btnCategory.addItem("All");
        List<String> uniqueCategories = tableManager.getProductDAO().getUniqueCategories();
        for (String category : uniqueCategories) {
            btnCategory.addItem(category);
        }
    }

    public void initializeSortList() {
        btnSort = new JComboBox<>(new String[]{
                "Name", "ID", "Price", "Quantity", "Supplier", "Expire Date", "Category"
        });
    }

    public void categoryBtnListener(JComboBox<String> button) {
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Object selectedItem = btnCategory.getSelectedItem();
                if (selectedItem != null) {
                    String category = selectedItem.toString();
                    if (category.equals("All")) {
                        tableManager.loadProducts();
                    }
                    else {
                        List<Product> products = tableManager.getProductDAO().getItemsByCategory(category);
                        tableManager.getTableModel().setRowCount(0);
                        for (Product product : products) {
                            Object[] row = {product.getName(), product.getId(), product.getPrice(),
                                    product.getQuantity(), product.getSupplier(),
                                    product.getExpireDate(), product.getCategory()};
                            tableManager.getTableModel().addRow(row);
                        }
                    }
                }
                else {
                    tableManager.loadProducts();
                }
            }
        });
    }

    public void sortBtnListener(JComboBox<String> button) {
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String sortCriteria;
                if (btnSort.getSelectedItem() == null)
                    sortCriteria = "Name";
                else
                    sortCriteria = btnSort.getSelectedItem().toString();
                List<Product> products = tableManager.getProductDAO().getItemsByCategory(sortCriteria);

                switch (sortCriteria) {
                    case "Name":
                        products.sort((p1, p2) -> p1.getName().compareTo(p2.getName()));
                        break;
                    case "ID":
                        products.sort((p1, p2) -> p1.getId().compareTo(p2.getId()));
                        break;
                    case "Price":
                        products.sort((p1, p2) -> Double.compare(p1.getPrice(), p2.getPrice()));
                        break;
                    case "Quantity":
                        products.sort((p1, p2) -> p1.getQuantity() - p2.getQuantity());
                        break;
                    case "Supplier":
                        products.sort((p1, p2) -> p1.getSupplier().compareTo(p2.getSupplier()));
                        break;
                    case "Expire Date":
                        products.sort((p1, p2) -> p1.getExpireDate().compareTo(p2.getExpireDate()));
                        break;
                    case "Category":
                        products.sort((p1, p2) -> p1.getCategory().compareTo(p2.getCategory()));
                        break;
                }

                tableManager.getTableModel().setRowCount(0);
                for (Product product : products) {
                    Object[] row = {product.getName(), product.getId(), product.getPrice(),
                            product.getQuantity(), product.getSupplier(),
                            product.getExpireDate(), product.getCategory()};
                    tableManager.getTableModel().addRow(row);
                }
            }
        });
    }
}
