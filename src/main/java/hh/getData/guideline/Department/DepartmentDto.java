package hh.getData.guideline.Department;

import hh.getData.guideline.Licence.Licence;
import hh.getData.guideline.Licence.LicenceDto;
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
public class DepartmentDto {

    @NotNull
    private long id;

    @NotBlank
    @NotNull
    private String departmentName;

    @NotBlank
    @NotNull
    private String description;

    private Status status = Status.ACTIVE ;

    private List<LicenceDto> licences = new ArrayList<>();

    public static DepartmentDto from(Department department) {
        DepartmentDto departmentDto = new DepartmentDto();
        departmentDto.setId(department.getId());
        departmentDto.setDepartmentName(department.getDepartmentName());
        departmentDto.setDescription(department.getDescription());
        departmentDto.setStatus(department.getStatus());
        departmentDto.setLicences(department.getLicences().stream().map(LicenceDto::from).collect(Collectors.toList()));
        return departmentDto;
    }
}
