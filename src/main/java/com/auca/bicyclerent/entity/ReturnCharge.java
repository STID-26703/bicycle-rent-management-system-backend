package com.auca.bicyclerent.entity;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.Objects;
import java.util.UUID;

@Entity
@Table(name = "return_charge")
public class ReturnCharge {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private String chargeName;

    private String notes;

    private int amount;

    private LocalDate chargedDate;

    @ManyToOne
    @JoinColumn(name = "hire_id")
    private Hire hire;

    public ReturnCharge() {
    }

    public ReturnCharge(UUID id, String chargeName, String notes, int amount, LocalDate chargedDate, Hire hire) {
        this.id = id;
        this.chargeName = chargeName;
        this.notes = notes;
        this.amount = amount;
        this.chargedDate = chargedDate;
        this.hire = hire;
    }

    public ReturnCharge(String chargeName, String notes, int amount, LocalDate chargedDate, Hire hire) {
        this.chargeName = chargeName;
        this.notes = notes;
        this.amount = amount;
        this.chargedDate = chargedDate;
        this.hire = hire;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getChargeName() {
        return chargeName;
    }

    public void setChargeName(String chargeName) {
        this.chargeName = chargeName;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public LocalDate getChargedDate() {
        return chargedDate;
    }

    public void setChargedDate(LocalDate chargedDate) {
        this.chargedDate = chargedDate;
    }

    public Hire getHire() {
        return hire;
    }

    public void setHire(Hire hire) {
        this.hire = hire;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ReturnCharge that = (ReturnCharge) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
