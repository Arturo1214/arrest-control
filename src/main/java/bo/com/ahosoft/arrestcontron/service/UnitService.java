package bo.com.ahosoft.arrestcontron.service;

import bo.com.ahosoft.arrestcontron.domain.Unit;
import bo.com.ahosoft.arrestcontron.repository.UnitRepository;
import bo.com.ahosoft.arrestcontron.service.dto.UnitDTO;
import bo.com.ahosoft.arrestcontron.service.mapper.UnitMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link Unit}.
 */
@Service
@Transactional
public class UnitService {

    private final Logger log = LoggerFactory.getLogger(UnitService.class);

    private final UnitRepository unitRepository;

    private final UnitMapper unitMapper;

    public UnitService(UnitRepository unitRepository, UnitMapper unitMapper) {
        this.unitRepository = unitRepository;
        this.unitMapper = unitMapper;
    }

    /**
     * Save a unit.
     *
     * @param unitDTO the entity to save.
     * @return the persisted entity.
     */
    public Unit save(UnitDTO unitDTO) {
        log.debug("Request to save Unit : {}", unitDTO);
        Unit unit = unitMapper.toEntity(unitDTO);
        return unitRepository.save(unit);
    }

    /**
     * Get all the units.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<Unit> findAll(Pageable pageable) {
        log.debug("Request to get all Units");
        return unitRepository.findAll(pageable);
    }

    /**
     * Get one unit by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<Unit> findOne(Long id) {
        log.debug("Request to get Unit : {}", id);
        return unitRepository.findById(id);
    }

    /**
     * Delete the unit by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Unit : {}", id);
        unitRepository.deleteById(id);
    }
}
