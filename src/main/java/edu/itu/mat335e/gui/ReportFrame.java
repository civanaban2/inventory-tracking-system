package edu.itu.mat335e.gui;

import edu.itu.mat335e.model.Product;

import javax.swing.*;
import java.awt.*;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class ReportFrame extends Frame{
    private JComboBox<Integer> filterComboBox;
    private JComboBox<String> sortComboBox;
    private TableManager tableManager;
    private JPanel topPanel;
    private JPanel listPanel;
	private List<Product> products;

    public ReportFrame() {
        super();
    }

    @Override
    public void setFrameSettings() {
        setTitle("Low Stock Report");
        setSize(600, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());
    }

    @Override
    public void initialize() {
        filterComboBox = new JComboBox<>(new Integer[]{5, 10, 20, 50, 100});
        sortComboBox = new JComboBox<>(new String[]{
                "Quantity Ascending",
                "Quantity Descending",
                "Alphabetically Ascending",
                "Alphabetically Descending"
        });
        tableManager = new TableManager();
        topPanel = new JPanel(new FlowLayout());
        listPanel = new JPanel(new BorderLayout());
        applyConfigurations();
    }

    @Override
    public void addSettings() {
        filterComboBox.setSelectedIndex(1);
        sortComboBox.setSelectedIndex(0);
    }

    @Override
    public void addListeners() {
        filterComboBox.addActionListener(e -> applyConfigurations());
        sortComboBox.addActionListener(e -> applyConfigurations());
    }

    @Override
    public void setLayout() {
        filterComboBox.setMinimumSize(new Dimension(100, 30));
        filterComboBox.setMaximumSize(new Dimension(100, 30));
        sortComboBox.setMinimumSize(new Dimension(200, 30));
        sortComboBox.setMaximumSize(new Dimension(200, 30));
        listPanel.setMinimumSize(new Dimension(600, 300));
        listPanel.setMaximumSize(new Dimension(600, 300));
    }

    @Override
    public void addComponents() {
        topPanel.add(new JLabel("Filter stock less than:"));
        topPanel.add(filterComboBox);
        topPanel.add(new JLabel("Sort by:"));
        topPanel.add(sortComboBox);
        listPanel.add(tableManager.getScrollPane());

        add(topPanel, BorderLayout.NORTH);
        add(listPanel, BorderLayout.CENTER);
    }

    private void applyConfigurations() {
        int selectedThreshold = (Integer) filterComboBox.getSelectedItem();
        String sortCriteria = (String) sortComboBox.getSelectedItem();

        products = tableManager.getProductDAO().getAllItems().stream()
                .filter(product -> product.getQuantity() < selectedThreshold)
                .collect(Collectors.toList());

        switch (sortCriteria) {
            case "Quantity Descending":
                products.sort((p1, p2) -> p2.getQuantity() - p1.getQuantity());
                break;
            case "Alphabetically Ascending":
                products.sort(Comparator.comparing(Product::getName));
                break;
            case "Alphabetically Descending":
                products.sort((p1, p2) -> p2.getName().compareTo(p1.getName()));
                break;
            case "Quantity Ascending":
            case null, default:
                products.sort(Comparator.comparingInt(Product::getQuantity));
        }
        tableManager.loadTable(products);
    }
}
