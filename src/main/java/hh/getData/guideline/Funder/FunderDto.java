package hh.getData.guideline.Funder;


import hh.getData.guideline.FunderSource.FunderSourceDto;
import hh.getData.guideline.Image.Image;
import hh.getData.guideline.enumeration.Status;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FunderDto {
    private Long id;

    @NotBlank
    @NotNull
    private String fundName;

    private String description;

    private Image image;

    private String email;

    @NotBlank
    @NotNull
    private String phoneNumber;

    private String requiredDocuments;

    private String advantages;

    private String conditions;

    private String products;

    private String fundingLimit;

    private String guarantees;

    private Status status = Status.ACTIVE;

    private List<FunderSourceDto> funderSourceDto ;

    public static FunderDto from(Funder funder) {
        FunderDto funderDto = new FunderDto();
        funderDto.setId(funder.getId());
        funderDto.setFundName(funder.getFundName());
        funderDto.setDescription(funder.getDescription());
        funderDto.setImage(funder.getImage());
        funderDto.setEmail(funder.getEmail());
        funderDto.setPhoneNumber(funder.getPhoneNumber());
        funderDto.setRequiredDocuments(funder.getRequiredDocuments());
        funderDto.setAdvantages(funder.getAdvantages());
        funderDto.setConditions(funder.getConditions());
        funderDto.setProducts(funder.getProducts());
        funderDto.setFundingLimit(funder.getFundingLimit());
        funderDto.setGuarantees(funder.getGuarantees());
        funderDto.setStatus(funder.getStatus());
        funderDto.setFunderSourceDto(funder.getFunderSources().stream().map(FunderSourceDto::from).collect(Collectors.toList()));
        return funderDto;
    }
}
