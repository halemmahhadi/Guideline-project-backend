package hh.getData.guideline.Office;

import com.fasterxml.jackson.annotation.JsonIgnore;
import hh.getData.guideline.Licence.LicenceDto;
import hh.getData.guideline.WorkDomain.WorkDomain;
import hh.getData.guideline.WorkDomain.WorkDomainDto;
import hh.getData.guideline.enumeration.Status;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OfficeDto {
    @NotNull
    private long id;

    @NotBlank
    @NotNull
    private String name;

    @NotBlank
    @NotNull
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

    @NotBlank
    @NotNull
    private String phone_number;

    private Status status = Status.ACTIVE ;

    @JsonIgnore
    private List<LicenceDto> licenceDto = new ArrayList<>();

    private List<WorkDomain> workDomainDto = new ArrayList<>();

    public static OfficeDto from(Office office) {
        OfficeDto officeDto = new OfficeDto();
        officeDto.setId(office.getId());
        officeDto.setName(office.getName());
        officeDto.setDescription(office.getDescription());
        officeDto.setAddress(office.getAddress());
        officeDto.setRequired_actions(office.getRequired_actions());
        officeDto.setNotes(office.getNotes());
        officeDto.setLogo(office.getLogo());
        officeDto.setWebsite_url(office.getWebsite_url());
        officeDto.setFacebook_account(office.getFacebook_account());
        officeDto.setLinkedin_account(office.getLinkedin_account());
        officeDto.setEmail(office.getEmail());
        officeDto.setPhone_number(office.getPhone_number());
        officeDto.setStatus(office.getStatus());
        officeDto.setLicenceDto(office.getLicences().stream().map(LicenceDto::from).collect(Collectors.toList()));
        officeDto.setWorkDomainDto(office.getWorkDomains().stream().collect(Collectors.toList()));
        return officeDto;
    }

}