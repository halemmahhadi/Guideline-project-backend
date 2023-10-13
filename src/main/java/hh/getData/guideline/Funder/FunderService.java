package hh.getData.guideline.Funder;

import hh.getData.guideline.Donors.Donors;
import hh.getData.guideline.Exception.EntityNotFoundException;
import hh.getData.guideline.Exception.StatusAlreadyInactiveException;
import hh.getData.guideline.FunderSource.FunderSource;
import hh.getData.guideline.FunderSource.FunderSourceService;
import hh.getData.guideline.Image.Image;
import hh.getData.guideline.Image.ImageService;
import hh.getData.guideline.SubDomain.SubDomainIsAlreadyAssignmentException;
import hh.getData.guideline.WorkDomain.WorkDomain;
import hh.getData.guideline.enumeration.Status;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class FunderService {
    private final FunderRepository funderRepository;

    private final FunderSourceService funderSourceService;

    private final ImageService imageService;

    public Funder createFunder(Funder newFunder) {
        log.info("Saving new Funder information: {}", newFunder.getFundName());
        // You can set default values or perform any other logic here before saving
        return funderRepository.save(newFunder);
    }

    public List<Funder> getAllFunders() {
        log.info("Fetching all Funders");
        return funderRepository.findAll();
    }

    public Funder getFunderById(Long id) {
        log.info("Fetching Funder by ID: {}", id);
        Optional<Funder> funder = funderRepository.findById(id);
        return funder.orElseThrow(() -> new EntityNotFoundException("Funder with ID " + id + " not found"));
    }

    public Funder updateFunder(Long id, Funder funderUpdate) {
        log.info("Updating Funder information with ID {}: {}", id, funderUpdate.getFundName());

        Optional<Funder> existingFunderOptional = funderRepository.findById(id);

        if (existingFunderOptional.isPresent()) {
            Funder existingFunder = existingFunderOptional.get();
            // Update attributes with new values
            existingFunder.setFundName(funderUpdate.getFundName());
            existingFunder.setDescription(funderUpdate.getDescription());
            existingFunder.setImage(funderUpdate.getImage());
            existingFunder.setEmail(funderUpdate.getEmail());
            existingFunder.setPhoneNumber(funderUpdate.getPhoneNumber());
            existingFunder.setRequiredDocuments(funderUpdate.getRequiredDocuments());
            existingFunder.setAdvantages(funderUpdate.getAdvantages());
            existingFunder.setConditions(funderUpdate.getConditions());
            existingFunder.setProducts(funderUpdate.getProducts());
            existingFunder.setFundingLimit(funderUpdate.getFundingLimit());
            existingFunder.setGuarantees(funderUpdate.getGuarantees());
            // Save the updated entity
            return funderRepository.save(existingFunder);
        } else {
            // Handle the case where the entity with the given ID does not exist
            throw new EntityNotFoundException("Funder with ID " + id + " not found");
        }
    }

    public boolean deleteFunder(Long id) {
        log.info("Deleting Funder by ID: {}", id);
        funderRepository.deleteById(id);
        return true;
    }

    public Funder getFunderByName(String fundName) {
        return funderRepository.findByFundName(fundName);
    }

    @Transactional
    public void changeStatusToInactive(Long id) {
        try {
            Funder funder = funderRepository.findById(id)
                    .orElseThrow(() -> new EntityNotFoundException("Funder with ID " + id + " not found"));

            if (funder.getStatus() != Status.INACTIVE) {
                funder.setStatus(Status.INACTIVE);
                funderRepository.save(funder);
            } else {
                throw new StatusAlreadyInactiveException("Funder with ID " + id + " is already INACTIVE");
            }
        } catch (Exception e) {
            log.error("An error occurred during status changing of Funder with ID " + id, e);
        }
    }

    @Transactional
    public void changeStatusToActive(Long id) {
        try {
            Funder funder = funderRepository.findById(id)
                    .orElseThrow(() -> new EntityNotFoundException("Funder with ID " + id + " not found"));

            if (funder.getStatus() != Status.ACTIVE) {
                funder.setStatus(Status.ACTIVE);
                funderRepository.save(funder);
            } else {
                throw new StatusAlreadyInactiveException("Funder with ID " + id + " is already ACTIVE");
            }
        } catch (Exception e) {
            log.error("An error occurred during status changing of Funder with ID " + id, e);
        }
    }

    public void deleteAllFunders() {
        funderRepository.deleteAll();
    }

    @Transactional
    public Funder addFunderSource(Long funderId, Long funderSourceId) {
        Funder funder = getFunderById(funderId);
        FunderSource funderSource = funderSourceService.getFunderSourceById(funderSourceId);
        if (Objects.nonNull(funderSource.getFunder())) {
            throw new SubDomainIsAlreadyAssignmentException(funderSourceId, (long) funderSource.getFunder().getId());
        }
        funder.addFunderSource(funderSource);
        funderSource.setFunder(funder);
        return funder;
    }

    @Transactional
    public Funder removeFunderSource(Long funderId, Long funderSourceId){
        Funder funder = getFunderById(funderId);
        FunderSource funderSource = funderSourceService.getFunderSourceById(funderSourceId);
        funder.removeFunderSource(funderSource);
        return funder;
    }

    @Transactional
    public Funder addImage(Long funderId, Long imageId){
        Funder funder=getFunderById(funderId);
        Image image=imageService.getById(imageId);
        funder.setImage(image);
        return funder;
    }

}