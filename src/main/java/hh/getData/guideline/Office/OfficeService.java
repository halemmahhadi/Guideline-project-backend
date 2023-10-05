package hh.getData.guideline.Office;

import hh.getData.guideline.Donors.Donors;
import hh.getData.guideline.Exception.EntityNotFoundException;
import hh.getData.guideline.enumeration.Status;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class OfficeService {
    private final OfficeRepository officeRepository;

    public Office createOffice(Office newOffice) {
        log.info("Saving new office information: {}", newOffice.getName());
        // You can set default values or perform any other logic here before saving
        return officeRepository.save(newOffice);
    }

    public List<Office> getAllOffices() {
        log.info("Fetching all offices");
        return officeRepository.findAll();
    }

    public Optional<Office> getOfficeById(Long id) {
        log.info("Fetching office by ID: {}", id);
        return officeRepository.findById(id);
    }

    public Office updateOffice(Long id, Office officeUpdate) {
        log.info("Updating office information with ID {}: {}", id, officeUpdate.getName());

        Optional<Office> existingOfficeOptional = officeRepository.findById(id);

        if (existingOfficeOptional.isPresent()) {
            Office existingOffice = existingOfficeOptional.get();

            // Update attributes with new values
            existingOffice.setName(officeUpdate.getName());
            existingOffice.setDescription(officeUpdate.getDescription());
            existingOffice.setAddress(officeUpdate.getAddress());
            existingOffice.setRequired_actions(officeUpdate.getRequired_actions());
            existingOffice.setNotes(officeUpdate.getNotes());
            existingOffice.setLogo(officeUpdate.getLogo());
            existingOffice.setWebsite_url(officeUpdate.getWebsite_url());
            existingOffice.setFacebook_account(officeUpdate.getFacebook_account());
            existingOffice.setLinkedin_account(officeUpdate.getLinkedin_account());
            existingOffice.setEmail(officeUpdate.getEmail());
            existingOffice.setPhone_number(officeUpdate.getPhone_number());


            // Save the updated entity
            return officeRepository.save(existingOffice);
        } else {
            // Handle the case where the entity with the given ID does not exist
            throw new EntityNotFoundException("Office with ID " + id + " not found");
        }
    }

    public boolean deleteOffice(Long id) {
        log.info("Deleting office by ID: {}", id);
        officeRepository.deleteById(id);
        return true;
    }

    public void softDeleteById(Long id) {
        Office office = officeRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Donors with ID " + id + " not found"));

        office.setStatus(Status.INACTIVE);
        officeRepository.save(office);
    }


    public Office getOfficeByName(String office_name) {
        return officeRepository.findByName(office_name);
    }
}