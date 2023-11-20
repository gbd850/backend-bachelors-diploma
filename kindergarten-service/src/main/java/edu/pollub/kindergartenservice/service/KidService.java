package edu.pollub.kindergartenservice.service;

import edu.pollub.kindergartenservice.dto.KidRequest;
import edu.pollub.kindergartenservice.model.Kid;
import edu.pollub.kindergartenservice.repository.KidRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class KidService {
    private KidRepository kidRepository;

    public List<Kid> getAlKids() {
        return kidRepository.findAll();
    }

    public Kid saveKid(KidRequest kidRequest) {
        Kid kid = Kid.builder()
                .firstName(kidRequest.getFirstName())
                .middleName(kidRequest.getMiddleName())
                .lastName(kidRequest.getLastName())
                .pesel(kidRequest.getPesel())
                .birthDate(kidRequest.getBirthDate())
                .build();
        return kidRepository.save(kid);
    }
}
