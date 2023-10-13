package hh.getData.guideline.Beneficiary;

import com.fasterxml.jackson.annotation.JsonIgnore;
import hh.getData.guideline.WorkDomain.WorkDomain;
import hh.getData.guideline.enumeration.Status;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BeneficiaryDto {

    private Long id;

    @NotBlank
    @NotNull
    private String beneName;

    private String email;

    @NotBlank
    @NotNull
    private String phoneNumber;

    private String nameProject;

    private Status status= Status.ACTIVE;

    @JsonIgnore
    private WorkDomain workDomains;

    public static BeneficiaryDto from(Beneficiary beneficiary) {
        BeneficiaryDto beneficiaryDto = new BeneficiaryDto();
        beneficiaryDto.setId(beneficiary.getId());
        beneficiaryDto.setBeneName(beneficiary.getBeneName());
        beneficiaryDto.setEmail(beneficiary.getEmail());
        beneficiaryDto.setPhoneNumber(beneficiary.getPhoneNumber());
        beneficiaryDto.setNameProject(beneficiary.getNameProject());
        beneficiaryDto.setStatus(beneficiary.getStatus());
        beneficiaryDto.setWorkDomains(beneficiary.getWorkDomains());
        return beneficiaryDto;
    }
}
