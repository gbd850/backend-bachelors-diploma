package edu.pollub.cateringservice.repository;

import edu.pollub.cateringservice.model.Meal;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Transactional
public interface MealRepository extends JpaRepository<Meal, Integer> {
    List<Meal> findByCateringId(Integer id);
}
