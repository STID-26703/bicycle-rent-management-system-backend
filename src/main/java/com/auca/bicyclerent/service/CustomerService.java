package com.auca.bicyclerent.service;

import com.auca.bicyclerent.entity.Customer;
import com.auca.bicyclerent.repository.BicycleRepository;
import com.auca.bicyclerent.repository.CustomerRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class CustomerService {

    private final CustomerRepository customerRepository;
    private final BicycleRepository bicycleRepository;

    public CustomerService(CustomerRepository customerRepository, BicycleRepository bicycleRepository) {
        this.customerRepository = customerRepository;
        this.bicycleRepository = bicycleRepository;
    }

    public String saveCustomer(Customer customer) {
        customerRepository.save(customer);
        return "Customer saved successfully";
    }

    public List<Customer> getAllCustomers() {
        return customerRepository.findAll();
    }

    public Optional<Customer> getCustomerById(UUID id) {
        return customerRepository.findById(id);
    }

    public String updateCustomer(UUID id, Customer customer) {
        Optional<Customer> opt = customerRepository.findById(id);
        if (opt.isEmpty()) {
            return "The customer with that id does not exist";
        }
        Customer existing = opt.get();
        if (customer.getFirstName() != null) {
            existing.setFirstName(customer.getFirstName());
        }
        if (customer.getLastName() != null) {
            existing.setLastName(customer.getLastName());
        }
        if (customer.getNationalId() != null) {
            existing.setNationalId(customer.getNationalId());
        }
        if (customer.getPhone() != null) {
            existing.setPhone(customer.getPhone());
        }
        customerRepository.save(existing);
        return "Customer updated successfully";
    }

    public String deleteCustomer(UUID id) {
        Optional<Customer> opt = customerRepository.findById(id);
        if (opt.isEmpty()) {
            return "The customer with that id does not exist";
        }
        customerRepository.delete(opt.get());
        return "Customer deleted successfully";
    }

    // B1 DERIVED
    public List<Customer> getCustomersByLastName(String lastName) {
        return customerRepository.findByLastNameIgnoreCaseOrderByFirstNameAsc(lastName);
    }

    // C4 JPQL
    public Optional<List<Customer>> getCustomersOfBicycle(UUID bicycleId) {
        if (!bicycleRepository.existsById(bicycleId)) {
            return Optional.empty();
        }
        return Optional.of(customerRepository.findCustomersByBicycleId(bicycleId));
    }
}
