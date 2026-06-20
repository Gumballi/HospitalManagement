package org.example.controller;

import org.example.view.BillingPage;
import org.example.model.Bill;
import org.example.database.DatabaseConnection;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.sql.*;

public class BillingController {
    private BillingPage view;

    public BillingController(BillingPage view) {
        this.view = view;
        initController();
        loadBills();
    }

    private void initController() {
        view.btnRefresh.addActionListener(e -> loadBills());
        view.btnAddBill.addActionListener(e -> addBill());
        view.btnUpdatePayment.addActionListener(e -> updatePayment());
    }

    private void loadBills() {
        DefaultTableModel model = view.getTableModel();
        model.setRowCount(0);

        String sql = "SELECT * FROM bills ORDER BY bill_date DESC";

        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Object[] row = {
                        rs.getInt("bill_id"),
                        rs.getInt("appointment_id"),
                        rs.getDate("bill_date"),
                        "ETB " + String.format("%,.2f", rs.getDouble("total_amount")),
                        rs.getString("payment_method") != null ? rs.getString("payment_method") : "-",
                        rs.getString("payment_status")
                };
                model.addRow(row);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            view.showError("Error loading bills: " + e.getMessage());
        }
    }

    private void addBill() {
        String appointmentId = view.getTxtAppointmentId().getText().trim();
        String date = view.getTxtDate().getText().trim();
        String amount = view.getTxtAmount().getText().trim();
        String method = (String) view.getComboMethod().getSelectedItem();
        String status = (String) view.getComboStatus().getSelectedItem();

        if (appointmentId.isEmpty() || date.isEmpty() || amount.isEmpty()) {
            view.showError("Please fill all required fields!");
            return;
        }

        String sql = "INSERT INTO bills (appointment_id, bill_date, total_amount, payment_method, payment_status) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, Integer.parseInt(appointmentId));
            ps.setDate(2, Date.valueOf(date));
            ps.setDouble(3, Double.parseDouble(amount));
            ps.setString(4, method);
            ps.setString(5, status);

            if (ps.executeUpdate() > 0) {
                //Call UML Business Method
                Bill newBill = new Bill();
                newBill.generateBill(Integer.parseInt(appointmentId), Double.parseDouble(amount));

                view.showMessage("Bill generated successfully!");
                loadBills();
                clearFields();
            } else {
                view.showError("Failed to generate bill!");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            view.showError("Database error: " + e.getMessage());
        }
    }

    private void updatePayment() {
        String billId = view.getTxtBillId().getText().trim();
        String method = (String) view.getComboMethod().getSelectedItem();
        String status = (String) view.getComboStatus().getSelectedItem();

        if (billId.isEmpty()) {
            view.showError("Please enter Bill ID to update!");
            return;
        }

        String sql = "UPDATE bills SET payment_method = ?, payment_status = ? WHERE bill_id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, method);
            ps.setString(2, status);
            ps.setInt(3, Integer.parseInt(billId));

            if (ps.executeUpdate() > 0) {
                //Call UML Business Method
                if (status.equals("PAID")) {
                    Bill bill = new Bill();
                    bill.markAsPaid(method);
                }

                view.showMessage("Payment status updated successfully!");
                loadBills();
                clearFields();
            } else {
                view.showError("Bill ID not found!");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            view.showError("Database error: " + e.getMessage());
        }
    }

    private void clearFields() {
        view.getTxtBillId().setText("");
        view.getTxtAppointmentId().setText("");
        view.getTxtDate().setText("");
        view.getTxtAmount().setText("");
        view.getComboMethod().setSelectedIndex(0);
        view.getComboStatus().setSelectedIndex(0);
    }
}