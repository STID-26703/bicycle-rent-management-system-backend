package com.auca.bicyclerent.controller;

import com.auca.bicyclerent.entity.Accessory;
import com.auca.bicyclerent.service.AccessoryService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/accessories")
public class AccessoryController {

    private final AccessoryService accessoryService;

    public AccessoryController(AccessoryService accessoryService) {
        this.accessoryService = accessoryService;
    }

    @PostMapping("/save")
    public ResponseEntity<String> save(@RequestBody Accessory accessory) {
        String res = accessoryService.saveAccessory(accessory);
        if (res.equals("Accessory saved successfully")) {
            return new ResponseEntity<>(res, HttpStatus.CREATED);
        } else {
            return ResponseEntity.badRequest().body(res);
        }
    }

    @GetMapping("/all")
    public ResponseEntity<List<Accessory>> getAll() {
        return ResponseEntity.ok(accessoryService.getAllAccessories());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable UUID id) {
        Optional<Accessory> opt = accessoryService.getAccessoryById(id);
        if (opt.isPresent()) {
            return ResponseEntity.ok(opt.get());
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("The accessory with that id does not exist");
        }
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<String> update(@PathVariable UUID id, @RequestBody Accessory accessory) {
        String res = accessoryService.updateAccessory(id, accessory);
        if (res.equals("Accessory updated successfully")) {
            return ResponseEntity.ok(res);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(res);
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> delete(@PathVariable UUID id) {
        String res = accessoryService.deleteAccessory(id);
        if (res.equals("Accessory deleted successfully")) {
            return ResponseEntity.ok(res);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(res);
        }
    }

    // C3 JPQL
    @GetMapping("/unused")
    public ResponseEntity<List<Accessory>> getUnused() {
        return ResponseEntity.ok(accessoryService.getUnusedAccessories());
    }
}
