package edu.pollub.kindergartenservice.repository;

import edu.pollub.kindergartenservice.model.Kid;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface KidRepository extends JpaRepository<Kid, Integer> {
}
