package com.example.IronMan.Repositories;

import com.example.IronMan.Entities.Material;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface MaterialRepo extends JpaRepository<Material, UUID> {

    @Query("Select m from Material m where lower(m.name)=lower(:name)")
    Optional<Material> findByName(@Param("name") String name);

}
