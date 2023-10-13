package hh.getData.guideline.AboutUs;


import hh.getData.guideline.Exception.EntityNotFoundException;
import hh.getData.guideline.Exception.StatusAlreadyInactiveException;
import hh.getData.guideline.Image.Image;
import hh.getData.guideline.Image.ImageService;
import hh.getData.guideline.enumeration.Status;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class AboutUsService {
    private final AboutUsRepository aboutUsRepository;
private final ImageService imageService;
    public AboutUs createAboutUs(AboutUs newAboutUs) {
        log.info("Saving new AboutUs information");
        // Check if an entry already exists
        if (doesAboutUsExist()) {
            // Handle the case where an entry already exists (You can throw an exception or return null, depending on your requirements)
            throw new AboutUsAlreadyExistsException("An AboutUs entry already exists.");
        }
        return aboutUsRepository.save(newAboutUs);
    }
    public boolean doesAboutUsExist() {
        return aboutUsRepository.count() > 0;
    }


    public List<AboutUs> getAllAboutUsEntries() {
        log.info("Fetching all AboutUs entries");
        return aboutUsRepository.findAll();
    }

    public AboutUs getAboutUsById(Long id) {
        log.info("Fetching AboutUs entry by ID: {}", id);
        Optional<AboutUs> aboutUs = aboutUsRepository.findById(id);
        return aboutUs.orElseThrow(() -> new EntityNotFoundException("AboutUs entry with ID " + id + " not found"));
    }

    public AboutUs updateAboutUs(Long id, AboutUs aboutUsUpdate) {
        log.info("Updating AboutUs information with ID: {}", id);

        Optional<AboutUs> existingAboutUsOptional = aboutUsRepository.findById(id);

        if (existingAboutUsOptional.isPresent()) {
            AboutUs existingAboutUs = existingAboutUsOptional.get();
            // Update attributes with new values
            existingAboutUs.setImage(aboutUsUpdate.getImage());
            existingAboutUs.setAbout_app(aboutUsUpdate.getAbout_app());
            existingAboutUs.setOur_services(aboutUsUpdate.getOur_services());
            existingAboutUs.setEmail(aboutUsUpdate.getEmail());
            existingAboutUs.setPhone_number(aboutUsUpdate.getPhone_number());
            // Save the updated entity
            return aboutUsRepository.save(existingAboutUs);
        } else {
            // Handle the case where the entity with the given ID does not exist
            throw new EntityNotFoundException("AboutUs entry with ID " + id + " not found");
        }
    }

    public boolean deleteAboutUs(Long id) {
        log.info("Deleting AboutUs entry by ID: {}", id);
        aboutUsRepository.deleteById(id);
        return true;
    }



    @Transactional
    public void changeStatusToInactive(Long id) {
        try {
            AboutUs aboutUs = aboutUsRepository.findById(id)
                    .orElseThrow(() -> new EntityNotFoundException("AboutUs entry with ID " + id + " not found"));

            if (aboutUs.getStatus() != Status.INACTIVE) {
                aboutUs.setStatus(Status.INACTIVE);
                aboutUsRepository.save(aboutUs);
            } else {
                throw new StatusAlreadyInactiveException("AboutUs entry with ID " + id + " is already INACTIVE");
            }
        } catch (Exception e) {
            log.error("An error occurred during status changing of AboutUs entry with ID " + id, e);
        }
    }

    @Transactional
    public void changeStatusToActive(Long id) {
        try {
            AboutUs aboutUs = aboutUsRepository.findById(id)
                    .orElseThrow(() -> new EntityNotFoundException("AboutUs entry with ID " + id + " not found"));

            if (aboutUs.getStatus() != Status.ACTIVE) {
                aboutUs.setStatus(Status.ACTIVE);
                aboutUsRepository.save(aboutUs);
            } else {
                throw new StatusAlreadyInactiveException("AboutUs entry with ID " + id + " is already ACTIVE");
            }
        } catch (Exception e) {
            log.error("An error occurred during status changing of AboutUs entry with ID " + id, e);
        }
    }

    public void deleteAllAboutUsEntries() {
        aboutUsRepository.deleteAll();
    }

    @Transactional
    public AboutUs addImage(Long aboutUsId, Long imageId){
        AboutUs aboutUs=getAboutUsById(aboutUsId);
        Image image=imageService.getById(imageId);
        aboutUs.setImage(image);
        return aboutUs;
    }
}
