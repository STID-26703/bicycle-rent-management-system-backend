package com.auca.bicyclerent.service;

import com.auca.bicyclerent.entity.Accessory;
import com.auca.bicyclerent.repository.AccessoryRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class AccessoryService {

    private final AccessoryRepository accessoryRepository;

    public AccessoryService(AccessoryRepository accessoryRepository) {
        this.accessoryRepository = accessoryRepository;
    }

    public String saveAccessory(Accessory accessory) {
        accessoryRepository.save(accessory);
        return "Accessory saved successfully";
    }

    public List<Accessory> getAllAccessories() {
        return accessoryRepository.findAll();
    }

    public Optional<Accessory> getAccessoryById(UUID id) {
        return accessoryRepository.findById(id);
    }

    public String updateAccessory(UUID id, Accessory accessory) {
        Optional<Accessory> opt = accessoryRepository.findById(id);
        if (opt.isEmpty()) {
            return "The accessory with that id does not exist";
        }
        Accessory existing = opt.get();
        existing.setName(accessory.getName());
        accessoryRepository.save(existing);
        return "Accessory updated successfully";
    }

    public String deleteAccessory(UUID id) {
        Optional<Accessory> opt = accessoryRepository.findById(id);
        if (opt.isEmpty()) {
            return "The accessory with that id does not exist";
        }
        accessoryRepository.delete(opt.get());
        return "Accessory deleted successfully";
    }

    public List<Accessory> getUnusedAccessories() {
        return accessoryRepository.findUnusedAccessories();
    }
}
