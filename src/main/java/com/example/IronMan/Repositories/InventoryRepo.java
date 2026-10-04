package com.example.IronMan.Repositories;

import com.example.IronMan.Entities.Inventory;
import com.example.IronMan.Entities.Material;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface InventoryRepo extends JpaRepository<Inventory, UUID>
{
            //You can write i.material.id because you mapped an OnetoOne association to the Material object inside Inventory.In JPQL, this is called path navigation:
            /*
          .i represents the Inventory entity.
        .material navigates across the association to the linked Material entity.
        .id references the id property on that Material class.

        Whenever an entity has an association to another entity (@OneToOne or @ManyToOne), JPQL lets you traverse the dot notation directly, and Hibernate automatically translates that traversal into the underlying SQL foreign key lookup.*/

    @Query("Select i from Inventory i where i.material.id=:mat_id")
    Optional<Inventory> findByMaterialId(@Param("mat_id")UUID mat_id);

    @Lock(LockModeType.PESSIMISTIC_WRITE)//emits "SELECT ... FOR UPDATE" in PostgreSQL
    @Query("Select i from Inventory i where i.material.id=:matId")
    Optional<Inventory> findByMaterialIdForUpdate(@Param("mat_id")UUID mat_id);

    @Query("Select i from Inventory i Join fetch i.material")
    List<Inventory> getAllInventory();


}
