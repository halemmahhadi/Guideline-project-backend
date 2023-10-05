package hh.getData.guideline.Office;

import hh.getData.guideline.enumeration.Status;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Office {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
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

    @Column(name = "status", nullable = false)
    private Status status;


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

    public static Office from(OfficeUpdateDto officeUpdateDto) {
        Office office = new Office();
        office.setName(officeUpdateDto.getName());
        office.setDescription(officeUpdateDto.getDescription());
        office.setAddress(officeUpdateDto.getAddress());
        office.setRequired_actions(officeUpdateDto.getRequired_actions());
        office.setNotes(officeUpdateDto.getNotes());
        office.setLogo(officeUpdateDto.getLogo());
        office.setWebsite_url(officeUpdateDto.getWebsite_url());
        office.setFacebook_account(officeUpdateDto.getFacebook_account());
        office.setLinkedin_account(officeUpdateDto.getLinkedin_account());
        office.setEmail(officeUpdateDto.getEmail());
        office.setPhone_number(officeUpdateDto.getPhone_number());
        return office;
    }

}
