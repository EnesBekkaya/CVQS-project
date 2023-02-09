package com.cvqs.terminalservice.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;


@Entity
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of={"id"})
@Table(name="terminals")
public class Terminal {
    @Id
    @Getter
    @GenericGenerator(name = "uuid", strategy = "uuid")
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "uuid")
    @Column(length = 36, nullable = false, updatable = false)
    private String id;
    @Column(name="name",nullable = false)
    @Getter
    @Setter
    private String name;
    @Column(name="active",nullable = false)
    @Getter
    @Setter
    private Boolean active;
    @Column(name="section",nullable = false)
    @Getter
    @Setter
    private String section;
}
