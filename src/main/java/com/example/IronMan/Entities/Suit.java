package com.example.IronMan.Entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name="suits")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Suit {

    @Id
    @Column(name = "id")
    private UUID id;

    @OneToMany(mappedBy = "suit",cascade = CascadeType.ALL)
    private List<Suit_Component> suitComponents = new ArrayList<>();

    @Column(name = "designation",nullable = false,unique = true)
    private String designation;

    @Column(name = "version")
    private String version;

    @Column(name = "status",nullable = false)
    private String status;

}
