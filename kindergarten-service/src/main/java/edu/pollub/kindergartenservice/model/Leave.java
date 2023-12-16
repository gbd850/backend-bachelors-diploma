package edu.pollub.kindergartenservice.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Leave {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private Integer employeeId;
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "facility_id",
    referencedColumnName = "id")
    private Facility facility;
    private Date startDate;
    private Date endDate;
    @Enumerated(value = EnumType.STRING)
    private LeaveStatus status;
}
