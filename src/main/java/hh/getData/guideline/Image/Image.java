package hh.getData.guideline.Image;

import com.fasterxml.jackson.annotation.JsonBackReference;
import hh.getData.guideline.Donors.Donors;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Image {

    @Id
    @GeneratedValue
    Long id;


    @Lob
    @Column(length=1000)
    byte[] content;

    @OneToOne(mappedBy = "logo")
    @JsonBackReference
    private Donors donors;

}
