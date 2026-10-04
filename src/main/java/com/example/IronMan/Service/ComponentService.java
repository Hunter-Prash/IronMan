package com.example.IronMan.Service;

import com.example.IronMan.DTOS.ComponentRequest;
import com.example.IronMan.DTOS.ComponentResponse;
import com.example.IronMan.Entities.Component;
import com.example.IronMan.Repositories.ComponentRepo;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.beans.Transient;
import java.util.List;
import java.util.UUID;

@Service

public class ComponentService {

    private final ComponentRepo componentRepo;

    public ComponentService(ComponentRepo componentRepo){
        this.componentRepo=componentRepo;
    }

    @Transactional
    public ComponentResponse createComponent(ComponentRequest req){

        componentRepo.findByName(req.name()).ifPresent(c->{throw new IllegalArgumentException("Component already exists with name:"+ req.name());});

        Component c=Component.builder()
                .id(UUID.randomUUID())
                .name(req.name())
                .category(req.category())
                .build();

        Component saved=componentRepo.save(c);

        return new ComponentResponse(saved.getId(), saved.getName(), saved.getCategory());
    }

    public List<ComponentResponse> getAllComponents(){

            return componentRepo.findAll().stream().map(m->new ComponentResponse(m.getId(),m.getName(),m.getCategory())).toList();
    }

    public ComponentResponse getComponentById(UUID id){
         Component component=componentRepo.findById(id).orElseThrow(()->new IllegalArgumentException("Component not found with ID:" + id));
        return new ComponentResponse(
                component.getId(),
                component.getName(),
                component.getCategory()
        );
    }
}
