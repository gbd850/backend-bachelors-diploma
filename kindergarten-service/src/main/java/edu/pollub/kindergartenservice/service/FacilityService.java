package edu.pollub.kindergartenservice.service;

import edu.pollub.kindergartenservice.dto.*;
import edu.pollub.kindergartenservice.model.*;
import edu.pollub.kindergartenservice.repository.*;
import lombok.AllArgsConstructor;
import org.aspectj.weaver.ast.Literal;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Mono;

import java.util.*;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class FacilityService {

    private FacilityRepository facilityRepository;
    private GroupRepository groupRepository;
    private SchoolClassRepository schoolClassRepository;
    private ScheduleRepository scheduleRepository;
    private KidRepository kidRepository;
    private AttendanceRepository attendanceRepository;
    private WebClient.Builder webClientBuilder;

    public ResponseEntity<List<Facility>> getAllFacilities() {
        return new ResponseEntity<>(facilityRepository.findAll(), HttpStatus.OK);
    }

    public ResponseEntity<Facility> createFacility(FacilityRequest facilityRequest) {
        AccountResponse principal = webClientBuilder.build().get()
                .uri("http://account-service/api/users/"+facilityRequest.getPrincipalId())
                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError, response ->
                        Mono.error(new WebClientResponseException(HttpStatus.NOT_FOUND, "Principal not found", null, null, null, null))
                )
                .bodyToMono(AccountResponse.class)
                .block();
        if (principal == null || principal.getRole().equalsIgnoreCase("ADMIN")) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Principal not found");
        }
        Facility facility = Facility.builder()
                .name(facilityRequest.getName())
                .principalId(facilityRequest.getPrincipalId())
                .build();
        facilityRepository.save(facility);
        return new ResponseEntity<>(facility, HttpStatus.CREATED);
    }

    public ResponseEntity<List<Group>> getAllGroups() {
        return new ResponseEntity<>(groupRepository.findAll(), HttpStatus.OK);
    }

    public ResponseEntity<Group> createGroup(GroupRequest groupRequest) {
        Facility facility = facilityRepository.findById(groupRequest.getFacilityId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Facility not found"));
        Group group = Group.builder()
                .name(groupRequest.getName())
                .facility(facility)
                .teacherId(groupRequest.getTeacherId())
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

    public List<Group> getAllGroupsInFacility(Integer facilityId) {
        facilityRepository.findById(facilityId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Facility not found"));
        return groupRepository.findByFacilityId(facilityId);
    }

    public FacilitySummaryResponse getFacilitySummary(Integer facilityId) {
        List<Group> groups = getAllGroupsInFacility(facilityId);
        Integer totalChildren = 0;
        for (var group : groups) {
            totalChildren += kidRepository.countByGroupId(group.getId());
        }
        Integer presentChildrenToday = attendanceRepository.countDistinctPresentKidByDate(new Date());

        Integer absentChildrenToday = attendanceRepository.countDistinctAbsentKidByDate(new Date());

        Date tommorowDate = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(tommorowDate);
        calendar.add(Calendar.DATE, 1);
        tommorowDate = calendar.getTime();
        Integer absentChildrenTomorrow = attendanceRepository.countDistinctAbsentKidByDate(tommorowDate);

        return FacilitySummaryResponse.builder()
                .presentChildrenToday(presentChildrenToday)
                .absentChildrenToday(absentChildrenToday)
                .absentChildrenTomorrow(absentChildrenTomorrow)
                .totalChildren(totalChildren)
                .build();

    }

    public Group getGroupById(Integer groupId) {
        return groupRepository.findById(groupId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Group not found"));
    }

    private Group findGroupInCollectionByTeacherId(List<Group> groups, Integer teacherId) {
        return groups.stream().filter(group -> group.getTeacherId().intValue() == teacherId).findFirst()
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Group with matching teacher not found"));
    }

    public List<Employee> getAllEmployees() {
        AccountResponse[] teachers = webClientBuilder.build().get()
                .uri("http://account-service/api/account/?role=TEACHER")
                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError, response ->
                        Mono.error(new WebClientResponseException(HttpStatus.NOT_FOUND, "Teacher not found", null, null, null, null))
                )
                .bodyToMono(AccountResponse[].class)
                .block();
        if (teachers == null || teachers.length == 0) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Teachers not found");
        }
        List<AccountResponse> teachersList = Arrays.stream(teachers).toList();
        List<Group> groups = groupRepository.findAll();
        return teachersList.stream()
                .map(teacher -> new Employee(
                        teacher.getId(),
                        teacher.getFirstName(),
                        teacher.getLastName(),
                        findGroupInCollectionByTeacherId(groups, teacher.getId()).getFacility().getId(),
                        findGroupInCollectionByTeacherId(groups, teacher.getId()).getId()
                ))
                .collect(Collectors.toList());
    }
}
