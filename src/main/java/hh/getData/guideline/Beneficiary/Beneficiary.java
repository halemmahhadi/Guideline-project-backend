package hh.getData.guideline.Beneficiary;

import com.fasterxml.jackson.annotation.JsonBackReference;
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
@SequenceGenerator(name = "seq", initialValue = 1, allocationSize = 200)
public class Beneficiary {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq")
    private Long id;

    @Column(name = "bene_name", nullable = false)
    private String beneName;

    @Column(name = "email")
    private String email;

    @Column(name = "phone_number", nullable = false)
    private String phoneNumber;

    @Column(name = "name_project")
    private String nameProject;

    @Column(name = "status", nullable = false)
    @Enumerated(EnumType.ORDINAL)
    private Status status;

    @ManyToOne()
    @JoinColumn(name = "workDomainID")
    @JsonBackReference
    private WorkDomain workDomains;

    public static Beneficiary from(BeneficiaryDto beneficiaryDto) {
        Beneficiary beneficiary = new Beneficiary();
        beneficiary.setBeneName(beneficiaryDto.getBeneName());
        beneficiary.setEmail(beneficiaryDto.getEmail());
        beneficiary.setPhoneNumber(beneficiaryDto.getPhoneNumber());
        beneficiary.setNameProject(beneficiaryDto.getNameProject());
        beneficiary.setStatus(beneficiaryDto.getStatus());
        beneficiary.setWorkDomains(beneficiaryDto.getWorkDomains());
        return beneficiary;
    }


}
