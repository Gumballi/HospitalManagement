package org.example.view;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class AppointmentPage extends JFrame {
    // Form Fields
    public JTextField txtAppointmentId;
    public JTextField txtPatientId;
    public JTextField txtDoctorId;
    public JTextField txtDate;
    public JTextField txtTime;
    public JTextField txtVisitType;
    public JTextField txtStatus;

    // Buttons
    public JButton btnAdd;
    public JButton btnUpdate;
    public JButton btnDelete;
    public JButton btnSearch;

    // Table
    public JTable table;
    public DefaultTableModel model;

    public AppointmentPage() {
        super("Hospital Management System - Appointment Desk");
        setSize(1000, 650);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));

        // Form Panel
        JPanel formPanel = new JPanel(new GridLayout(4, 4, 10, 10));
        formPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 0, 15));

        formPanel.add(new JLabel("Appointment ID:"));
        txtAppointmentId = new JTextField();
        formPanel.add(txtAppointmentId);

        formPanel.add(new JLabel("Patient ID:"));
        txtPatientId = new JTextField();
        formPanel.add(txtPatientId);

        formPanel.add(new JLabel("Doctor ID:"));
        txtDoctorId = new JTextField();
        formPanel.add(txtDoctorId);

        formPanel.add(new JLabel("Date (YYYY-MM-DD):"));
        txtDate = new JTextField();
        formPanel.add(txtDate);

        formPanel.add(new JLabel("Time (HH:MM):"));
        txtTime = new JTextField();
        formPanel.add(txtTime);

        formPanel.add(new JLabel("Visit Type:"));
        txtVisitType = new JTextField();
        formPanel.add(txtVisitType);

        formPanel.add(new JLabel("Status:"));
        txtStatus = new JTextField();
        formPanel.add(txtStatus);

        add(formPanel, BorderLayout.NORTH);

        // Table Panel
        model = new DefaultTableModel();
        model.setColumnIdentifiers(new String[]{"Appt ID", "Patient ID", "Doctor ID", "Date", "Time", "Type", "Status"});
        table = new JTable(model);
        table.setRowHeight(25);
        table.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 12));

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBorder(BorderFactory.createTitledBorder("📋 Appointment Registers"));
        scrollPane.setPreferredSize(new Dimension(950, 350));
        add(scrollPane, BorderLayout.CENTER);

        // Button Panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 10));
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(10, 15, 15, 15));

        btnAdd = new JButton("Add");
        btnAdd.setBackground(new Color(46, 204, 113));
        btnAdd.setForeground(Color.WHITE);

        btnUpdate = new JButton("Update");
        btnUpdate.setBackground(new Color(52, 152, 219));
        btnUpdate.setForeground(Color.WHITE);

        btnDelete = new JButton("Delete");
        btnDelete.setBackground(new Color(231, 76, 60));
        btnDelete.setForeground(Color.WHITE);

        btnSearch = new JButton("Search");
        btnSearch.setBackground(new Color(155, 89, 182));
        btnSearch.setForeground(Color.WHITE);

        buttonPanel.add(btnAdd);
        buttonPanel.add(btnUpdate);
        buttonPanel.add(btnDelete);
        buttonPanel.add(btnSearch);

        add(buttonPanel, BorderLayout.SOUTH);
    }

    // Getters for Controller
    public DefaultTableModel getTableModel() { return model; }
    public JTable getTable() { return table; }
    public JTextField getTxtAppointmentId() { return txtAppointmentId; }
    public JTextField getTxtPatientId() { return txtPatientId; }
    public JTextField getTxtDoctorId() { return txtDoctorId; }
    public JTextField getTxtDate() { return txtDate; }
    public JTextField getTxtTime() { return txtTime; }
    public JTextField getTxtVisitType() { return txtVisitType; }
    public JTextField getTxtStatus() { return txtStatus; }
    public JButton getBtnAdd() { return btnAdd; }
    public JButton getBtnUpdate() { return btnUpdate; }
    public JButton getBtnDelete() { return btnDelete; }
    public JButton getBtnSearch() { return btnSearch; }

    public void showMessage(String message) {
        JOptionPane.showMessageDialog(this, message);
    }

    public void showError(String error) {
        JOptionPane.showMessageDialog(this, error, "Error", JOptionPane.ERROR_MESSAGE);
    }
}