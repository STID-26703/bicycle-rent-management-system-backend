package com.auca.bicyclerent.entity;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Entity
@Table(name = "bicycle")
public class Bicycle {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private String brand;

    private String model;

    private String bikeCode;

    @ManyToOne
    @JoinColumn(name = "rack_id", nullable = true)
    private Rack rack;

    @ManyToMany
    @JoinTable(
            name = "bicycle_accessory",
            joinColumns = @JoinColumn(name = "bicycle_id"),
            inverseJoinColumns = @JoinColumn(name = "accessory_id")
    )
    private List<Accessory> accessories = new ArrayList<>();

    public Bicycle() {
    }

    public Bicycle(UUID id, String brand, String model, String bikeCode, Rack rack, List<Accessory> accessories) {
        this.id = id;
        this.brand = brand;
        this.model = model;
        this.bikeCode = bikeCode;
        this.rack = rack;
        this.accessories = (accessories != null) ? accessories : new ArrayList<>();
    }

    public Bicycle(String brand, String model, String bikeCode, Rack rack) {
        this.brand = brand;
        this.model = model;
        this.bikeCode = bikeCode;
        this.rack = rack;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getBikeCode() {
        return bikeCode;
    }

    public void setBikeCode(String bikeCode) {
        this.bikeCode = bikeCode;
    }

    public Rack getRack() {
        return rack;
    }

    public void setRack(Rack rack) {
        this.rack = rack;
    }

    public List<Accessory> getAccessories() {
        return accessories;
    }

    public void setAccessories(List<Accessory> accessories) {
        this.accessories = accessories;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Bicycle bicycle = (Bicycle) o;
        return Objects.equals(id, bicycle.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
