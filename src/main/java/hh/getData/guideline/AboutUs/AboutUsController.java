package hh.getData.guideline.AboutUs;

import hh.getData.guideline.AboutUs.AboutUs;
import hh.getData.guideline.AboutUs.AboutUsDto;
import hh.getData.guideline.AboutUs.AboutUsService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.coyote.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.function.EntityResponse;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@RestController
@CrossOrigin
@RequestMapping("/about-us/")
public class AboutUsController {

    private final AboutUsService aboutUsService;
    @RequestMapping("getAll/")
    @ApiOperation(value = "view a list of available AboutUs information",
            notes = "get all AboutUs info",
            response = List.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success"),
            @ApiResponse(code = 404, message = "There is no info to display"),
    })
    public ResponseEntity<List<AboutUsDto>> getAll(){
        log.debug("Received request to get all info");
        try {
            List<AboutUs> aboutUses = aboutUsService.getAll();
            List<AboutUsDto> aboutUsDto = aboutUses.stream().map(AboutUsDto::from).collect(Collectors.toList());
            return new ResponseEntity<>(aboutUsDto, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);

        }

    }

    // Post

    @PostMapping(value = "create/")

    public ResponseEntity<AboutUsDto> addAboutUs(@RequestBody AboutUsDto aboutUsDto) {
        try {
            AboutUs aboutUs = aboutUsService.createAboutUS(AboutUs.from(aboutUsDto));
            return new ResponseEntity<>(AboutUsDto.from(aboutUs), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);

        }
    }


    // delete

    @DeleteMapping(value = "delete/id/{id}/")
    @ApiOperation(value = "Delete aboutUs Info",
            notes = " deleting info",
            response = List.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully deleted aboutUs info"),
            @ApiResponse(code = 404, message = "Can not found the data"),


    })
    public ResponseEntity<Boolean> deleteAboutUs(@PathVariable final Long id) {
        try {
            return new ResponseEntity<>(aboutUsService.deleteAboutUs(id), HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(null, HttpStatus.OK);

        }
    }


    //updating

    @PutMapping(value = "update/id/{id}/")
    @ApiOperation(value = "Update aboutUs",
            notes = "edit AboutUs by id",
            response = List.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully updated "),
            @ApiResponse(code = 404, message = "There is no Data with this ID to update")
    })
    public ResponseEntity<AboutUsDto> editAboutUs(@PathVariable final Long id, @RequestBody final AboutUsDto aboutUsDto) {
        try {
            AboutUs editAboutUs = aboutUsService.updateAboutUs(id, AboutUs.from(aboutUsDto));
            return new ResponseEntity<>(AboutUsDto.from(editAboutUs), HttpStatus.OK);
        }
        catch (Exception e){
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

}
