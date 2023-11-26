package edu.pollub.kindergartenservice.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Group {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;
    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
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
