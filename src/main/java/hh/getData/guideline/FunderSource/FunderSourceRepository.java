package hh.getData.guideline.FunderSource;

import org.springframework.data.jpa.repository.JpaRepository;

public interface FunderSourceRepository extends JpaRepository<FunderSource, Long> {

    FunderSource findByFundTypeName(String name);
}