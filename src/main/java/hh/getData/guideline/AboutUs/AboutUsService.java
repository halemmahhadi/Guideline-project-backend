package hh.getData.guideline.AboutUs;

import hh.getData.guideline.Exception.EntityNotFoundException;
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


     public AboutUs createAboutUS(AboutUs newAboutUs){
         log.info("saving new About_us information: {}", newAboutUs.getAbout_app());
         return aboutUsRepository.save(newAboutUs);
     }

    public List<AboutUs> getAll() {
        log.info("Fetching all");
        return aboutUsRepository.findAll();
    }

    public AboutUs getById(Long id){
        log.info("Fetching by id: {}", id);
         return aboutUsRepository.getById(id);
    }

    public AboutUs updateAboutUs(Long id,AboutUs aboutUsUpdate){
        log.info("updating About_us information: {}", aboutUsUpdate.getAbout_app());

        Optional<AboutUs> existingAboutUsOptional = aboutUsRepository.findById(id);

        if (existingAboutUsOptional.isPresent()) {
            AboutUs existingAboutUs = existingAboutUsOptional.get();

            // Update attributes with new values
            existingAboutUs.setAbout_app(aboutUsUpdate.getAbout_app());
            existingAboutUs.setLogo(aboutUsUpdate.getLogo());
            existingAboutUs.setOur_services(aboutUsUpdate.getOur_services());
            existingAboutUs.setEmail(aboutUsUpdate.getEmail());
            existingAboutUs.setPhone_number(aboutUsUpdate.getPhone_number());
            existingAboutUs.setOur_services(aboutUsUpdate.getOur_services());
            // Update other attributes as needed

            // Save the updated entity
            return aboutUsRepository.save(existingAboutUs);
        } else {
            // Handle the case where the entity with the given ID does not exist
            throw new EntityNotFoundException("AboutUs with ID " + id + " not found");
        }
    }


    public boolean deleteAboutUs(Long id){
        log.info("deleting by id: {}", id);
        aboutUsRepository.deleteById(id);
        return Boolean.TRUE;
    }


}
