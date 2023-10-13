package hh.getData.guideline.SubDomain;

import org.springframework.data.jpa.repository.JpaRepository;

public interface SubDomainRepository extends JpaRepository<SubDomain, Long> {

    SubDomain findBySubDomainName(String name);
}