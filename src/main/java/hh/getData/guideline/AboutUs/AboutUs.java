package hh.getData.guideline.AboutUs;

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

    @Column(name = "logo", nullable = false)
    private String logo;

    @Column(name = "about_app", nullable = false)
    private String about_app;

    @Column(name = "our_services", nullable = false)
    private String our_services;

    @Column(name = "email", nullable = false)
    private String email;

    @Column(name = "phone_number", nullable = false)
    private String phone_number;

    @Column(name = "status", nullable = true)
    private boolean status;
}
