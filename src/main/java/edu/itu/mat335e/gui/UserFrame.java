package edu.itu.mat335e.gui;

import edu.itu.mat335e.model.Product;

import javax.swing.*;
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
        setMaximumSize(new Dimension(800, 450));
        setMinimumSize(new Dimension(800, 450));
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
		btnSort = new JComboBox<>(new String[]{
			"Name", "ID", "Price", "Quantity", "Supplier", "Expire Date", "Category"
		});
        updateCategoryList();
        tableManager.loadTable(applyConfigurations());
    }

    @Override
    public void addSettings() {
		btnCategory.setSelectedIndex(0);
		btnSort.setSelectedIndex(0);
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
        tableManager.getScrollPane().setMinimumSize(new Dimension(750, 250));
        tableManager.getScrollPane().setMaximumSize(new Dimension(750, 250));
        listPanel.setLayout(new BoxLayout(listPanel, BoxLayout.Y_AXIS));
		listPanel.setMinimumSize(new Dimension(750, 250));
		listPanel.setMaximumSize(new Dimension(750, 250));
    }

    @Override
    public void addComponents() {
        add(btnCategory);
        add(btnSort);
        listPanel.add(tableManager.getScrollPane());

        getContentPane().setFocusable(false);
        add(listPanel);
        getContentPane().setFocusable(true);
    }

    public void updateCategoryList() {
        btnCategory.removeAllItems();
        btnCategory.addItem("All");
        List<String> uniqueCategories = tableManager.getProductDAO().getUniqueCategories();
        for (String category : uniqueCategories) {
            btnCategory.addItem(category);
        }
    }

    public void categoryBtnListener(JComboBox<String> button) {
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                tableManager.loadTable(applyConfigurations());
            }
        });
    }

    public void sortBtnListener(JComboBox<String> button) {
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                tableManager.loadTable(applyConfigurations());
            }
        });
    }

	public List<Product> applyConfigurations() {
		String sortCriteria = btnSort.getSelectedItem().toString();
		String category = btnCategory.getSelectedItem().toString();
		List<Product> products;

        if (category == null){
            btnCategory.setSelectedItem("All");
			category = "All";
            products = tableManager.getProductDAO().getAllItems();
		}
		else if (category.equals("All")) {
            products = tableManager.getProductDAO().getAllItems();
		}
		else {
			products = tableManager.getProductDAO().getItemsByCategory(category);
		}

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
			default:
				products.sort((p1, p2) -> p1.getName().compareTo(p2.getName()));
				break;
		}
		return products;
	}
}
