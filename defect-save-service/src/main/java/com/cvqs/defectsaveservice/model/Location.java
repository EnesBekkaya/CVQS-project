package com.cvqs.defectsaveservice.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;


@Entity
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of={"id"})
@Table(name="location")
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
    private int x;
    @Getter
    @Setter
    @Column(name="y",nullable = false)
    private int y;
    @Getter
    @Setter
    @ManyToOne(fetch = FetchType.LAZY, cascade =CascadeType.ALL)
    @JoinColumn(name="defect_id",nullable = false,referencedColumnName = "id")
    @JsonIgnore
    private Defect defect;

}
