package hh.getData.guideline.ContactUs;


import org.springframework.data.jpa.repository.JpaRepository;

public interface ContactUsRepository extends JpaRepository<ContactUs, Long> {

    ContactUs findByName(String name);
}
