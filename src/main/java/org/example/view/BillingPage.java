package org.example.view;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class BillingPage extends JFrame {
    // Form Fields
    public JTextField txtBillId;
    public JTextField txtAppointmentId;
    public JTextField txtDate;
    public JTextField txtAmount;
    public JComboBox<String> comboMethod;
    public JComboBox<String> comboStatus;

    // Buttons
    public JButton btnAddBill;
    public JButton btnUpdatePayment;
    public JButton btnRefresh;

    // Table
    public JTable table;
    public DefaultTableModel model;

    public BillingPage() {
        super("Hospital Management System - Financial Billing Desk");
        setSize(1000, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));

        // Table Panel
        model = new DefaultTableModel();
        model.setColumnIdentifiers(new String[]{"Bill ID", "Appt ID", "Bill Date", "Total (ETB)", "Method", "Status"});
        table = new JTable(model);
        table.setRowHeight(25);
        table.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 12));

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBorder(BorderFactory.createTitledBorder("Invoice Ledger"));
        add(scrollPane, BorderLayout.CENTER);

        // Bottom Panel
        JPanel bottomPanel = new JPanel(new BorderLayout(10, 10));
        bottomPanel.setBorder(BorderFactory.createEmptyBorder(10, 15, 15, 15));

        // Form Panel
        JPanel formPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 5));

        formPanel.add(new JLabel("Bill ID:"));
        txtBillId = new JTextField(5);
        formPanel.add(txtBillId);

        formPanel.add(new JLabel("Appt ID:"));
        txtAppointmentId = new JTextField(5);
        formPanel.add(txtAppointmentId);

        formPanel.add(new JLabel("Date (YYYY-MM-DD):"));
        txtDate = new JTextField(8);
        formPanel.add(txtDate);

        formPanel.add(new JLabel("Amount:"));
        txtAmount = new JTextField(7);
        formPanel.add(txtAmount);

        formPanel.add(new JLabel("Method:"));
        comboMethod = new JComboBox<>(new String[]{"Cash", "CBE Birr", "Telebirr", "Bank Transfer"});
        formPanel.add(comboMethod);

        formPanel.add(new JLabel("Status:"));
        comboStatus = new JComboBox<>(new String[]{"PENDING", "PAID"});
        formPanel.add(comboStatus);

        bottomPanel.add(formPanel, BorderLayout.NORTH);

        // Button Panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 5));

        btnRefresh = new JButton("Refresh");
        btnAddBill = new JButton("Generate Invoice");
        btnUpdatePayment = new JButton("Update Payment");

        btnRefresh.setBackground(new Color(52, 152, 219));
        btnRefresh.setForeground(Color.WHITE);

        btnAddBill.setBackground(new Color(46, 204, 113));
        btnAddBill.setForeground(Color.WHITE);

        btnUpdatePayment.setBackground(new Color(241, 196, 15));
        btnUpdatePayment.setForeground(Color.WHITE);

        buttonPanel.add(btnRefresh);
        buttonPanel.add(btnAddBill);
        buttonPanel.add(btnUpdatePayment);

        bottomPanel.add(buttonPanel, BorderLayout.SOUTH);

        add(bottomPanel, BorderLayout.SOUTH);
    }

    // Getters for Controller
    public DefaultTableModel getTableModel() { return model; }
    public JTable getTable() { return table; }
    public JTextField getTxtBillId() { return txtBillId; }
    public JTextField getTxtAppointmentId() { return txtAppointmentId; }
    public JTextField getTxtDate() { return txtDate; }
    public JTextField getTxtAmount() { return txtAmount; }
    public JComboBox<String> getComboMethod() { return comboMethod; }
    public JComboBox<String> getComboStatus() { return comboStatus; }
    public JButton getBtnAddBill() { return btnAddBill; }
    public JButton getBtnUpdatePayment() { return btnUpdatePayment; }
    public JButton getBtnRefresh() { return btnRefresh; }

    public void showMessage(String message) {
        JOptionPane.showMessageDialog(this, message);
    }

    public void showError(String error) {
        JOptionPane.showMessageDialog(this, error, "Error", JOptionPane.ERROR_MESSAGE);
    }
}