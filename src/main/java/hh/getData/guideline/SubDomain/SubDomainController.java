package hh.getData.guideline.SubDomain;


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
@RequestMapping("/subDomains/")
public class SubDomainController {

    private final SubDomainService subDomainService;


    @GetMapping("getAll/")
    public ResponseEntity<List<SubDomainDto>> getAll() {
        try {
            List<SubDomain> subDomains = subDomainService.getAllSubDomains();
            List<SubDomainDto> subDomainDto = subDomains.stream().map(SubDomainDto::from).collect(Collectors.toList());
            return new ResponseEntity<>(subDomainDto, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    //getById

    @GetMapping("getBYId/id/{id}")
    public ResponseEntity<SubDomainDto> getSubDomainById(@PathVariable Long id) {

        SubDomain subDomain = subDomainService.getSubDomainById(id);

        if (subDomain != null) {
            return new ResponseEntity<>(SubDomainDto.from(subDomain), HttpStatus.OK);
        } else
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);

    }


    // Post
    @PostMapping(value = "create/")
    public ResponseEntity<SubDomainDto> addSubDomain(@RequestBody SubDomainDto subDomainDto) {
        try {
            if (subDomainDto.getStatus() == null) {
                subDomainDto.setStatus(Status.ACTIVE);
            }
            SubDomain subDomain = subDomainService.createSubDomain(SubDomain.from(subDomainDto));
            return new ResponseEntity<>(SubDomainDto.from(subDomain), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }



    // Delete
    @DeleteMapping(value = "delete_subDomain/id/{id}")
    public ResponseEntity<Boolean> deleteSubDomain(@PathVariable final Long id) {
        try {
            return new ResponseEntity<>(subDomainService.deleteSubDomain(id), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.OK);
        }
    }


    // Updating
    @PutMapping(value = "update_subDomain/id/{id}")
    public ResponseEntity<SubDomainDto> editSubDomain(@PathVariable final Long id, @RequestBody final SubDomainDto subDomainDto) {
        try {
            SubDomain editsubDomain = subDomainService.updateSubDomain(id, SubDomain.from(subDomainDto));
            return new ResponseEntity<>(SubDomainDto.from(editsubDomain), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }



    @PutMapping("soft-delete/id/{id}")
    public ResponseEntity<String> softDeleteById(@PathVariable Long id) {
        try {
            subDomainService.softDeleteById(id);
            return new ResponseEntity<>(null, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Failed to soft delete record with ID " + id, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @GetMapping("getByName/name/{name}")
    public ResponseEntity<SubDomainDto> getSubDomainByName(@PathVariable("name") String subDomain_name) {
        try {
            SubDomain subDomain = subDomainService.getSubDomainByName(subDomain_name);
            if (subDomain != null) {
                return new ResponseEntity<>(SubDomainDto.from(subDomain), HttpStatus.OK);
            } else {
                return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @DeleteMapping("deleteAllSubDomain/")
    public ResponseEntity<String> deleteAllSubDomain() {
        try {
            subDomainService.deleteAllSub();
            return new ResponseEntity<>("All workSubDomain have been deleted.", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Failed to delete all workSubDomain.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
