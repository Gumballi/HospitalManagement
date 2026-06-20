package org.example.controller;

import org.example.view.PatientDirectoryPage;
import org.example.database.DatabaseConnection;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.sql.*;

public class PatientDirectoryController {
    private PatientDirectoryPage view;

    public PatientDirectoryController(PatientDirectoryPage view) {
        this.view = view;
        initController();
        loadAllPatients();
    }

    private void initController() {
        view.btnSearch.addActionListener(e -> searchPatients());
        view.btnRefresh.addActionListener(e -> loadAllPatients());
    }

    private void loadAllPatients() {
        DefaultTableModel model = view.getTableModel();
        model.setRowCount(0);

        String sql = "SELECT patient_id, first_name, last_name, gender, phone, email, date_of_birth, blood_type, address, emergency_contact FROM patients ORDER BY first_name";

        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Object[] row = {
                        rs.getInt("patient_id"),
                        rs.getString("first_name"),
                        rs.getString("last_name"),
                        rs.getString("gender"),
                        rs.getString("phone"),
                        rs.getString("email"),
                        rs.getDate("date_of_birth"),
                        rs.getString("blood_type"),
                        rs.getString("address"),
                        rs.getString("emergency_contact")
                };
                model.addRow(row);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            view.showError("Error loading patients: " + e.getMessage());
        }
    }

    private void searchPatients() {
        String searchText = view.getTxtSearch().getText().trim();

        if (searchText.isEmpty()) {
            loadAllPatients();
            return;
        }

        DefaultTableModel model = view.getTableModel();
        model.setRowCount(0);

        String sql = "SELECT patient_id, first_name, last_name, gender, phone, email, date_of_birth, blood_type, address, emergency_contact FROM patients WHERE first_name LIKE ? OR last_name LIKE ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, "%" + searchText + "%");
            ps.setString(2, "%" + searchText + "%");
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Object[] row = {
                        rs.getInt("patient_id"),
                        rs.getString("first_name"),
                        rs.getString("last_name"),
                        rs.getString("gender"),
                        rs.getString("phone"),
                        rs.getString("email"),
                        rs.getDate("date_of_birth"),
                        rs.getString("blood_type"),
                        rs.getString("address"),
                        rs.getString("emergency_contact")
                };
                model.addRow(row);
            }

            if (model.getRowCount() == 0) {
                view.showMessage("No patients found matching: " + searchText);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            view.showError("Error searching patients: " + e.getMessage());
        }
    }
}