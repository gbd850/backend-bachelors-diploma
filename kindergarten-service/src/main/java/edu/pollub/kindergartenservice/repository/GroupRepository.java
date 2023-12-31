package edu.pollub.kindergartenservice.repository;

import edu.pollub.kindergartenservice.model.Group;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
public interface GroupRepository extends JpaRepository<Group, Integer> {
    List<Group> findByFacilityId(Integer id);
}
