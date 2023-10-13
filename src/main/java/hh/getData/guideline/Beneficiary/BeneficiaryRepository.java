package hh.getData.guideline.Beneficiary;

import hh.getData.guideline.Department.Department;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BeneficiaryRepository extends JpaRepository<Beneficiary, Long> {

    Beneficiary findByBeneName(String name);
}
