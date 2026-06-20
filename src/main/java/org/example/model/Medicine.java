package org.example.model;

import java.util.Date;

public class Medicine {
    private int medicineId;
    private String medicineName;
    private String dosageForm;
    private double price;
    private int quantityInStock;
    private Date expiryDate;

    public Medicine() {}

    // Business Methods
    public void addMedicine(String name, String form, double price, int stock, Date expiry) {
        this.medicineName = name;
        this.dosageForm = form;
        this.price = price;
        this.quantityInStock = stock;
        this.expiryDate = expiry;
        System.out.println("Medicine added: " + name);
    }

    public void updateMedicine(String name, String form, double price, int stock, Date expiry) {
        this.medicineName = name;
        this.dosageForm = form;
        this.price = price;
        this.quantityInStock = stock;
        this.expiryDate = expiry;
        System.out.println("Medicine updated: " + name);
    }

    public int checkStock() {
        System.out.println("Current stock of " + medicineName + ": " + quantityInStock);
        return quantityInStock;
    }

    public boolean reduceStock(int quantity) {
        if (quantityInStock >= quantity) {
            quantityInStock -= quantity;
            System.out.println("Stock reduced by " + quantity + ". Remaining: " + quantityInStock);
            return true;
        }
        System.out.println("Insufficient stock! Available: " + quantityInStock);
        return false;
    }

    public void restock(int quantity) {
        quantityInStock += quantity;
        System.out.println("Stock increased by " + quantity + ". New stock: " + quantityInStock);
    }

    public boolean isExpired() {
        return expiryDate != null && new Date().after(expiryDate);
    }

    // Getters and Setters
    public int getMedicineId() { return medicineId; }
    public void setMedicineId(int medicineId) { this.medicineId = medicineId; }

    public String getMedicineName() { return medicineName; }
    public void setMedicineName(String medicineName) { this.medicineName = medicineName; }

    public String getDosageForm() { return dosageForm; }
    public void setDosageForm(String dosageForm) { this.dosageForm = dosageForm; }

    public double getPrice() { return price; }
    public void setPrice(double price) { this.price = price; }

    public int getQuantityInStock() { return quantityInStock; }
    public void setQuantityInStock(int quantityInStock) { this.quantityInStock = quantityInStock; }

    public Date getExpiryDate() { return expiryDate; }
    public void setExpiryDate(Date expiryDate) { this.expiryDate = expiryDate; }
}