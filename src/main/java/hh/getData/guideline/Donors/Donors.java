package hh.getData.guideline.Donors;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import hh.getData.guideline.Image.Image;
import hh.getData.guideline.enumeration.Status;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Donors {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private  long donor_id;

    @Column(name = "donorName", nullable = false)
    private String donorName;

    @OneToOne(fetch = FetchType.EAGER, cascade= {CascadeType.PERSIST,CascadeType.MERGE,CascadeType.DETACH})
    @JoinColumn(name = "image_id", referencedColumnName = "id")
    @JsonManagedReference
    private Image image;

    @Column(name = "about_donor", nullable = false)
    private String about_donor;

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



    public static Donors from(DonorsDto donorsDto){

        Donors donors = new Donors();
        donors.setAbout_donor(donorsDto.getAbout_donor());
        donors.setDonorName(donorsDto.getDonorName());
        donors.setImage(donorsDto.getImage());
        donors.setFacebook_account(donorsDto.getFacebook_account());
        donors.setLinkedin_account(donorsDto.getLinkedin_account());
        donors.setEmail(donorsDto.getEmail());
        donors.setPhone_number(donorsDto.getPhone_number());
        donors.setStatus(donorsDto.getStatus());
        return donors;
    }

}
