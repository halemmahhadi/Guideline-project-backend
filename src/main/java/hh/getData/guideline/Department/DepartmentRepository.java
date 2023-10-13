package hh.getData.guideline.Department;

import hh.getData.guideline.SubDomain.SubDomain;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DepartmentRepository extends JpaRepository<Department, Long> {

    Department findByDepartmentName(String name);
}
