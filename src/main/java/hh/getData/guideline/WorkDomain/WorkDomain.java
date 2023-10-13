package hh.getData.guideline.WorkDomain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import hh.getData.guideline.Beneficiary.Beneficiary;
import hh.getData.guideline.Office.Office;
import hh.getData.guideline.SubDomain.SubDomain;
import hh.getData.guideline.enumeration.Status;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@SequenceGenerator(name = "seq", initialValue = 1)
public class WorkDomain {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq")
    private int id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "description", nullable = false)
    private String description;


    @Column(name = "status", nullable = false, columnDefinition = "int default 1")
    @Enumerated(EnumType.ORDINAL)
    private Status status  ;

    @OneToMany(mappedBy = "workDomain",  cascade= {CascadeType.PERSIST,CascadeType.MERGE,CascadeType.DETACH} , fetch = FetchType.EAGER)
    @JsonManagedReference
    private List<SubDomain> subDomains = new ArrayList<>();

    @ManyToMany(fetch = FetchType.EAGER,cascade = {CascadeType.PERSIST,CascadeType.MERGE,CascadeType.DETACH})
    @JoinTable(
            name = "workDomain_office",
            joinColumns = @JoinColumn(name = "workDomainId"),
            inverseJoinColumns = @JoinColumn(name = "officeId"))
    @JsonBackReference
    private List<Office> offices =new ArrayList<>();


    @OneToMany(mappedBy = "workDomains", fetch = FetchType.EAGER, cascade= {CascadeType.PERSIST,CascadeType.MERGE,CascadeType.DETACH})
    @JsonManagedReference
    private List<Beneficiary> beneficiaries = new ArrayList<>();

    public static WorkDomain from(WorkDomainDto workDomainDto) {
        WorkDomain workDomain = new WorkDomain();
        workDomain.setName(workDomainDto.getName());
        workDomain.setDescription(workDomainDto.getDescription());
        workDomain.setStatus(workDomainDto.getStatus());
        return workDomain;
    }

    public void addSubToWorkDomain(SubDomain subDomain) {subDomains.add(subDomain);}

    public void removeSubFromWorkDomain(SubDomain subDomain){ subDomains.remove(subDomain); }


    public void assignWorkDomainToOffice(Office office) {
        offices.add(office);
    }

    public void removeWorkDomainFromOffice(Office office) {
        offices.remove(office);
    }

    public void addBeneficiary(Beneficiary beneficiary) {beneficiaries.add(beneficiary);}

    public void removeBeneficiary(Beneficiary beneficiary){ beneficiaries.remove(beneficiary); }
}
