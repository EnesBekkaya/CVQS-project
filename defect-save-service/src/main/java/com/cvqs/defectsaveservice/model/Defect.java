package com.cvqs.defectsaveservice.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;
import java.util.List;
@Entity(name = "defect")
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Defect {
    @Id
    @Getter
    @GenericGenerator(name = "uuid", strategy = "uuid")
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "uuid")
    @Column(length = 36, nullable = false, updatable = false)
    private String id;
    @Getter
    @Setter
    @Column(name="type",nullable = false)
    private String type;
    @Getter
    @Setter
    @ManyToOne
    @JoinColumn(name="vehicle_id",nullable = false)
    @JsonIgnore
    private Vehicle vehicle;
    @Getter
    @Setter
    @ManyToMany(fetch = FetchType.LAZY,cascade = CascadeType.ALL)
    @JoinTable(name = "defect_location",
            joinColumns = { @JoinColumn(name = "defect_id") },
            inverseJoinColumns = { @JoinColumn(name = "location_id") })
    @Column(name="roles",nullable = false)
    @Size(min = 1)
    private List<Location> locations;
    @Getter
    @Setter
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "image_id", referencedColumnName = "id")
    private Image image;

    public Defect(String type, Vehicle vehicle, List<Location> locations, Image image) {
        this.type = type;
        this.vehicle = vehicle;
        this.locations = locations;
        this.image = image;
    }
}
