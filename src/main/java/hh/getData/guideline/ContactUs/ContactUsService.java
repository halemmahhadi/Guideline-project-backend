package hh.getData.guideline.ContactUs;

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
public class ContactUsService {
    private final ContactUsRepository contactUsRepository;

    public ContactUs createContactUs(ContactUs newContactUs) {
        log.info("Saving new ContactUs information: {}", newContactUs.getName());
        // You can set default values or perform any other logic here before saving
        return contactUsRepository.save(newContactUs);
    }

    public List<ContactUs> getAllContactUs() {
        log.info("Fetching all ContactUs entries");
        return contactUsRepository.findAll();
    }

    public ContactUs getContactUsById(Long id) {
        log.info("Fetching ContactUs by ID: {}", id);
        Optional<ContactUs> contactUs = contactUsRepository.findById(id);
        return contactUs.orElseThrow(() -> new EntityNotFoundException("ContactUs with ID " + id + " not found"));
    }

    public ContactUs updateContactUs(Long id, ContactUs contactUsUpdate) {
        log.info("Updating ContactUs information with ID {}: {}", id, contactUsUpdate.getName());

        Optional<ContactUs> existingContactUsOptional = contactUsRepository.findById(id);

        if (existingContactUsOptional.isPresent()) {
            ContactUs existingContactUs = existingContactUsOptional.get();
            // Update attributes with new values
            existingContactUs.setName(contactUsUpdate.getName());
            existingContactUs.setEmail(contactUsUpdate.getEmail());
            existingContactUs.setPhoneNumber(contactUsUpdate.getPhoneNumber());
            existingContactUs.setMessage(contactUsUpdate.getMessage());
            // Save the updated entity
            return contactUsRepository.save(existingContactUs);
        } else {
            // Handle the case where the entity with the given ID does not exist
            throw new EntityNotFoundException("ContactUs with ID " + id + " not found");
        }
    }

    public boolean deleteContactUs(Long id) {
        log.info("Deleting ContactUs by ID: {}", id);
        contactUsRepository.deleteById(id);
        return true;
    }

    public ContactUs getContactUsByName(String name) {
        return contactUsRepository.findByName(name);
    }

    @Transactional
    public void changeStatusToInactive(Long id) {
        try {
            ContactUs contactUs = contactUsRepository.findById(id)
                    .orElseThrow(() -> new EntityNotFoundException("ContactUs with ID " + id + " not found"));

            if (contactUs.getStatus() != Status.INACTIVE) {
                contactUs.setStatus(Status.INACTIVE);
                contactUsRepository.save(contactUs);
            } else {
                throw new StatusAlreadyInactiveException("ContactUs with ID " + id + " is already INACTIVE");
            }
        } catch (Exception e) {
            log.error("An error occurred during status changing of ContactUs with ID " + id, e);
        }
    }

    @Transactional
    public void changeStatusToActive(Long id) {
        try {
            ContactUs contactUs = contactUsRepository.findById(id)
                    .orElseThrow(() -> new EntityNotFoundException("ContactUs with ID " + id + " not found"));

            if (contactUs.getStatus() != Status.ACTIVE) {
                contactUs.setStatus(Status.ACTIVE);
                contactUsRepository.save(contactUs);
            } else {
                throw new StatusAlreadyInactiveException("ContactUs with ID " + id + " is already ACTIVE");
            }
        } catch (Exception e) {
            log.error("An error occurred during status changing of ContactUs with ID " + id, e);
        }
    }

    public void deleteAllContactUsEntries() {
        contactUsRepository.deleteAll();
    }
}
