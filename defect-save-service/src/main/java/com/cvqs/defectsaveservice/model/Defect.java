package com.cvqs.defectsaveservice.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;
import java.util.List;
@Entity
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of={"id"})
@Table(name="defect")
@JsonIgnoreProperties({"hibernateLazyInitializer","handler","vehichle","location"})
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
    @JoinColumn(name="vehichle_id",nullable = false)
    @JsonIgnore
    private Vehichle vehichle;
    @Getter
    @Setter
    @OneToMany(mappedBy = "defect",fetch = FetchType.LAZY,cascade =CascadeType.ALL)
    private List<Location> locations;

    //image eklenecek

}
