package hh.getData.guideline.Consultant;

import hh.getData.guideline.Exception.EntityNotFoundException;
import hh.getData.guideline.Exception.StatusAlreadyInactiveException;
import hh.getData.guideline.enumeration.Status;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class ConsultantService {
    private final ConsultantRepository consultantRepository;

    public Consultant createConsultant(Consultant newConsultant) {
        log.info("Saving new Consultant information: {}", newConsultant.getConsName());
        // You can set default values or perform any other logic here before saving
        return consultantRepository.save(newConsultant);
    }

    public List<Consultant> getAllConsultants() {
        log.info("Fetching all Consultants");
        return consultantRepository.findAll();
    }

    public Consultant getConsultantById(Long id) {
        log.info("Fetching Consultant by ID: {}", id);
        Optional<Consultant> consultant = consultantRepository.findById(id);
        return consultant.orElseThrow(() -> new EntityNotFoundException("Consultant with ID " + id + " not found"));
    }

    public Consultant updateConsultant(Long id, Consultant consultantUpdate) {
        log.info("Updating Consultant information with ID {}: {}", id, consultantUpdate.getConsName());

        Optional<Consultant> existingConsultantOptional = consultantRepository.findById(id);

        if (existingConsultantOptional.isPresent()) {
            Consultant existingConsultant = existingConsultantOptional.get();
            // Update attributes with new values
            existingConsultant.setConsName(consultantUpdate.getConsName());
            existingConsultant.setDescription(consultantUpdate.getDescription());
            existingConsultant.setFacebookAccount(consultantUpdate.getFacebookAccount());
            existingConsultant.setLinkedinAccount(consultantUpdate.getLinkedinAccount());
            existingConsultant.setEmail(consultantUpdate.getEmail());
            existingConsultant.setPhoneNumber(consultantUpdate.getPhoneNumber());
            // Save the updated entity
            return consultantRepository.save(existingConsultant);
        } else {
            // Handle the case where the entity with the given ID does not exist
            throw new EntityNotFoundException("Consultant with ID " + id + " not found");
        }
    }

    public boolean deleteConsultant(Long id) {
        log.info("Deleting Consultant by ID: {}", id);
        consultantRepository.deleteById(id);
        return true;
    }

    public Consultant getConsByName(String consName) {
        return consultantRepository.findByConsName(consName);
    }

    @Transactional
    public void changeStatusToInactive(Long id) {
        try {
            Consultant consultant = consultantRepository.findById(id)
                    .orElseThrow(() -> new EntityNotFoundException("Consultant with ID " + id + " not found"));

            if (consultant.getStatus() != Status.INACTIVE) {
                consultant.setStatus(Status.INACTIVE);
                consultantRepository.save(consultant);
            } else {
                throw new StatusAlreadyInactiveException("Consultant with ID " + id + " is already INACTIVE");
            }
        } catch (Exception e) {
            log.error("An error occurred during status changing of Consultant with ID " + id, e);
        }
    }

    @Transactional
    public void changeStatusToActive(Long id) {
        try {
            Consultant consultant = consultantRepository.findById(id)
                    .orElseThrow(() -> new EntityNotFoundException("Consultant with ID " + id + " not found"));

            if (consultant.getStatus() != Status.ACTIVE) {
                consultant.setStatus(Status.ACTIVE);
                consultantRepository.save(consultant);
            } else {
                throw new StatusAlreadyInactiveException("Consultant with ID " + id + " is already ACTIVE");
            }
        } catch (Exception e) {
            log.error("An error occurred during status changing of Consultant with ID " + id, e);
        }
    }

    public void deleteAllConsultants() {
        consultantRepository.deleteAll();
    }
}

