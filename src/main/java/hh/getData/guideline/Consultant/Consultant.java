package hh.getData.guideline.Consultant;

import hh.getData.guideline.enumeration.Status;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@SequenceGenerator(name = "seq", initialValue = 1)
public class Consultant {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq")
    private Long id;

    @Column(name = "cons_name", nullable = false)
    private String consName;

    @Column(name = "description")
    private String description;

    @Column(name = "facebook_account")
    private String facebookAccount;

    @Column(name = "linkedin_account")
    private String linkedinAccount;

    @Column(name = "email")
    private String email;

    @Column(name = "phone_number", nullable = false)
    private String phoneNumber;

    @Column(name = "status", nullable = false)
    @Enumerated(EnumType.ORDINAL)
    private Status status;


    public static Consultant from(ConsultantDto consultantDto) {
        Consultant consultant = new Consultant();
        consultant.setConsName(consultantDto.getConsName());
        consultant.setDescription(consultantDto.getDescription());
        consultant.setFacebookAccount(consultantDto.getFacebookAccount());
        consultant.setLinkedinAccount(consultantDto.getLinkedinAccount());
        consultant.setEmail(consultantDto.getEmail());
        consultant.setPhoneNumber(consultantDto.getPhoneNumber());
        consultant.setStatus(consultantDto.getStatus());
        return consultant;
    }
}
