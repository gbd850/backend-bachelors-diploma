package edu.pollub.cateringservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GroupResponse {
    private Integer id;
    private String name;
    private Integer facilityId;
}
