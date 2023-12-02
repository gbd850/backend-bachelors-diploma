package edu.pollub.kindergartenservice.controller;

import edu.pollub.kindergartenservice.dto.FacilityRequest;
import edu.pollub.kindergartenservice.dto.FacilitySummaryResponse;
import edu.pollub.kindergartenservice.dto.GroupRequest;
import edu.pollub.kindergartenservice.dto.SchoolClassRequest;
import edu.pollub.kindergartenservice.model.Facility;
import edu.pollub.kindergartenservice.model.Group;
import edu.pollub.kindergartenservice.model.SchoolClass;
import edu.pollub.kindergartenservice.service.FacilityService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
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

    @GetMapping("/{facilityId}/summary")
    public ResponseEntity<FacilitySummaryResponse> getFacilitySummary(@PathVariable Integer facilityId) {
        return new ResponseEntity<>(facilityService.getFacilitySummary(facilityId), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Facility> createFacility(@RequestBody FacilityRequest facilityRequest) {
        return facilityService.createFacility(facilityRequest);
    }

    @GetMapping("groups")
    public ResponseEntity<List<Group>> getAllGroups() {
        return facilityService.getAllGroups();
    }

    @GetMapping("groups/{facilityId}")
    public ResponseEntity<List<Group>> getAllGroupsInFacility(@PathVariable Integer facilityId) {
        return new ResponseEntity<>(facilityService.getAllGroupsInFacility(facilityId), HttpStatus.OK);
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
