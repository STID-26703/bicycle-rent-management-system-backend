package com.auca.bicyclerent.controller;

import com.auca.bicyclerent.entity.Bicycle;
import com.auca.bicyclerent.service.BicycleService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/bicycles")
public class BicycleController {

    private final BicycleService bicycleService;

    public BicycleController(BicycleService bicycleService) {
        this.bicycleService = bicycleService;
    }

    @PostMapping("/save")
    public ResponseEntity<String> save(@RequestBody Bicycle bicycle) {
        String res = bicycleService.saveBicycle(bicycle);
        if (res.equals("Bicycle saved successfully")) {
            return new ResponseEntity<>(res, HttpStatus.CREATED);
        } else {
            return ResponseEntity.badRequest().body(res);
        }
    }

    @GetMapping("/all")
    public ResponseEntity<List<Bicycle>> getAll() {
        return ResponseEntity.ok(bicycleService.getAllBicycles());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable UUID id) {
        Optional<Bicycle> opt = bicycleService.getBicycleById(id);
        if (opt.isPresent()) {
            return ResponseEntity.ok(opt.get());
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("The bicycle with that id does not exist");
        }
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<String> update(@PathVariable UUID id, @RequestBody Bicycle bicycle) {
        String res = bicycleService.updateBicycle(id, bicycle);
        if (res.equals("Bicycle updated successfully")) {
            return ResponseEntity.ok(res);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(res);
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> delete(@PathVariable UUID id) {
        String res = bicycleService.deleteBicycle(id);
        if (res.equals("Bicycle deleted successfully")) {
            return ResponseEntity.ok(res);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(res);
        }
    }

    // C1 JPQL
    @GetMapping("/by-accessory")
    public ResponseEntity<List<Bicycle>> getByAccessory(@RequestParam String name) {
        return ResponseEntity.ok(bicycleService.getBicyclesByAccessory(name));
    }

    // D3
    @PutMapping("/{bicycleId}/accessories/{accessoryId}")
    public ResponseEntity<String> addAccessory(@PathVariable UUID bicycleId, @PathVariable UUID accessoryId) {
        String res = bicycleService.addAccessoryToBicycle(bicycleId, accessoryId);
        if (res.equals("Accessory added to the bicycle")) {
            return ResponseEntity.ok(res);
        } else if (res.equals("The bicycle with that id does not exist") || res.equals("That accessory does not exist")) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(res);
        } else if (res.equals("The bicycle already has that accessory")) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(res);
        } else {
            return ResponseEntity.badRequest().body(res);
        }
    }

    // D4
    @PatchMapping("/{bicycleId}/rack")
    public ResponseEntity<String> changeRack(@PathVariable UUID bicycleId, @RequestParam int rackNumber) {
        String res = bicycleService.changeRack(bicycleId, rackNumber);
        if (res.equals("Rack changed successfully")) {
            return ResponseEntity.ok(res);
        } else if (res.equals("The bicycle with that id does not exist") || res.equals("The rack with that rackNumber does not exist")) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(res);
        } else if (res.equals("That rack is taken by another bicycle")) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(res);
        } else {
            return ResponseEntity.badRequest().body(res);
        }
    }
}
