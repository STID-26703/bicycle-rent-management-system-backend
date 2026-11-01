package com.auca.bicyclerent.service;

import com.auca.bicyclerent.entity.Rack;
import com.auca.bicyclerent.repository.RackRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class RackService {

    private final RackRepository rackRepository;

    public RackService(RackRepository rackRepository) {
        this.rackRepository = rackRepository;
    }

    public String saveRack(Rack rack) {
        rackRepository.save(rack);
        return "Rack saved successfully";
    }

    public List<Rack> getAllRacks() {
        return rackRepository.findAll();
    }

    public Optional<Rack> getRackById(UUID id) {
        return rackRepository.findById(id);
    }

    public String updateRack(UUID id, Rack rack) {
        Optional<Rack> opt = rackRepository.findById(id);
        if (opt.isEmpty()) {
            return "The rack with that id does not exist";
        }
        Rack existing = opt.get();
        if (rack.getRackNumber() > 0) {
            existing.setRackNumber(rack.getRackNumber());
        }
        if (rack.getName() != null) {
            existing.setName(rack.getName());
        }
        rackRepository.save(existing);
        return "Rack updated successfully";
    }

    public String deleteRack(UUID id) {
        Optional<Rack> opt = rackRepository.findById(id);
        if (opt.isEmpty()) {
            return "The rack with that id does not exist";
        }
        rackRepository.delete(opt.get());
        return "Rack deleted successfully";
    }

    public List<Rack> getFreeRacks() {
        return rackRepository.findFreeRacks();
    }
}
