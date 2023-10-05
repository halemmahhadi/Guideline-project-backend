package hh.getData.guideline.Donors;

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
public class DonorsUpdateDto {

    @NotNull
    private  long donor_id;

    @NotBlank
    @NotNull
    private String donor_name;

    @NotBlank
    @NotNull
    private Image logo;

    private String about_donor;



    public static DonorsUpdateDto from(Donors donors){

        DonorsUpdateDto donorsDto = new DonorsUpdateDto();

        donorsDto.setDonor_id(donors.getDonor_id());
        donorsDto.setAbout_donor(donors.getAbout_donor());
        donorsDto.setLogo(donors.getLogo());
        donorsDto.setDonor_name(donors.getDonor_name());
        return donorsDto;
    }

}