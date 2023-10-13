package hh.getData.guideline.Funder;

import hh.getData.guideline.Donors.Donors;
import hh.getData.guideline.Donors.DonorsDto;
import hh.getData.guideline.WorkDomain.WorkDomain;
import hh.getData.guideline.WorkDomain.WorkDomainDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@Slf4j
@CrossOrigin
@RequestMapping("/funders/")
public class FunderController {
    private final FunderService funderService;

    @GetMapping("getAll/")
    public ResponseEntity<List<FunderDto>> getAll() {
        try {
            List<Funder> funders = funderService.getAllFunders();
            List<FunderDto> funderDto = funders.stream()
                    .map(FunderDto::from)
                    .collect(Collectors.toList());
            return new ResponseEntity<>(funderDto, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("getById/id/{id}")
    public ResponseEntity<FunderDto> getFunderById(@PathVariable Long id) {
        try {
            Funder funder = funderService.getFunderById(id);
            if (funder != null) {
                return new ResponseEntity<>(FunderDto.from(funder), HttpStatus.OK);
            } else {
                return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("create/")
    public ResponseEntity<FunderDto> addFunder(@RequestBody FunderDto funderDto) {
        try {
            Funder funder = funderService.createFunder(Funder.from(funderDto));
            return new ResponseEntity<>(FunderDto.from(funder), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("delete/id/{id}")
    public ResponseEntity<Boolean> deleteFunder(@PathVariable Long id) {
        try {
            return new ResponseEntity<>(funderService.deleteFunder(id), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.OK);
        }
    }

    @PutMapping("update/id/{id}")
    public ResponseEntity<FunderDto> editFunder(@PathVariable Long id, @RequestBody FunderDto funderDto) {
        try {
            Funder editedFunder = funderService.updateFunder(id, Funder.from(funderDto));
            return new ResponseEntity<>(FunderDto.from(editedFunder), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("getByName/name/{name}")
    public ResponseEntity<FunderDto> getFunderByName(@PathVariable("name") String fundName) {
        try {
            Funder funder = funderService.getFunderByName(fundName);
            if (funder != null) {
                return new ResponseEntity<>(FunderDto.from(funder), HttpStatus.OK);
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
            funderService.changeStatusToInactive(id);
            return new ResponseEntity<>(null, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Failed to change status record with ID " + id, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("active/id/{id}")
    public ResponseEntity<String> changeStatusToActive(@PathVariable Long id) {
        try {
            funderService.changeStatusToActive(id);
            return new ResponseEntity<>(null, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Failed to change status record with ID " + id, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("deleteAll/")
    public ResponseEntity<String> deleteAllFunders() {
        try {
            funderService.deleteAllFunders();
            return new ResponseEntity<>("All funders have been deleted.", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Failed to delete all funders.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping(value = "{funderId}/funderSource/{funderSourceId}/add")
    public ResponseEntity<FunderDto> addFunderSource(@PathVariable final Long funderId,
                                                        @PathVariable final Long funderSourceId) {
        try {
            Funder funder = funderService.addFunderSource(funderId, funderSourceId);
            return new ResponseEntity<>(FunderDto.from(funder), HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(null,HttpStatus.NOT_FOUND);

        }
    }

    @DeleteMapping(value = "{funderId}/funderSource/{funderSourceId}/remove")
    public ResponseEntity<FunderDto> removeFunderSource(@PathVariable final Long funderId,
                                                     @PathVariable final Long funderSourceId) {
        try {
            Funder funder = funderService.removeFunderSource(funderId, funderSourceId);
            return new ResponseEntity<>(FunderDto.from(funder), HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(null,HttpStatus.NOT_FOUND);

        }
    }

    @PostMapping(value = "{funderId}/image/{imageId}/add")
    public ResponseEntity<FunderDto> addImage(@PathVariable final Long funderId, @PathVariable final Long imageId) {
        Funder funder = funderService.addImage(funderId, imageId);
        return new ResponseEntity<>(FunderDto.from(funder), HttpStatus.OK);
    }
}

