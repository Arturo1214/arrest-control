package bo.com.ahosoft.arrestcontron.repository;

import bo.com.ahosoft.arrestcontron.domain.TypeCase;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the TipeCase entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TypeCaseRepository extends JpaRepository<TypeCase, Long>, JpaSpecificationExecutor<TypeCase> {

}
