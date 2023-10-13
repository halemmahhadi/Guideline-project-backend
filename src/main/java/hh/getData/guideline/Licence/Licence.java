package hh.getData.guideline.Licence;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import hh.getData.guideline.Department.Department;
import hh.getData.guideline.Office.Office;
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
public class Licence {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq")
    private int id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "description", nullable = false)
    private String description;

    @Column(name = "required_document", nullable = false)
    private String requiredDocument;

    @Column(name = "procedures", nullable = false)
    private String procedures;

    @Column(name = "issuing_authority", nullable = false)
    private String issuingAuthority;

    @Column(name = "fees", nullable = false)
    private String fees;

    @Column(name = "penalties", nullable = false)
    private String penalties;

    @Column(name = "notes", columnDefinition = "TEXT", nullable = false)
    private String notes;

    @Column(name = "status", nullable = false)
    @Enumerated(EnumType.ORDINAL)
    private Status status  ;

    @ManyToOne
    @JoinColumn(name = "office_id")
    @JsonBackReference
    private Office office;

    @ManyToMany(cascade = {CascadeType.PERSIST,CascadeType.MERGE,CascadeType.DETACH}, fetch = FetchType.EAGER)
    @JoinTable(name = "department_licence",
            joinColumns = @JoinColumn(name = "department_id "),
            inverseJoinColumns = @JoinColumn(name = "licence_id"))
    @JsonBackReference
    private List<Department> departments = new ArrayList<>();



    public static Licence from(LicenceDto licenceDto) {
        Licence licence = new Licence();
        licence.setName(licenceDto.getName());
        licence.setDescription(licenceDto.getDescription());
        licence.setRequiredDocument(licenceDto.getRequiredDocument());
        licence.setProcedures(licenceDto.getProcedures());
        licence.setIssuingAuthority(licenceDto.getIssuingAuthority());
        licence.setFees(licenceDto.getFees());
        licence.setPenalties(licenceDto.getPenalties());
        licence.setNotes(licenceDto.getNotes());
        licence.setStatus(licenceDto.getStatus());
        licence.setOffice(licenceDto.getOffice());
        return licence;
    }



}

