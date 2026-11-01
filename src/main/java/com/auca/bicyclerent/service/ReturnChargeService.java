package com.auca.bicyclerent.service;

import com.auca.bicyclerent.entity.Hire;
import com.auca.bicyclerent.entity.HireStatus;
import com.auca.bicyclerent.entity.ReturnCharge;
import com.auca.bicyclerent.repository.HireRepository;
import com.auca.bicyclerent.repository.ReturnChargeRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ReturnChargeService {

    private final ReturnChargeRepository returnChargeRepository;
    private final HireRepository hireRepository;

    public ReturnChargeService(ReturnChargeRepository returnChargeRepository, HireRepository hireRepository) {
        this.returnChargeRepository = returnChargeRepository;
        this.hireRepository = hireRepository;
    }

    // E1
    public String saveReturnCharge(UUID hireId, ReturnCharge returnCharge) {
        Optional<Hire> opt = hireRepository.findById(hireId);
        if (opt.isEmpty()) {
            return "The hire with that id does not exist";
        }
        Hire hire = opt.get();
        if (hire.getStatus() != HireStatus.RETURNED) {
            return "Return charges are only for RETURNED hires";
        }
        returnCharge.setChargedDate(LocalDate.now());
        returnCharge.setHire(hire);
        returnChargeRepository.save(returnCharge);
        return "Return charge saved successfully";
    }

    // E2 JPQL
    public List<ReturnCharge> getReturnChargesByCustomerNationalId(String nationalId) {
        return returnChargeRepository.findByCustomerNationalId(nationalId);
    }

    // E3
    public String deleteReturnCharge(UUID id) {
        Optional<ReturnCharge> opt = returnChargeRepository.findById(id);
        if (opt.isEmpty()) {
            return "The return charge with that id does not exist";
        }
        returnChargeRepository.delete(opt.get());
        return "Return charge deleted successfully";
    }

    public List<ReturnCharge> getAllReturnCharges() {
        return returnChargeRepository.findAll();
    }

    public Optional<ReturnCharge> getReturnChargeById(UUID id) {
        return returnChargeRepository.findById(id);
    }
}
