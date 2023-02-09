package com.cvqs.usermanagementservice.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of={"id"})
@Table(name="roles")
public class Role {
    @Id
    @Getter
    @SequenceGenerator(name = "seq_role",allocationSize = 1)
    @GeneratedValue(generator = "seq_role",strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "name")
    @Getter
    @Setter
    private String name;
}
