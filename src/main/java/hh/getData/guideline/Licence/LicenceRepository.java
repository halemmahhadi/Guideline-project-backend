package hh.getData.guideline.Licence;

import hh.getData.guideline.Office.Office;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LicenceRepository extends JpaRepository<Licence,Long> {

    Licence findByName(String name);

}
