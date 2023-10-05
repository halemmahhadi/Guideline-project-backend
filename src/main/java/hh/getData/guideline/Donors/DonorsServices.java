package hh.getData.guideline.Donors;

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
public class DonorsServices {
    private final DonorsRepository donorsRepository;


    public Donors createDonors(Donors newDonors){
        log.info("saving new Donor information: {}", newDonors.getDonor_name());
        newDonors.setStatus(Status.ACTIVE);
        return donorsRepository.save(newDonors);
    }

    public List<Donors> getAll() {
        log.info("Fetching all");
        return donorsRepository.findAll();
    }

    public Donors getById(Long id){
        log.info("Fetching by id: {}", id);
        return donorsRepository.getById(id);
    }

    public Donors updateDonors(Long id,Donors donorsUpdate){
        log.info("updating Donor information: {}", donorsUpdate.getDonor_name());

        Optional<Donors> existingDonorsOptional = donorsRepository.findById(id);

        if (existingDonorsOptional.isPresent()) {
            Donors existingDonors = existingDonorsOptional.get();

            // Update attributes with new values
            existingDonors.setDonor_name(donorsUpdate.getDonor_name());
            existingDonors.setLogo(donorsUpdate.getLogo());
            existingDonors.setAbout_donor(donorsUpdate.getAbout_donor());
            // Update other attributes as needed

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

        donors.setStatus(Status.INACTIVE);
        donorsRepository.save(donors);
    }



}
