package com.auca.bicyclerent.service;

import com.auca.bicyclerent.entity.Hire;
import com.auca.bicyclerent.entity.HireStatus;
import com.auca.bicyclerent.repository.BicycleRepository;
import com.auca.bicyclerent.repository.CustomerRepository;
import com.auca.bicyclerent.repository.HireRepository;
import com.auca.bicyclerent.repository.ReturnChargeRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class HireService {

    private final HireRepository hireRepository;
    private final CustomerRepository customerRepository;
    private final BicycleRepository bicycleRepository;
    private final ReturnChargeRepository returnChargeRepository;

    public HireService(HireRepository hireRepository,
                       CustomerRepository customerRepository,
                       BicycleRepository bicycleRepository,
                       ReturnChargeRepository returnChargeRepository) {
        this.hireRepository = hireRepository;
        this.customerRepository = customerRepository;
        this.bicycleRepository = bicycleRepository;
        this.returnChargeRepository = returnChargeRepository;
    }

    // D1
    public String saveHire(Hire hire) {
        if (hire.getHireDate() == null || hire.getHireDate().isBefore(LocalDate.now())) {
            return "Hire date cannot be in the past";
        }
        if (hire.getCustomer() != null && hire.getCustomer().getId() != null) {
            customerRepository.findById(hire.getCustomer().getId()).ifPresent(hire::setCustomer);
        }
        if (hire.getBicycle() != null && hire.getBicycle().getId() != null) {
            bicycleRepository.findById(hire.getBicycle().getId()).ifPresent(hire::setBicycle);
        }
        UUID bicycleId = (hire.getBicycle() != null) ? hire.getBicycle().getId() : null;
        if (bicycleId != null && hireRepository.existsByBicycleIdAndHireDateAndStatusNot(bicycleId, hire.getHireDate(), HireStatus.WITHDRAWN)) {
            return "Bicycle is already hired on that date";
        }
        hire.setStatus(HireStatus.BOOKED);
        hireRepository.save(hire);
        return "Hire saved successfully";
    }

    public List<Hire> getAllHires() {
        return hireRepository.findAll();
    }

    public Optional<Hire> getHireById(UUID id) {
        return hireRepository.findById(id);
    }

    public String updateHire(UUID id, Hire hire) {
        Optional<Hire> opt = hireRepository.findById(id);
        if (opt.isEmpty()) {
            return "The hire with that id does not exist";
        }
        Hire existing = opt.get();
        if (hire.getHireDate() != null) {
            existing.setHireDate(hire.getHireDate());
        }
        if (hire.getHours() > 0) {
            existing.setHours(hire.getHours());
        }
        if (hire.getHourlyRate() > 0.0) {
            existing.setHourlyRate(hire.getHourlyRate());
        }
        if (hire.getCustomer() != null && hire.getCustomer().getId() != null) {
            customerRepository.findById(hire.getCustomer().getId()).ifPresent(existing::setCustomer);
        }
        if (hire.getBicycle() != null && hire.getBicycle().getId() != null) {
            bicycleRepository.findById(hire.getBicycle().getId()).ifPresent(existing::setBicycle);
        }
        // PUT update on hires must NOT change status
        hireRepository.save(existing);
        return "Hire updated successfully";
    }

    public String deleteHire(UUID id) {
        Optional<Hire> opt = hireRepository.findById(id);
        if (opt.isEmpty()) {
            return "The hire with that id does not exist";
        }
        Hire hire = opt.get();
        if (returnChargeRepository.existsByHire(hire)) {
            return "That hire has return charges, delete them first";
        }
        hireRepository.delete(hire);
        return "Hire deleted successfully";
    }

    // B2 DERIVED
    public List<Hire> getHiresByStatus(HireStatus status) {
        return hireRepository.findByStatusOrderByHireDateAsc(status);
    }

    // B3 DERIVED
    public List<Hire> getHiresBetween(LocalDate start, LocalDate end) {
        return hireRepository.findByHireDateBetweenOrderByHireDateAsc(start, end);
    }

    // D2
    public String updateStatus(UUID id, HireStatus newStatus) {
        Optional<Hire> opt = hireRepository.findById(id);
        if (opt.isEmpty()) {
            return "The hire with that id does not exist";
        }
        Hire hire = opt.get();
        HireStatus current = hire.getStatus();
        boolean valid = false;
        if (current == HireStatus.BOOKED && (newStatus == HireStatus.ON_RENT || newStatus == HireStatus.WITHDRAWN)) {
            valid = true;
        } else if (current == HireStatus.ON_RENT && (newStatus == HireStatus.RETURNED || newStatus == HireStatus.WITHDRAWN)) {
            valid = true;
        }
        if (!valid) {
            return "Cannot change status from " + current + " to " + newStatus;
        }
        hire.setStatus(newStatus);
        hireRepository.save(hire);
        return "Hire status updated successfully";
    }

    // F1 JPQL
    public List<Object[]> getStatsByStatus() {
        return hireRepository.getHireStatsByStatus();
    }

    // F2 JPQL
    @Transactional
    public String cancelDay(UUID bicycleId, LocalDate date) {
        int updatedCount = hireRepository.cancelDayHires(bicycleId, date, HireStatus.WITHDRAWN, HireStatus.RETURNED);
        return updatedCount + " hires cancelled";
    }

    // BONUS
    public Page<Hire> getHiresPage(Pageable pageable) {
        return hireRepository.findAll(pageable);
    }
}
