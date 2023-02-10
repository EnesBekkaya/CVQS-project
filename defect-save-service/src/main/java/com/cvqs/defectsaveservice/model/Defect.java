package com.cvqs.defectsaveservice.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of={"id"})
@Table(name="defects")
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
    @OneToOne
    private Vehicle vehicle;
    @Getter
    @Setter
    @OneToMany
    private List<Location> location;

}
