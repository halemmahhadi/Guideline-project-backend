package hh.getData.guideline.Image;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import hh.getData.guideline.AboutUs.AboutUs;
import hh.getData.guideline.Donors.Donors;
import hh.getData.guideline.Funder.Funder;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Image {

    @Id
    @GeneratedValue
    Long id;


    @Lob
    @Column(length=1000)
    byte[] content;

    @OneToOne(mappedBy = "image")
    @JsonBackReference
    private Donors donors;

    @OneToOne(mappedBy = "image")
    @JsonBackReference
    private Funder funder;

    @OneToOne(mappedBy = "image")
    @JsonBackReference
    private AboutUs aboutUs;

}
