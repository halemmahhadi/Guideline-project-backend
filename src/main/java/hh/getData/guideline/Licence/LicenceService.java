package hh.getData.guideline.Licence;

import hh.getData.guideline.Exception.EntityNotFoundException;
import hh.getData.guideline.Exception.StatusAlreadyInactiveException;
import hh.getData.guideline.enumeration.Status;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class LicenceService {
    private final LicenceRepository licenceRepository;

    public Licence createLicence(Licence newLicence) {
        log.info("Saving new licence information: {}", newLicence.getName());
        // You can set default values or perform any other logic here before saving
        return licenceRepository.save(newLicence);
    }

    public List<Licence> getAllLicences() {
        log.info("Fetching all licences");
        return licenceRepository.findAll();
    }

    public Licence getLicenceById(Long id) {
        return licenceRepository.findById(id)
                .orElseThrow(() ->
                        new EntityNotFoundException("Licence with ID " + id + " not found"));
    }

    public Licence updateLicence(Long id, Licence licenceUpdate) {
        log.info("Updating licence information with ID {}: {}", id, licenceUpdate.getName());

        Optional<Licence> existingLicenceOptional = licenceRepository.findById(id);

        if (existingLicenceOptional.isPresent()) {
            Licence existingLicence = existingLicenceOptional.get();

            // Update attributes with new values
            existingLicence.setName(licenceUpdate.getName());
            existingLicence.setDescription(licenceUpdate.getDescription());
            existingLicence.setRequiredDocument(licenceUpdate.getRequiredDocument());
            existingLicence.setProcedures(licenceUpdate.getProcedures());
            existingLicence.setIssuingAuthority(licenceUpdate.getIssuingAuthority());
            existingLicence.setFees(licenceUpdate.getFees());
            existingLicence.setPenalties(licenceUpdate.getPenalties());
            existingLicence.setNotes(licenceUpdate.getNotes());

            // Save the updated entity
            return licenceRepository.save(existingLicence);
        } else {
            // Handle the case where the entity with the given ID does not exist
            throw new EntityNotFoundException("Licence with ID " + id + " not found");
        }
    }

    public boolean deleteLicence(Long id) {
        log.info("Deleting licence by ID: {}", id);
        licenceRepository.deleteById(id);
        return true;
    }

    public void softDeleteById(Long id) {
        Licence licence = licenceRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Licence with ID " + id + " not found"));

        if (licence.getStatus() != Status.INACTIVE) {
            licence.setStatus(Status.INACTIVE);
            // Log the status after the change
        } else {
            throw new StatusAlreadyInactiveException("SubDomain with ID " + id + " is already INACTIVE");
        }
        licenceRepository.save(licence);
    }

    public Licence getLicenceByName(String licenceName) {
        return licenceRepository.findByName(licenceName);
    }


    public void deleteAllLicences(){
        licenceRepository.deleteAll();
    }
}