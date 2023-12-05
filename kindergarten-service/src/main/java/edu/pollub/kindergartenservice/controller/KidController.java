package edu.pollub.kindergartenservice.controller;

import edu.pollub.kindergartenservice.dto.AttendanceRequest;
import edu.pollub.kindergartenservice.dto.KidRequest;
import edu.pollub.kindergartenservice.model.Attendance;
import edu.pollub.kindergartenservice.model.Kid;
import edu.pollub.kindergartenservice.service.KidService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/kid")
@AllArgsConstructor
public class KidController {
    private KidService kidService;
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<Kid> getAllKids() {
        return kidService.getAlKids();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Kid saveKid(@RequestBody KidRequest kid) {
        return kidService.saveKid(kid);
    }

    @GetMapping("/{kidId}/attendance")
    public ResponseEntity<List<Attendance>> getKidAttendance(@PathVariable Integer kidId) {
        return kidService.getKidAttendance(kidId);
    }

    @PostMapping("/{kidId}/attendance")
    public ResponseEntity<Attendance> markKidAttendance(@PathVariable Integer kidId, @RequestBody AttendanceRequest attendanceRequest) {
        return kidService.markKidAttendance(kidId, attendanceRequest);
    }

    @GetMapping("/group/{groupId}")
    public ResponseEntity<List<Kid>> getKidsByGroup(@PathVariable Integer groupId) {
        return new ResponseEntity<>(kidService.getKidsByGroup(groupId), HttpStatus.OK);
    }


}
