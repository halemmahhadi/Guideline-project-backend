package hh.getData.guideline.FunderSource;

import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@Data
@RestController
@CrossOrigin
@RequestMapping("/funderSource/")
public class FunderSourceController {

    private final FunderSourceService funderSourceService;

    @GetMapping("getAll/")
    public ResponseEntity<List<FunderSourceDto>> getAll() {
        try {
            List<FunderSource> funderSources = funderSourceService.getAllFunderSources();
            List<FunderSourceDto> funderSourceDto = funderSources.stream()
                    .map(FunderSourceDto::from)
                    .collect(Collectors.toList());
            return new ResponseEntity<>(funderSourceDto, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("getById/id/{id}")
    public ResponseEntity<FunderSourceDto> getFunderSourceById(@PathVariable Long id) {
        try {
            FunderSource funderSource = funderSourceService.getFunderSourceById(id);
            if (funderSource != null) {
                return new ResponseEntity<>(FunderSourceDto.from(funderSource), HttpStatus.OK);
            } else {
                return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("create/")
    public ResponseEntity<FunderSourceDto> addFunderSource(@RequestBody FunderSourceDto funderSourceDto) {
        try {
            FunderSource funderSource = funderSourceService.createFunderSource(FunderSource.from(funderSourceDto));
            return new ResponseEntity<>(FunderSourceDto.from(funderSource), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("delete/id/{id}")
    public ResponseEntity<Boolean> deleteFunderSource(@PathVariable Long id) {
        try {
            return new ResponseEntity<>(funderSourceService.deleteFunderSource(id), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.OK);
        }
    }

    @PutMapping("update/id/{id}")
    public ResponseEntity<FunderSourceDto> editFunderSource(@PathVariable Long id, @RequestBody FunderSourceDto funderSourceDto) {
        try {
            FunderSource editedFunderSource = funderSourceService.updateFunderSource(id, FunderSource.from(funderSourceDto));
            return new ResponseEntity<>(FunderSourceDto.from(editedFunderSource), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("getByFundTypeName/fundTypeName/{fundTypeName}")
    public ResponseEntity<FunderSourceDto> getFunderSourceByName(@PathVariable("fundTypeName") String fundTypeName) {
        try {
            FunderSource funderSource = funderSourceService.getFunderSourceByName(fundTypeName);
            if (funderSource != null) {
                return new ResponseEntity<>(FunderSourceDto.from(funderSource), HttpStatus.OK);
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
            funderSourceService.changeStatusToInactive(id);
            return new ResponseEntity<>(null, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Failed to change status record with ID " + id, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("active/id/{id}")
    public ResponseEntity<String> changeStatusToActive(@PathVariable Long id) {
        try {
            funderSourceService.changeStatusToActive(id);
            return new ResponseEntity<>(null, HttpStatus.OK);
        } catch ( Exception e) {
            return new ResponseEntity<>("Failed to change status record with ID " + id, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("deleteAll/")
    public ResponseEntity<String> deleteAllFunderSources() {
        try {
            funderSourceService.deleteAllFunderSources();
            return new ResponseEntity<>("All funder sources have been deleted.", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Failed to delete all funder sources.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
