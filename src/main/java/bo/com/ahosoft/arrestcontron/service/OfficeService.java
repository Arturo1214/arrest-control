package bo.com.ahosoft.arrestcontron.service;

import bo.com.ahosoft.arrestcontron.domain.Office;
import bo.com.ahosoft.arrestcontron.repository.OfficeRepository;
import bo.com.ahosoft.arrestcontron.service.dto.OfficeDTO;
import bo.com.ahosoft.arrestcontron.service.mapper.OfficeMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link Office}.
 */
@Service
@Transactional
public class OfficeService {

    private final Logger log = LoggerFactory.getLogger(OfficeService.class);

    private final OfficeRepository officeRepository;

    private final OfficeMapper officeMapper;

    public OfficeService(OfficeRepository officeRepository, OfficeMapper officeMapper) {
        this.officeRepository = officeRepository;
        this.officeMapper = officeMapper;
    }

    /**
     * Save a office.
     *
     * @param officeDTO the entity to save.
     * @return the persisted entity.
     */
    public Office save(OfficeDTO officeDTO) {
        log.debug("Request to save Office : {}", officeDTO);
        Office office = officeMapper.toEntity(officeDTO);
        return officeRepository.save(office);
    }

    /**
     * Get all the offices.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<Office> findAll(Pageable pageable) {
        log.debug("Request to get all Offices");
        return officeRepository.findAll(pageable);
    }

    /**
     * Get one office by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<Office> findOne(Long id) {
        log.debug("Request to get Office : {}", id);
        return officeRepository.findById(id);
    }

    /**
     * Delete the office by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Office : {}", id);
        officeRepository.deleteById(id);
    }
}
