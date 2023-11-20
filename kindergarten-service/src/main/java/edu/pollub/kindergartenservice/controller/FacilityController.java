package edu.pollub.kindergartenservice.controller;

import edu.pollub.kindergartenservice.dto.FacilityRequest;
import edu.pollub.kindergartenservice.dto.GroupRequest;
import edu.pollub.kindergartenservice.dto.SchoolClassRequest;
import edu.pollub.kindergartenservice.model.Facility;
import edu.pollub.kindergartenservice.model.Group;
import edu.pollub.kindergartenservice.model.SchoolClass;
import edu.pollub.kindergartenservice.service.FacilityService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/facility")
@RequiredArgsConstructor
public class FacilityController {

    private FacilityService facilityService;

    @GetMapping
    public ResponseEntity<List<Facility>> getAllFacilities() {
        return facilityService.getAllFacilities();
    }

    @PostMapping
    public ResponseEntity<Facility> createFacility(@RequestBody FacilityRequest facilityRequest) {
        return facilityService.createFacility(facilityRequest);
    }

    @GetMapping("groups")
    public ResponseEntity<List<Group>> getAllGroups() {
        return facilityService.getAllGroups();
    }

    @PostMapping("groups")
    public ResponseEntity<Group> createGroup(@RequestBody GroupRequest groupRequest) {
        return facilityService.createGroup(groupRequest);
    }

    @GetMapping("groups/schedule/{groupId}")
    public ResponseEntity<List<SchoolClass>> getGroupSchedule(@PathVariable Integer groupId) {
        return facilityService.getGroupSchedule(groupId);
    }

    @PostMapping("groups/schedule/{groupId}")
    public ResponseEntity<SchoolClass> createSchoolClass(@PathVariable Integer groupId, @RequestBody SchoolClassRequest schoolClassRequest) {
        return facilityService.createSchoolClass(groupId, schoolClassRequest);
    }
}
