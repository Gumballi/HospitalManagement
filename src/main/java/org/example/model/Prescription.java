package org.example.model;

import java.util.Date;
import java.util.ArrayList;
import java.util.List;

public class Prescription {
    private int prescriptionId;
    private int doctorId;
    private int patientId;
    private Doctor doctor;
    private Patient patient;
    private Date prescriptionDate;
    private List<PrescriptionItem> items;

    public Prescription() {
        this.items = new ArrayList<>();
    }

    // Business Methods
    public void createPrescription(Doctor doctor, Patient patient) {
        this.doctor = doctor;
        this.patient = patient;
        this.doctorId = doctor.getDoctorId();
        this.patientId = patient.getPatientId();
        this.prescriptionDate = new Date();
        System.out.println("Prescription created for patient " + patient.getFullName() + " by doctor " + doctor.getFullName());
    }

    public void updatePrescription(Date newDate) {
        this.prescriptionDate = newDate;
        System.out.println("Prescription " + prescriptionId + " updated to date: " + newDate);
    }

    public void addItem(PrescriptionItem item) {
        this.items.add(item);
        item.setPrescription(this);
        System.out.println("Added medicine to prescription");
    }

    public void removeItem(PrescriptionItem item) {
        this.items.remove(item);
        System.out.println("Removed medicine from prescription");
    }

    public int getTotalQuantity() {
        return items.stream().mapToInt(PrescriptionItem::getQuantityPrescribed).sum();
    }

    public double getTotalCost() {
        return items.stream().mapToDouble(i -> i.getMedicine().getPrice() * i.getQuantityPrescribed()).sum();
    }

    // Getters and Setters
    public int getPrescriptionId() { return prescriptionId; }
    public void setPrescriptionId(int prescriptionId) { this.prescriptionId = prescriptionId; }

    public int getDoctorId() { return doctorId; }
    public void setDoctorId(int doctorId) { this.doctorId = doctorId; }

    public int getPatientId() { return patientId; }
    public void setPatientId(int patientId) { this.patientId = patientId; }

    public Doctor getDoctor() { return doctor; }
    public void setDoctor(Doctor doctor) {
        this.doctor = doctor;
        if (doctor != null) this.doctorId = doctor.getDoctorId();
    }

    public Patient getPatient() { return patient; }
    public void setPatient(Patient patient) {
        this.patient = patient;
        if (patient != null) this.patientId = patient.getPatientId();
    }

    public Date getPrescriptionDate() { return prescriptionDate; }
    public void setPrescriptionDate(Date prescriptionDate) { this.prescriptionDate = prescriptionDate; }

    public List<PrescriptionItem> getItems() { return items; }
    public void setItems(List<PrescriptionItem> items) { this.items = items; }
}