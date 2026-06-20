package org.example.dao;

import org.example.model.Bill;
import org.example.database.DatabaseConnection;
import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class BillDAOImp implements BillDAO {

    @Override
    public boolean addBill(Bill bill) {
        String sql = "INSERT INTO bills (appointment_id, bill_date, total_amount, payment_method, payment_status) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setInt(1, bill.getAppointmentId());
            ps.setDate(2, new java.sql.Date(bill.getBillDate().getTime()));
            ps.setDouble(3, bill.getTotalAmount());
            ps.setString(4, bill.getPaymentMethod());
            ps.setString(5, bill.getPaymentStatus());

            int rows = ps.executeUpdate();
            if (rows > 0) {
                ResultSet rs = ps.getGeneratedKeys();
                if (rs.next()) {
                    bill.setBillId(rs.getInt(1));
                }
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean updateBill(Bill bill) {
        String sql = "UPDATE bills SET appointment_id=?, bill_date=?, total_amount=?, payment_method=?, payment_status=? WHERE bill_id=?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, bill.getAppointmentId());
            ps.setDate(2, new java.sql.Date(bill.getBillDate().getTime()));
            ps.setDouble(3, bill.getTotalAmount());
            ps.setString(4, bill.getPaymentMethod());
            ps.setString(5, bill.getPaymentStatus());
            ps.setInt(6, bill.getBillId());

            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean deleteBill(int billId) {
        String sql = "DELETE FROM bills WHERE bill_id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, billId);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public Bill getBillById(int billId) {
        String sql = "SELECT * FROM bills WHERE bill_id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, billId);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                Bill bill = new Bill();
                bill.setBillId(rs.getInt("bill_id"));
                bill.setAppointmentId(rs.getInt("appointment_id"));
                bill.setBillDate(rs.getDate("bill_date"));
                bill.setTotalAmount(rs.getDouble("total_amount"));
                bill.setPaymentMethod(rs.getString("payment_method"));
                bill.setPaymentStatus(rs.getString("payment_status"));
                return bill;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Bill getBillByAppointmentId(int appointmentId) {
        String sql = "SELECT * FROM bills WHERE appointment_id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, appointmentId);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                Bill bill = new Bill();
                bill.setBillId(rs.getInt("bill_id"));
                bill.setAppointmentId(rs.getInt("appointment_id"));
                bill.setBillDate(rs.getDate("bill_date"));
                bill.setTotalAmount(rs.getDouble("total_amount"));
                bill.setPaymentMethod(rs.getString("payment_method"));
                bill.setPaymentStatus(rs.getString("payment_status"));
                return bill;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Bill> getBillsByPatientId(int patientId) {
        List<Bill> bills = new ArrayList<>();
        String sql = "SELECT b.* FROM bills b " +
                "JOIN appointments a ON b.appointment_id = a.appointment_id " +
                "WHERE a.patient_id = ? ORDER BY b.bill_date DESC";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, patientId);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Bill bill = new Bill();
                bill.setBillId(rs.getInt("bill_id"));
                bill.setAppointmentId(rs.getInt("appointment_id"));
                bill.setBillDate(rs.getDate("bill_date"));
                bill.setTotalAmount(rs.getDouble("total_amount"));
                bill.setPaymentMethod(rs.getString("payment_method"));
                bill.setPaymentStatus(rs.getString("payment_status"));
                bills.add(bill);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return bills;
    }

    @Override
    public List<Bill> getBillsByDate(Date date) {
        List<Bill> bills = new ArrayList<>();
        String sql = "SELECT * FROM bills WHERE bill_date = ? ORDER BY total_amount DESC";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setDate(1, new java.sql.Date(date.getTime()));
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Bill bill = new Bill();
                bill.setBillId(rs.getInt("bill_id"));
                bill.setAppointmentId(rs.getInt("appointment_id"));
                bill.setBillDate(rs.getDate("bill_date"));
                bill.setTotalAmount(rs.getDouble("total_amount"));
                bill.setPaymentMethod(rs.getString("payment_method"));
                bill.setPaymentStatus(rs.getString("payment_status"));
                bills.add(bill);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return bills;
    }

    @Override
    public List<Bill> getBillsByStatus(String status) {
        List<Bill> bills = new ArrayList<>();
        String sql = "SELECT * FROM bills WHERE payment_status = ? ORDER BY bill_date DESC";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, status);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Bill bill = new Bill();
                bill.setBillId(rs.getInt("bill_id"));
                bill.setAppointmentId(rs.getInt("appointment_id"));
                bill.setBillDate(rs.getDate("bill_date"));
                bill.setTotalAmount(rs.getDouble("total_amount"));
                bill.setPaymentMethod(rs.getString("payment_method"));
                bill.setPaymentStatus(rs.getString("payment_status"));
                bills.add(bill);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return bills;
    }

    @Override
    public List<Bill> getPendingBills() {
        return getBillsByStatus("PENDING");
    }

    @Override
    public List<Bill> getAllBills() {
        List<Bill> bills = new ArrayList<>();
        String sql = "SELECT * FROM bills ORDER BY bill_date DESC";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Bill bill = new Bill();
                bill.setBillId(rs.getInt("bill_id"));
                bill.setAppointmentId(rs.getInt("appointment_id"));
                bill.setBillDate(rs.getDate("bill_date"));
                bill.setTotalAmount(rs.getDouble("total_amount"));
                bill.setPaymentMethod(rs.getString("payment_method"));
                bill.setPaymentStatus(rs.getString("payment_status"));
                bills.add(bill);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return bills;
    }

    @Override
    public boolean updatePaymentStatus(int billId, String status, String method) {
        String sql = "UPDATE bills SET payment_status = ?, payment_method = ? WHERE bill_id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, status);
            ps.setString(2, method);
            ps.setInt(3, billId);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public double getTotalRevenue() {
        String sql = "SELECT SUM(total_amount) FROM bills WHERE payment_status = 'PAID'";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            if (rs.next()) {
                return rs.getDouble(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0.0;
    }

    @Override
    public double getTotalRevenueByDateRange(Date startDate, Date endDate) {
        String sql = "SELECT SUM(total_amount) FROM bills WHERE payment_status = 'PAID' AND bill_date BETWEEN ? AND ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setDate(1, new java.sql.Date(startDate.getTime()));
            ps.setDate(2, new java.sql.Date(endDate.getTime()));
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return rs.getDouble(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0.0;
    }
}