package hh.getData.guideline.SubDomain;


import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
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
@SequenceGenerator(name = "seq", initialValue = 1)
public class SubDomain {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq")
    private int id;

    @Column(name = "subDomainName", nullable = false)
    private String subDomainName;

    @Column(name = "subDescription", nullable = false)
    private String subDescription;


    @Column(name = "status", nullable = false, columnDefinition = "int default 1")
    @Enumerated(EnumType.ORDINAL)
    private Status status  ;

    @ManyToOne()
    @JoinColumn(name = "workDomainID")
    @JsonBackReference
    private WorkDomain workDomain;

    public static SubDomain from(SubDomainDto subDomainDto) {
        SubDomain subDomain = new SubDomain();
        subDomain.setSubDomainName(subDomainDto.getSubDomainName());
        subDomain.setSubDescription(subDomainDto.getSubDescription());
        subDomain.setStatus(subDomainDto.getStatus());
        subDomain.setWorkDomain(subDomainDto.getWorkDomain());
        return subDomain;
    }
}
