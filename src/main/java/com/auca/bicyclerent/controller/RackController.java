package com.auca.bicyclerent.controller;

import com.auca.bicyclerent.entity.Rack;
import com.auca.bicyclerent.service.RackService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/racks")
public class RackController {

    private final RackService rackService;

    public RackController(RackService rackService) {
        this.rackService = rackService;
    }

    @PostMapping("/save")
    public ResponseEntity<String> save(@RequestBody Rack rack) {
        String res = rackService.saveRack(rack);
        if (res.equals("Rack saved successfully")) {
            return new ResponseEntity<>(res, HttpStatus.CREATED);
        } else {
            return ResponseEntity.badRequest().body(res);
        }
    }

    @GetMapping("/all")
    public ResponseEntity<List<Rack>> getAll() {
        return ResponseEntity.ok(rackService.getAllRacks());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable UUID id) {
        Optional<Rack> opt = rackService.getRackById(id);
        if (opt.isPresent()) {
            return ResponseEntity.ok(opt.get());
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("The rack with that id does not exist");
        }
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<String> update(@PathVariable UUID id, @RequestBody Rack rack) {
        String res = rackService.updateRack(id, rack);
        if (res.equals("Rack updated successfully")) {
            return ResponseEntity.ok(res);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(res);
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> delete(@PathVariable UUID id) {
        String res = rackService.deleteRack(id);
        if (res.equals("Rack deleted successfully")) {
            return ResponseEntity.ok(res);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(res);
        }
    }

    // C2 JPQL
    @GetMapping("/free")
    public ResponseEntity<List<Rack>> getFreeRacks() {
        return ResponseEntity.ok(rackService.getFreeRacks());
    }
}
