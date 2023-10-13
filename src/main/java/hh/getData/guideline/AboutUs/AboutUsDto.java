package hh.getData.guideline.AboutUs;

import hh.getData.guideline.Image.Image;
import hh.getData.guideline.enumeration.Status;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AboutUsDto {

    @NotNull
    private  long about_id;

    private Image image;


    private String about_app;


    private String our_services;

    @NotBlank
    @NotNull
    private String email;

    @NotBlank
    @NotNull
    private String phone_number;

    private Status status = Status.ACTIVE;


    public static AboutUsDto from(AboutUs aboutUs){
        AboutUsDto aboutUsDto= new AboutUsDto();
        aboutUsDto.setAbout_id(aboutUs.getAbout_id());
        aboutUsDto.setAbout_app(aboutUs.getAbout_app());
        aboutUsDto.setImage(aboutUs.getImage());
        aboutUsDto.setEmail(aboutUs.getEmail());
        aboutUsDto.setPhone_number(aboutUs.getPhone_number());
        aboutUsDto.setOur_services(aboutUs.getOur_services());
        aboutUsDto.setStatus(aboutUs.getStatus());
        return aboutUsDto;
    }
}
