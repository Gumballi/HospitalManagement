package org.example.dao;

import org.example.model.Bill;
import java.util.Date;
import java.util.List;

public interface BillDAO {
    boolean addBill(Bill bill);
    boolean updateBill(Bill bill);
    boolean deleteBill(int billId);
    Bill getBillById(int billId);
    Bill getBillByAppointmentId(int appointmentId);
    List<Bill> getBillsByPatientId(int patientId);
    List<Bill> getBillsByDate(Date date);
    List<Bill> getBillsByStatus(String status);
    List<Bill> getPendingBills();
    List<Bill> getAllBills();
    boolean updatePaymentStatus(int billId, String status, String method);
    double getTotalRevenue();
    double getTotalRevenueByDateRange(Date startDate, Date endDate);
}