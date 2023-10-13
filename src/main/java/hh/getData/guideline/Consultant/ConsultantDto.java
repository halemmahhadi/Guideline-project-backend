package hh.getData.guideline.Consultant;

import hh.getData.guideline.enumeration.Status;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ConsultantDto {
    private Long id;

    @NotBlank
    @NotNull
    private String consName;

    private String description;

    private String facebookAccount;

    private String linkedinAccount;

    private String email;

    @NotBlank
    @NotNull
    private String phoneNumber;

    private Status status = Status.ACTIVE;

    public static ConsultantDto from(Consultant consultant) {
        ConsultantDto consultantDto = new ConsultantDto();
        consultantDto.setId(consultant.getId());
        consultantDto.setConsName(consultant.getConsName());
        consultantDto.setDescription(consultant.getDescription());
        consultantDto.setFacebookAccount(consultant.getFacebookAccount());
        consultantDto.setLinkedinAccount(consultant.getLinkedinAccount());
        consultantDto.setEmail(consultant.getEmail());
        consultantDto.setPhoneNumber(consultant.getPhoneNumber());
        consultantDto.setStatus(consultant.getStatus());
        return consultantDto;
    }
}
