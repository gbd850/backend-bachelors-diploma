package edu.pollub.cateringservice.repository;

import edu.pollub.cateringservice.model.Catering;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@Transactional
public interface CateringRepository extends JpaRepository<Catering, Integer> {
    Optional<Catering> findByGroupId(Integer id);
}
