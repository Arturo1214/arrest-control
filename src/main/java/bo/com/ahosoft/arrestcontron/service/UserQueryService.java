package bo.com.ahosoft.arrestcontron.service;

import bo.com.ahosoft.arrestcontron.config.Constants;
import bo.com.ahosoft.arrestcontron.domain.*;
import bo.com.ahosoft.arrestcontron.repository.UserRepository;
import bo.com.ahosoft.arrestcontron.service.dto.UserCriteria;
import bo.com.ahosoft.arrestcontron.service.dto.UserDTO;
import bo.com.ahosoft.arrestcontron.service.mapper.UserMapper;
import io.github.jhipster.service.QueryService;
import io.github.jhipster.service.filter.StringFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.JoinType;
import java.util.List;

/**
 * Service for executing complex queries for {@link User} entities in the database.
 * The main input is a {@link UserCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link UserDTO} or a {@link Page} of {@link UserDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class UserQueryService extends QueryService<User> {

    private final Logger log = LoggerFactory.getLogger(UserQueryService.class);

    private final UserRepository userRepository;

    private final UserMapper userMapper;

    public UserQueryService(UserRepository userRepository, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }

    /**
     * Return a {@link List} of {@link UserDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<UserDTO> findByCriteria(UserCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<User> specification = createSpecification(criteria);
        return userMapper.usersToUserDTOs(userRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link UserDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<UserDTO> findByCriteria(UserCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<User> specification = createSpecification(criteria);
        return userRepository.findAll(specification, page).map(UserDTO::new);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(UserCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<User> specification = createSpecification(criteria);
        return userRepository.count(specification);
    }

    /**
     * Function to convert {@link UserCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<User> createSpecification(UserCriteria criteria) {
        Specification<User> specification = Specification.where(null);

        StringFilter loginFilter = new StringFilter();
        loginFilter.setNotEquals(Constants.ANONYMOUS_USER);
        specification = specification.and(buildStringSpecification(loginFilter, User_.login));

        if (criteria != null) {

            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), User_.id));
            }
            if (criteria.getLogin() != null) {
                specification = specification.and(buildStringSpecification(criteria.getLogin(), User_.login));
            }
            if (criteria.getFullName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getFullName(), User_.fullName));
            }
            if (criteria.getTelephone() != null) {
                specification = specification.and(buildStringSpecification(criteria.getTelephone(), User_.telephone));
            }

            if (criteria.getEmail() != null) {
                specification = specification.and(buildStringSpecification(criteria.getEmail(), User_.email));
            }
            if (criteria.getUnitId() != null) {
                specification = specification.and(buildSpecification(criteria.getUnitId(),
                    root -> root.join(User_.office, JoinType.LEFT)
                        .join(Office_.unit, JoinType.INNER)
                        .get(Unit_.id)));
            }
            if (criteria.getOfficeId() != null) {
                specification = specification.and(buildSpecification(criteria.getOfficeId(),
                    root -> root.join(User_.office, JoinType.LEFT).get(Office_.id)));
            }

        }
        return specification;
    }
}
