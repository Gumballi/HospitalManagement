package org.example.view;

import org.example.model.Patient;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class PatientDashboard extends JFrame {
    private Patient currentPatient;

    // UI Components
    public JButton btnViewAppointments;
    public JButton btnViewPrescriptions;
    public JButton btnViewBills;
    public JButton btnUpdateProfile;
    public JButton btnLogout;

    private JTable appointmentsTable;
    private DefaultTableModel appointmentsModel;
    private JTable prescriptionsTable;
    private DefaultTableModel prescriptionsModel;
    private JTable billsTable;
    private DefaultTableModel billsModel;

    public PatientDashboard(Patient patient) {
        this.currentPatient = patient;

        setTitle("Hospital Management System - Patient Dashboard");
        setSize(1000, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // Header
        JPanel headerPanel = new JPanel();
        headerPanel.setBackground(new Color(0, 102, 204));
        headerPanel.setPreferredSize(new Dimension(1000, 80));

        JLabel lblWelcome = new JLabel("Welcome, " + patient.getFullName());
        lblWelcome.setFont(new Font("Segoe UI", Font.BOLD, 20));
        lblWelcome.setForeground(Color.WHITE);
        headerPanel.add(lblWelcome);

        JLabel lblPatientId = new JLabel("Patient ID: " + patient.getPatientId());
        lblPatientId.setFont(new Font("Segoe UI", Font.ITALIC, 14));
        lblPatientId.setForeground(Color.WHITE);
        headerPanel.add(lblPatientId);

        add(headerPanel, BorderLayout.NORTH);

        // Tabbed Pane
        JTabbedPane tabbedPane = new JTabbedPane();

        // Appointments Tab
        tabbedPane.addTab("My Appointments", createAppointmentsPanel());

        // Prescriptions Tab
        tabbedPane.addTab("My Prescriptions", createPrescriptionsPanel());

        // Bills Tab
        tabbedPane.addTab("My Bills", createBillsPanel());

        // Profile Tab
        tabbedPane.addTab("My Profile", createProfilePanel());

        add(tabbedPane, BorderLayout.CENTER);

        // Footer
        JPanel footerPanel = new JPanel();
        footerPanel.setBackground(new Color(200, 200, 200));
        footerPanel.setPreferredSize(new Dimension(1000, 40));

        btnLogout = new JButton("Logout");
        btnLogout.setBackground(new Color(220, 60, 50));
        btnLogout.setForeground(Color.WHITE);
        btnLogout.setFont(new Font("Segoe UI", Font.BOLD, 12));
        footerPanel.add(btnLogout);

        add(footerPanel, BorderLayout.SOUTH);
    }

    private JPanel createAppointmentsPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(Color.WHITE);
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        btnViewAppointments = new JButton("Refresh");
        buttonPanel.add(btnViewAppointments);
        panel.add(buttonPanel, BorderLayout.NORTH);

        String[] columns = {"ID", "Doctor", "Date", "Time", "Type", "Status"};
        appointmentsModel = new DefaultTableModel(columns, 0);
        appointmentsTable = new JTable(appointmentsModel);
        appointmentsTable.setRowHeight(25);
        appointmentsTable.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 12));

        JScrollPane scrollPane = new JScrollPane(appointmentsTable);
        scrollPane.setPreferredSize(new Dimension(950, 450));
        panel.add(scrollPane, BorderLayout.CENTER);

        return panel;
    }

    private JPanel createPrescriptionsPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(Color.WHITE);
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        btnViewPrescriptions = new JButton("Refresh");
        buttonPanel.add(btnViewPrescriptions);
        panel.add(buttonPanel, BorderLayout.NORTH);

        String[] columns = {"ID", "Doctor", "Date", "Medicines"};
        prescriptionsModel = new DefaultTableModel(columns, 0);
        prescriptionsTable = new JTable(prescriptionsModel);
        prescriptionsTable.setRowHeight(25);

        JScrollPane scrollPane = new JScrollPane(prescriptionsTable);
        scrollPane.setPreferredSize(new Dimension(950, 450));
        panel.add(scrollPane, BorderLayout.CENTER);

        return panel;
    }

    private JPanel createBillsPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(Color.WHITE);
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        btnViewBills = new JButton("Refresh");
        buttonPanel.add(btnViewBills);
        panel.add(buttonPanel, BorderLayout.NORTH);

        String[] columns = {"ID", "Date", "Amount", "Status", "Method"};
        billsModel = new DefaultTableModel(columns, 0);
        billsTable = new JTable(billsModel);
        billsTable.setRowHeight(25);

        JScrollPane scrollPane = new JScrollPane(billsTable);
        scrollPane.setPreferredSize(new Dimension(950, 450));
        panel.add(scrollPane, BorderLayout.CENTER);

        return panel;
    }

    private JPanel createProfilePanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(Color.WHITE);
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.anchor = GridBagConstraints.WEST;

        // Display patient information
        gbc.gridx = 0; gbc.gridy = 0;
        panel.add(new JLabel("Patient ID:"), gbc);
        gbc.gridx = 1;
        panel.add(new JLabel(String.valueOf(currentPatient.getPatientId())), gbc);

        gbc.gridx = 0; gbc.gridy = 1;
        panel.add(new JLabel("Full Name:"), gbc);
        gbc.gridx = 1;
        panel.add(new JLabel(currentPatient.getFullName()), gbc);

        gbc.gridx = 0; gbc.gridy = 2;
        panel.add(new JLabel("Gender:"), gbc);
        gbc.gridx = 1;
        panel.add(new JLabel(currentPatient.getGender()), gbc);

        gbc.gridx = 0; gbc.gridy = 3;
        panel.add(new JLabel("Phone:"), gbc);
        gbc.gridx = 1;
        panel.add(new JLabel(currentPatient.getPhone()), gbc);

        gbc.gridx = 0; gbc.gridy = 4;
        panel.add(new JLabel("Email:"), gbc);
        gbc.gridx = 1;
        panel.add(new JLabel(currentPatient.getEmail()), gbc);

        gbc.gridx = 0; gbc.gridy = 5;
        panel.add(new JLabel("Blood Type:"), gbc);
        gbc.gridx = 1;
        panel.add(new JLabel(currentPatient.getBloodType()), gbc);

        gbc.gridx = 0; gbc.gridy = 6;
        panel.add(new JLabel("Address:"), gbc);
        gbc.gridx = 1;
        panel.add(new JLabel(currentPatient.getAddress()), gbc);

        gbc.gridx = 0; gbc.gridy = 7;
        gbc.gridwidth = 2;
        btnUpdateProfile = new JButton("✏️ Update Profile");
        btnUpdateProfile.setBackground(new Color(52, 152, 219));
        btnUpdateProfile.setForeground(Color.WHITE);
        btnUpdateProfile.setFont(new Font("Segoe UI", Font.BOLD, 12));
        panel.add(btnUpdateProfile, gbc);

        return panel;
    }

    // Getters for Controller
    public DefaultTableModel getAppointmentsTableModel() { return appointmentsModel; }
    public DefaultTableModel getPrescriptionsTableModel() { return prescriptionsModel; }
    public DefaultTableModel getBillsTableModel() { return billsModel; }
    public Patient getCurrentPatient() { return currentPatient; }

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