package edu.pollub.cateringservice.controller;

import edu.pollub.cateringservice.dto.CateringRequest;
import edu.pollub.cateringservice.dto.MealRequest;
import edu.pollub.cateringservice.model.Catering;
import edu.pollub.cateringservice.model.Meal;
import edu.pollub.cateringservice.service.CateringService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.http.HttpClient;
import java.util.List;

@RestController
@RequestMapping("/api/catering")
@AllArgsConstructor
public class CateringController {
    private CateringService cateringService;
    @GetMapping
    public ResponseEntity<List<Catering>> getAllCaterings() {
        return new ResponseEntity<>(cateringService.getAllCaterings(), HttpStatus.OK);
    }
    @GetMapping("{cateringId}")
    public ResponseEntity<Catering> getCateringById(@PathVariable Integer cateringId) {
        return new ResponseEntity<>(cateringService.getCateringById(cateringId), HttpStatus.OK);
    }
    @GetMapping("{cateringId}/meals")
    public ResponseEntity<List<Meal>> getCateringMeals(@PathVariable Integer cateringId) {
        return new ResponseEntity<>(cateringService.getCateringMeals(cateringId), HttpStatus.OK);
    }
    @GetMapping("/group/{groupId}")
    public ResponseEntity<Catering> getCateringByGroupId(@PathVariable Integer groupId) {
        return new ResponseEntity<>(cateringService.getCateringByGroupId(groupId), HttpStatus.OK);
    }
    @PostMapping
    public ResponseEntity<Catering> createCatering(@RequestBody CateringRequest cateringRequest) {
        return new ResponseEntity<>(cateringService.createCatering(cateringRequest), HttpStatus.CREATED);
    }
    @PostMapping("{cateringId}/meals")
    public ResponseEntity<Meal> createMeal(@RequestBody MealRequest mealRequest) {
        return new ResponseEntity<>(cateringService.createMeal(mealRequest), HttpStatus.CREATED);
    }
}
