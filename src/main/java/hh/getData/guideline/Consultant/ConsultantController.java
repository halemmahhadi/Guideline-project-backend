package hh.getData.guideline.Consultant;

import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@Data
@RestController
@CrossOrigin
@RequestMapping("/consultants/")
public class ConsultantController {
    private final ConsultantService consultantService;

    @GetMapping("getAll/")
    public ResponseEntity<List<ConsultantDto>> getAll() {
        try {
            List<Consultant> consultants = consultantService.getAllConsultants();
            List<ConsultantDto> consultantDto = consultants.stream()
                    .map(ConsultantDto::from)
                    .collect(Collectors.toList());
            return new ResponseEntity<>(consultantDto, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("getById/id/{id}")
    public ResponseEntity<ConsultantDto> getConsultantById(@PathVariable Long id) {
        try {
            Consultant consultant = consultantService.getConsultantById(id);
            if (consultant != null) {
                return new ResponseEntity<>(ConsultantDto.from(consultant), HttpStatus.OK);
            } else {
                return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("create/")
    public ResponseEntity<ConsultantDto> addConsultant(@RequestBody ConsultantDto consultantDto) {
        try {
            Consultant consultant = consultantService.createConsultant(Consultant.from(consultantDto));
            return new ResponseEntity<>(ConsultantDto.from(consultant), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("delete/id/{id}")
    public ResponseEntity<Boolean> deleteConsultant(@PathVariable Long id) {
        try {
            return new ResponseEntity<>(consultantService.deleteConsultant(id), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.OK);
        }
    }

    @PutMapping("update/id/{id}")
    public ResponseEntity<ConsultantDto> editConsultant(@PathVariable Long id, @RequestBody ConsultantDto consultantDto) {
        try {
            Consultant editedConsultant = consultantService.updateConsultant(id, Consultant.from(consultantDto));
            return new ResponseEntity<>(ConsultantDto.from(editedConsultant), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("getByName/name/{name}")
    public ResponseEntity<ConsultantDto> getConsByName(@PathVariable("name") String consName) {
        try {
            Consultant consultant = consultantService.getConsByName(consName);
            if (consultant != null) {
                return new ResponseEntity<>(ConsultantDto.from(consultant), HttpStatus.OK);
            } else {
                return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("inactive/id/{id}")
    public ResponseEntity<String> changeStatusToInactive(@PathVariable Long id) {
        try {
            consultantService.changeStatusToInactive(id);
            return new ResponseEntity<>(null, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Failed to change status record with ID " + id, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("active/id/{id}")
    public ResponseEntity<String> changeStatusToActive(@PathVariable Long id) {
        try {
            consultantService.changeStatusToActive(id);
            return new ResponseEntity<>(null, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Failed to change status record with ID " + id, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("deleteAll/")
    public ResponseEntity<String> deleteAllConsultants() {
        try {
            consultantService.deleteAllConsultants();
            return new ResponseEntity<>("All consultants have been deleted.", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Failed to delete all consultants.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
