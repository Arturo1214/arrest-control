package bo.com.ahosoft.arrestcontron.repository;

import bo.com.ahosoft.arrestcontron.domain.SystemParameter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the SystemParameter entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SystemParameterRepository extends JpaRepository<SystemParameter, String> {

}
