package com.cvqs.usermanagementservice.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import java.util.List;
@Entity
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of={"id"})
@Table(name="users")
public class User {
    @Id
    @Getter
    @GenericGenerator(name = "uuid", strategy = "uuid")
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "uuid")
    @Column(length = 36, nullable = false, updatable = false)
    private String id;
    @Getter
    @Setter
    @Column(name="name")
    private String name;
    @Getter
    @Setter
    @Column(name="last_name")
    private String lastName;
    @Getter
    @Setter
    @Column(name="user_name")
    private String userName;
    @Getter
    @Setter
    @ManyToMany(fetch = FetchType.EAGER)
    @Column(name="roles")
    private List<Role> roles;

}
