package org.example.view;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class PatientDirectoryPage extends JFrame {
    public JTextField txtSearch;
    public JButton btnSearch;
    public JButton btnRefresh;
    public JTable table;
    public DefaultTableModel model;

    public PatientDirectoryPage() {
        super("Hospital Management System - Patient Master Directory");
        setSize(1100, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));

        // Search Panel
        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
        searchPanel.setBorder(BorderFactory.createEmptyBorder(10, 15, 5, 15));

        searchPanel.add(new JLabel("Search by Name:"));
        txtSearch = new JTextField(20);
        searchPanel.add(txtSearch);

        btnSearch = new JButton("Search");
        btnSearch.setBackground(new Color(52, 152, 219));
        btnSearch.setForeground(Color.WHITE);
        searchPanel.add(btnSearch);

        btnRefresh = new JButton("Show All");
        btnRefresh.setBackground(new Color(46, 204, 113));
        btnRefresh.setForeground(Color.WHITE);
        searchPanel.add(btnRefresh);

        add(searchPanel, BorderLayout.NORTH);

        // Table Panel
        model = new DefaultTableModel();
        model.setColumnIdentifiers(new String[]{
                "Patient ID", "First Name", "Last Name", "Gender", "Phone",
                "Email", "DOB", "Blood Type", "Address", "Emergency Contact"
        });
        table = new JTable(model);
        table.setRowHeight(25);
        table.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 11));

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBorder(BorderFactory.createTitledBorder("Registered Patients"));
        scrollPane.setPreferredSize(new Dimension(1050, 450));
        add(scrollPane, BorderLayout.CENTER);
    }

    // Getters for Controller
    public DefaultTableModel getTableModel() { return model; }
    public JTable getTable() { return table; }
    public JTextField getTxtSearch() { return txtSearch; }
    public JButton getBtnSearch() { return btnSearch; }
    public JButton getBtnRefresh() { return btnRefresh; }

    public void showMessage(String message) {
        JOptionPane.showMessageDialog(this, message);
    }

    public void showError(String error) {
        JOptionPane.showMessageDialog(this, error, "Error", JOptionPane.ERROR_MESSAGE);
    }
}