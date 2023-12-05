package edu.pollub.kindergartenservice.service;

import edu.pollub.kindergartenservice.dto.AttendanceRequest;
import edu.pollub.kindergartenservice.dto.KidRequest;
import edu.pollub.kindergartenservice.model.*;
import edu.pollub.kindergartenservice.repository.AttendanceRepository;
import edu.pollub.kindergartenservice.repository.GroupRepository;
import edu.pollub.kindergartenservice.repository.KidRepository;
import edu.pollub.kindergartenservice.repository.SchoolClassRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.sql.Date;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class KidService {
    private KidRepository kidRepository;
    private GroupRepository groupRepository;
    private AttendanceRepository attendanceRepository;
    private SchoolClassRepository schoolClassRepository;

    public List<Kid> getAlKids() {
        return kidRepository.findAll();
    }

    public Kid saveKid(KidRequest kidRequest) {
        Kid.KidBuilder kid = Kid.builder()
                .firstName(kidRequest.getFirstName())
                .middleName(kidRequest.getMiddleName())
                .lastName(kidRequest.getLastName())
                .pesel(kidRequest.getPesel())
                .birthDate(kidRequest.getBirthDate())
                .parentId(kidRequest.getParentId());
        if (kidRequest.getGroupId() != null) {
            Optional<Group> group = groupRepository.findById(kidRequest.getGroupId());
            group.ifPresentOrElse(kid::group, () -> {throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Group not found");
            });
        }
        return kidRepository.save(kid.build());
    }

    public ResponseEntity<List<Attendance>> getKidAttendance(Integer kidId) {
        Optional<Kid> kid = kidRepository.findById(kidId);
        if (kid.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Kid not found");
        }
        return new ResponseEntity<>(attendanceRepository.findByKidId(kidId), HttpStatus.OK);
    }

    public ResponseEntity<Attendance> markKidAttendance(Integer kidId, AttendanceRequest attendanceRequest) {
        Kid kid = kidRepository.findById(kidId)
                .orElseThrow(() ->new ResponseStatusException(HttpStatus.NOT_FOUND, "Kid not found"));
        SchoolClass schoolClass = null;
        if (attendanceRequest.getSchoolClassId() != null) {
            schoolClass = schoolClassRepository.findById(attendanceRequest.getSchoolClassId())
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "School class not found"));
        }
        if (attendanceRequest.getDate() == null) {
            attendanceRequest.setDate(new Date(new java.util.Date().getTime()));
        }
        Attendance attendance = Attendance.builder()
                .date(attendanceRequest.getDate())
                .kid(kid)
                .aClass(schoolClass)
                .isPresent(attendanceRequest.isPresent())
                .build();
        attendanceRepository.save(attendance);
        return new ResponseEntity<>(attendance, HttpStatus.CREATED);
    }

    public List<Kid> getKidsByGroup(Integer groupId) {
        Group group = groupRepository.findById(groupId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Group not found"));
        return kidRepository.findByGroupId(groupId);
    }
}
