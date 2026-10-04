package com.example.IronMan.Repositories;

import com.example.IronMan.Entities.Component;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface ComponentRepo extends JpaRepository<Component, UUID> {

    @Query("SELECT c FROM Component c WHERE LOWER(c.name) = LOWER(:name)")
    Optional<Component> findByName(@Param("name") String name);
}