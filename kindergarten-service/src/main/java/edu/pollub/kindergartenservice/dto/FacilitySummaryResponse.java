package edu.pollub.kindergartenservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FacilitySummaryResponse {
    private Integer presentChildrenToday;
    private Integer absentChildrenToday;
    private Integer absentChildrenTomorrow;
    private Integer totalChildren;
}
