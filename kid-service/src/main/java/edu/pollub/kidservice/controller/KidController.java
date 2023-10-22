package edu.pollub.kidservice.controller;

import edu.pollub.kidservice.dto.KidRequest;
import edu.pollub.kidservice.model.Kid;
import edu.pollub.kidservice.service.KidService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/kid")
@RequiredArgsConstructor
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
}
