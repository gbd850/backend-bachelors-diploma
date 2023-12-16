package edu.pollub.kindergartenservice.controller;

import edu.pollub.kindergartenservice.dto.LeaveRequest;
import edu.pollub.kindergartenservice.dto.LeaveStatusRequest;
import edu.pollub.kindergartenservice.model.Leave;
import edu.pollub.kindergartenservice.service.LeaveService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/leaves")
@AllArgsConstructor
public class LeaveController {
    private LeaveService leaveService;
    @GetMapping
    public ResponseEntity<List<Leave>> getAllLeaves() {
        return new ResponseEntity<>(leaveService.getAllLeaves(), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Leave> createLeave(@RequestBody LeaveRequest leaveRequest) {
        return new ResponseEntity<>(leaveService.createLeave(leaveRequest), HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity<Leave> updateLeaveStatus(@RequestBody LeaveStatusRequest leaveStatusRequest) {
        return new ResponseEntity<>(leaveService.updateLeaveStatus(leaveStatusRequest), HttpStatus.OK);
    }
}
