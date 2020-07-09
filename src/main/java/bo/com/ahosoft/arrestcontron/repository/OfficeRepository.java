package bo.com.ahosoft.arrestcontron.repository;

import bo.com.ahosoft.arrestcontron.domain.Office;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Spring Data  repository for the Office entity.
 */
@SuppressWarnings("unused")
@Repository
public interface OfficeRepository extends JpaRepository<Office, Long>, JpaSpecificationExecutor<Office> {

    List<Office> findAllByUnitId(Long unitId);
}
