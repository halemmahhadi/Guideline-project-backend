package hh.getData.guideline.Donors;

import hh.getData.guideline.Office.Office;
import org.springframework.data.jpa.repository.JpaRepository;


public interface DonorsRepository extends JpaRepository<Donors,Long> {

    Donors findByDonorName(String name);

}
