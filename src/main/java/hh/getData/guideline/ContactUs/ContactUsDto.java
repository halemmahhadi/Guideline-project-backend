package hh.getData.guideline.ContactUs;

import hh.getData.guideline.enumeration.Status;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ContactUsDto {
    private Long id;

    @NotBlank
    @NotNull
    private String name;

    private String email;

    private String phoneNumber;

    private String message;

    private Status status = Status.ACTIVE;

    public static ContactUsDto from(ContactUs contactUs) {
        ContactUsDto contactUsDto = new ContactUsDto();
        contactUsDto.setId(contactUs.getId());
        contactUsDto.setName(contactUs.getName());
        contactUsDto.setEmail(contactUs.getEmail());
        contactUsDto.setPhoneNumber(contactUs.getPhoneNumber());
        contactUsDto.setMessage(contactUs.getMessage());
        contactUsDto.setStatus(contactUs.getStatus());
        return contactUsDto;
    }
}
