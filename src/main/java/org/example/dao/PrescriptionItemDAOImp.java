package org.example.dao;

import org.example.model.PrescriptionItem;
import org.example.model.Medicine;
import org.example.database.DatabaseConnection;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PrescriptionItemDAOImp implements PrescriptionItemDAO {

    private MedicineDAO medicineDAO = new MedicineDAOImp();

    @Override
    public boolean addPrescriptionItem(PrescriptionItem item) {
        String sql = "INSERT INTO prescription_items (prescription_id, medicine_id, quantity_prescribed, dosage, frequency) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setInt(1, item.getPrescriptionId());
            ps.setInt(2, item.getMedicineId());
            ps.setInt(3, item.getQuantityPrescribed());
            ps.setString(4, item.getDosage());
            ps.setString(5, item.getFrequency());

            int rows = ps.executeUpdate();
            if (rows > 0) {
                ResultSet rs = ps.getGeneratedKeys();
                if (rs.next()) {
                    item.setItemId(rs.getInt(1));
                }
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean updatePrescriptionItem(PrescriptionItem item) {
        String sql = "UPDATE prescription_items SET quantity_prescribed = ?, dosage = ?, frequency = ? WHERE item_id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, item.getQuantityPrescribed());
            ps.setString(2, item.getDosage());
            ps.setString(3, item.getFrequency());
            ps.setInt(4, item.getItemId());

            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean deletePrescriptionItem(int itemId) {
        String sql = "DELETE FROM prescription_items WHERE item_id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, itemId);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public PrescriptionItem getPrescriptionItemById(int itemId) {
        String sql = "SELECT * FROM prescription_items WHERE item_id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, itemId);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                PrescriptionItem item = new PrescriptionItem();
                item.setItemId(rs.getInt("item_id"));
                item.setPrescriptionId(rs.getInt("prescription_id"));
                item.setMedicineId(rs.getInt("medicine_id"));
                item.setQuantityPrescribed(rs.getInt("quantity_prescribed"));
                item.setDosage(rs.getString("dosage"));
                item.setFrequency(rs.getString("frequency"));

                Medicine medicine = medicineDAO.getMedicineById(item.getMedicineId());
                item.setMedicine(medicine);

                return item;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<PrescriptionItem> getItemsByPrescriptionId(int prescriptionId) {
        List<PrescriptionItem> items = new ArrayList<>();
        String sql = "SELECT * FROM prescription_items WHERE prescription_id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, prescriptionId);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                PrescriptionItem item = new PrescriptionItem();
                item.setItemId(rs.getInt("item_id"));
                item.setPrescriptionId(rs.getInt("prescription_id"));
                item.setMedicineId(rs.getInt("medicine_id"));
                item.setQuantityPrescribed(rs.getInt("quantity_prescribed"));
                item.setDosage(rs.getString("dosage"));
                item.setFrequency(rs.getString("frequency"));

                Medicine medicine = medicineDAO.getMedicineById(item.getMedicineId());
                item.setMedicine(medicine);

                items.add(item);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return items;
    }

    @Override
    public List<PrescriptionItem> getItemsByMedicineId(int medicineId) {
        List<PrescriptionItem> items = new ArrayList<>();
        String sql = "SELECT * FROM prescription_items WHERE medicine_id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, medicineId);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                PrescriptionItem item = new PrescriptionItem();
                item.setItemId(rs.getInt("item_id"));
                item.setPrescriptionId(rs.getInt("prescription_id"));
                item.setMedicineId(rs.getInt("medicine_id"));
                item.setQuantityPrescribed(rs.getInt("quantity_prescribed"));
                item.setDosage(rs.getString("dosage"));
                item.setFrequency(rs.getString("frequency"));
                items.add(item);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return items;
    }

    @Override
    public List<PrescriptionItem> getAllPrescriptionItems() {
        List<PrescriptionItem> items = new ArrayList<>();
        String sql = "SELECT * FROM prescription_items";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                PrescriptionItem item = new PrescriptionItem();
                item.setItemId(rs.getInt("item_id"));
                item.setPrescriptionId(rs.getInt("prescription_id"));
                item.setMedicineId(rs.getInt("medicine_id"));
                item.setQuantityPrescribed(rs.getInt("quantity_prescribed"));
                item.setDosage(rs.getString("dosage"));
                item.setFrequency(rs.getString("frequency"));

                Medicine medicine = medicineDAO.getMedicineById(item.getMedicineId());
                item.setMedicine(medicine);

                items.add(item);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return items;
    }
}