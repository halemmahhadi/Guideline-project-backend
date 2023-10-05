package hh.getData.guideline.Office;

import hh.getData.guideline.Donors.Donors;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OfficeRepository extends JpaRepository<Office,Long> {

    Office findByName(String name);
}
