package com.auca.bicyclerent.service;

import com.auca.bicyclerent.entity.Accessory;
import com.auca.bicyclerent.entity.Bicycle;
import com.auca.bicyclerent.entity.Rack;
import com.auca.bicyclerent.repository.AccessoryRepository;
import com.auca.bicyclerent.repository.BicycleRepository;
import com.auca.bicyclerent.repository.RackRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class BicycleService {

    private final BicycleRepository bicycleRepository;
    private final RackRepository rackRepository;
    private final AccessoryRepository accessoryRepository;

    public BicycleService(BicycleRepository bicycleRepository,
                          RackRepository rackRepository,
                          AccessoryRepository accessoryRepository) {
        this.bicycleRepository = bicycleRepository;
        this.rackRepository = rackRepository;
        this.accessoryRepository = accessoryRepository;
    }

    public String saveBicycle(Bicycle bicycle) {
        if (bicycle.getRack() != null && bicycle.getRack().getId() != null) {
            rackRepository.findById(bicycle.getRack().getId()).ifPresent(bicycle::setRack);
        }
        if (bicycle.getAccessories() != null && !bicycle.getAccessories().isEmpty()) {
            List<Accessory> managed = new ArrayList<>();
            for (Accessory acc : bicycle.getAccessories()) {
                if (acc.getId() != null) {
                    accessoryRepository.findById(acc.getId()).ifPresent(managed::add);
                }
            }
            bicycle.setAccessories(managed);
        }
        bicycleRepository.save(bicycle);
        return "Bicycle saved successfully";
    }

    public List<Bicycle> getAllBicycles() {
        return bicycleRepository.findAll();
    }

    public Optional<Bicycle> getBicycleById(UUID id) {
        return bicycleRepository.findById(id);
    }

    public String updateBicycle(UUID id, Bicycle bicycle) {
        Optional<Bicycle> opt = bicycleRepository.findById(id);
        if (opt.isEmpty()) {
            return "The bicycle with that id does not exist";
        }
        Bicycle existing = opt.get();
        if (bicycle.getBrand() != null) {
            existing.setBrand(bicycle.getBrand());
        }
        if (bicycle.getModel() != null) {
            existing.setModel(bicycle.getModel());
        }
        if (bicycle.getBikeCode() != null) {
            existing.setBikeCode(bicycle.getBikeCode());
        }
        if (bicycle.getRack() != null && bicycle.getRack().getId() != null) {
            rackRepository.findById(bicycle.getRack().getId()).ifPresent(existing::setRack);
        }
        if (bicycle.getAccessories() != null && !bicycle.getAccessories().isEmpty()) {
            List<Accessory> managed = new ArrayList<>();
            for (Accessory acc : bicycle.getAccessories()) {
                if (acc.getId() != null) {
                    accessoryRepository.findById(acc.getId()).ifPresent(managed::add);
                }
            }
            existing.setAccessories(managed);
        }
        bicycleRepository.save(existing);
        return "Bicycle updated successfully";
    }

    public String deleteBicycle(UUID id) {
        Optional<Bicycle> opt = bicycleRepository.findById(id);
        if (opt.isEmpty()) {
            return "The bicycle with that id does not exist";
        }
        bicycleRepository.delete(opt.get());
        return "Bicycle deleted successfully";
    }

    // C1 JPQL
    public List<Bicycle> getBicyclesByAccessory(String name) {
        return bicycleRepository.findByAccessoryName(name);
    }

    // D3
    public String addAccessoryToBicycle(UUID bicycleId, UUID accessoryId) {
        Optional<Bicycle> bikeOpt = bicycleRepository.findById(bicycleId);
        if (bikeOpt.isEmpty()) {
            return "The bicycle with that id does not exist";
        }
        Optional<Accessory> accOpt = accessoryRepository.findById(accessoryId);
        if (accOpt.isEmpty()) {
            return "That accessory does not exist";
        }
        Bicycle bike = bikeOpt.get();
        Accessory accessory = accOpt.get();
        if (bike.getAccessories() != null && bike.getAccessories().contains(accessory)) {
            return "The bicycle already has that accessory";
        }
        if (bike.getAccessories() == null) {
            bike.setAccessories(new ArrayList<>());
        }
        bike.getAccessories().add(accessory);
        bicycleRepository.save(bike);
        return "Accessory added to the bicycle";
    }

    // D4
    public String changeRack(UUID bicycleId, int rackNumber) {
        Optional<Bicycle> bikeOpt = bicycleRepository.findById(bicycleId);
        if (bikeOpt.isEmpty()) {
            return "The bicycle with that id does not exist";
        }
        Optional<Rack> rackOpt = rackRepository.findByRackNumber(rackNumber);
        if (rackOpt.isEmpty()) {
            return "The rack with that rackNumber does not exist";
        }
        Bicycle bike = bikeOpt.get();
        Rack targetRack = rackOpt.get();
        if (bike.getRack() == null || !bike.getRack().getId().equals(targetRack.getId())) {
            if (bicycleRepository.existsByRack(targetRack)) {
                return "That rack is taken by another bicycle";
            }
        }
        bike.setRack(targetRack);
        bicycleRepository.save(bike);
        return "Rack changed successfully";
    }
}
