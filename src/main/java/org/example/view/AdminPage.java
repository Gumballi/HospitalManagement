package org.example.view;

import org.example.model.Admin;
import javax.swing.*;
import java.awt.*;

public class AdminPage extends JFrame {
    private Admin currentAdmin;

    // Staff Management
    public JButton btnManageDoctors = new JButton("Manage Doctors");
    public JButton btnManageReceptionists = new JButton("Manage Receptionists");

    // Medicine Management
    public JButton btnAddMedicine = new JButton("Add Medicine");
    public JButton btnViewMedicines = new JButton("View Medicines");
    public JButton btnUpdateStock = new JButton("Update Stock");
    public JButton btnLowStockAlert = new JButton("Low Stock Alert");

    // Reports & Settings
    public JButton btnViewReports = new JButton("View Reports");
    public JButton btnSettings = new JButton("Settings");
    public JButton btnLogout = new JButton("Logout");

    public AdminPage(Admin admin) {
        this.currentAdmin = admin;

        setTitle("Ethiopian Hospital Management System - Admin Dashboard");
        setSize(900, 650);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel mainPanel = new JPanel(new BorderLayout(15, 15));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        mainPanel.setBackground(new Color(240, 248, 255));

        // Header
        JPanel headerPanel = new JPanel();
        headerPanel.setBackground(new Color(0, 102, 204));
        headerPanel.setPreferredSize(new Dimension(900, 80));

        JLabel welcomeLabel = new JLabel("Admin Control Center: Welcome, " + admin.getFullName());
        welcomeLabel.setFont(new Font("Segoe UI", Font.BOLD, 18));
        welcomeLabel.setForeground(Color.WHITE);
        headerPanel.add(welcomeLabel);
        mainPanel.add(headerPanel, BorderLayout.NORTH);

        // Content Panel with Tabs
        JTabbedPane tabbedPane = new JTabbedPane();

        // Staff Management Tab
        tabbedPane.addTab("Staff Management", createStaffPanel());

        // Pharmacy Management Tab
        tabbedPane.addTab("Pharmacy Management", createPharmacyPanel());

        // Reports Tab
        tabbedPane.addTab("Reports", createReportsPanel());

        mainPanel.add(tabbedPane, BorderLayout.CENTER);

        // Footer
        JPanel footerPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        footerPanel.setBackground(new Color(240, 248, 255));
        btnLogout.setBackground(new Color(231, 76, 60));
        btnLogout.setForeground(Color.WHITE);
        footerPanel.add(btnLogout);
        mainPanel.add(footerPanel, BorderLayout.SOUTH);

        add(mainPanel);
    }

    private JPanel createStaffPanel() {
        JPanel panel = new JPanel(new GridLayout(2, 1, 15, 15));
        panel.setBackground(new Color(240, 248, 255));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Doctors Section
        JPanel doctorsPanel = new JPanel(new BorderLayout());
        doctorsPanel.setBorder(BorderFactory.createTitledBorder("Doctors Management"));
        doctorsPanel.setBackground(Color.WHITE);

        JPanel doctorsButtonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 15));
        btnManageDoctors.setPreferredSize(new Dimension(200, 50));
        doctorsButtonPanel.add(btnManageDoctors);
        doctorsPanel.add(doctorsButtonPanel, BorderLayout.CENTER);

        // Receptionists Section
        JPanel receptionistsPanel = new JPanel(new BorderLayout());
        receptionistsPanel.setBorder(BorderFactory.createTitledBorder("Receptionists Management"));
        receptionistsPanel.setBackground(Color.WHITE);

        JPanel receptionistsButtonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 15));
        btnManageReceptionists.setPreferredSize(new Dimension(200, 50));
        receptionistsButtonPanel.add(btnManageReceptionists);
        receptionistsPanel.add(receptionistsButtonPanel, BorderLayout.CENTER);

        panel.add(doctorsPanel);
        panel.add(receptionistsPanel);

        return panel;
    }

    private JPanel createPharmacyPanel() {
        JPanel panel = new JPanel(new GridLayout(2, 2, 15, 15));
        panel.setBackground(new Color(240, 248, 255));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        Dimension buttonSize = new Dimension(180, 80);
        Font buttonFont = new Font("Segoe UI", Font.BOLD, 14);

        btnAddMedicine.setPreferredSize(buttonSize);
        btnAddMedicine.setFont(buttonFont);
        btnAddMedicine.setBackground(new Color(46, 204, 113));
        btnAddMedicine.setForeground(Color.WHITE);

        btnViewMedicines.setPreferredSize(buttonSize);
        btnViewMedicines.setFont(buttonFont);
        btnViewMedicines.setBackground(new Color(52, 152, 219));
        btnViewMedicines.setForeground(Color.WHITE);

        btnUpdateStock.setPreferredSize(buttonSize);
        btnUpdateStock.setFont(buttonFont);
        btnUpdateStock.setBackground(new Color(241, 196, 15));
        btnUpdateStock.setForeground(Color.WHITE);

        btnLowStockAlert.setPreferredSize(buttonSize);
        btnLowStockAlert.setFont(buttonFont);
        btnLowStockAlert.setBackground(new Color(230, 126, 34));
        btnLowStockAlert.setForeground(Color.WHITE);

        panel.add(btnAddMedicine);
        panel.add(btnViewMedicines);
        panel.add(btnUpdateStock);
        panel.add(btnLowStockAlert);

        return panel;
    }

    private JPanel createReportsPanel() {
        JPanel panel = new JPanel(new GridLayout(1, 2, 15, 15));
        panel.setBackground(new Color(240, 248, 255));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        Dimension buttonSize = new Dimension(200, 100);
        Font buttonFont = new Font("Segoe UI", Font.BOLD, 14);

        btnViewReports.setPreferredSize(buttonSize);
        btnViewReports.setFont(buttonFont);
        btnViewReports.setBackground(new Color(155, 89, 182));
        btnViewReports.setForeground(Color.WHITE);

        btnSettings.setPreferredSize(buttonSize);
        btnSettings.setFont(buttonFont);
        btnSettings.setBackground(new Color(149, 165, 166));
        btnSettings.setForeground(Color.WHITE);

        panel.add(btnViewReports);
        panel.add(btnSettings);

        return panel;
    }

    public Admin getCurrentAdmin() { return currentAdmin; }

    public void showMessage(String message) {
        JOptionPane.showMessageDialog(this, message);
    }

    public void showError(String error) {
        JOptionPane.showMessageDialog(this, error, "Error", JOptionPane.ERROR_MESSAGE);
    }

    public int showConfirmDialog(String message) {
        return JOptionPane.showConfirmDialog(this, message, "Confirm", JOptionPane.YES_NO_OPTION);
    }
}