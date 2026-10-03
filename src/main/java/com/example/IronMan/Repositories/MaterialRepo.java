package com.example.IronMan.Repositories;

import com.example.IronMan.Entities.Material;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface MaterialRepo extends JpaRepository<Material, UUID> {

    @Query("Select m from Material m where lower(m.name)=lower(:name)")
    Optional<Material> findByName(@Param("name") String name);

    @Query("Select m from Material m where m.uuid=:uuid")
    Optional<Material> findByMaterialId(@Param("uuid") UUID uuid);

    // The magic JOIN FETCH query to prevent N+1
    @Query("Select m from Material m join fetch m.inventory")
    List<Material> findAllWithInventory();
}
