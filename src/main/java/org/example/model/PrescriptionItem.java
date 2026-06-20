package org.example.model;

public class PrescriptionItem {
    private int itemId;
    private int prescriptionId;
    private int medicineId;
    private int quantityPrescribed;
    private String dosage;
    private String frequency;
    private Medicine medicine;
    private Prescription prescription;

    public PrescriptionItem() {}

    // Business Methods
    public void addMedicine(Medicine medicine, int quantity, String dosage, String frequency) {
        this.medicine = medicine;
        this.medicineId = medicine.getMedicineId();
        this.quantityPrescribed = quantity;
        this.dosage = dosage;
        this.frequency = frequency;
        System.out.println("Added medicine: " + medicine.getMedicineName() + " x" + quantity);
    }

    public void removeMedicine() {
        System.out.println("Removed medicine: " + (medicine != null ? medicine.getMedicineName() : "Unknown"));
        this.medicine = null;
        this.medicineId = 0;
        this.quantityPrescribed = 0;
    }

    public String getInstruction() {
        return "Take " + dosage + " of " + (medicine != null ? medicine.getMedicineName() : "medicine") + " " + frequency;
    }

    // Getters and Setters
    public int getItemId() { return itemId; }
    public void setItemId(int itemId) { this.itemId = itemId; }

    public int getPrescriptionId() { return prescriptionId; }
    public void setPrescriptionId(int prescriptionId) { this.prescriptionId = prescriptionId; }

    public int getMedicineId() { return medicineId; }
    public void setMedicineId(int medicineId) { this.medicineId = medicineId; }

    public int getQuantityPrescribed() { return quantityPrescribed; }
    public void setQuantityPrescribed(int quantityPrescribed) { this.quantityPrescribed = quantityPrescribed; }

    public String getDosage() { return dosage; }
    public void setDosage(String dosage) { this.dosage = dosage; }

    public String getFrequency() { return frequency; }
    public void setFrequency(String frequency) { this.frequency = frequency; }

    public Medicine getMedicine() { return medicine; }
    public void setMedicine(Medicine medicine) {
        this.medicine = medicine;
        if (medicine != null) this.medicineId = medicine.getMedicineId();
    }

    public Prescription getPrescription() { return prescription; }
    public void setPrescription(Prescription prescription) {
        this.prescription = prescription;
        if (prescription != null) this.prescriptionId = prescription.getPrescriptionId();
    }
}