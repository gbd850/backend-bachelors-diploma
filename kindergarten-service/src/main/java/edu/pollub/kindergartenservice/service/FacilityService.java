package edu.pollub.kindergartenservice.service;

import edu.pollub.kindergartenservice.dto.FacilityRequest;
import edu.pollub.kindergartenservice.dto.GroupRequest;
import edu.pollub.kindergartenservice.dto.SchoolClassRequest;
import edu.pollub.kindergartenservice.model.Facility;
import edu.pollub.kindergartenservice.model.Group;
import edu.pollub.kindergartenservice.model.Schedule;
import edu.pollub.kindergartenservice.model.SchoolClass;
import edu.pollub.kindergartenservice.repository.FacilityRepository;
import edu.pollub.kindergartenservice.repository.GroupRepository;
import edu.pollub.kindergartenservice.repository.ScheduleRepository;
import edu.pollub.kindergartenservice.repository.SchoolClassRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class FacilityService {

    private FacilityRepository facilityRepository;
    private GroupRepository groupRepository;
    private SchoolClassRepository schoolClassRepository;
    private ScheduleRepository scheduleRepository;

    public ResponseEntity<List<Facility>> getAllFacilities() {
        return new ResponseEntity<>(facilityRepository.findAll(), HttpStatus.OK);
    }

    public ResponseEntity<Facility> createFacility(FacilityRequest facilityRequest) {
        Facility facility = Facility.builder()
                .name(facilityRequest.getName())
                .build();
        facilityRepository.save(facility);
        return new ResponseEntity<>(facility, HttpStatus.CREATED);
    }

    public ResponseEntity<List<Group>> getAllGroups() {
        return new ResponseEntity<>(groupRepository.findAll(), HttpStatus.OK);
    }

    public ResponseEntity<Group> createGroup(GroupRequest groupRequest) {
        Optional<Facility> facility = facilityRepository.findById(groupRequest.getFacilityId());
        if (facility.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Facility not found");
        }
        Group group = Group.builder()
                .name(groupRequest.getName())
                .facility(facility.get())
                .build();
        groupRepository.save(group);
        return new ResponseEntity<>(group, HttpStatus.CREATED);
    }

    public ResponseEntity<List<SchoolClass>> getGroupSchedule(Integer groupId) {
        Optional<Group> group = groupRepository.findById(groupId);
        if (group.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Group not found");
        }
        List<SchoolClass> schoolClasses = schoolClassRepository.findByGroupId(groupId);
        return new ResponseEntity<>(schoolClasses, HttpStatus.OK);
    }

    public ResponseEntity<SchoolClass> createSchoolClass(Integer groupId, SchoolClassRequest schoolClassRequest) {
        Optional<Group> group = groupRepository.findById(groupId);
        if (group.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Group not found");
        }
        SchoolClass.SchoolClassBuilder schoolClass = SchoolClass.builder()
                .name(schoolClassRequest.getName())
                .teacherId(schoolClassRequest.getTeacherId())
                .group(group.get());

        Optional<Schedule> schedule = scheduleRepository.findByDayOfWeekAndStartTimeAndEndTime(
                schoolClassRequest.getDayOfWeek(),
                schoolClassRequest.getStartTime(),
                schoolClassRequest.getEndTime()
        );

        if (schedule.isEmpty()) {
            Schedule newSchedule = Schedule.builder()
                    .dayOfWeek(schoolClassRequest.getDayOfWeek())
                    .startTime(schoolClassRequest.getStartTime())
                    .endTime(schoolClassRequest.getEndTime())
                    .build();
            scheduleRepository.save(newSchedule);
            schedule = Optional.of(newSchedule);
        }

        schoolClass.schedule(schedule.get());
        schoolClassRepository.save(schoolClass.build());
        return new ResponseEntity<>(schoolClass.build(), HttpStatus.CREATED);
    }
}
