package hh.getData.guideline.Office;

import hh.getData.guideline.Exception.EntityNotFoundException;
import hh.getData.guideline.Licence.Licence;
import hh.getData.guideline.Licence.LicenceDto;
import hh.getData.guideline.Licence.LicenceService;
import hh.getData.guideline.WorkDomain.WorkDomain;
import hh.getData.guideline.WorkDomain.WorkDomainRepository;
import hh.getData.guideline.enumeration.Status;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@Data
@RestController
@CrossOrigin
@RequestMapping("/offices/")
public class OfficeController {

    @Autowired
    final private OfficeService officeService;

    @Autowired
    final private LicenceService licenceService;


    @GetMapping("getAll/")
    public ResponseEntity<List<OfficeDto>> getAll() {
        try {
            List<Office> offices = officeService.getAllOffices();
            List<OfficeDto> officeDto = offices.stream().map(OfficeDto::from).collect(Collectors.toList());
            return new ResponseEntity<>(officeDto, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    //getById

    @GetMapping("getBYId/id/{id}")
    public ResponseEntity<OfficeDto> getOfficeById(@PathVariable Long id) {

            Office office = officeService.getOfficeById(id);

            if (office != null) {
                return new ResponseEntity<>(OfficeDto.from(office), HttpStatus.OK);
            } else
                return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);

    }


    // Post
    @PostMapping(value = "create/")
    public ResponseEntity<OfficeDto> addOffice(@RequestBody OfficeDto officeDto) {
        try {
            if (officeDto.getStatus() == null) {
                officeDto.setStatus(Status.ACTIVE);
            }
            Office office = officeService.createOffice(Office.from(officeDto));
            return new ResponseEntity<>(OfficeDto.from(office), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }



    // Delete
    @DeleteMapping(value = "delete_office/id/{id}")
    public ResponseEntity<Boolean> deleteOffice(@PathVariable final Long id) {
        try {
            return new ResponseEntity<>(officeService.deleteOffice(id), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.OK);
        }
    }


    // Updating
    @PutMapping(value = "update_office/id/{id}")
    public ResponseEntity<OfficeDto> editOffice(@PathVariable final Long id, @RequestBody final OfficeDto officeDto) {
        try {
            Office editOffice = officeService.updateOffice(id, Office.from(officeDto));
            return new ResponseEntity<>(OfficeDto.from(editOffice), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }


    @DeleteMapping("soft-delete/id/{id}")
    public ResponseEntity<String> softDeleteById(@PathVariable Long id) {
        try {
            officeService.softDeleteById(id);
            return new ResponseEntity<>("Record with ID " + id + " has been soft deleted.", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Failed to soft delete record with ID " + id, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @GetMapping("getByName/name/{name}")
    public ResponseEntity<OfficeDto> getOfficeByName(@PathVariable("name") String office_name) {
        try {
            Office office = officeService.getOfficeByName(office_name);
            if (office != null) {
                return new ResponseEntity<>(OfficeDto.from(office), HttpStatus.OK);
            } else {
                return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping(value = "{officeId}/licence/{licenceId}/add")
    public ResponseEntity<OfficeDto> addLicenceToOffice(@PathVariable final Long officeId,
                                                           @PathVariable final Long licenceId) {
        try {
            Office office = officeService.addLicenceToOffice(officeId, licenceId);
            return new ResponseEntity<>(OfficeDto.from(office), HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(null,HttpStatus.NOT_FOUND);

        }
    }

    @DeleteMapping(value = "{officeId}/licence/{licenceId}/remove")
    public ResponseEntity<OfficeDto> removeLicenceFromOffice(@PathVariable final Long officeId,
                                                                @PathVariable final Long licenceId) {
        try {
            Office office = officeService.removeLicenceFromOffice(officeId, licenceId);
            return new ResponseEntity<>(OfficeDto.from(office), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }


    @PutMapping(value = "{officeId}/licence/{licenceId}/update")
    public ResponseEntity<OfficeDto> editILicence(@PathVariable final Long officeId, @PathVariable final Long licenceId, @RequestBody final LicenceDto licenceDto) {
        try {
            Office editOffice = officeService.editLicence(officeId,licenceId, Licence.from(licenceDto));
            return new ResponseEntity<>(OfficeDto.from(editOffice), HttpStatus.OK);
        }
        catch (Exception e){
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }
    @DeleteMapping("deleteAllOffices/")
    public ResponseEntity<String> deleteAllOffices() {
        try {
            officeService.deleteAllOffices();
            return new ResponseEntity<>("All offices have been deleted.", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Failed to delete all offices.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

   /* @PostMapping("/{officeId}/assign-workDomain/{workDomainId}/assign")
    public ResponseEntity<OfficeDto> assignOfficeToWorkDomain(@PathVariable Long officeId, @PathVariable Long workDomainId) {
        try {
            Office office = officeService.assignOfficeToWorkDomain(officeId, workDomainId);
            return new ResponseEntity<>(OfficeDto.from(office), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping ("/{officeId}/assign-workDomain/{workDomainId}/remove")
    public ResponseEntity<OfficeDto> removeOfficeFromWorkDomain(@PathVariable Long officeId, @PathVariable Long workDomainId) {
        try {
            Office office = officeService.removeOfficeFromWorkDomain(officeId, workDomainId);
            return new ResponseEntity<>(OfficeDto.from(office), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }*/

    /*@GetMapping("workDomain/{workDomainId}/offices")
    public ResponseEntity<List<Office>>getAllOfficesByWorkDomainId(@PathVariable Long workDomainId) {
        List<Office> offices = officeService.findOfficesByWorkDomainId(workDomainId);
        return new ResponseEntity<>(offices, HttpStatus.OK);
    }*/


}
