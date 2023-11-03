package edu.pollub.dictionaryservice.repository;

import edu.pollub.dictionaryservice.model.Facility;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public interface FacilityRepository extends JpaRepository<Facility, Integer> {
}
