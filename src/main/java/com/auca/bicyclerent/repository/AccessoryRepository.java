package com.auca.bicyclerent.repository;

import com.auca.bicyclerent.entity.Accessory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface AccessoryRepository extends JpaRepository<Accessory, UUID> {

    // C3 JPQL
    @Query("SELECT a FROM Accessory a WHERE a.bicycles IS EMPTY")
    List<Accessory> findUnusedAccessories();
}
