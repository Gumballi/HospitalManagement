package org.example.dao;

import org.example.model.PrescriptionItem;
import java.util.List;

public interface PrescriptionItemDAO {
    boolean addPrescriptionItem(PrescriptionItem item);
    boolean updatePrescriptionItem(PrescriptionItem item);
    boolean deletePrescriptionItem(int itemId);
    PrescriptionItem getPrescriptionItemById(int itemId);
    List<PrescriptionItem> getItemsByPrescriptionId(int prescriptionId);
    List<PrescriptionItem> getItemsByMedicineId(int medicineId);
    List<PrescriptionItem> getAllPrescriptionItems();
}