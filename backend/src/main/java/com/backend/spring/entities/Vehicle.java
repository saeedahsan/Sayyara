package com.backend.spring.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import static com.fasterxml.jackson.annotation.JsonProperty.Access.WRITE_ONLY;
import static javax.persistence.GenerationType.SEQUENCE;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "vehicles")
public class Vehicle {
    @JsonIgnore
    @Id
    @SequenceGenerator(name = "vehicle_sequence", sequenceName = "vehicle_sequence", allocationSize = 1)
    @GeneratedValue(strategy = SEQUENCE, generator = "vehicle_sequence")
    @Column(name = "vehicle_id")
    private Long id;

    @Column(name = "vehicle_year")
    private int year;

    @Column(name = "vehicle_make")
    private String make;

    @Column(name = "vehicle_model")
    private String model;

    @Column(name = "vehicle_vin")
    private String vin;

    @Column(name = "vehicle_plate")
    private String plate;

    @JsonProperty(access = WRITE_ONLY)
    @ToString.Exclude
    @OneToOne
    @JoinColumn(name = "user_id", referencedColumnName = "user_id")
    private VehicleOwner owner;

    private String registeredOwner;

    public Vehicle(int year, String make, String model, String vin, String plate, String registeredOwner) {
        this.year = year;
        this.make = make;
        this.model = model;
        this.vin = vin;
        this.plate = plate;
        this.registeredOwner = registeredOwner;
    }
}
