package com.auca.bicyclerent.entity;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.Objects;
import java.util.UUID;

@Entity
@Table(name = "hire")
public class Hire {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private LocalDate hireDate;

    @Enumerated(EnumType.STRING)
    private HireStatus status;

    private int hours;

    private double hourlyRate;

    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;

    @ManyToOne
    @JoinColumn(name = "bicycle_id")
    private Bicycle bicycle;

    public Hire() {
    }

    public Hire(UUID id, LocalDate hireDate, HireStatus status, int hours, double hourlyRate, Customer customer, Bicycle bicycle) {
        this.id = id;
        this.hireDate = hireDate;
        this.status = status;
        this.hours = hours;
        this.hourlyRate = hourlyRate;
        this.customer = customer;
        this.bicycle = bicycle;
    }

    public Hire(LocalDate hireDate, HireStatus status, int hours, double hourlyRate, Customer customer, Bicycle bicycle) {
        this.hireDate = hireDate;
        this.status = status;
        this.hours = hours;
        this.hourlyRate = hourlyRate;
        this.customer = customer;
        this.bicycle = bicycle;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public LocalDate getHireDate() {
        return hireDate;
    }

    public void setHireDate(LocalDate hireDate) {
        this.hireDate = hireDate;
    }

    public HireStatus getStatus() {
        return status;
    }

    public void setStatus(HireStatus status) {
        this.status = status;
    }

    public int getHours() {
        return hours;
    }

    public void setHours(int hours) {
        this.hours = hours;
    }

    public double getHourlyRate() {
        return hourlyRate;
    }

    public void setHourlyRate(double hourlyRate) {
        this.hourlyRate = hourlyRate;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Bicycle getBicycle() {
        return bicycle;
    }

    public void setBicycle(Bicycle bicycle) {
        this.bicycle = bicycle;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Hire hire = (Hire) o;
        return Objects.equals(id, hire.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
