package bo.com.ahosoft.arrestcontron.service;

import bo.com.ahosoft.arrestcontron.domain.TypeCase;
import bo.com.ahosoft.arrestcontron.repository.TypeCaseRepository;
import bo.com.ahosoft.arrestcontron.service.dto.TypeCaseDTO;
import bo.com.ahosoft.arrestcontron.service.mapper.TypeCaseMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link TypeCase}.
 */
@Service
@Transactional
public class TypeCaseService {

    private final Logger log = LoggerFactory.getLogger(TypeCaseService.class);

    private final TypeCaseRepository typeCaseRepository;

    private final TypeCaseMapper typeCaseMapper;

    public TypeCaseService(TypeCaseRepository typeCaseRepository, TypeCaseMapper typeCaseMapper) {
        this.typeCaseRepository = typeCaseRepository;
        this.typeCaseMapper = typeCaseMapper;
    }

    /**
     * Save a tipeCase.
     *
     * @param typeCaseDTO the entity to save.
     * @return the persisted entity.
     */
    public TypeCaseDTO save(TypeCaseDTO typeCaseDTO) {
        log.debug("Request to save TipeCase : {}", typeCaseDTO);
        TypeCase typeCase = typeCaseMapper.toEntity(typeCaseDTO);
        typeCase = typeCaseRepository.save(typeCase);
        return typeCaseMapper.toDto(typeCase);
    }

    /**
     * Get all the tipeCases.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<TypeCaseDTO> findAll(Pageable pageable) {
        log.debug("Request to get all TipeCases");
        return typeCaseRepository.findAll(pageable)
            .map(typeCaseMapper::toDto);
    }

    /**
     * Get one tipeCase by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<TypeCaseDTO> findOne(Long id) {
        log.debug("Request to get TipeCase : {}", id);
        return typeCaseRepository.findById(id)
            .map(typeCaseMapper::toDto);
    }

    /**
     * Delete the tipeCase by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete TipeCase : {}", id);
        typeCaseRepository.deleteById(id);
    }
}
