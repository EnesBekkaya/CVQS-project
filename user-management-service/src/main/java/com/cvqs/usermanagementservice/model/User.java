package com.cvqs.usermanagementservice.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Where;

import java.util.List;
@Entity
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of={"id"})
@Table(name="users")
@Where(clause = "deleted = false")
public class User {
    @Id
    @Getter
    @GenericGenerator(name = "uuid", strategy = "uuid")
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "uuid")
    @Column(length = 36, nullable = false, updatable = false)
    private String id;
    @Getter
    @Setter
    @Column(name="name",nullable = false)
    private String name;
    @Getter
    @Setter
    @Column(name="last_name",nullable = false)
    private String lastname;
    @Getter
    @Setter
    @Column(name="user_name",nullable = false,unique = true)
    private String username;
    @Getter
    @Setter
    @Column(name="password",nullable = false)
    private String password;
    @Getter
    @Setter
    @ManyToMany(fetch = FetchType.LAZY,cascade = CascadeType.ALL)
    @JoinTable(name = "user_role",
            joinColumns = { @JoinColumn(name = "user_id") },
            inverseJoinColumns = { @JoinColumn(name = "role_id") })
    @Column(name="roles",nullable = false)
    private List<Role> roles;
    @Getter
    @Setter
    @Column(name="deleted")
    private boolean deleted;


}
