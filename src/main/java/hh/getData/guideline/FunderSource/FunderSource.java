package hh.getData.guideline.FunderSource;

import com.fasterxml.jackson.annotation.JsonBackReference;
import hh.getData.guideline.Funder.Funder;
import hh.getData.guideline.WorkDomain.WorkDomain;
import hh.getData.guideline.enumeration.Status;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class FunderSource {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq")
    private Long id;

    @Column(name = "fund_type_name", nullable = false)
    private String fundTypeName;

    @Column(name = "description")
    private String description;

    @Column(name = "status", nullable = false)
    @Enumerated(EnumType.ORDINAL)
    private Status status;

    @ManyToOne()
    @JoinColumn(name = "funderId")
    @JsonBackReference
    private Funder funder;

    public static FunderSource from(FunderSourceDto funderSourceDto) {
        FunderSource funderSource = new FunderSource();
        funderSource.setFundTypeName(funderSourceDto.getFundTypeName());
        funderSource.setDescription(funderSourceDto.getDescription());
        funderSource.setStatus(funderSourceDto.getStatus());
        funderSource.setFunder(funderSourceDto.getFunder());
        return funderSource;
    }
}
