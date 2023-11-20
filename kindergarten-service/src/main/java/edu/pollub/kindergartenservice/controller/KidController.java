package edu.pollub.kindergartenservice.controller;

import edu.pollub.kindergartenservice.dto.KidRequest;
import edu.pollub.kindergartenservice.model.Kid;
import edu.pollub.kindergartenservice.service.KidService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/kid")
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
