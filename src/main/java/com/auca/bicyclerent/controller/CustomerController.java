package com.auca.bicyclerent.controller;

import com.auca.bicyclerent.entity.Customer;
import com.auca.bicyclerent.service.CustomerService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/customers")
public class CustomerController {

    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @PostMapping("/save")
    public ResponseEntity<String> save(@RequestBody Customer customer) {
        String res = customerService.saveCustomer(customer);
        if (res.equals("Customer saved successfully")) {
            return new ResponseEntity<>(res, HttpStatus.CREATED);
        } else {
            return ResponseEntity.badRequest().body(res);
        }
    }

    @GetMapping("/all")
    public ResponseEntity<List<Customer>> getAll() {
        return ResponseEntity.ok(customerService.getAllCustomers());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable UUID id) {
        Optional<Customer> opt = customerService.getCustomerById(id);
        if (opt.isPresent()) {
            return ResponseEntity.ok(opt.get());
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("The customer with that id does not exist");
        }
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<String> update(@PathVariable UUID id, @RequestBody Customer customer) {
        String res = customerService.updateCustomer(id, customer);
        if (res.equals("Customer updated successfully")) {
            return ResponseEntity.ok(res);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(res);
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> delete(@PathVariable UUID id) {
        String res = customerService.deleteCustomer(id);
        if (res.equals("Customer deleted successfully")) {
            return ResponseEntity.ok(res);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(res);
        }
    }

    // B1 DERIVED
    @GetMapping("/by-last-name")
    public ResponseEntity<List<Customer>> getByLastName(@RequestParam String lastName) {
        return ResponseEntity.ok(customerService.getCustomersByLastName(lastName));
    }

    // C4 JPQL
    @GetMapping("/of-bicycle/{bicycleId}")
    public ResponseEntity<?> getCustomersOfBicycle(@PathVariable UUID bicycleId) {
        Optional<List<Customer>> opt = customerService.getCustomersOfBicycle(bicycleId);
        if (opt.isPresent()) {
            return ResponseEntity.ok(opt.get());
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("The bicycle with that id does not exist");
        }
    }
}
