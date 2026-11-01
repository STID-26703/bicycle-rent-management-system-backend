package com.auca.bicyclerent.repository;

import com.auca.bicyclerent.entity.Bicycle;
import com.auca.bicyclerent.entity.Rack;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface BicycleRepository extends JpaRepository<Bicycle, UUID> {

    // C1 JPQL
    @Query("SELECT DISTINCT b FROM Bicycle b JOIN b.accessories a WHERE LOWER(a.name) LIKE LOWER(CONCAT('%', :name, '%'))")
    List<Bicycle> findByAccessoryName(@Param("name") String name);

    // D4 DERIVED
    boolean existsByRack(Rack rack);
}
