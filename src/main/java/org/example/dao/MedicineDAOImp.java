package org.example.dao;

import org.example.model.Medicine;
import org.example.database.DatabaseConnection;
import java.sql.*;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class MedicineDAOImp implements MedicineDAO {

    @Override
    public boolean addMedicine(Medicine medicine) {
        String sql = "INSERT INTO medicines (medicine_name, dosage_form, price, quantity_in_stock, expiry_date) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setString(1, medicine.getMedicineName());
            ps.setString(2, medicine.getDosageForm());
            ps.setDouble(3, medicine.getPrice());
            ps.setInt(4, medicine.getQuantityInStock());
            ps.setDate(5, medicine.getExpiryDate() != null ? new java.sql.Date(medicine.getExpiryDate().getTime()) : null);

            int rows = ps.executeUpdate();
            if (rows > 0) {
                ResultSet rs = ps.getGeneratedKeys();
                if (rs.next()) {
                    medicine.setMedicineId(rs.getInt(1));
                }
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean updateMedicine(Medicine medicine) {
        String sql = "UPDATE medicines SET medicine_name=?, dosage_form=?, price=?, quantity_in_stock=?, expiry_date=? WHERE medicine_id=?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, medicine.getMedicineName());
            ps.setString(2, medicine.getDosageForm());
            ps.setDouble(3, medicine.getPrice());
            ps.setInt(4, medicine.getQuantityInStock());
            ps.setDate(5, medicine.getExpiryDate() != null ? new java.sql.Date(medicine.getExpiryDate().getTime()) : null);
            ps.setInt(6, medicine.getMedicineId());

            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean deleteMedicine(int medicineId) {
        String sql = "DELETE FROM medicines WHERE medicine_id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, medicineId);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public Medicine getMedicineById(int medicineId) {
        String sql = "SELECT * FROM medicines WHERE medicine_id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, medicineId);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                Medicine medicine = new Medicine();
                medicine.setMedicineId(rs.getInt("medicine_id"));
                medicine.setMedicineName(rs.getString("medicine_name"));
                medicine.setDosageForm(rs.getString("dosage_form"));
                medicine.setPrice(rs.getDouble("price"));
                medicine.setQuantityInStock(rs.getInt("quantity_in_stock"));
                medicine.setExpiryDate(rs.getDate("expiry_date"));
                return medicine;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Medicine getMedicineByName(String name) {
        String sql = "SELECT * FROM medicines WHERE medicine_name = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, name);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                Medicine medicine = new Medicine();
                medicine.setMedicineId(rs.getInt("medicine_id"));
                medicine.setMedicineName(rs.getString("medicine_name"));
                medicine.setDosageForm(rs.getString("dosage_form"));
                medicine.setPrice(rs.getDouble("price"));
                medicine.setQuantityInStock(rs.getInt("quantity_in_stock"));
                medicine.setExpiryDate(rs.getDate("expiry_date"));
                return medicine;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Medicine> getAllMedicines() {
        List<Medicine> medicines = new ArrayList<>();
        String sql = "SELECT * FROM medicines ORDER BY medicine_name";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Medicine medicine = new Medicine();
                medicine.setMedicineId(rs.getInt("medicine_id"));
                medicine.setMedicineName(rs.getString("medicine_name"));
                medicine.setDosageForm(rs.getString("dosage_form"));
                medicine.setPrice(rs.getDouble("price"));
                medicine.setQuantityInStock(rs.getInt("quantity_in_stock"));
                medicine.setExpiryDate(rs.getDate("expiry_date"));
                medicines.add(medicine);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return medicines;
    }

    @Override
    public List<Medicine> getLowStockMedicines(int threshold) {
        List<Medicine> medicines = new ArrayList<>();
        String sql = "SELECT * FROM medicines WHERE quantity_in_stock <= ? ORDER BY quantity_in_stock";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, threshold);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Medicine medicine = new Medicine();
                medicine.setMedicineId(rs.getInt("medicine_id"));
                medicine.setMedicineName(rs.getString("medicine_name"));
                medicine.setDosageForm(rs.getString("dosage_form"));
                medicine.setPrice(rs.getDouble("price"));
                medicine.setQuantityInStock(rs.getInt("quantity_in_stock"));
                medicine.setExpiryDate(rs.getDate("expiry_date"));
                medicines.add(medicine);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return medicines;
    }

    @Override
    public List<Medicine> getExpiringMedicines(int daysThreshold) {
        List<Medicine> medicines = new ArrayList<>();
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DAY_OF_MONTH, daysThreshold);
        Date thresholdDate = cal.getTime();

        String sql = "SELECT * FROM medicines WHERE expiry_date <= ? ORDER BY expiry_date";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setDate(1, new java.sql.Date(thresholdDate.getTime()));
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Medicine medicine = new Medicine();
                medicine.setMedicineId(rs.getInt("medicine_id"));
                medicine.setMedicineName(rs.getString("medicine_name"));
                medicine.setDosageForm(rs.getString("dosage_form"));
                medicine.setPrice(rs.getDouble("price"));
                medicine.setQuantityInStock(rs.getInt("quantity_in_stock"));
                medicine.setExpiryDate(rs.getDate("expiry_date"));
                medicines.add(medicine);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return medicines;
    }

    @Override
    public boolean updateStock(int medicineId, int quantity) {
        String sql = "UPDATE medicines SET quantity_in_stock = quantity_in_stock + ? WHERE medicine_id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, quantity);
            ps.setInt(2, medicineId);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean updateStock(String medicineName, int quantity) {
        String sql = "UPDATE medicines SET quantity_in_stock = quantity_in_stock + ? WHERE medicine_name = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, quantity);
            ps.setString(2, medicineName);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}