package edu.pollub.kindergartenservice.repository;

import edu.pollub.kindergartenservice.model.Attendance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
public interface AttendanceRepository extends JpaRepository<Attendance, Long> {
    List<Attendance> findByKidId(Integer kidId);
}
