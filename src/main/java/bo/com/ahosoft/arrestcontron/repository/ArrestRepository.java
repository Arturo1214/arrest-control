package bo.com.ahosoft.arrestcontron.repository;

import bo.com.ahosoft.arrestcontron.domain.Arrest;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the Arrest entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ArrestRepository extends JpaRepository<Arrest, Long>, JpaSpecificationExecutor<Arrest> {

}
