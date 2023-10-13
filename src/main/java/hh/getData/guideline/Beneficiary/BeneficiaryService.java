package hh.getData.guideline.Beneficiary;

import hh.getData.guideline.Department.Department;
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
public class BeneficiaryService {

    private final BeneficiaryRepository beneficiaryRepository;

    public Beneficiary createBeneficiary(Beneficiary newBeneficiary) {
        log.info("Saving new Beneficiary information: {}", newBeneficiary.getBeneName());
        // You can set default values or perform any other logic here before saving
        return beneficiaryRepository.save(newBeneficiary);
    }

    public List<Beneficiary> getAllBeneficiaries() {
        log.info("Fetching all Beneficiaries");
        return beneficiaryRepository.findAll();
    }

    public Beneficiary getBeneficiaryById(Long id) {
        log.info("Fetching Beneficiary by ID: {}", id);
        Optional<Beneficiary> beneficiary = beneficiaryRepository.findById(id);
        return beneficiary.orElseThrow(() -> new EntityNotFoundException("Beneficiary with ID " + id + " not found"));
    }


    public Beneficiary updateBeneficiary(Long id, Beneficiary beneficiaryUpdate) {
        log.info("Updating Beneficiary information with ID {}: {}", id, beneficiaryUpdate.getBeneName());

        Optional<Beneficiary> existingBeneficiaryOptional = beneficiaryRepository.findById(id);

        if (existingBeneficiaryOptional.isPresent()) {
            Beneficiary existingBeneficiary = existingBeneficiaryOptional.get();
            // Update attributes with new values
            existingBeneficiary.setBeneName(beneficiaryUpdate.getBeneName());
            existingBeneficiary.setEmail(beneficiaryUpdate.getEmail());
            existingBeneficiary.setPhoneNumber(beneficiaryUpdate.getPhoneNumber());
            existingBeneficiary.setNameProject(beneficiaryUpdate.getNameProject());
            // Save the updated entity
            return beneficiaryRepository.save(existingBeneficiary);
        } else {
            // Handle the case where the entity with the given ID does not exist
            throw new EntityNotFoundException("Beneficiary with ID " + id + " not found");
        }
    }

    public boolean deleteBeneficiary(Long id) {
        log.info("Deleting Beneficiary by ID: {}", id);
        beneficiaryRepository.deleteById(id);
        return true;
    }
    public Beneficiary getBeneByName(String beneName) {
        return beneficiaryRepository.findByBeneName(beneName);
    }

    @Transactional
    public void changeStatusToInactive(Long id) {
        try {
            Beneficiary beneficiary = beneficiaryRepository.findById(id)
                    .orElseThrow(() -> new EntityNotFoundException("Beneficiary with ID " + id + " not found"));

            if (beneficiary.getStatus() != Status.INACTIVE) {
                beneficiary.setStatus(Status.INACTIVE);
                beneficiaryRepository.save(beneficiary);
            } else {
                throw new StatusAlreadyInactiveException("Beneficiary with ID " + id + " is already INACTIVE");
            }
        } catch (Exception e) {
            log.error("An error occurred during status changing of Beneficiary with ID " + id, e);
        }
    }

    @Transactional
    public void changeStatusToActive(Long id) {
        try {
            Beneficiary beneficiary = beneficiaryRepository.findById(id)
                    .orElseThrow(() -> new EntityNotFoundException("Beneficiary with ID " + id + " not found"));

            if (beneficiary.getStatus() != Status.ACTIVE) {
                beneficiary.setStatus(Status.ACTIVE);
                beneficiaryRepository.save(beneficiary);
            } else {
                throw new StatusAlreadyInactiveException("Beneficiary with ID " + id + " is already ACTIVE");
            }
        } catch (Exception e) {
            log.error("An error occurred during status changing of Beneficiary with ID " + id, e);
        }
    }


    public void deleteAllBeni(){
        beneficiaryRepository.deleteAll();
    }

}
