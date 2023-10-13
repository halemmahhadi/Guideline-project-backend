package hh.getData.guideline.Donors;

import hh.getData.guideline.Exception.EntityNotFoundException;
import hh.getData.guideline.Exception.StatusAlreadyInactiveException;
import hh.getData.guideline.Image.Image;
import hh.getData.guideline.Image.ImageService;
import hh.getData.guideline.Office.Office;
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
public class DonorsServices {
    private final DonorsRepository donorsRepository;

    private final ImageService imageService;


    public Donors createDonors(Donors newDonors){
        log.info("saving new Donor information: {}", newDonors.getDonorName());
        return donorsRepository.save(newDonors);
    }

    public List<Donors> getAll() {
        log.info("Fetching all");
        return donorsRepository.findAll();
    }

    public Donors findById(Long id){
        log.info("Fetching by id: {}", id);
        Optional<Donors> donors = donorsRepository.findById(id);
        return donors.orElseThrow();
    }

    public Donors updateDonors(Long id,Donors donorsUpdate){
        log.info("updating Donor information: {}", donorsUpdate.getDonorName());
        Optional<Donors> existingDonorsOptional = donorsRepository.findById(id);

        if (existingDonorsOptional.isPresent()) {
            Donors existingDonors = existingDonorsOptional.get();
            // Update attributes with new values
            existingDonors.setDonorName(donorsUpdate.getDonorName());
            //existingDonors.setLogo(donorsUpdate.getLogo());
            existingDonors.setAbout_donor(donorsUpdate.getAbout_donor());
            existingDonors.setFacebook_account(donorsUpdate.getFacebook_account());
            existingDonors.setLinkedin_account(donorsUpdate.getLinkedin_account());
            existingDonors.setEmail(donorsUpdate.getEmail());
            existingDonors.setPhone_number(donorsUpdate.getPhone_number());

            // Save the updated entity
            return donorsRepository.save(existingDonors);
        } else {
            // Handle the case where the entity with the given ID does not exist
            throw new EntityNotFoundException("Donors with ID " + id + " not found");
        }
    }


    public boolean deleteDonors(Long id){
        log.info("deleting by id: {}", id);
        donorsRepository.deleteById(id);
        return Boolean.TRUE;
    }

    public void softDeleteById(Long id) {
        Donors donors = donorsRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Donors with ID " + id + " not found"));

        if (donors.getStatus() != Status.INACTIVE) {
            donors.setStatus(Status.INACTIVE);
        } else {
            throw new StatusAlreadyInactiveException("This Donor with ID " + id + " is already INACTIVE");
        }
        donorsRepository.save(donors);
    }

    public Donors getDonorByName(String donor_name) {
        return donorsRepository.findByDonorName(donor_name);
    }

    public void deleteAllDonors(){
        donorsRepository.deleteAll();
    }

    @Transactional
    public Donors addImage(Long donorId, Long imageId){
        Donors donors=findById(donorId);
        Image image=imageService.getById(imageId);
        donors.setImage(image);
        return donors;
    }

}
