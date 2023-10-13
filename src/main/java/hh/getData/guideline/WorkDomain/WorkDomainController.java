package hh.getData.guideline.WorkDomain;

import hh.getData.guideline.SubDomain.SubDomain;
import hh.getData.guideline.SubDomain.SubDomainDto;
import hh.getData.guideline.SubDomain.SubDomainService;
import hh.getData.guideline.enumeration.Status;
import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@Data
@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/workDomains/")
public class WorkDomainController {

    private final WorkDomainService workDomainService;
    private final SubDomainService subDomainService;

    @GetMapping("getAll/")
    public ResponseEntity<List<WorkDomainDto>> getAll() {
        try {
            List<WorkDomain> workDomain = workDomainService.getAllWorkDomains();
            List<WorkDomainDto> workDomainDto = workDomain.stream().map(WorkDomainDto::from).collect(Collectors.toList());
            return new ResponseEntity<>(workDomainDto, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    //getById

    @GetMapping("getBYId/id/{id}")
    public ResponseEntity<WorkDomainDto> getWorkDomainById(@PathVariable Long id) {

        WorkDomain workDomain = workDomainService.getWorkDomainById(id);

        if (workDomain != null) {
            return new ResponseEntity<>(WorkDomainDto.from(workDomain), HttpStatus.OK);
        } else
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);

    }


    // Post
    @PostMapping(value = "create/")
    public ResponseEntity<WorkDomainDto> addWorkDomain(@RequestBody WorkDomainDto workDomainDto) {
        try {
            if (workDomainDto.getStatus() == null) {
                workDomainDto.setStatus(Status.ACTIVE);
            }
            WorkDomain workDomain = workDomainService.createWorkDomain(WorkDomain.from(workDomainDto));
            return new ResponseEntity<>(WorkDomainDto.from(workDomain), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }



    // Delete
    @DeleteMapping(value = "delete_domain/id/{id}") // Updated the endpoint to "/offices/delete_office/id/{id}"
    public ResponseEntity<Boolean> deleteWorkDomain(@PathVariable final Long id) {
        try {
            return new ResponseEntity<>(workDomainService.deleteWorkDomain(id), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.OK);
        }
    }


    // Updating
    @PutMapping(value = "update_domain/id/{id}") // Updated the endpoint to "/offices/update_office/id/{id}"
    public ResponseEntity<WorkDomainDto> editWorkDomain(@PathVariable final Long id, @RequestBody final WorkDomainDto workDomainDto) {
        try {
            WorkDomain editWorkDomain = workDomainService.updateWorkDomain(id, WorkDomain.from(workDomainDto));
            return new ResponseEntity<>(WorkDomainDto.from(editWorkDomain), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }



    @PutMapping("soft-delete/id/{id}")
    public ResponseEntity<String> softDeleteById(@PathVariable Long id) {
        try {
            workDomainService.softDeleteById(id);
            return new ResponseEntity<>(null, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Failed to soft delete record with ID " + id, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @GetMapping("getByName/name/{name}")
    public ResponseEntity<WorkDomainDto> getWorkDomainByName(@PathVariable("name") String workDomain_name) {
        try {
            WorkDomain workDomain = workDomainService.getWorkDomainByName(workDomain_name);
            if (workDomain != null) {
                return new ResponseEntity<>(WorkDomainDto.from(workDomain), HttpStatus.OK);
            } else {
                return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping(value = "{workDomainId}/subDomain/{subDomainId}/add")
    public ResponseEntity<WorkDomainDto> addSubToWorkDomain(@PathVariable final Long workDomainId,
                                                        @PathVariable final Long subDomainId) {
        try {
            WorkDomain workDomain = workDomainService.addSubToWorkDomain(workDomainId, subDomainId);
            return new ResponseEntity<>(WorkDomainDto.from(workDomain), HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(null,HttpStatus.NOT_FOUND);

        }
    }

    @DeleteMapping(value = "{workDomainId}/subDomain/{subDomainId}/remove")
    public ResponseEntity<WorkDomainDto> removeSubFromWorkDomain(@PathVariable final Long workDomainId,
                                                                @PathVariable final Long subDomainId) {
        try {
            WorkDomain workDomain = workDomainService.removeSubDomainFromWorkDomain(workDomainId, subDomainId);
            return new ResponseEntity<>(WorkDomainDto.from(workDomain), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }


    @PutMapping(value = "{workDomainId}/subDomain/{subDomainId}/update")
    public ResponseEntity<WorkDomainDto> editSubDomain(@PathVariable final Long workDomainId, @PathVariable final Long subDomainId, @RequestBody final SubDomainDto subDomainDto) {
        try {
            WorkDomain editWorkDomain = workDomainService.editSubDomain(workDomainId,subDomainId, SubDomain.from(subDomainDto));
            return new ResponseEntity<>(WorkDomainDto.from(editWorkDomain), HttpStatus.OK);
        }
        catch (Exception e){
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("deleteAllWorkDomain/")
    public ResponseEntity<String> deleteAllWorkDomain() {
        try {
            workDomainService.deleteAll();
            return new ResponseEntity<>("All workDomain have been deleted.", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Failed to delete all workDomain.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("{workDomainId}/offices/{officeId}/assign")
    public ResponseEntity<WorkDomainDto> assignOfficeToWorkDomain(@PathVariable Long workDomainId,
            @PathVariable Long officeId) {
            try {
                WorkDomain workDomain = workDomainService.assignWorkDomainToOffice(workDomainId, officeId);
                return new ResponseEntity<>(WorkDomainDto.from(workDomain), HttpStatus.OK);
            }catch (Exception e){
                return new ResponseEntity<>(null,HttpStatus.NOT_FOUND);

            }
    }

    @DeleteMapping("{workDomainId}/offices/{officeId}/remove")
    public ResponseEntity<WorkDomainDto> removeOfficeFromWorkDomain(@PathVariable Long workDomainId,
                                                                  @PathVariable Long officeId) {
        try {
            WorkDomain workDomain = workDomainService.removeWorkDomainFromOffice(workDomainId, officeId);
            return new ResponseEntity<>(WorkDomainDto.from(workDomain), HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(null,HttpStatus.NOT_FOUND);

        }
    }

    @PostMapping(value = "{workDomainId}/beneficiary/{beneId}/add")
    public ResponseEntity<WorkDomainDto> addBeneficiary(@PathVariable final Long workDomainId,
                                                            @PathVariable final Long beneId) {
        try {
            WorkDomain workDomain = workDomainService.addBeneficiary(workDomainId, beneId);
            return new ResponseEntity<>(WorkDomainDto.from(workDomain), HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(null,HttpStatus.NOT_FOUND);

        }
    }

    @DeleteMapping(value = "{workDomainId}/beneficiary/{beneId}/remove")
    public ResponseEntity<WorkDomainDto> removeBeneficiary(@PathVariable final Long workDomainId,
                                                                 @PathVariable final Long beneId) {
        try {
            WorkDomain workDomain = workDomainService.removeBeneficiary(workDomainId, beneId);
            return new ResponseEntity<>(WorkDomainDto.from(workDomain), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }



}


