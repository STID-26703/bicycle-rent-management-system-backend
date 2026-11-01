package com.auca.bicyclerent.repository;

import com.auca.bicyclerent.entity.Hire;
import com.auca.bicyclerent.entity.ReturnCharge;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ReturnChargeRepository extends JpaRepository<ReturnCharge, UUID> {

    // E2 JPQL
    @Query("SELECT rc FROM ReturnCharge rc WHERE rc.hire.customer.nationalId = :nationalId ORDER BY rc.chargedDate DESC")
    List<ReturnCharge> findByCustomerNationalId(@Param("nationalId") String nationalId);

    // E3 DERIVED
    boolean existsByHire(Hire hire);
}
