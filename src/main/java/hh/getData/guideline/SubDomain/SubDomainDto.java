package hh.getData.guideline.SubDomain;


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
public class SubDomainDto {
    @NotNull
    private long id;

    @NotBlank
    @NotNull
    private String subDomainName;

    @NotBlank
    @NotNull
    private String subDescription;

    private Status status = Status.ACTIVE ;

    @JsonIgnore
    private WorkDomain workDomain;

    public static SubDomainDto from(SubDomain subDomain) {
        SubDomainDto subDomainDto = new SubDomainDto();
        subDomainDto.setId(subDomain.getId());
        subDomainDto.setSubDomainName(subDomain.getSubDomainName());
        subDomainDto.setSubDescription(subDomain.getSubDescription());
        subDomainDto.setStatus(subDomain.getStatus());
        subDomainDto.setWorkDomain(subDomain.getWorkDomain());
        return subDomainDto;
    }
}
