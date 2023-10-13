package hh.getData.guideline.FunderSource;

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
public class FunderSourceService {
    private final FunderSourceRepository funderSourceRepository;

    public FunderSource createFunderSource(FunderSource newFunderSource) {
        log.info("Saving new FunderSource information: {}", newFunderSource.getFundTypeName());
        // You can set default values or perform any other logic here before saving
        return funderSourceRepository.save(newFunderSource);
    }

    public List<FunderSource> getAllFunderSources() {
        log.info("Fetching all FunderSources");
        return funderSourceRepository.findAll();
    }

    public FunderSource getFunderSourceById(Long id) {
        log.info("Fetching FunderSource by ID: {}", id);
        Optional<FunderSource> funderSource = funderSourceRepository.findById(id);
        return funderSource.orElseThrow(() -> new EntityNotFoundException("FunderSource with ID " + id + " not found"));
    }

    public FunderSource updateFunderSource(Long id, FunderSource funderSourceUpdate) {
        log.info("Updating FunderSource information with ID {}: {}", id, funderSourceUpdate.getFundTypeName());

        Optional<FunderSource> existingFunderSourceOptional = funderSourceRepository.findById(id);

        if (existingFunderSourceOptional.isPresent()) {
            FunderSource existingFunderSource = existingFunderSourceOptional.get();
            // Update attributes with new values
            existingFunderSource.setFundTypeName(funderSourceUpdate.getFundTypeName());
            existingFunderSource.setDescription(funderSourceUpdate.getDescription());
            // Save the updated entity
            return funderSourceRepository.save(existingFunderSource);
        } else {
            // Handle the case where the entity with the given ID does not exist
            throw new EntityNotFoundException("FunderSource with ID " + id + " not found");
        }
    }

    public boolean deleteFunderSource(Long id) {
        log.info("Deleting FunderSource by ID: {}", id);
        funderSourceRepository.deleteById(id);
        return true;
    }

    public FunderSource getFunderSourceByName(String fundTypeName) {
        return funderSourceRepository.findByFundTypeName(fundTypeName);
    }

    @Transactional
    public void changeStatusToInactive(Long id) {
        try {
            FunderSource funderSource = funderSourceRepository.findById(id)
                    .orElseThrow(() -> new EntityNotFoundException("FunderSource with ID " + id + " not found"));

            if (funderSource.getStatus() != Status.INACTIVE) {
                funderSource.setStatus(Status.INACTIVE);
                funderSourceRepository.save(funderSource);
            } else {
                throw new StatusAlreadyInactiveException("FunderSource with ID " + id + " is already INACTIVE");
            }
        } catch (Exception e) {
            log.error("An error occurred during status changing of FunderSource with ID " + id, e);
        }
    }

    @Transactional
    public void changeStatusToActive(Long id) {
        try {
            FunderSource funderSource = funderSourceRepository.findById(id)
                    .orElseThrow(() -> new EntityNotFoundException("FunderSource with ID " + id + " not found"));

            if (funderSource.getStatus() != Status.ACTIVE) {
                funderSource.setStatus(Status.ACTIVE);
                funderSourceRepository.save(funderSource);
            } else {
                throw new StatusAlreadyInactiveException("FunderSource with ID " + id + " is already ACTIVE");
            }
        } catch (Exception e) {
            log.error("An error occurred during status changing of FunderSource with ID " + id, e);
        }
    }

    public void deleteAllFunderSources() {
        funderSourceRepository.deleteAll();
    }
}