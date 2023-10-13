package hh.getData.guideline.FunderSource;

import com.fasterxml.jackson.annotation.JsonIgnore;
import hh.getData.guideline.Funder.Funder;
import hh.getData.guideline.enumeration.Status;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FunderSourceDto {
    private Long id;

    @NotBlank
    @NotNull
    private String fundTypeName;

    private String description;

    private Status status = Status.ACTIVE;

    @JsonIgnore
    private Funder funder;

    public static FunderSourceDto from(FunderSource funderSource) {
        FunderSourceDto funderSourceDto = new FunderSourceDto();
        funderSourceDto.setId(funderSource.getId());
        funderSourceDto.setFundTypeName(funderSource.getFundTypeName());
        funderSourceDto.setDescription(funderSource.getDescription());
        funderSourceDto.setStatus(funderSource.getStatus());
        funderSourceDto.setFunder(funderSource.getFunder());
        return funderSourceDto;
    }
}
