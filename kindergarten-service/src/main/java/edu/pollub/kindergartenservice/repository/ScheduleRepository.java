package edu.pollub.kindergartenservice.repository;

import edu.pollub.kindergartenservice.model.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Time;
import java.util.Optional;

@Repository
@Transactional
public interface ScheduleRepository extends JpaRepository<Schedule, Long> {
    Optional<Schedule> findByDayOfWeekAndStartTimeAndEndTime(String dayOfWeek, Time startTime, Time endTime);
}
