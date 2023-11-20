package edu.pollub.kindergartenservice.repository;

import edu.pollub.kindergartenservice.model.SchoolClass;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public interface SchoolClassRepository extends JpaRepository<SchoolClass, Long> {
}