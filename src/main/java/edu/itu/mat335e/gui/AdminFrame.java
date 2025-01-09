package edu.itu.mat335e.gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.List;

import edu.itu.mat335e.model.Product;

public class AdminFrame extends Frame {
    private JPanel textPanel;
    private JPanel buttonPanel;
    private JPanel listPanel;
    private JTextField txtName;
    private JTextField txtId;
    private JTextField txtPrice;
    private JTextField txtQuantity;
    private JTextField txtSupplier;
    private JTextField txtExpireDate;
    private JTextField txtCategory;
    private JButton btnAdd;
    private JButton btnUpdate;
    private JButton btnDelete;
    private JButton btnReport;
    private JButton btnClear;
    private JComboBox<String> btnCategory;
    private JComboBox<String> btnSort;
    private TableManager tableManager;

    public AdminFrame() {
        super();
    }

    @Override
    public void setFrameSettings() {
        setTitle("Admin Panel - Product Management");
        setMaximumSize(new Dimension(800, 600));
        setMinimumSize(new Dimension(800, 600));
        setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
    }

    @Override
    public void initialize() {
        tableManager = new TableManager();
        textPanel = new JPanel();
        buttonPanel = new JPanel();
        listPanel = new JPanel();
        txtName = new JTextField("Name", 20);
        txtId = new JTextField("ID", 20);
        txtPrice = new JTextField("Price", 20);
        txtQuantity = new JTextField("Quantity", 20);
        txtSupplier = new JTextField("Supplier", 20);
        txtExpireDate = new JTextField("Expire Date", 20);
        txtCategory = new JTextField("Category", 20);
        btnAdd = new JButton("Add");
        btnUpdate = new JButton("Update");
        btnDelete = new JButton("Delete");
        btnReport = new JButton("Report");
        btnClear = new JButton("Clear");
        btnCategory = new JComboBox<>();
        btnSort = new JComboBox<>(new String[]{
			"Name", "ID", "Price", "Quantity", "Supplier", "Expire Date", "Category"
		});
		updateCategoryList();
        tableManager.loadTable(applyConfigurations());
    }

    @Override
    public void addSettings() {
        addTextSettings(txtName, "Name");
        addTextSettings(txtId, "ID");
        addTextSettings(txtPrice, "Price");
        addTextSettings(txtQuantity, "Quantity");
        addTextSettings(txtSupplier, "Supplier");
        addTextSettings(txtExpireDate, "Expire Date");
        addTextSettings(txtCategory, "Category");
        addTableSettings(tableManager.getProductTable());
    }

    @Override
    public void addListeners() {
        addBtnListener(btnAdd);
        updateBtnListener(btnUpdate);
        deleteBtnListener(btnDelete);
        reportBtnListener(btnReport);
        clearBtnListener(btnClear);
        categoryBtnListener(btnCategory);
        sortBtnListener(btnSort);
    }

    @Override
    public void setLayout() {
        textPanel.setLayout(new FlowLayout());
        textPanel.setMinimumSize(new Dimension(800, 100));
        textPanel.setMaximumSize(new Dimension(800, 100));
        buttonPanel.setLayout(new FlowLayout());
        buttonPanel.setMinimumSize(new Dimension(800, 50));
        buttonPanel.setMaximumSize(new Dimension(800, 50));
        btnCategory.setMinimumSize(new Dimension(150, 30));
        btnCategory.setMaximumSize(new Dimension(150, 30));
        btnSort.setMinimumSize(new Dimension(150, 30));
        btnSort.setMaximumSize(new Dimension(150, 30));
        tableManager.getScrollPane().setMinimumSize(new Dimension(750, 250));
        tableManager.getScrollPane().setMaximumSize(new Dimension(750, 250));
        listPanel.setLayout(new BoxLayout(listPanel, BoxLayout.Y_AXIS));
        listPanel.setMinimumSize(new Dimension(800, 400));
        listPanel.setMaximumSize(new Dimension(800, 400));
    }

    @Override
    public void addComponents() {
        textPanel.add(txtName);
        textPanel.add(txtId);
        textPanel.add(txtPrice);
        textPanel.add(txtQuantity);
        textPanel.add(txtSupplier);
        textPanel.add(txtExpireDate);
        textPanel.add(txtCategory);
        buttonPanel.add(btnAdd);
        buttonPanel.add(btnUpdate);
        buttonPanel.add(btnDelete);
        buttonPanel.add(btnReport);
        buttonPanel.add(btnClear);
        listPanel.add(btnCategory);
        listPanel.add(btnSort);
        listPanel.add(tableManager.getScrollPane());

        getContentPane().setFocusable(false);
        add(textPanel);
        add(buttonPanel);
        add(listPanel);
        getContentPane().setFocusable(true);
    }

    public void addTextSettings(JTextField textBox, String text) {
        textBox.setForeground(Color.GRAY);
        textBox.setText(text);
        textBox.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                if (textBox.getText().equals(text)) {
                    textBox.setText("");
                    textBox.setForeground(Color.BLACK);
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (textBox.getText().isEmpty()) {
                    textBox.setForeground(Color.GRAY);
                    textBox.setText(text);
                }
            }
        });
    }

    public void addTableSettings(JTable productTable) {
        productTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        productTable.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    int selectedRow = productTable.getSelectedRow();
                    if (selectedRow != -1) {
                        txtName.setText(tableManager.getTableModel().getValueAt(selectedRow, 0).toString());
                        txtId.setText(tableManager.getTableModel().getValueAt(selectedRow, 1).toString());
                        txtPrice.setText(tableManager.getTableModel().getValueAt(selectedRow, 2).toString());
                        txtQuantity.setText(tableManager.getTableModel().getValueAt(selectedRow, 3).toString());
                        txtSupplier.setText(tableManager.getTableModel().getValueAt(selectedRow, 4).toString());
                        txtExpireDate.setText(tableManager.getTableModel().getValueAt(selectedRow, 5).toString());
                        txtCategory.setText(tableManager.getTableModel().getValueAt(selectedRow, 6).toString());
                        txtName.setForeground(Color.BLACK);
                        txtId.setForeground(Color.BLACK);
                        txtPrice.setForeground(Color.BLACK);
                        txtQuantity.setForeground(Color.BLACK);
                        txtSupplier.setForeground(Color.BLACK);
                        txtExpireDate.setForeground(Color.BLACK);
                        txtCategory.setForeground(Color.BLACK);
                    }
                }
            }
        });
    }

    public void updateCategoryList() {
        btnCategory.removeAllItems();
        btnCategory.addItem("All");
        List<String> uniqueCategories = tableManager.getProductDAO().getUniqueCategories();
        for (String category : uniqueCategories) {
            btnCategory.addItem(category);
        }
    }

    public void addBtnListener(JButton button) {
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String name = txtName.getText();
                String id = txtId.getText();
                double price = Double.parseDouble(txtPrice.getText());
                int quantity = Integer.parseInt(txtQuantity.getText());
                String supplier = txtSupplier.getText();
                String expireDate = txtExpireDate.getText();
                String category = txtCategory.getText();
                List<Product> products = tableManager.getProductDAO().getAllItems();
                boolean idExists = products.stream().anyMatch(product -> product.getId().equals(id));

                if (idExists) {
                    JOptionPane.showMessageDialog(null, "Bu ID zaten mevcut. Lütfen farklı bir ID girin.", "Hata", JOptionPane.ERROR_MESSAGE);
                } else {
                    Product product = new Product(name, id, price, quantity, supplier, expireDate, category);
                    tableManager.getProductDAO().addItem(product);
					tableManager.loadTable(applyConfigurations());                
				}
            }
        });
    }

    public void updateBtnListener(JButton button) {
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow = tableManager.getProductTable().getSelectedRow();
                if (selectedRow != -1) {
                    String name = txtName.getText();
                    String id = txtId.getText();
                    double price = Double.parseDouble(txtPrice.getText());
                    int quantity = Integer.parseInt(txtQuantity.getText());
                    String supplier = txtSupplier.getText();
                    String expireDate = txtExpireDate.getText();
                    String category = txtCategory.getText();

                    Product product = new Product(name, id, price, quantity, supplier, expireDate, category);
                    tableManager.getProductDAO().updateItem(product);
					tableManager.loadTable(applyConfigurations());
				}
            }
        });
    }

    public void deleteBtnListener(JButton button) {
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow = tableManager.getProductTable().getSelectedRow();
                if (selectedRow != -1) {
                    String id = tableManager.getTableModel().getValueAt(selectedRow, 1).toString();
                    tableManager.getProductDAO().removeItem(id);
                    tableManager.loadTable(applyConfigurations());                
				}
            }
        });
    }

    public void reportBtnListener(JButton button) {
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ReportFrame reportFrame = new ReportFrame();
                reportFrame.setVisible(true);
            }
        });
    }

    public void clearBtnListener(JButton button) {
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                txtName.setText("Name");
                txtId.setText("ID");
                txtPrice.setText("Price");
                txtQuantity.setText("Quantity");
                txtSupplier.setText("Supplier");
                txtExpireDate.setText("Expire Date");
                txtCategory.setText("Category");
                txtName.setForeground(Color.GRAY);
                txtId.setForeground(Color.GRAY);
                txtPrice.setForeground(Color.GRAY);
                txtQuantity.setForeground(Color.GRAY);
                txtSupplier.setForeground(Color.GRAY);
                txtExpireDate.setForeground(Color.GRAY);
                txtCategory.setForeground(Color.GRAY);
            }
        });
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
