package hh.getData.guideline.WorkDomain;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface WorkDomainRepository extends JpaRepository <WorkDomain, Long> {

    WorkDomain findByName(String name);

    //List<WorkDomain> findWorkDomainsByOffices(Long officeId);
}
