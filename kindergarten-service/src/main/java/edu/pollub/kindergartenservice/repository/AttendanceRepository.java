package edu.pollub.kindergartenservice.repository;

import edu.pollub.kindergartenservice.model.Attendance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Repository
@Transactional
public interface AttendanceRepository extends JpaRepository<Attendance, Long> {
    List<Attendance> findByKidId(Integer kidId);
    @Query(
            nativeQuery = true,
            value = "SELECT COUNT(DISTINCT kid_id) FROM `attendance` WHERE is_present = 1 AND date = :date"
    )
    Integer countDistinctPresentKidByDate(@Param("date") Date date);
    @Query(
            nativeQuery = true,
            value = "SELECT COUNT(DISTINCT kid_id) FROM `attendance` WHERE is_present = 0 AND date = :date"
    )
    Integer countDistinctAbsentKidByDate(@Param("date") Date date);
}
