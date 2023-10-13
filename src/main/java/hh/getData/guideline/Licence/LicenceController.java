package hh.getData.guideline.Licence;


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
@RequestMapping("/licences/")
public class LicenceController {

   private final LicenceService licenceService;


    @GetMapping("getAll/")
    public ResponseEntity<List<LicenceDto>> getAll() {
        try {
            List<Licence> licences = licenceService.getAllLicences();
            List<LicenceDto> licenceDto = licences.stream().map(LicenceDto::from).collect(Collectors.toList());
            return new ResponseEntity<>(licenceDto, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("getById/{id}")
    public ResponseEntity<LicenceDto> getLicenceById(@PathVariable Long id) {

            Licence licence = licenceService.getLicenceById(id);

        if (licence != null) {
            return new ResponseEntity<>(LicenceDto.from(licence), HttpStatus.OK);
        } else
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
    }

    @PostMapping("create/")
    public ResponseEntity<LicenceDto> addLicence(@RequestBody LicenceDto licenceDto) {
        try {
            if (licenceDto.getStatus() == null) {
                licenceDto.setStatus(Status.ACTIVE);
            }
            Licence licence = licenceService.createLicence(Licence.from(licenceDto));
            return new ResponseEntity<>(LicenceDto.from(licence), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("delete_licence/id/{id}")
    public ResponseEntity<Boolean> deleteLicence(@PathVariable final Long id) {
        try {
            return new ResponseEntity<>(licenceService.deleteLicence(id), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("update_licence/id/{id}")
    public ResponseEntity<LicenceDto> editLicence(@PathVariable final Long id, @RequestBody final LicenceDto licenceDto) {
        try {
            Licence editLicence = licenceService.updateLicence(id, Licence.from(licenceDto));
            return new ResponseEntity<>(LicenceDto.from(editLicence), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("soft-delete/id/{id}")
    public ResponseEntity<String> softDeleteById(@PathVariable Long id) {
        try {
            licenceService.softDeleteById(id);
            return new ResponseEntity<>("Record with ID " + id + " has been soft deleted.", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Failed to soft delete record with ID " + id, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("getByName/name/{name}")
    public ResponseEntity<LicenceDto> getLicenceByName(@PathVariable("name") String licenceName) {
        try {
            Licence licence = licenceService.getLicenceByName(licenceName);
            if (licence != null) {
                return new ResponseEntity<>(LicenceDto.from(licence), HttpStatus.OK);
            } else {
                return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("deleteAllLicences/")
    public ResponseEntity<String> deleteAllLicences() {
        try {
            licenceService.deleteAllLicences();
            return new ResponseEntity<>("All Licences have been deleted.", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Failed to delete all Licences.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}

