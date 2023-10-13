package hh.getData.guideline.Image;

import hh.getData.guideline.Exception.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ImageService {

    private final ImageRepository imageRepository;

    public Image uploadImage(Image image){
        return imageRepository.save(image);
    }

    public List<Image> getAll(){
        return imageRepository.findAll();
    }

    public Image getById(Long id)
    {
        return imageRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Image with ID " + id + " not found"));

    }

    public Image deleteImage(Long id){
        Image image=getById(id);
        imageRepository.delete(image);
        return image;
    }
    @Transactional
    public Image editImage(Long id, MultipartFile multipartImage){
        Image imageToEdit=getById(id);
        try {
            imageToEdit.setContent(multipartImage.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return imageToEdit;
    }

    public void deleteAll(){
        imageRepository.deleteAll();
    }
}

