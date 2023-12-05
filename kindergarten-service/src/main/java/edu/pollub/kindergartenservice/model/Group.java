package edu.pollub.kindergartenservice.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "school_group")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Group {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;
    private Long teacherId;
    @OneToMany(mappedBy = "group")
    private List<SchoolClass> aClass;
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "facility_id",
            referencedColumnName = "id")
    private Facility facility;
}
