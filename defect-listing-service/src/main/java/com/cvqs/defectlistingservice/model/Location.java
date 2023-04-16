package com.cvqs.defectlistingservice.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import java.util.List;

@Entity(name = "location")
@NoArgsConstructor
@AllArgsConstructor
public class Location {
    @Id
    @Getter
    @GenericGenerator(name = "uuid", strategy = "uuid")
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "uuid")
    @Column(length = 36, nullable = false, updatable = false)
    @JsonIgnore
    private String id;
    @Getter
    @Setter
    @Column(name="x",nullable = false)
    @NotNull(message = "x değeri boş bırakılamaz")
    @Min(value = 0,message = "x koordinatı sıfırdan küçük olamaz")
    private Integer x;
    @Getter
    @Setter
    @Column(name="y",nullable = false)
    @NotNull(message = "y değeri boş bırakılamaz")
    @Min(value = 0,message = "y koordinatı sıfırdan küçük olamaz")
    private Integer y;
    @Getter
    @Setter
    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "locations")
    @JsonIgnore
    private List<Defect> defects;
}
