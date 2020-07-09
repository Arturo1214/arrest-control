package bo.com.ahosoft.arrestcontron.repository;

import bo.com.ahosoft.arrestcontron.domain.RegisterCase;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Spring Data  repository for the RegisterCase entity.
 */
@SuppressWarnings("unused")
@Repository
public interface RegisterCaseRepository extends JpaRepository<RegisterCase, Long>, JpaSpecificationExecutor<RegisterCase> {

    @Query("select registerCase from RegisterCase registerCase where registerCase.receptionist.login = ?#{principal.username}")
    List<RegisterCase> findByReceptionistIsCurrentUser();

}
