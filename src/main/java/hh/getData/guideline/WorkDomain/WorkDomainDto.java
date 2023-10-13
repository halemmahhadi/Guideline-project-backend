package hh.getData.guideline.WorkDomain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import hh.getData.guideline.Beneficiary.BeneficiaryDto;
import hh.getData.guideline.Office.OfficeDto;
import hh.getData.guideline.SubDomain.SubDomainDto;
import hh.getData.guideline.enumeration.Status;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
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
public class WorkDomainDto {

    @NotNull
    private long id;

    @NotBlank
    @NotNull
    private String name;

    @NotBlank
    @NotNull
    private String description;

    private Status status = Status.ACTIVE ;

    //@JsonIgnore
    private List<SubDomainDto> subDomainDtoList = new ArrayList<>();

    //@JsonIgnore
    private List<BeneficiaryDto> beneficiaryDto = new ArrayList<>();

    private List<OfficeDto> officeDto ;

    public static WorkDomainDto from(WorkDomain workDomain) {
        WorkDomainDto workDomainDto = new WorkDomainDto();
        workDomainDto.setId(workDomain.getId());
        workDomainDto.setName(workDomain.getName());
        workDomainDto.setDescription(workDomain.getDescription());
        workDomainDto.setStatus(workDomain.getStatus());
        workDomainDto.setSubDomainDtoList(workDomain.getSubDomains().stream().map(SubDomainDto::from).collect(Collectors.toList()));
        workDomainDto.setOfficeDto(workDomain.getOffices().stream().map(OfficeDto::from).collect(Collectors.toList()));
        workDomainDto.setBeneficiaryDto(workDomain.getBeneficiaries().stream().map(BeneficiaryDto::from).collect(Collectors.toList()));
        return workDomainDto;
    }
}
