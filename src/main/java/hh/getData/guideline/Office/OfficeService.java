package hh.getData.guideline.Office;

import hh.getData.guideline.Donors.Donors;
import hh.getData.guideline.Exception.EntityNotFoundException;
import hh.getData.guideline.Exception.StatusAlreadyInactiveException;
import hh.getData.guideline.Licence.Licence;
import hh.getData.guideline.Licence.LicenceIsAlreadyAssignmentException;
import hh.getData.guideline.Licence.LicenceService;
import hh.getData.guideline.WorkDomain.WorkDomain;
import hh.getData.guideline.WorkDomain.WorkDomainRepository;
import hh.getData.guideline.WorkDomain.WorkDomainService;
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
public class OfficeService {
    private final OfficeRepository officeRepository;

    private final LicenceService licenceService;


    public Office createOffice(Office newOffice) {
        log.info("Saving new office information: {}", newOffice.getName());
        // You can set default values or perform any other logic here before saving
        return officeRepository.save(newOffice);
    }

    public List<Office> getAllOffices() {
        log.info("Fetching all offices");
        return officeRepository.findAll();
    }


    public Office getOfficeById(Long id) {
        log.info("Fetching office by ID: {}", id);
        Optional<Office> office = officeRepository.findById(id);
        return office.orElseThrow();
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
                .orElseThrow(() -> new EntityNotFoundException("Office with ID " + id + " not found"));
        if (office.getStatus() != Status.INACTIVE) {
            office.setStatus(Status.INACTIVE);
        } else {
            throw new StatusAlreadyInactiveException("This Office with ID " + id + " is already INACTIVE");
        }

        officeRepository.save(office);
    }



    public Office getOfficeByName(String office_name) {
        return officeRepository.findByName(office_name);
    }


    @Transactional
    public Office addLicenceToOffice(Long officeId, Long licenceId) {
        Office office = getOfficeById(officeId);
        Licence licence = licenceService.getLicenceById(licenceId);
        if (Objects.nonNull(licence.getOffice())) {
            throw new LicenceIsAlreadyAssignmentException(licenceId, (long) licence.getOffice().getId());
        }
        office.addLicenceToOffice(licence);
        licence.setOffice(office);
        return office;
    }

    @Transactional
    public Office removeLicenceFromOffice(Long officeId, Long licenceId){
        Office office = getOfficeById(officeId);
        Licence licence = licenceService.getLicenceById(licenceId);
        office.removeLicenceFromOffice(licence);
        return office;
    }


    @Transactional
    public Office editLicence(Long officeId, Long licenceId, Licence licence){
        Office office = getOfficeById(officeId);
        Licence licenceID =licenceService.getLicenceById(licenceId);
        Licence licenceTo= licenceService.updateLicence((long) licenceID.getId(),licence);
        licenceTo.setName(licence.getName());
        licenceTo.setDescription(licence.getDescription());
        licenceTo.setRequiredDocument(licence.getRequiredDocument());
        licenceTo.setProcedures(licence.getProcedures());
        licenceTo.setIssuingAuthority(licence.getIssuingAuthority());
        licenceTo.setFees(licence.getFees());
        licenceTo.setPenalties(licence.getPenalties());
        licenceTo.setNotes(licence.getNotes());
        return office;
    }

    public void deleteAllOffices(){
        officeRepository.deleteAll();
    }


    /*public Office assignOfficeToWorkDomain(Long officeId, Long workDomainId){
        Office office=getOfficeById(officeId);
        WorkDomain workDomain=workDomainService.getWorkDomainById(workDomainId);
        office.assignOfficeToWorkDomain(workDomain);
        workDomainService.assignWorkDomainToOffice(workDomainId,officeId);
        return  office;
    }

    public Office removeOfficeFromWorkDomain(Long officeId, Long workDomainId){
        Office office=getOfficeById(officeId);
        WorkDomain workDomain=workDomainService.getWorkDomainById(workDomainId);
        office.removeOfficeFromWorkDomain(workDomain);
        workDomainService.removeWorkDomainFromOffice(workDomainId,officeId);
        return office;
    }*/

    /*public List<Office> findOfficesByWorkDomainId(Long workDomainId) {
        if (!workDomainRepository.existsById(workDomainId)) {
            throw new EntityNotFoundException("Not found WorkDomain with id = " + workDomainId);
        }
        return officeRepository.findOfficesByWorkDomains(workDomainId);
    }*/





}