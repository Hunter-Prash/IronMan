package com.example.IronMan.Entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Entity
@Table(name="suit_components")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Suit_Component {

    @Id
    @Column(name="id")
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "suit_id",nullable = false)
    private Suit suit;

    @ManyToOne
    @JoinColumn(name = "component_id",nullable = false)
    private Component component;

    @Column(name = "quantity_required")
    private Integer quantity;
}
