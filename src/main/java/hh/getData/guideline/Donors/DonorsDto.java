package hh.getData.guideline.Donors;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
public class DonorsDto {
    @NotNull
    private  long donor_id;

    @NotBlank
    @NotNull
    private String donorName;

    //@NotBlank
    //@NotNull
    private Image image;

    private String about_donor;

    private String facebook_account;

    private String linkedin_account;

    private String email;

    @NotBlank
    @NotNull
    private String phone_number;

    private Status status = Status.ACTIVE ;


    public static DonorsDto from(Donors donors){

        DonorsDto donorsDto = new DonorsDto();

        donorsDto.setDonor_id(donors.getDonor_id());
        donorsDto.setAbout_donor(donors.getAbout_donor());
        donorsDto.setImage(donors.getImage());
        donorsDto.setDonorName(donors.getDonorName());
        donorsDto.setFacebook_account(donors.getFacebook_account());
        donorsDto.setLinkedin_account(donors.getLinkedin_account());
        donorsDto.setEmail(donors.getEmail());
        donorsDto.setPhone_number(donors.getPhone_number());
        donorsDto.setStatus(donors.getStatus());
        return donorsDto;
    }

}
