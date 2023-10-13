package hh.getData.guideline.Beneficiary;

import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@Data
@RestController
@CrossOrigin
@RequestMapping("/beneficiaries/")
public class BeneficiaryController {

    private final BeneficiaryService beneficiaryService;

    @GetMapping("getAll/")
    public ResponseEntity<List<BeneficiaryDto>> getAll() {
        try {
            List<Beneficiary> beneficiaries = beneficiaryService.getAllBeneficiaries();
            List<BeneficiaryDto> beneficiaryDto = beneficiaries.stream()
                    .map(BeneficiaryDto::from)
                    .collect(Collectors.toList());
            return new ResponseEntity<>(beneficiaryDto, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("getById/id/{id}")
    public ResponseEntity<BeneficiaryDto> getBeneficiaryById(@PathVariable Long id) {
        try {
            Beneficiary beneficiary = beneficiaryService.getBeneficiaryById(id);
            if (beneficiary != null) {
                return new ResponseEntity<>(BeneficiaryDto.from(beneficiary), HttpStatus.OK);
            } else {
                return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("create/")
    public ResponseEntity<BeneficiaryDto> addBeneficiary(@RequestBody BeneficiaryDto beneficiaryDto) {
        try {
            Beneficiary beneficiary = beneficiaryService.createBeneficiary(Beneficiary.from(beneficiaryDto));
            return new ResponseEntity<>(BeneficiaryDto.from(beneficiary), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("delete/id/{id}")
    public ResponseEntity<Boolean> deleteBeneficiary(@PathVariable Long id) {
        try {
            return new ResponseEntity<>(beneficiaryService.deleteBeneficiary(id), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.OK);
        }
    }

    @PutMapping("update/id/{id}")
    public ResponseEntity<BeneficiaryDto> editBeneficiary(@PathVariable Long id, @RequestBody BeneficiaryDto beneficiaryDto) {
        try {
            Beneficiary editedBeneficiary = beneficiaryService.updateBeneficiary(id, Beneficiary.from(beneficiaryDto));
            return new ResponseEntity<>(BeneficiaryDto.from(editedBeneficiary), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }
    @GetMapping("getByName/name/{name}")
    public ResponseEntity<BeneficiaryDto> getBeneByName(@PathVariable("name") String beneName) {
        try {
            Beneficiary beneficiary = beneficiaryService.getBeneByName(beneName);
            if (beneficiary != null) {
                return new ResponseEntity<>(BeneficiaryDto.from(beneficiary), HttpStatus.OK);
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
            beneficiaryService.changeStatusToInactive(id);
            return new ResponseEntity<>(null, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Failed to change status record with ID " + id, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("active/id/{id}")
    public ResponseEntity<String> changeStatusToActive(@PathVariable Long id) {
        try {
            beneficiaryService.changeStatusToActive(id);
            return new ResponseEntity<>(null, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Failed to change status record with ID " + id, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @DeleteMapping("deleteAll/")
    public ResponseEntity<String> deleteAllDepartments() {
        try {
            beneficiaryService.deleteAllBeni();
            return new ResponseEntity<>("All beneficiaries have been deleted.", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Failed to delete all beneficiaries.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
