package bo.com.ahosoft.arrestcontron.repository;

import bo.com.ahosoft.arrestcontron.domain.Authority;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Spring Data JPA repository for the {@link Authority} entity.
 */
public interface AuthorityRepository extends JpaRepository<Authority, String> {
}
