package edu.pollub.kindergartenservice.service;

import edu.pollub.kindergartenservice.dto.KidRequest;
import edu.pollub.kindergartenservice.model.Group;
import edu.pollub.kindergartenservice.model.Kid;
import edu.pollub.kindergartenservice.repository.GroupRepository;
import edu.pollub.kindergartenservice.repository.KidRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class KidService {
    private KidRepository kidRepository;
    private GroupRepository groupRepository;

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
}
