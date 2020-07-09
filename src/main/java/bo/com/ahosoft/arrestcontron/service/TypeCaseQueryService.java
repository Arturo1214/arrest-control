package bo.com.ahosoft.arrestcontron.service;

import bo.com.ahosoft.arrestcontron.domain.TypeCase;
import bo.com.ahosoft.arrestcontron.domain.TypeCase_;
import bo.com.ahosoft.arrestcontron.repository.TypeCaseRepository;
import bo.com.ahosoft.arrestcontron.service.dto.TypeCaseCriteria;
import bo.com.ahosoft.arrestcontron.service.dto.TypeCaseDTO;
import bo.com.ahosoft.arrestcontron.service.mapper.TypeCaseMapper;
import io.github.jhipster.service.QueryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Service for executing complex queries for {@link TypeCase} entities in the database.
 * The main input is a {@link TypeCaseCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link TypeCaseDTO} or a {@link Page} of {@link TypeCaseDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class TypeCaseQueryService extends QueryService<TypeCase> {

    private final Logger log = LoggerFactory.getLogger(TypeCaseQueryService.class);

    private final TypeCaseRepository typeCaseRepository;

    private final TypeCaseMapper typeCaseMapper;

    public TypeCaseQueryService(TypeCaseRepository typeCaseRepository, TypeCaseMapper typeCaseMapper) {
        this.typeCaseRepository = typeCaseRepository;
        this.typeCaseMapper = typeCaseMapper;
    }

    /**
     * Return a {@link List} of {@link TypeCaseDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<TypeCaseDTO> findByCriteria(TypeCaseCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<TypeCase> specification = createSpecification(criteria);
        return typeCaseMapper.toDto(typeCaseRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link TypeCaseDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<TypeCaseDTO> findByCriteria(TypeCaseCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<TypeCase> specification = createSpecification(criteria);
        return typeCaseRepository.findAll(specification, page)
            .map(typeCaseMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(TypeCaseCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<TypeCase> specification = createSpecification(criteria);
        return typeCaseRepository.count(specification);
    }

    /**
     * Function to convert {@link TypeCaseCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<TypeCase> createSpecification(TypeCaseCriteria criteria) {
        Specification<TypeCase> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), TypeCase_.id));
            }
            if (criteria.getName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getName(), TypeCase_.name));
            }
        }
        return specification;
    }
}
