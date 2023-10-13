package hh.getData.guideline.Licence;


import com.fasterxml.jackson.annotation.JsonIgnore;
import hh.getData.guideline.Department.Department;
import hh.getData.guideline.Office.Office;
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
public class LicenceDto {

    private long id;

    @NotBlank
    @NotNull
    private String name;

    @NotBlank
    @NotNull
    private String description;

    @NotBlank
    @NotNull
    private String requiredDocument;

    @NotBlank
    @NotNull
    private String procedures;

    @NotBlank
    @NotNull
    private String issuingAuthority;

    @NotBlank
    @NotNull
    private String fees;

    @NotBlank
    @NotNull
    private String penalties;

    private String notes;

    private Status status =Status.ACTIVE;

    @JsonIgnore
    private Office office;

    private List<Department> departmentDto = new ArrayList<>();

    public static LicenceDto from(Licence licence) {
        LicenceDto licenceDto = new LicenceDto();
        licenceDto.setId(licence.getId());
        licenceDto.setName(licence.getName());
        licenceDto.setDescription(licence.getDescription());
        licenceDto.setRequiredDocument(licence.getRequiredDocument());
        licenceDto.setProcedures(licence.getProcedures());
        licenceDto.setIssuingAuthority(licence.getIssuingAuthority());
        licenceDto.setFees(licence.getFees());
        licenceDto.setPenalties(licence.getPenalties());
        licenceDto.setNotes(licence.getNotes());
        licenceDto.setStatus(licence.getStatus());
        licenceDto.setOffice(licence.getOffice());
        licenceDto.setDepartmentDto(licence.getDepartments().stream().collect(Collectors.toList()));

        return licenceDto;
    }

}
