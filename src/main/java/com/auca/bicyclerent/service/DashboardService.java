package com.auca.bicyclerent.service;

import com.auca.bicyclerent.entity.HireStatus;
import com.auca.bicyclerent.repository.*;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.LinkedHashMap;

@Service
public class DashboardService {

    private final CustomerRepository customerRepository;
    private final BicycleRepository bicycleRepository;
    private final RackRepository rackRepository;
    private final HireRepository hireRepository;
    private final ReturnChargeRepository returnChargeRepository;

    public DashboardService(CustomerRepository customerRepository,
                            BicycleRepository bicycleRepository,
                            RackRepository rackRepository,
                            HireRepository hireRepository,
                            ReturnChargeRepository returnChargeRepository) {
        this.customerRepository = customerRepository;
        this.bicycleRepository = bicycleRepository;
        this.rackRepository = rackRepository;
        this.hireRepository = hireRepository;
        this.returnChargeRepository = returnChargeRepository;
    }

    public LinkedHashMap<String, Long> getDashboardSummary() {
        LinkedHashMap<String, Long> summary = new LinkedHashMap<>();
        summary.put("customers", customerRepository.count());
        summary.put("bicycles", bicycleRepository.count());
        summary.put("freeRacks", (long) rackRepository.findFreeRacks().size());
        summary.put("hiresToday", hireRepository.countByHireDate(LocalDate.now()));
        summary.put("booked", hireRepository.countByStatus(HireStatus.BOOKED));
        summary.put("returnCharges", returnChargeRepository.count());
        return summary;
    }
}
