package com.auca.bicyclerent.repository;

import com.auca.bicyclerent.entity.Rack;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface RackRepository extends JpaRepository<Rack, UUID> {

    // C2 JPQL
    @Query("SELECT r FROM Rack r WHERE NOT EXISTS (SELECT b FROM Bicycle b WHERE b.rack = r) ORDER BY r.rackNumber")
    List<Rack> findFreeRacks();

    // D4 DERIVED
    Optional<Rack> findByRackNumber(int rackNumber);
}
