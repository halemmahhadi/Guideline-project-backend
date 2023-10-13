package hh.getData.guideline.ContactUs;

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
public class ContactUs {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq")
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "email")
    private String email;

    @Column(name = "phone_number")
    private String phoneNumber;

    @Column(name = "message")
    private String message;

    @Column(name = "status", nullable = false)
    @Enumerated(EnumType.ORDINAL)
    private Status status;

    public static ContactUs from(ContactUsDto contactUsDto) {
        ContactUs contactUs = new ContactUs();
        contactUs.setName(contactUsDto.getName());
        contactUs.setEmail(contactUsDto.getEmail());
        contactUs.setPhoneNumber(contactUsDto.getPhoneNumber());
        contactUs.setMessage(contactUsDto.getMessage());
        contactUs.setStatus(contactUsDto.getStatus());
        return contactUs;
    }
}
