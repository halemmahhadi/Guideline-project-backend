package hh.getData.guideline.Funder;

import org.springframework.data.jpa.repository.JpaRepository;

public interface FunderRepository extends JpaRepository<Funder, Long> {

    Funder findByFundName(String name);
}