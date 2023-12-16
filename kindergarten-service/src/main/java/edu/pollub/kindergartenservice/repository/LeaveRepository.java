package edu.pollub.kindergartenservice.repository;

import edu.pollub.kindergartenservice.model.Leave;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
@Transactional
public interface LeaveRepository extends JpaRepository<Leave, Integer> {
}
