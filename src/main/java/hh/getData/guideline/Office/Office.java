package hh.getData.guideline.Office;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import hh.getData.guideline.Licence.Licence;
import hh.getData.guideline.WorkDomain.WorkDomain;
import hh.getData.guideline.enumeration.Status;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Proxy;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@SequenceGenerator(name = "seq", initialValue = 1)
public class Office {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq")
    private int id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "description", nullable = false)
    private String description;

    @Column(name = "address", nullable = false)
    private String address;

    @Column(name = "required_actions", nullable = false)
    private String required_actions;

    @Column(name = "notes", columnDefinition = "TEXT", nullable = false)
    private String notes;

    @Column(name = "logo", nullable = false)
    private String logo;

    @Column(name = "website_url", nullable = false)
    private String website_url;

    @Column(name = "facebook_account", nullable = true)
    private String facebook_account;

    @Column(name = "linkedin_account", nullable = true)
    private String linkedin_account;

    @Column(name = "email", nullable = true)
    private String email;

    @Column(name = "phone_number", nullable = false)
    private String phone_number;

    @Column(name = "status", nullable = false, columnDefinition = "int default 1")
    @Enumerated(EnumType.ORDINAL)
    private Status status  ;

    @OneToMany(mappedBy = "office", fetch = FetchType.EAGER, cascade= {CascadeType.PERSIST,CascadeType.MERGE,CascadeType.DETACH})
    @JsonIgnore
    @JsonManagedReference
    private List<Licence> licences = new ArrayList<>();

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL,mappedBy = "offices")
    @JsonIgnore
    private List<WorkDomain> workDomains = new ArrayList<>();


    public static Office from(OfficeDto officeDto) {
        Office office = new Office();
        office.setName(officeDto.getName());
        office.setDescription(officeDto.getDescription());
        office.setAddress(officeDto.getAddress());
        office.setRequired_actions(officeDto.getRequired_actions());
        office.setNotes(officeDto.getNotes());
        office.setLogo(officeDto.getLogo());
        office.setWebsite_url(officeDto.getWebsite_url());
        office.setFacebook_account(officeDto.getFacebook_account());
        office.setLinkedin_account(officeDto.getLinkedin_account());
        office.setEmail(officeDto.getEmail());
        office.setPhone_number(officeDto.getPhone_number());
        office.setStatus(officeDto.getStatus());
        return office;
    }

    public void addLicenceToOffice(Licence licence){licences.add(licence);}

    public void removeLicenceFromOffice(Licence licence){ licences.remove(licence); }

    public void assignOfficeToWorkDomain(WorkDomain workDomain) {
        workDomains.add(workDomain);
    }

    public void removeOfficeFromWorkDomain(WorkDomain workDomain) {
        workDomains.remove(workDomain);
    }



}
