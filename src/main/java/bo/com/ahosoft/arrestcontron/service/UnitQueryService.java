package bo.com.ahosoft.arrestcontron.service;

import java.util.List;

import javax.persistence.criteria.JoinType;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.github.jhipster.service.QueryService;

import bo.com.ahosoft.arrestcontron.domain.Unit;
import bo.com.ahosoft.arrestcontron.domain.*; // for static metamodels
import bo.com.ahosoft.arrestcontron.repository.UnitRepository;
import bo.com.ahosoft.arrestcontron.service.dto.UnitCriteria;
import bo.com.ahosoft.arrestcontron.service.dto.UnitDTO;
import bo.com.ahosoft.arrestcontron.service.mapper.UnitMapper;

/**
 * Service for executing complex queries for {@link Unit} entities in the database.
 * The main input is a {@link UnitCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link UnitDTO} or a {@link Page} of {@link UnitDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class UnitQueryService extends QueryService<Unit> {

    private final Logger log = LoggerFactory.getLogger(UnitQueryService.class);

    private final UnitRepository unitRepository;

    private final UnitMapper unitMapper;

    public UnitQueryService(UnitRepository unitRepository, UnitMapper unitMapper) {
        this.unitRepository = unitRepository;
        this.unitMapper = unitMapper;
    }

    /**
     * Return a {@link List} of {@link UnitDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<Unit> findByCriteria(UnitCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Unit> specification = createSpecification(criteria);
        return unitRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link UnitDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<Unit> findByCriteria(UnitCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Unit> specification = createSpecification(criteria);
        return unitRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(UnitCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Unit> specification = createSpecification(criteria);
        return unitRepository.count(specification);
    }

    /**
     * Function to convert {@link UnitCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Unit> createSpecification(UnitCriteria criteria) {
        Specification<Unit> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Unit_.id));
            }
            if (criteria.getName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getName(), Unit_.name));
            }
        }
        return specification;
    }
}
