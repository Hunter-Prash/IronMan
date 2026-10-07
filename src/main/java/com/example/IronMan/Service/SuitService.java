package com.example.IronMan.Service;

import com.example.IronMan.DTOS.SuitComponentRequest;
import com.example.IronMan.DTOS.SuitComponentResponse;
import com.example.IronMan.DTOS.SuitRequest;
import com.example.IronMan.DTOS.SuitResponse;
import com.example.IronMan.Entities.Component;
import com.example.IronMan.Entities.Suit;
import com.example.IronMan.Entities.Suit_Component;
import com.example.IronMan.Repositories.ComponentRepo;
import com.example.IronMan.Repositories.SuitRepo;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class SuitService {
    private final SuitRepo suitRepo;
    private final ComponentRepo componentRepo;

    public SuitService(SuitRepo suitRepo, ComponentRepo componentRepo) {
        this.suitRepo = suitRepo;
        this.componentRepo = componentRepo;
    }

    public SuitResponse createSuit(SuitRequest req) {
        // 1. Create the new Suit entity
        Suit suit = Suit.builder()
                .id(UUID.randomUUID())
                .designation(req.designation())
                .version(req.version())
                .status("PROTOTYPE")
                .suitComponents(new ArrayList<>())
                .build();

        // 2. Map components from request to the Suit
        if (req.components() != null) {
            for (SuitComponentRequest compReq : req.components()) {
                // Fetch the component from DB
                Component component = componentRepo.findById(compReq.componentId())
                        .orElseThrow(() -> new RuntimeException("Component not found: " + compReq.componentId()));

                // Create mapping entity using Lombok Builder
                Suit_Component suitComponent = Suit_Component.builder()
                        .id(UUID.randomUUID())
                        .suit(suit)
                        .component(component)
                        .quantity(compReq.quantity())
                        .build();

                suit.getSuitComponents().add(suitComponent);
            }
        }

        // 3. Save the Suit (which cascades saving to suit_components)
        Suit savedSuit = suitRepo.save(suit);

        // 4. Map the saved Suit to a SuitResponse
        List<SuitComponentResponse> responseComponents = savedSuit.getSuitComponents().stream()
                .map(sc -> new SuitComponentResponse(
                        sc.getComponent().getId(),
                        sc.getComponent().getName(),
                        sc.getQuantity()))
                .toList();

        return new SuitResponse(
                savedSuit.getId(),
                savedSuit.getDesignation(),
                savedSuit.getVersion(),
                savedSuit.getStatus(),
                responseComponents);
    }

    public List<SuitResponse> getAllSuits() {

        List<Suit> suits = suitRepo.findAllWithComponents();
        List<SuitResponse> suitResponses = new ArrayList<>();
        for (Suit it : suits) {
            List<SuitComponentResponse> responseComponents = it.getSuitComponents().stream()
                    .map(sc -> new SuitComponentResponse(sc.getComponent().getId(), sc.getComponent().getName(),
                            sc.getQuantity()))
                    .toList();
            suitResponses.add(new SuitResponse(
                    it.getId(),
                    it.getDesignation(),
                    it.getVersion(),
                    it.getStatus(),
                    responseComponents));
        }
        return suitResponses;
    }
}
