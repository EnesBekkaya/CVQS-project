package com.cvqs.defectlistingservice.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import java.sql.Blob;

@Entity(name = "image")
@NoArgsConstructor
@AllArgsConstructor
public class Image {
    @Id
    @GenericGenerator(name = "uuid", strategy = "uuid")
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "uuid")
    @Getter
    @Column(length = 36, nullable = false, updatable = false)
    private String id;
    @Column(name = "data")
    @Setter
    @Getter
    @Lob
    private Blob data;
    @Getter
    @Setter
    @OneToOne(mappedBy = "image")
    private Defect defect;
}
