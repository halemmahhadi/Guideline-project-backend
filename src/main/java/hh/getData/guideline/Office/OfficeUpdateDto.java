package hh.getData.guideline.Office;

import hh.getData.guideline.enumeration.Status;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OfficeUpdateDto {
    @NotNull
    private long id;

    @NotBlank
    @NotNull
    private String name;

    private String description;

    @NotBlank
    @NotNull
    private String address;

    private String required_actions;

    private String notes;

    private String logo;

    private String website_url;

    private String facebook_account;

    private String linkedin_account;

    private String email;

    private String phone_number;


    public static OfficeUpdateDto from(Office office) {
        OfficeUpdateDto OfficeUpdateDto = new OfficeUpdateDto();
        OfficeUpdateDto.setId(office.getId());
        OfficeUpdateDto.setName(office.getName());
        OfficeUpdateDto.setDescription(office.getDescription());
        OfficeUpdateDto.setAddress(office.getAddress());
        OfficeUpdateDto.setRequired_actions(office.getRequired_actions());
        OfficeUpdateDto.setNotes(office.getNotes());
        OfficeUpdateDto.setLogo(office.getLogo());
        OfficeUpdateDto.setWebsite_url(office.getWebsite_url());
        OfficeUpdateDto.setFacebook_account(office.getFacebook_account());
        OfficeUpdateDto.setLinkedin_account(office.getLinkedin_account());
        OfficeUpdateDto.setEmail(office.getEmail());
        OfficeUpdateDto.setPhone_number(office.getPhone_number());
        return OfficeUpdateDto;
    }

}
