package hh.getData.guideline.Donors;


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

    @Column(name = "donor_name", nullable = false)
    private String donor_name;



    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "image_id", referencedColumnName = "id", nullable = false)
    @JsonManagedReference
    private Image logo;

    @Column(name = "about_donor", nullable = false)
    private String about_donor;

    @Column(name = "status", nullable = true)
    private Status status ;



    public static Donors from(DonorsDto donorsDto){

        Donors donors = new Donors();
        donors.setAbout_donor(donorsDto.getAbout_donor());
        donors.setLogo(donorsDto.getLogo());
        donors.setDonor_name(donorsDto.getDonor_name());
        donors.setStatus(donorsDto.getStatus());
        return donors;
    }

    public static Donors from(DonorsUpdateDto donorsDto){
        Donors donors = new Donors();
        donors.setAbout_donor(donorsDto.getAbout_donor());
        donors.setLogo(donorsDto.getLogo());
        donors.setDonor_name(donorsDto.getDonor_name());
        return donors;
    }
}
