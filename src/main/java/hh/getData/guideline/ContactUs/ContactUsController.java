package hh.getData.guideline.ContactUs;

import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@Data
@RestController
@CrossOrigin
@RequestMapping("/contactus/")
public class ContactUsController {
    private final ContactUsService contactUsService;

    @GetMapping("getAll/")
    public ResponseEntity<List<ContactUsDto> > getAll() {
        try {
            List<ContactUs> contactUsList = contactUsService.getAllContactUs();
            List<ContactUsDto> contactUsDtoList = contactUsList.stream()
                    .map(ContactUsDto::from)
                    .collect(Collectors.toList());
            return new ResponseEntity<>(contactUsDtoList, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("getById/id/{id}")
    public ResponseEntity<ContactUsDto> getContactUsById(@PathVariable Long id) {
        try {
            ContactUs contactUs = contactUsService.getContactUsById(id);
            if (contactUs != null) {
                return new ResponseEntity<>(ContactUsDto.from(contactUs), HttpStatus.OK);
            } else {
                return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("create/")
    public ResponseEntity<ContactUsDto> addContactUs(@RequestBody ContactUsDto contactUsDto) {
        try {
            ContactUs contactUs = contactUsService.createContactUs(ContactUs.from(contactUsDto));
            return new ResponseEntity<>(ContactUsDto.from(contactUs), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("delete/id/{id}")
    public ResponseEntity<Boolean> deleteContactUs(@PathVariable Long id) {
        try {
            return new ResponseEntity<>(contactUsService.deleteContactUs(id), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.OK);
        }
    }

    @PutMapping("update/id/{id}")
    public ResponseEntity<ContactUsDto> editContactUs(@PathVariable Long id, @RequestBody ContactUsDto contactUsDto) {
        try {
            ContactUs editedContactUs = contactUsService.updateContactUs(id, ContactUs.from(contactUsDto));
            return new ResponseEntity<>(ContactUsDto.from(editedContactUs), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("getByName/name/{name}")
    public ResponseEntity<ContactUsDto> getContactUsByName(@PathVariable("name") String name) {
        try {
            ContactUs contactUs = contactUsService.getContactUsByName(name);
            if (contactUs != null) {
                return new ResponseEntity<>(ContactUsDto.from(contactUs), HttpStatus.OK);
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
            contactUsService.changeStatusToInactive(id);
            return new ResponseEntity<>(null, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Failed to change status record with ID " + id, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("active/id/{id}")
    public ResponseEntity<String> changeStatusToActive(@PathVariable Long id) {
        try {
            contactUsService.changeStatusToActive(id);
            return new ResponseEntity<>(null, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Failed to change status record with ID " + id, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("deleteAll/")
    public ResponseEntity<String> deleteAllContactUsEntries() {
        try {
            contactUsService.deleteAllContactUsEntries();
            return new ResponseEntity<>("All contactUs entries have been deleted.", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Failed to delete all contactUs entries.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
