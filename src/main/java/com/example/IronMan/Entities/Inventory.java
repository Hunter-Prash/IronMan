package com.example.IronMan.Entities;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Getter
@Setter
@Table(name="inventory")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Inventory {

    @Id
    @Column(name="id",nullable = false,updatable = false)
    private UUID id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="material_id",referencedColumnName = "id",nullable = false)
    private Material material;

    @Column(name="stock_quantity", nullable = false)
    private Double stockQuantity;

    @Column(name = "last_updated", nullable = false)
    private LocalDateTime lastUpdated;

}
