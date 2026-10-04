package com.example.IronMan.Entities;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name="components")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Component {

    @Id
    @Column(name = "id")
    private UUID id;

    @Column(name = "name")
    private String name;

    @Column(name = "category")
    private String category;

    @OneToMany(mappedBy = "component")
    private List<Suit_Component> suitComponents = new ArrayList<>();

}
