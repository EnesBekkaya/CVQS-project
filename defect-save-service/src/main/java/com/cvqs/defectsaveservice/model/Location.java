package com.cvqs.defectsaveservice.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;

@Entity
public class Location {
    @Id
    private String id;

    private String x;

}
