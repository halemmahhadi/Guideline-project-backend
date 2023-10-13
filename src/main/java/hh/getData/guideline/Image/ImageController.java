package hh.getData.guideline.Image;

import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Data
@RestController
@CrossOrigin
@RequestMapping("/images/")
public class ImageController {


    private final ImageService imageService;


    @PostMapping(value = "upload/")
    public ResponseEntity<Image> uploadImage( @io.swagger.v3.oas.annotations.parameters.RequestBody(
            description = "Image file to upload",
            content = @Content(
                    mediaType = "multipart/form-data",
                    schema = @Schema(type = "byte", format = "binary")
            )
    ) @RequestPart("multipartImage") MultipartFile multipartImage) throws Exception {
        try {
            Image dbImage = new Image();
            dbImage.setContent(multipartImage.getBytes());
            return new ResponseEntity<>(imageService.uploadImage(dbImage), HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }

    }



    Resource downloadImage(@PathVariable Long imageId) {
        Image image = imageService.getById(imageId);
        return new ByteArrayResource(image.getContent());
    }

    @DeleteMapping(value = "delete_image/id/{id}")
    public Image deleteImage(@PathVariable final Long id){
        Image image=imageService.deleteImage(id);
        return image;
    }
    @PutMapping(value = "update_image/id/{id}")
    public Image editImage(
            @PathVariable final Long id,
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Image file to upload",
                    content = @Content(
                            mediaType = "multipart/form-data",
                            schema = @Schema(type = "string", format = "binary")
                    )
            ) @RequestParam("multipartImage") MultipartFile multipartImage
    ) throws Exception {
        Image editImage = imageService.editImage(id,multipartImage);
        return editImage;
    }

    @GetMapping("getAll/")
    public ResponseEntity<List<Image>> getAllImages() {
        List<Image> images = imageService.getAll();
        return new ResponseEntity<>(images, HttpStatus.OK);
    }

    @GetMapping("getById/id/{id}")
    public ResponseEntity<Image> getImageById(@PathVariable Long id) {
        try {
            Image image = imageService.getById(id);
            return new ResponseEntity<>(image, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/deleteAll")
    public ResponseEntity<String> deleteAllImages() {
        try {
            imageService.deleteAll();
            return new ResponseEntity<>("All images deleted successfully.", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Failed to delete images.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
