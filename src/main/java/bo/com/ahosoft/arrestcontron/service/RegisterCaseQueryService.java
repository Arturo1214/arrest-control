package bo.com.ahosoft.arrestcontron.service;

import bo.com.ahosoft.arrestcontron.domain.*;
import bo.com.ahosoft.arrestcontron.repository.RegisterCaseRepository;
import bo.com.ahosoft.arrestcontron.service.dto.RegisterCaseCriteria;
import bo.com.ahosoft.arrestcontron.service.dto.RegisterCaseDTO;
import bo.com.ahosoft.arrestcontron.service.mapper.RegisterCaseMapper;
import io.github.jhipster.service.QueryService;
import lombok.RequiredArgsConstructor;
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
 * Service for executing complex queries for {@link RegisterCase} entities in the database.
 * The main input is a {@link RegisterCaseCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link RegisterCaseDTO} or a {@link Page} of {@link RegisterCaseDTO} which fulfills the criteria.
 */
@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class RegisterCaseQueryService extends QueryService<RegisterCase> {

    private final Logger log = LoggerFactory.getLogger(RegisterCaseQueryService.class);

    private final RegisterCaseRepository registerCaseRepository;


    /**
     * Return a {@link List} of {@link RegisterCaseDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<RegisterCase> findByCriteria(RegisterCaseCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<RegisterCase> specification = createSpecification(criteria);
        return registerCaseRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link RegisterCaseDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<RegisterCase> findByCriteria(RegisterCaseCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<RegisterCase> specification = createSpecification(criteria);
        return registerCaseRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(RegisterCaseCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<RegisterCase> specification = createSpecification(criteria);
        return registerCaseRepository.count(specification);
    }

    /**
     * Function to convert {@link RegisterCaseCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<RegisterCase> createSpecification(RegisterCaseCriteria criteria) {
        Specification<RegisterCase> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), RegisterCase_.id));
            }
            if (criteria.getRegistrationDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getRegistrationDate(), RegisterCase_.registrationDate));
            }
            if (criteria.getAddress() != null) {
                specification = specification.and(buildStringSpecification(criteria.getAddress(), RegisterCase_.address));
            }
            if (criteria.getInformer() != null) {
                specification = specification.and(buildStringSpecification(criteria.getInformer(), RegisterCase_.informer));
            }
            if (criteria.getPhone() != null) {
                specification = specification.and(buildStringSpecification(criteria.getPhone(), RegisterCase_.phone));
            }
            if (criteria.getDescription() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDescription(), RegisterCase_.description));
            }
            if (criteria.getState() != null) {
                specification = specification.and(buildSpecification(criteria.getState(), RegisterCase_.state));
            }
            if (criteria.getZone() != null) {
                specification = specification.and(buildStringSpecification(criteria.getZone(), RegisterCase_.zone));
            }
            if (criteria.getAcronymPatrol() != null) {
                specification = specification.and(buildStringSpecification(criteria.getAcronymPatrol(), RegisterCase_.acronymPatrol));
            }
            if (criteria.getPatrolLeader() != null) {
                specification = specification.and(buildStringSpecification(criteria.getPatrolLeader(), RegisterCase_.patrolLeader));
            }
            if (criteria.getStateCase() != null) {
                specification = specification.and(buildSpecification(criteria.getStateCase(), RegisterCase_.stateCase));
            }
            if (criteria.getDescriptionCompletion() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDescriptionCompletion(), RegisterCase_.descriptionCompletion));
            }
            if (criteria.getLatitude() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getLatitude(), RegisterCase_.latitude));
            }
            if (criteria.getLongitude() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getLongitude(), RegisterCase_.longitude));
            }
            if (criteria.getReceptionistId() != null) {
                specification = specification.and(buildSpecification(criteria.getReceptionistId(),
                    root -> root.join(RegisterCase_.receptionist, JoinType.LEFT).get(User_.id)));
            }
            if (criteria.getUnitId() != null) {
                specification = specification.and(buildSpecification(criteria.getUnitId(),
                    root -> root.join(RegisterCase_.unit, JoinType.LEFT).get(Unit_.id)));
            }
            if (criteria.getDispatcherId() != null) {
                specification = specification.and(buildSpecification(criteria.getDispatcherId(),
                    root -> root.join(RegisterCase_.dispatcher, JoinType.LEFT).get(User_.id)));
            }
            if (criteria.getTypeCaseId() != null) {
                specification = specification.and(buildSpecification(criteria.getTypeCaseId(),
                    root -> root.join(RegisterCase_.typeCase, JoinType.LEFT).get(TypeCase_.id)));
            }
        }
        return specification;
    }
}
