package com.example.IronMan.Repositories;

import com.example.IronMan.Entities.Suit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface SuitRepo extends JpaRepository<Suit, UUID> {

    @Query("SELECT s FROM Suit s WHERE LOWER(s.designation) = LOWER(:designation)")
    Optional<Suit> findByDesignation(@Param("designation") String designation);
}