package com.example.IronMan.Service;

import com.example.IronMan.DTOS.MaterialRequest;
import com.example.IronMan.DTOS.MaterialResponse;
import com.example.IronMan.Entities.Inventory;
import com.example.IronMan.Entities.Material;
import com.example.IronMan.Helpers.RetryHelper;
import com.example.IronMan.Repositories.InventoryRepo;
import com.example.IronMan.Repositories.MaterialRepo;
import jakarta.transaction.Transactional;
import org.hibernate.PessimisticLockException;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

@Service
public class InventoryService {

    private final MaterialRepo materialRepo;
    private final InventoryRepo inventoryRepo;
    private final RetryHelper retryHelper;
    private final InventoryService self; // ← new field

    public InventoryService(MaterialRepo materialRepo, InventoryRepo inventoryRepo,
                            RetryHelper retryHelper, @Lazy InventoryService self) {
        this.materialRepo = materialRepo;
        this.inventoryRepo = inventoryRepo;
        this.retryHelper = retryHelper;
        this.self = self;
    }

    @Transactional
    public MaterialResponse updateStock(UUID mat_id,Double newStock){
        if (newStock == null || newStock <= 0) {
            throw new IllegalArgumentException("Deduction quantity must be positive");
        }

        //fetch the data from the inventory table
        Inventory savedInventory=inventoryRepo.findByMaterialId(mat_id).orElseThrow(()->new RuntimeException("Material not found"));

        //in memory save
        savedInventory.setStockQuantity(savedInventory.getStockQuantity()+newStock);
        savedInventory.setLastUpdated(LocalDateTime.now());
        //db save
        inventoryRepo.save(savedInventory);

        return new MaterialResponse(savedInventory.getMaterial().getId(), savedInventory.getMaterial().getName(),savedInventory.getMaterial().getUnit(),savedInventory.getStockQuantity());
    }


    //the retry orchestrator,
    public MaterialResponse deductStock(UUID matId, Double deductionAmount) throws InterruptedException{
        return retryHelper.executeWithRetry(()->self.deductStockTransactional(matId,deductionAmount),3);
    }


    @Transactional
    public MaterialResponse deductStockTransactional(UUID matId, Double deductionAmount) {
        if (deductionAmount == null || deductionAmount <= 0) {
            throw new IllegalArgumentException("Deduction amount must be greater than zero");
        }

        Inventory savedInventory = inventoryRepo.findByMaterialIdForUpdate(matId)
                .orElseThrow(() -> new RuntimeException("Material not found in inventory"));

        if (savedInventory.getStockQuantity() < deductionAmount) {
            throw new IllegalArgumentException("Deduction not possible: insufficient stock");
        }

        savedInventory.setStockQuantity(savedInventory.getStockQuantity() - deductionAmount);
        savedInventory.setLastUpdated(LocalDateTime.now());

        inventoryRepo.save(savedInventory);

        return new MaterialResponse(
                savedInventory.getMaterial().getId(),
                savedInventory.getMaterial().getName(),
                savedInventory.getMaterial().getUnit(),
                savedInventory.getStockQuantity()
        );
    }



    @Transactional
    public List<MaterialResponse> getAllInventory(){
        return inventoryRepo.getAllInventory().stream().map(m->new MaterialResponse(m.getMaterial().getId(),m.getMaterial().getName(),m.getMaterial().getUnit(),m.getStockQuantity())).toList();

    }
}
