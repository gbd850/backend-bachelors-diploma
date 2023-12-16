package edu.pollub.kindergartenservice.service;

import edu.pollub.kindergartenservice.dto.AccountResponse;
import edu.pollub.kindergartenservice.dto.LeaveRequest;
import edu.pollub.kindergartenservice.dto.LeaveStatusRequest;
import edu.pollub.kindergartenservice.model.Facility;
import edu.pollub.kindergartenservice.model.Leave;
import edu.pollub.kindergartenservice.model.LeaveStatus;
import edu.pollub.kindergartenservice.repository.FacilityRepository;
import edu.pollub.kindergartenservice.repository.LeaveRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Mono;

import java.util.List;

@Service
@AllArgsConstructor
public class LeaveService {
    private LeaveRepository leaveRepository;
    private FacilityRepository facilityRepository;
    private WebClient.Builder webClientBuilder;


    public List<Leave> getAllLeaves() {
        return leaveRepository.findAll();
    }

    public Leave createLeave(LeaveRequest leaveRequest) {
        AccountResponse employee = webClientBuilder.build().get()
                .uri("http://account-service/api/users/"+leaveRequest.getEmployeeId())
                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError, response ->
                        Mono.error(new WebClientResponseException(HttpStatus.NOT_FOUND, "Employee not found", null, null, null, null))
                )
                .bodyToMono(AccountResponse.class)
                .block();
        if (employee == null || employee.getRole().equalsIgnoreCase("PARENT")) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Employee not found");
        }
        Facility facility = facilityRepository.findById(leaveRequest.getFacilityId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Facility not found"));
        Leave leave = Leave.builder()
                .employeeId(leaveRequest.getEmployeeId())
                .facility(facility)
                .startDate(leaveRequest.getStartDate())
                .endDate(leaveRequest.getEndDate())
                .status(LeaveStatus.PENDING)
                .build();
        leaveRepository.save(leave);
        return leave;
    }

    public Leave updateLeaveStatus(LeaveStatusRequest leaveStatusRequest) {
        Leave leave = leaveRepository.findById(leaveStatusRequest.getId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Leave not found"));
        try {
            LeaveStatus leaveStatus = LeaveStatus.valueOf(leaveStatusRequest.getStatus().toUpperCase());
            leave.setStatus(leaveStatus);
            leaveRepository.save(leave);
            return leave;
        }
        catch (IllegalArgumentException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Invalid status");
        }
    }
}
