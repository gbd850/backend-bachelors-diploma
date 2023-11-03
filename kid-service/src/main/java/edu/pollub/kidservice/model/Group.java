package edu.pollub.kidservice.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Group {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;
    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "group_class",
            joinColumns = { @JoinColumn(
                    name = "group_id",
                    referencedColumnName = "id"
            ) },
            inverseJoinColumns = { @JoinColumn(
                    name = "class_id",
                    referencedColumnName = "id"
            ) }
    )
    private SchoolClass aClass;
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "facility_id",
            referencedColumnName = "id")
    private Facility facility;
}
