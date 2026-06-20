package org.example.controller;

import org.example.view.AdminPage;
import org.example.model.Medicine;
import org.example.dao.MedicineDAO;
import org.example.dao.MedicineDAOImp;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.*;
import java.util.List;

public class MedicineController {
    private AdminPage view;
    private MedicineDAO medicineDAO;

    public MedicineController(AdminPage view) {
        this.view = view;
        this.medicineDAO = new MedicineDAOImp();
    }

    public void addMedicine() {
        JTextField txtName = new JTextField(15);
        JTextField txtDosageForm = new JTextField(10);
        JTextField txtPrice = new JTextField(10);
        JTextField txtStock = new JTextField(10);
        JTextField txtExpiry = new JTextField(10);

        JPanel panel = new JPanel(new GridLayout(0, 2, 10, 10));
        panel.add(new JLabel("Medicine Name:"));
        panel.add(txtName);
        panel.add(new JLabel("Dosage Form:"));
        panel.add(txtDosageForm);
        panel.add(new JLabel("Price (ETB):"));
        panel.add(txtPrice);
        panel.add(new JLabel("Initial Stock:"));
        panel.add(txtStock);
        panel.add(new JLabel("Expiry Date (YYYY-MM-DD):"));
        panel.add(txtExpiry);

        int result = JOptionPane.showConfirmDialog(view, panel, "Add New Medicine",
                JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

        if (result == JOptionPane.OK_OPTION) {
            try {
                Medicine medicine = new Medicine();
                medicine.setMedicineName(txtName.getText().trim());
                medicine.setDosageForm(txtDosageForm.getText().trim());
                medicine.setPrice(Double.parseDouble(txtPrice.getText().trim()));
                medicine.setQuantityInStock(Integer.parseInt(txtStock.getText().trim()));

                if (!txtExpiry.getText().trim().isEmpty()) {
                    medicine.setExpiryDate(Date.valueOf(txtExpiry.getText().trim()));
                }

                if (medicineDAO.addMedicine(medicine)) {
                    //Call UML Business Method
                    medicine.addMedicine(medicine.getMedicineName(), medicine.getDosageForm(),
                            medicine.getPrice(), medicine.getQuantityInStock(),
                            medicine.getExpiryDate());

                    view.showMessage("Medicine added successfully!");
                    viewMedicines();
                } else {
                    view.showError("Failed to add medicine!");
                }
            } catch (NumberFormatException e) {
                view.showError("Invalid number format!");
            }
        }
    }

    public void viewMedicines() {
        List<Medicine> medicines = medicineDAO.getAllMedicines();

        if (medicines.isEmpty()) {
            view.showMessage("No medicines found in inventory.");
            return;
        }

        String[] columns = {"ID", "Medicine Name", "Dosage Form", "Price (ETB)", "Stock", "Expiry Date"};
        DefaultTableModel model = new DefaultTableModel(columns, 0);

        for (Medicine m : medicines) {
            Object[] row = {
                    m.getMedicineId(),
                    m.getMedicineName(),
                    m.getDosageForm() != null ? m.getDosageForm() : "N/A",
                    String.format("%.2f", m.getPrice()),
                    m.getQuantityInStock(),
                    m.getExpiryDate() != null ? m.getExpiryDate() : "N/A"
            };
            model.addRow(row);
        }

        JTable table = new JTable(model);
        table.setRowHeight(25);
        table.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 12));
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setPreferredSize(new Dimension(700, 400));

        JOptionPane.showMessageDialog(view, scrollPane, "Medicine Inventory", JOptionPane.INFORMATION_MESSAGE);
    }

    public void updateStock() {
        String name = JOptionPane.showInputDialog(view, "Enter Medicine Name to Restock:");
        if (name == null || name.trim().isEmpty()) return;

        String quantityStr = JOptionPane.showInputDialog(view, "Enter Quantity to Add:");
        if (quantityStr == null || quantityStr.trim().isEmpty()) return;

        try {
            int quantity = Integer.parseInt(quantityStr.trim());

            // Get medicine before update to call business method
            Medicine medicine = medicineDAO.getMedicineByName(name);

            if (medicineDAO.updateStock(name, quantity)) {
                //Call UML Business Method
                if (medicine != null) {
                    medicine.restock(quantity);
                }
                view.showMessage("Stock updated successfully!");
                viewMedicines();
            } else {
                view.showError("Medicine not found!");
            }
        } catch (NumberFormatException e) {
            view.showError("Invalid quantity!");
        }
    }

    public void checkLowStock() {
        List<Medicine> lowStockMedicines = medicineDAO.getLowStockMedicines(50);

        if (lowStockMedicines.isEmpty()) {
            view.showMessage("All medicines have sufficient stock (>50 units).");
            return;
        }

        StringBuilder report = new StringBuilder("LOW STOCK ALERT\n\n");
        report.append("The following medicines have stock below 50 units:\n\n");

        for (Medicine m : lowStockMedicines) {
            report.append("").append(m.getMedicineName())
                    .append(" - Current Stock: ").append(m.getQuantityInStock())
                    .append(" units\n");

            //Call UML Business Method
            m.checkStock();
        }

        JTextArea textArea = new JTextArea(report.toString());
        textArea.setEditable(false);
        textArea.setFont(new Font("Monospaced", Font.PLAIN, 12));

        JOptionPane.showMessageDialog(view, new JScrollPane(textArea), "Low Stock Alert",
                JOptionPane.WARNING_MESSAGE);
    }
}