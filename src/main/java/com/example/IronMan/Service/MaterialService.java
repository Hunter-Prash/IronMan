package com.example.IronMan.Service;

import com.example.IronMan.DTOS.MaterialRequest;
import com.example.IronMan.DTOS.MaterialResponse;
import com.example.IronMan.Entities.Material;
import com.example.IronMan.Repositories.MaterialRepo;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import lombok.*;

import java.util.Optional;
import java.util.UUID;

@Service
public class MaterialService {

    private final MaterialRepo materialRepo;

    public MaterialService(MaterialRepo materialRepo){
        this.materialRepo=materialRepo;
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
            return new MaterialResponse(savedMaterial.getId(), savedMaterial.getName(), savedMaterial.getUnit());

        } catch (RuntimeException e) {
            throw new RuntimeException(e);//propagate up to controller
        }
    }
}
