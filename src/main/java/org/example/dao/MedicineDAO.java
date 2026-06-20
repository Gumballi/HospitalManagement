package org.example.dao;

import org.example.model.Medicine;
import java.util.List;

public interface MedicineDAO {
    boolean addMedicine(Medicine medicine);
    boolean updateMedicine(Medicine medicine);
    boolean deleteMedicine(int medicineId);
    Medicine getMedicineById(int medicineId);
    Medicine getMedicineByName(String name);
    List<Medicine> getAllMedicines();
    List<Medicine> getLowStockMedicines(int threshold);
    List<Medicine> getExpiringMedicines(int daysThreshold);
    boolean updateStock(int medicineId, int quantity);
    boolean updateStock(String medicineName, int quantity);
}