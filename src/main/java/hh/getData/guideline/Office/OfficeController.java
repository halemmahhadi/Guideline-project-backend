package hh.getData.guideline.Office;

import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Data
@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/offices/")
public class OfficeController {

    final private OfficeService officeService;

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
        try {
            Optional<Office> officeOptional = officeService.getOfficeById(id);

            if (officeOptional.isPresent()) {
                return new ResponseEntity<>(OfficeDto.from(officeOptional.get()), HttpStatus.OK);
            } else {
                return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    // Post
    @PostMapping(value = "create/") // Updated the endpoint to "/offices/create/"
    public ResponseEntity<OfficeDto> addOffice(@RequestBody OfficeDto officeDto) {
        try {
            Office office = officeService.createOffice(Office.from(officeDto));
            return new ResponseEntity<>(OfficeDto.from(office), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }



    // Delete
    @DeleteMapping(value = "delete_office/id/{id}") // Updated the endpoint to "/offices/delete_office/id/{id}"
    public ResponseEntity<Boolean> deleteOffice(@PathVariable final Long id) {
        try {
            return new ResponseEntity<>(officeService.deleteOffice(id), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.OK);
        }
    }


    // Updating
    @PutMapping(value = "update_office/id/{id}") // Updated the endpoint to "/offices/update_office/id/{id}"
    public ResponseEntity<OfficeUpdateDto> editOffice(@PathVariable final Long id, @RequestBody final OfficeUpdateDto officeDto) {
        try {
            Office editOffice = officeService.updateOffice(id, Office.from(officeDto));
            return new ResponseEntity<>(OfficeUpdateDto.from(editOffice), HttpStatus.OK);
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


}
