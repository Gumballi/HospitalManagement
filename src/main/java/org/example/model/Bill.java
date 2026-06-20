package org.example.model;

import java.util.Date;

public class Bill {
    private int billId;
    private int appointmentId;
    private Date billDate;
    private double totalAmount;
    private String paymentMethod;
    private String paymentStatus;

    public Bill() {}

    // Business Methods
    public void generateBill(int appointmentId, double amount) {
        this.appointmentId = appointmentId;
        this.totalAmount = amount;
        this.billDate = new Date();
        this.paymentStatus = "PENDING";
        System.out.println("Bill generated for appointment " + appointmentId + " - Amount: $" + amount);
    }

    public void markAsPaid(String method) {
        this.paymentStatus = "PAID";
        this.paymentMethod = method;
        System.out.println("Bill " + billId + " paid via " + method);
    }

    public boolean isPaid() {
        return "PAID".equals(paymentStatus);
    }

    // Getters and Setters
    public int getBillId() { return billId; }
    public void setBillId(int billId) { this.billId = billId; }

    public int getAppointmentId() { return appointmentId; }
    public void setAppointmentId(int appointmentId) { this.appointmentId = appointmentId; }

    public Date getBillDate() { return billDate; }
    public void setBillDate(Date billDate) { this.billDate = billDate; }

    public double getTotalAmount() { return totalAmount; }
    public void setTotalAmount(double totalAmount) { this.totalAmount = totalAmount; }

    public String getPaymentMethod() { return paymentMethod; }
    public void setPaymentMethod(String paymentMethod) { this.paymentMethod = paymentMethod; }

    public String getPaymentStatus() { return paymentStatus; }
    public void setPaymentStatus(String paymentStatus) { this.paymentStatus = paymentStatus; }
}