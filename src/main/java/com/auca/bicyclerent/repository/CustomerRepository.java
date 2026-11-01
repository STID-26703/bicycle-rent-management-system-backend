package com.auca.bicyclerent.repository;

import com.auca.bicyclerent.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, UUID> {

    // B1 DERIVED
    List<Customer> findByLastNameIgnoreCaseOrderByFirstNameAsc(String lastName);

    // C4 JPQL
    @Query("SELECT DISTINCT h.customer FROM Hire h WHERE h.bicycle.id = :bicycleId")
    List<Customer> findCustomersByBicycleId(@Param("bicycleId") UUID bicycleId);
}
