package hh.getData.guideline.Donors;

import hh.getData.guideline.Image.Image;
import hh.getData.guideline.Image.ImageService;
import hh.getData.guideline.Office.Office;
import hh.getData.guideline.Office.OfficeDto;
import jakarta.transaction.Transactional;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.stream.Collectors;

@Data
@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/donors/")
public class DonorsController {

    private final DonorsServices donorsServices;
    private final ImageService imageService;

    @GetMapping("getAll/")
    public ResponseEntity<List<DonorsDto>> getAll(){
        //log.debug("Received request to get all info");
        try {
            List<Donors> donors = donorsServices.getAll();
            List<DonorsDto> donorsDto = donors.stream().map(DonorsDto::from).collect(Collectors.toList());
            return new ResponseEntity<>(donorsDto, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);

        }

    }
    // Post
    @PostMapping(value = "create/")
    public ResponseEntity<DonorsDto> addDonor(@RequestBody DonorsDto donorsDto) {
        try {
            Donors donors= donorsServices.createDonors(Donors.from(donorsDto));
            return new ResponseEntity<>(DonorsDto.from(donors), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);

        }
    }

    // delete
    @DeleteMapping(value = "delete_donors/id/{id}")
    public ResponseEntity<Boolean> deleteDonor(@PathVariable final Long id) {
        try {
            return new ResponseEntity<>(donorsServices.deleteDonors(id), HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(null, HttpStatus.OK);

        }
    }

    //updating
    @PutMapping(value = "update_donors/id/{id}")
    public ResponseEntity<DonorsDto> editDonor(@PathVariable final Long id, @RequestBody final DonorsDto donorsDto) {
        try {
            Donors editDonors = donorsServices.updateDonors(id, Donors.from(donorsDto));
            return new ResponseEntity<>(DonorsDto.from(editDonors), HttpStatus.OK);
        }
        catch (Exception e){
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }


    @DeleteMapping("soft-delete/{id}")
    public ResponseEntity<String> softDeleteById(@PathVariable Long id) {
        try {
            donorsServices.softDeleteById(id);
            return new ResponseEntity<>("Record with ID " + id + " has been soft deleted.", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Failed to soft delete record with ID " + id, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("getBYId/id/{id}")
    public ResponseEntity<DonorsDto> getDonorById(@PathVariable Long id) {

        Donors donors = donorsServices.findById(id);

        if (donors != null) {
            return new ResponseEntity<>(DonorsDto.from(donors), HttpStatus.OK);
        } else
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);

    }

    @GetMapping("getByName/name/{name}")
    public ResponseEntity<DonorsDto> getDonorByName(@PathVariable("name") String donor_name) {
        try {
            Donors donors = donorsServices.getDonorByName(donor_name);
            if (donors != null) {
                return new ResponseEntity<>(DonorsDto.from(donors), HttpStatus.OK);
            } else {
                return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("deleteAllDonors/")
    public ResponseEntity<String> deleteAllDonors() {
        try {
            donorsServices.deleteAllDonors();
            return new ResponseEntity<>("All Donors have been deleted.", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Failed to delete all donors.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping(value = "{donorId}/image/{imageId}/add")
    public ResponseEntity<DonorsDto> addImage(@PathVariable final Long donorId, @PathVariable final Long imageId) {
        Donors donors = donorsServices.addImage(donorId, imageId);
        return new ResponseEntity<>(DonorsDto.from(donors), HttpStatus.OK);
    }

}
