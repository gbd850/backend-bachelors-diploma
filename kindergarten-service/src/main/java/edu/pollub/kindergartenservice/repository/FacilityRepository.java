package edu.pollub.kindergartenservice.repository;

import edu.pollub.kindergartenservice.model.Facility;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
public interface FacilityRepository extends JpaRepository<Facility, Integer> {
    List<Facility> findByPrincipalId(Integer principalId);
}
