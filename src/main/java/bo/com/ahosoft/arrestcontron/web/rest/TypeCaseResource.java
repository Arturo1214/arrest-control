package bo.com.ahosoft.arrestcontron.web.rest;

import bo.com.ahosoft.arrestcontron.domain.TypeCase;
import bo.com.ahosoft.arrestcontron.service.TypeCaseQueryService;
import bo.com.ahosoft.arrestcontron.service.TypeCaseService;
import bo.com.ahosoft.arrestcontron.service.dto.TypeCaseCriteria;
import bo.com.ahosoft.arrestcontron.service.dto.TypeCaseDTO;
import bo.com.ahosoft.arrestcontron.web.rest.errors.BadRequestAlertException;
import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link TypeCase}.
 */
@RestController
@RequestMapping("/api")
public class TypeCaseResource {

    private final Logger log = LoggerFactory.getLogger(TypeCaseResource.class);

    private static final String ENTITY_NAME = "typeCase";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final TypeCaseService typeCaseService;

    private final TypeCaseQueryService typeCaseQueryService;

    public TypeCaseResource(TypeCaseService typeCaseService, TypeCaseQueryService typeCaseQueryService) {
        this.typeCaseService = typeCaseService;
        this.typeCaseQueryService = typeCaseQueryService;
    }

    /**
     * {@code POST  /type-cases} : Create a new typeCase.
     *
     * @param typeCaseDTO the typeCaseDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new typeCaseDTO, or with status {@code 400 (Bad Request)} if the typeCase has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/type-cases")
    public ResponseEntity<TypeCaseDTO> createTypeCase(@Valid @RequestBody TypeCaseDTO typeCaseDTO) throws URISyntaxException {
        log.debug("REST request to save TypeCase : {}", typeCaseDTO);
        if (typeCaseDTO.getId() != null) {
            throw new BadRequestAlertException("A new typeCase cannot already have an ID", ENTITY_NAME, "idexists");
        }
        TypeCaseDTO result = typeCaseService.save(typeCaseDTO);
        return ResponseEntity.created(new URI("/api/type-cases/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /type-cases} : Updates an existing typeCase.
     *
     * @param typeCaseDTO the typeCaseDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated typeCaseDTO,
     * or with status {@code 400 (Bad Request)} if the typeCaseDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the typeCaseDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/type-cases")
    public ResponseEntity<TypeCaseDTO> updateTypeCase(@Valid @RequestBody TypeCaseDTO typeCaseDTO) throws URISyntaxException {
        log.debug("REST request to update TypeCase : {}", typeCaseDTO);
        if (typeCaseDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        TypeCaseDTO result = typeCaseService.save(typeCaseDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, typeCaseDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /type-cases} : get all the typeCases.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of typeCases in body.
     */
    @GetMapping("/type-cases")
    public ResponseEntity<List<TypeCaseDTO>> getAllTypeCases(TypeCaseCriteria criteria, Pageable pageable) {
        log.debug("REST request to get TypeCases by criteria: {}", criteria);
        Page<TypeCaseDTO> page = typeCaseQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    @GetMapping("/type-cases/all")
    public ResponseEntity<List<TypeCaseDTO>> getAllTypeCases(TypeCaseCriteria criteria) {
        log.debug("REST request to get TypeCases by criteria: {}", criteria);
        List<TypeCaseDTO> page = typeCaseQueryService.findByCriteria(criteria);
        return ResponseEntity.ok().body(page);
    }

    /**
     * {@code GET  /type-cases/count} : count all the typeCases.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/type-cases/count")
    public ResponseEntity<Long> countTypeCases(TypeCaseCriteria criteria) {
        log.debug("REST request to count TypeCases by criteria: {}", criteria);
        return ResponseEntity.ok().body(typeCaseQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /type-cases/:id} : get the "id" typeCase.
     *
     * @param id the id of the typeCaseDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the typeCaseDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/type-cases/{id}")
    public ResponseEntity<TypeCaseDTO> getTypeCase(@PathVariable Long id) {
        log.debug("REST request to get TypeCase : {}", id);
        Optional<TypeCaseDTO> typeCaseDTO = typeCaseService.findOne(id);
        return ResponseUtil.wrapOrNotFound(typeCaseDTO);
    }

    /**
     * {@code DELETE  /type-cases/:id} : delete the "id" typeCase.
     *
     * @param id the id of the typeCaseDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/type-cases/{id}")
    public ResponseEntity<Void> deleteTypeCase(@PathVariable Long id) {
        log.debug("REST request to delete TypeCase : {}", id);
        typeCaseService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
