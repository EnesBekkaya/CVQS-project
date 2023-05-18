package com.cvqs.usermanagementservice.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;

import java.util.List;

@Entity(name = "role")
@NoArgsConstructor
@AllArgsConstructor
public class Role {
    @Id
    @Getter
    @SequenceGenerator(name = "seq_role",allocationSize = 1)
    @GeneratedValue(generator = "seq_role",strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "name",unique = true)
    @Getter
    @Setter
    @NotEmpty(message = "Role için name alanı boş bırakılamaz")
    private String name;
    @Getter
    @Setter
    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "roles")
    @JsonIgnore
    private List<User> users;
}
