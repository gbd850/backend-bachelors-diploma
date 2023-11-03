package edu.pollub.dictionaryservice.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Kid {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String firstName;
    private String middleName;
    private String lastName;
    private String pesel;
    private Date birthDate;
//    @OneToOne(cascade = CascadeType.ALL)
//    @JoinColumn(name = "catering_id",
//            referencedColumnName = "id")
//    private Catering catering;
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "parent_id",
            referencedColumnName = "id")
    private User parent;
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "group_id",
            referencedColumnName = "id")
    private Group group;
}
