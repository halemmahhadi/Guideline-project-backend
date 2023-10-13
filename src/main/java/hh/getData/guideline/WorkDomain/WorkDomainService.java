package hh.getData.guideline.WorkDomain;

import hh.getData.guideline.Beneficiary.Beneficiary;
import hh.getData.guideline.Beneficiary.BeneficiaryService;
import hh.getData.guideline.Exception.EntityNotFoundException;
import hh.getData.guideline.Exception.StatusAlreadyInactiveException;
import hh.getData.guideline.Office.Office;
import hh.getData.guideline.Office.OfficeRepository;
import hh.getData.guideline.Office.OfficeService;
import hh.getData.guideline.SubDomain.SubDomain;
import hh.getData.guideline.SubDomain.SubDomainIsAlreadyAssignmentException;
import hh.getData.guideline.SubDomain.SubDomainService;
import hh.getData.guideline.enumeration.Status;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class WorkDomainService {

    @Autowired
    private final WorkDomainRepository workDomainRepository;

    private final SubDomainService subDomainService;

    private final OfficeService officeService;

    private final BeneficiaryService beneficiaryService;


    public WorkDomain createWorkDomain(WorkDomain newWorkDomain) {
        log.info("Saving new WorkDomain information: {}", newWorkDomain.getName());
        // You can set default values or perform any other logic here before saving
        return workDomainRepository.save(newWorkDomain);
    }



    public List<WorkDomain> getAllWorkDomains() {
        log.info("Fetching all WorkDomains");
        return workDomainRepository.findAll();
    }


    public WorkDomain getWorkDomainById(Long id) {
        log.info("Fetching WorkDomain by ID: {}", id);
        Optional<WorkDomain> workDomain = workDomainRepository.findById(id);
        return workDomain.orElseThrow();
    }

    public WorkDomain updateWorkDomain(Long id, WorkDomain workDomainUpdate) {
        log.info("Updating WorkDomain information with ID {}: {}", id, workDomainUpdate.getName());

        Optional<WorkDomain> existingWorkDomainOptional = workDomainRepository.findById(id);

        if (existingWorkDomainOptional.isPresent()) {
            WorkDomain existingWorkDomain = existingWorkDomainOptional.get();
            // Update attributes with new values
            existingWorkDomain.setName(workDomainUpdate.getName());
            existingWorkDomain.setDescription(workDomainUpdate.getDescription());
            // Save the updated entity
            return workDomainRepository.save(existingWorkDomain);
        } else {
            // Handle the case where the entity with the given ID does not exist
            throw new EntityNotFoundException("workDomainUpdate with ID " + id + " not found");
        }
    }

    public boolean deleteWorkDomain(Long id) {
        log.info("Deleting WorkDomain by ID: {}", id);
        workDomainRepository.deleteById(id);
        return true;
    }

    @Transactional
    public void softDeleteById(Long id) {
        try {
            WorkDomain workDomain = workDomainRepository.findById(id)
                    .orElseThrow(() -> new EntityNotFoundException("WorkDomain with ID " + id + " not found"));

            // Log the status before the change
            log.info("Status before update: " + workDomain.getStatus());
            if (workDomain.getStatus() != Status.INACTIVE) {
                workDomain.setStatus(Status.INACTIVE);
                workDomainRepository.save(workDomain);
                // Log the status after the change
                log.info("Status after update: " + workDomain.getStatus());
            } else {
                throw new StatusAlreadyInactiveException("WorkDomain with ID " + id + " is already INACTIVE");
            }
        } catch (Exception e) {
            log.error("An error occurred during soft deletion of WorkDomain with ID " + id, e);
        }
    }


    /*@Transactional
    public WorkDomain updateWorkDomainStatus(Long id, Status newStatus) {
        log.info("Updating WorkDomain status with ID {}: {}", id, newStatus);

        Optional<WorkDomain> existingWorkDomainOptional = workDomainRepository.findById(id);

        if (existingWorkDomainOptional.isPresent()) {
            WorkDomain existingWorkDomain = existingWorkDomainOptional.get();
            // Update the status attribute with the new value
            existingWorkDomain.setStatus(newStatus);
            // Save the updated entity
            return workDomainRepository.save(existingWorkDomain);
        } else {
            // Handle the case where the entity with the given ID does not exist
            throw new EntityNotFoundException("WorkDomain with ID " + id + " not found");
        }
    }*/


    public WorkDomain getWorkDomainByName(String office_name) {
        return workDomainRepository.findByName(office_name);
    }

    @Transactional
    public WorkDomain addSubToWorkDomain(Long workDomainId, Long subDomainId) {
        WorkDomain workDomain = getWorkDomainById(workDomainId);
        SubDomain subDomain = subDomainService.getSubDomainById(subDomainId);
        if (Objects.nonNull(subDomain.getWorkDomain())) {
            throw new SubDomainIsAlreadyAssignmentException(subDomainId, (long) subDomain.getWorkDomain().getId());
        }
        workDomain.addSubToWorkDomain(subDomain);
        subDomain.setWorkDomain(workDomain);
        return workDomain;
    }

    @Transactional
    public WorkDomain removeSubDomainFromWorkDomain(Long workDomainId, Long subDomainId){
        WorkDomain workDomain = getWorkDomainById(workDomainId);
        SubDomain subDomain = subDomainService.getSubDomainById(subDomainId);
        workDomain.removeSubFromWorkDomain(subDomain);
        return workDomain;
    }

    @Transactional
    public WorkDomain editSubDomain(Long workDomainId, Long subDomainId, SubDomain subDomain){
        WorkDomain workDomain = getWorkDomainById(workDomainId);
        SubDomain getSubDomain =subDomainService.getSubDomainById(subDomainId);
        SubDomain subDomainTo= subDomainService.updateSubDomain((long) getSubDomain.getId(),subDomain);
        subDomainTo.setSubDomainName(subDomain.getSubDomainName());
        subDomainTo.setSubDescription(subDomain.getSubDescription());
        return workDomain;
    }

    public void deleteAll(){
        workDomainRepository.deleteAll();
    }

    public WorkDomain assignWorkDomainToOffice(Long workDomainId, Long officeId){
        WorkDomain workDomain= getWorkDomainById(workDomainId);
        Office office=officeService.getOfficeById(officeId);
        workDomain.assignWorkDomainToOffice(office);
        workDomainRepository.save(workDomain);
        return  workDomain;
    }


    @Transactional
    public WorkDomain removeWorkDomainFromOffice(Long workDomainId, Long officeId){
        WorkDomain workDomain= getWorkDomainById(workDomainId);
        Office office=officeService.getOfficeById(officeId);
        workDomain.removeWorkDomainFromOffice(office);
        return workDomain;
    }


    @Transactional
    public WorkDomain addBeneficiary(Long workDomainId, Long beneId) {
        WorkDomain workDomain = getWorkDomainById(workDomainId);
        Beneficiary beneficiary = beneficiaryService.getBeneficiaryById(beneId);
        if (Objects.nonNull(beneficiary.getWorkDomains())) {
            throw new SubDomainIsAlreadyAssignmentException(beneId, (long) beneficiary.getWorkDomains().getId());
        }
        workDomain.addBeneficiary(beneficiary);
        beneficiary.setWorkDomains(workDomain);
        return workDomain;
    }

    @Transactional
    public WorkDomain removeBeneficiary(Long workDomainId, Long beneId){
        WorkDomain workDomain = getWorkDomainById(workDomainId);
        Beneficiary beneficiary = beneficiaryService.getBeneficiaryById(beneId);
        workDomain.removeBeneficiary(beneficiary);
        return workDomain;
    }



}
