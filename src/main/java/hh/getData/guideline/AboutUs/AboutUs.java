package hh.getData.guideline.AboutUs;

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
public class AboutUs {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private  long about_id;

    @OneToOne(fetch = FetchType.EAGER, cascade= {CascadeType.PERSIST,CascadeType.MERGE,CascadeType.DETACH})
    @JoinColumn(name = "image_id", referencedColumnName = "id")
    @JsonManagedReference
    private Image image;

    @Column(name = "about_app", nullable = false)
    private String about_app;

    @Column(name = "our_services", nullable = false)
    private String our_services;

    @Column(name = "email", nullable = false)
    private String email;

    @Column(name = "phone_number", nullable = false)
    private String phone_number;

    @Column(name = "status")
    private Status status;

    public static AboutUs from(AboutUsDto aboutUsDto){

        AboutUs aboutUs = new AboutUs();
        aboutUs.setAbout_app(aboutUsDto.getAbout_app());
        aboutUs.setImage(aboutUsDto.getImage());
        aboutUs.setEmail(aboutUsDto.getEmail());
        aboutUs.setPhone_number(aboutUsDto.getPhone_number());
        aboutUs.setOur_services(aboutUsDto.getOur_services());
        aboutUs.setStatus(aboutUsDto.getStatus());
        return aboutUs;
    }
}
