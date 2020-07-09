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

import bo.com.ahosoft.arrestcontron.domain.Office;
import bo.com.ahosoft.arrestcontron.domain.*; // for static metamodels
import bo.com.ahosoft.arrestcontron.repository.OfficeRepository;
import bo.com.ahosoft.arrestcontron.service.dto.OfficeCriteria;
import bo.com.ahosoft.arrestcontron.service.dto.OfficeDTO;
import bo.com.ahosoft.arrestcontron.service.mapper.OfficeMapper;

/**
 * Service for executing complex queries for {@link Office} entities in the database.
 * The main input is a {@link OfficeCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link OfficeDTO} or a {@link Page} of {@link OfficeDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class OfficeQueryService extends QueryService<Office> {

    private final Logger log = LoggerFactory.getLogger(OfficeQueryService.class);

    private final OfficeRepository officeRepository;

    private final OfficeMapper officeMapper;

    public OfficeQueryService(OfficeRepository officeRepository, OfficeMapper officeMapper) {
        this.officeRepository = officeRepository;
        this.officeMapper = officeMapper;
    }

    /**
     * Return a {@link List} of {@link OfficeDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<Office> findByCriteria(OfficeCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Office> specification = createSpecification(criteria);
        return officeRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link OfficeDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<Office> findByCriteria(OfficeCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Office> specification = createSpecification(criteria);
        return officeRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(OfficeCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Office> specification = createSpecification(criteria);
        return officeRepository.count(specification);
    }

    /**
     * Function to convert {@link OfficeCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Office> createSpecification(OfficeCriteria criteria) {
        Specification<Office> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Office_.id));
            }
            if (criteria.getName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getName(), Office_.name));
            }
            if (criteria.getUnitId() != null) {
                specification = specification.and(buildSpecification(criteria.getUnitId(),
                    root -> root.join(Office_.unit, JoinType.LEFT).get(Unit_.id)));
            }
        }
        return specification;
    }
}
