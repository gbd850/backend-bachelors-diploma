package edu.pollub.kidservice.service;

import edu.pollub.kidservice.dto.KidRequest;
import edu.pollub.kidservice.model.Kid;
import edu.pollub.kidservice.repository.KidRepository;
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
