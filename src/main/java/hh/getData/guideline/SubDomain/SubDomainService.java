package hh.getData.guideline.SubDomain;

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
public class SubDomainService {

    private final SubDomainRepository subDomainRepository;

    public SubDomain createSubDomain(SubDomain newSubDomain) {
        log.info("Saving new SubDomain information: {}", newSubDomain.getSubDomainName());
        // You can set default values or perform any other logic here before saving
        return subDomainRepository.save(newSubDomain);
    }



    public List<SubDomain> getAllSubDomains() {
        log.info("Fetching all subDomains");
        return subDomainRepository.findAll();
    }


    public SubDomain getSubDomainById(Long id) {
        log.info("Fetching subDomain by ID: {}", id);
        Optional<SubDomain> subDomain = subDomainRepository.findById(id);
        return subDomain.orElseThrow();
    }

    public SubDomain updateSubDomain(Long id, SubDomain subDomainUpdate) {
        log.info("Updating subDomain information with ID {}: {}", id, subDomainUpdate.getSubDomainName());

        Optional<SubDomain> existingSubDomainOptional = subDomainRepository.findById(id);

        if (existingSubDomainOptional.isPresent()) {
            SubDomain existingSubDomain = existingSubDomainOptional.get();
            // Update attributes with new values
            existingSubDomain.setSubDomainName(subDomainUpdate.getSubDomainName());
            existingSubDomain.setSubDescription(subDomainUpdate.getSubDescription());
            // Save the updated entity
            return subDomainRepository.save(existingSubDomain);
        } else {
            // Handle the case where the entity with the given ID does not exist
            throw new EntityNotFoundException("subDomainUpdate with ID " + id + " not found");
        }
    }

    public boolean deleteSubDomain(Long id) {
        log.info("Deleting subDomain by ID: {}", id);
        subDomainRepository.deleteById(id);
        return true;
    }

    @Transactional
    public void softDeleteById(Long id) {
        try {
            SubDomain subDomain = subDomainRepository.findById(id)
                    .orElseThrow(() -> new EntityNotFoundException("subDomain with ID " + id + " not found"));

            if (subDomain.getStatus() != Status.INACTIVE) {
                subDomain.setStatus(Status.INACTIVE);
                subDomainRepository.save(subDomain);
            } else {
                throw new StatusAlreadyInactiveException("SubDomain with ID " + id + " is already INACTIVE");
            }
        } catch (Exception e) {
            log.error("An error occurred during soft deletion of SubDomain with ID " + id, e);
        }
    }


    public SubDomain getSubDomainByName(String office_name) {
        return subDomainRepository.findBySubDomainName(office_name);
    }

    public void deleteAllSub(){
        subDomainRepository.deleteAll();
    }
}

