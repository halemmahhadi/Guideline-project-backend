package hh.getData.guideline.Consultant;


import org.springframework.data.jpa.repository.JpaRepository;

public interface ConsultantRepository extends JpaRepository<Consultant, Long> {

    Consultant findByConsName(String name);
}