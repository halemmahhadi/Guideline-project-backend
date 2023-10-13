package hh.getData.guideline.AboutUs;

import hh.getData.guideline.Donors.Donors;
import hh.getData.guideline.Donors.DonorsDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@RestController
@CrossOrigin
@RequestMapping("/aboutUs/")
public class AboutUsController {

    private final AboutUsService aboutUsService;

    @GetMapping("getAll/")
    public ResponseEntity<List<AboutUsDto>> getAll() {
        try {
            List<AboutUs> aboutUsEntries = aboutUsService.getAllAboutUsEntries();
            List<AboutUsDto> aboutUsDtoList = aboutUsEntries.stream()
                    .map(AboutUsDto::from)
                    .collect(Collectors.toList());
            return new ResponseEntity<>(aboutUsDtoList, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("getById/id/{id}")
    public ResponseEntity<AboutUsDto> getAboutUsById(@PathVariable Long id) {
        try {
            AboutUs aboutUs = aboutUsService.getAboutUsById(id);
            if (aboutUs != null) {
                return new ResponseEntity<>(AboutUsDto.from(aboutUs), HttpStatus.OK);
            } else {
                return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("create/")
    public ResponseEntity<AboutUsDto> addAboutUs(@RequestBody AboutUsDto aboutUsDto) {
        try {
            AboutUs aboutUs = aboutUsService.createAboutUs(AboutUs.from(aboutUsDto));
            return new ResponseEntity<>(AboutUsDto.from(aboutUs), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("delete/id/{id}")
    public ResponseEntity<Boolean> deleteAboutUs(@PathVariable Long id) {
        try {
            return new ResponseEntity<>(aboutUsService.deleteAboutUs(id), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.OK);
        }
    }

    @PutMapping("update/id/{id}")
    public ResponseEntity<AboutUsDto> editAboutUs(@PathVariable Long id, @RequestBody AboutUsDto aboutUsDto) {
        try {
            AboutUs editedAboutUs = aboutUsService.updateAboutUs(id, AboutUs.from(aboutUsDto));
            return new ResponseEntity<>(AboutUsDto.from(editedAboutUs), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("inactive/id/{id}")
    public ResponseEntity<String> changeStatusToInactive(@PathVariable Long id) {
        try {
            aboutUsService.changeStatusToInactive(id);
            return new ResponseEntity<>(null, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Failed to change status record with ID " + id, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("active/id/{id}")
    public ResponseEntity<String> changeStatusToActive(@PathVariable Long id) {
        try {
            aboutUsService.changeStatusToActive(id);
            return new ResponseEntity<>(null, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Failed to change status record with ID " + id, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("deleteAll/")
    public ResponseEntity<String> deleteAllAboutUsEntries() {
        try {
            aboutUsService.deleteAllAboutUsEntries();
            return new ResponseEntity<>("All AboutUs entries have been deleted.", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Failed to delete all AboutUs entries.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @PostMapping(value = "{aboutUsId}/image/{imageId}/add")
    public ResponseEntity<AboutUsDto> addImage(@PathVariable final Long aboutUsId, @PathVariable final Long imageId) {
        AboutUs aboutUs = aboutUsService.addImage(aboutUsId, imageId);
        return new ResponseEntity<>(AboutUsDto.from(aboutUs), HttpStatus.OK);
    }
}
