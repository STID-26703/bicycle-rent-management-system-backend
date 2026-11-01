package com.auca.bicyclerent.repository;

import com.auca.bicyclerent.entity.Hire;
import com.auca.bicyclerent.entity.HireStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Repository
public interface HireRepository extends JpaRepository<Hire, UUID> {

    // B2 DERIVED
    List<Hire> findByStatusOrderByHireDateAsc(HireStatus status);

    // B3 DERIVED
    List<Hire> findByHireDateBetweenOrderByHireDateAsc(LocalDate start, LocalDate end);

    // D1 DERIVED
    boolean existsByBicycleIdAndHireDateAndStatusNot(UUID bicycleId, LocalDate hireDate, HireStatus status);

    // F1 JPQL
    @Query("SELECT h.status, COUNT(h) FROM Hire h GROUP BY h.status")
    List<Object[]> getHireStatsByStatus();

    // F2 JPQL
    @Modifying
    @Query("UPDATE Hire h SET h.status = :withdrawnStatus WHERE h.bicycle.id = :bicycleId AND h.hireDate = :date AND h.status <> :returnedStatus")
    int cancelDayHires(@Param("bicycleId") UUID bicycleId,
                       @Param("date") LocalDate date,
                       @Param("withdrawnStatus") HireStatus withdrawnStatus,
                       @Param("returnedStatus") HireStatus returnedStatus);

    // BONUS DERIVED
    long countByHireDate(LocalDate hireDate);

    // BONUS DERIVED
    long countByStatus(HireStatus status);
}
