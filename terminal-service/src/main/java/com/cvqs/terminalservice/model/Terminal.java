package com.cvqs.terminalservice.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;
import java.util.List;


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
   /* @Column(name="createdDate",nullable = false)
    @Getter
    @Setter
    private Date createdDate;*/
    @Column(name="active",nullable = false)
    @Getter
    @Setter
    private Boolean active;
    @Column(name="deleted",nullable = false)
    @Getter
    @Setter
    private Boolean deleted;
    @Getter
    @Setter
    @ManyToMany(fetch = FetchType.LAZY,cascade = CascadeType.ALL)
    @JoinTable(name = "terminal_section",
            joinColumns = { @JoinColumn(name = "terminal_id") },
            inverseJoinColumns = { @JoinColumn(name = "section_id") })
    private List<Section> sections;

}
