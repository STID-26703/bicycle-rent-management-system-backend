package com.auca.bicyclerent.controller;

import com.auca.bicyclerent.entity.ReturnCharge;
import com.auca.bicyclerent.service.ReturnChargeService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/return-charges")
public class ReturnChargeController {

    private final ReturnChargeService returnChargeService;

    public ReturnChargeController(ReturnChargeService returnChargeService) {
        this.returnChargeService = returnChargeService;
    }

    // E1
    @PostMapping("/save")
    public ResponseEntity<String> save(@RequestParam UUID hireId, @RequestBody ReturnCharge returnCharge) {
        String res = returnChargeService.saveReturnCharge(hireId, returnCharge);
        if (res.equals("Return charge saved successfully")) {
            return new ResponseEntity<>(res, HttpStatus.CREATED);
        } else if (res.equals("The hire with that id does not exist")) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(res);
        } else if (res.equals("Return charges are only for RETURNED hires")) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(res);
        } else {
            return ResponseEntity.badRequest().body(res);
        }
    }

    // E2 JPQL
    @GetMapping("/customer/{nationalId}")
    public ResponseEntity<List<ReturnCharge>> getByCustomerNationalId(@PathVariable String nationalId) {
        return ResponseEntity.ok(returnChargeService.getReturnChargesByCustomerNationalId(nationalId));
    }

    // E3
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> delete(@PathVariable UUID id) {
        String res = returnChargeService.deleteReturnCharge(id);
        if (res.equals("Return charge deleted successfully")) {
            return ResponseEntity.ok(res);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(res);
        }
    }

    @GetMapping("/all")
    public ResponseEntity<List<ReturnCharge>> getAll() {
        return ResponseEntity.ok(returnChargeService.getAllReturnCharges());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable UUID id) {
        Optional<ReturnCharge> opt = returnChargeService.getReturnChargeById(id);
        if (opt.isPresent()) {
            return ResponseEntity.ok(opt.get());
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("The return charge with that id does not exist");
        }
    }
}
