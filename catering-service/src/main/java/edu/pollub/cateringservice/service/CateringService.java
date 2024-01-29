package edu.pollub.cateringservice.service;

import edu.pollub.cateringservice.dto.CateringRequest;
import edu.pollub.cateringservice.dto.GroupResponse;
import edu.pollub.cateringservice.dto.MealRequest;
import edu.pollub.cateringservice.model.Catering;
import edu.pollub.cateringservice.model.Meal;
import edu.pollub.cateringservice.repository.CateringRepository;
import edu.pollub.cateringservice.repository.MealRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Mono;

import java.util.List;

@Service
@AllArgsConstructor
public class CateringService {
    private CateringRepository cateringRepository;
    private MealRepository mealRepository;
    private WebClient.Builder webClientBuilder;

    public List<Catering> getAllCaterings() {
        return cateringRepository.findAll();
    }

    public Catering getCateringById(Integer cateringId) {
        return cateringRepository.findById(cateringId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Catering not found"));
    }

    public List<Meal> getCateringMeals(Integer cateringId) {
        Catering catering = cateringRepository.findById(cateringId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Catering not found"));
        return mealRepository.findByCateringId(cateringId);
    }

    public Catering getCateringByGroupId(Integer groupId) {
        return cateringRepository.findByGroupId(groupId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Catering group not found"));
    }

    public Catering createCatering(CateringRequest cateringRequest) {
        GroupResponse group = webClientBuilder.build().get()
                .uri("http://kindergarten-service/api/facility/groups"+cateringRequest.getGroupId())
                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError, response ->
                        Mono.error(new WebClientResponseException(HttpStatus.NOT_FOUND, "Group not found", null, null, null, null))
                )
                .bodyToMono(GroupResponse.class)
                .block();
        if (group == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Group not found");
        }
        Catering catering = Catering.builder()
                .groupId(group.getId())
                .build();
        cateringRepository.save(catering);
        return catering;
    }

    public Meal createMeal(MealRequest mealRequest) {
        Catering catering = getCateringById(mealRequest.getCateringId());
        Meal meal = Meal.builder()
                .name(mealRequest.getName())
                .catering(catering)
                .build();
        mealRepository.save(meal);
        return meal;

    }
}
