package edu.pollub.cateringservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MealRequest {
    private String name;
    private Integer cateringId;
}
