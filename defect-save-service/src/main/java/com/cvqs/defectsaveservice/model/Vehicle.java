package com.cvqs.defectsaveservice.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import java.util.List;

@Entity(name = "vehicle")
@NoArgsConstructor
@AllArgsConstructor
public class Vehicle {
    @Id
    @Getter
    @GenericGenerator(name = "uuid", strategy = "uuid")
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "uuid")
    @Column(length = 36, nullable = false, updatable = false)
    @JsonIgnore
    private String id;
    @Getter
    @Setter
    @Column(name="brand",nullable = false)
    private String brand;
    @Getter
    @Setter
    @Column(name="registrationPlate",nullable = false,unique = true)
    @NotEmpty(message = "registrationPlate  değeri boş bırakılamaz")
    private String  registrationPlate;
    @Getter
    @Setter
    @OneToMany(mappedBy = "vehicle",cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Defect> defect;
}
