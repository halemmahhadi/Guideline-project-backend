package hh.getData.guideline.Donors;

import jakarta.transaction.Transactional;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@Data
@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/donors/")
public class DonorsController {

    @Autowired
    private DonorsServices donorsServices;

    @GetMapping("get/")
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
            Donors donors = donorsServices.createDonors(Donors.from(donorsDto));
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
    public ResponseEntity<DonorsUpdateDto> editDonor(@PathVariable final Long id, @RequestBody final DonorsUpdateDto donorsDto) {
        try {
            Donors editDonors = donorsServices.updateDonors(id, Donors.from(donorsDto));
            return new ResponseEntity<>(DonorsUpdateDto.from(editDonors), HttpStatus.OK);
        }
        catch (Exception e){
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }


    @DeleteMapping("/soft-delete/{id}")
    public ResponseEntity<String> softDeleteById(@PathVariable Long id) {
        try {
            donorsServices.softDeleteById(id);
            return new ResponseEntity<>("Record with ID " + id + " has been soft deleted.", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Failed to soft delete record with ID " + id, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
