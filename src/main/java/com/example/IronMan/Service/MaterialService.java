package com.example.IronMan.Service;

import com.example.IronMan.DTOS.MaterialRequest;
import com.example.IronMan.DTOS.MaterialResponse;
import com.example.IronMan.Entities.Inventory;
import com.example.IronMan.Entities.Material;
import com.example.IronMan.Repositories.InventoryRepo;
import com.example.IronMan.Repositories.MaterialRepo;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class MaterialService {

    private final MaterialRepo materialRepo;
    private final InventoryRepo inventoryRepo;

    public MaterialService(MaterialRepo materialRepo,InventoryRepo inventoryRepo){
        this.materialRepo=materialRepo; this.inventoryRepo=inventoryRepo;
    }

    @Transactional
    public MaterialResponse createMaterial(MaterialRequest req){
        try{

            materialRepo.findByName(req.name()).ifPresent(m->{throw new IllegalArgumentException("Exists");});

            //generate uuid
            UUID uuid=UUID.randomUUID();
            Material material=Material.builder()
                    .id(uuid)
                    .name(req.name())
                    .unit(req.unit())
                    .build();

            Material savedMaterial=materialRepo.save(material);

            //  Create and save the linked Inventory
            Inventory inventory=Inventory.builder()
                    .id(UUID.randomUUID())
                    .material(savedMaterial)
                    .stockQuantity(req.stockQuantity())
                    .lastUpdated(LocalDateTime.now())
                    .build();

            Inventory savedInventory = inventoryRepo.save(inventory);

            return new MaterialResponse(savedMaterial.getId(), savedMaterial.getName(), savedMaterial.getUnit(),savedInventory.getStockQuantity());

        } catch (RuntimeException e) {
            throw new RuntimeException(e);//propagate up to controller
        }
    }

    @Transactional
    public MaterialResponse getMaterialById(UUID req){
        try{
            Material material=materialRepo.findByMaterialId(req).orElseThrow(()->new RuntimeException("Material not found"));
            return new MaterialResponse(material.getId(),material.getName(),material.getUnit(),material.getInventory().getStockQuantity());
        } catch (RuntimeException e) {
            throw new RuntimeException(e);
        }
    }

    @Transactional
    public List<MaterialResponse> getAllMaterials(){
        try{
            List<MaterialResponse> materials=new ArrayList<>();
            List<Material>itr=materialRepo.findAllWithInventory();
            for(Material x:itr){
                materials.add(new MaterialResponse(x.getId(), x.getName(),x.getUnit(),x.getInventory().getStockQuantity()));
            }

            /*
             * Optional: You can also write this cleanly with the Stream API:
             * return materialRepo.findAll().stream()
             *     .map(m -> new MaterialResponse(m.getId(), m.getName(), m.getUnit()))
             *     .toList();
             */

            return materials;

        } catch (RuntimeException e) {
            throw new RuntimeException(e);
        }
    }
}
