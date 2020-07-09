package bo.com.ahosoft.arrestcontron.web.rest;

import bo.com.ahosoft.arrestcontron.domain.Unit;
import bo.com.ahosoft.arrestcontron.security.AuthoritiesConstants;
import bo.com.ahosoft.arrestcontron.service.UnitService;
import bo.com.ahosoft.arrestcontron.web.rest.errors.BadRequestAlertException;
import bo.com.ahosoft.arrestcontron.service.dto.UnitDTO;
import bo.com.ahosoft.arrestcontron.service.dto.UnitCriteria;
import bo.com.ahosoft.arrestcontron.service.UnitQueryService;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link bo.com.ahosoft.arrestcontron.domain.Unit}.
 */
@RestController
@RequestMapping("/api")
public class UnitResource {

    private final Logger log = LoggerFactory.getLogger(UnitResource.class);

    private static final String ENTITY_NAME = "unit";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final UnitService unitService;

    private final UnitQueryService unitQueryService;

    public UnitResource(UnitService unitService, UnitQueryService unitQueryService) {
        this.unitService = unitService;
        this.unitQueryService = unitQueryService;
    }

    /**
     * {@code POST  /units} : Create a new unit.
     *
     * @param unitDTO the unitDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new unitDTO, or with status {@code 400 (Bad Request)} if the unit has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/units")
    @PreAuthorize("hasRole(\"" + AuthoritiesConstants.ADMIN + "\")")
    public ResponseEntity<Unit> createUnit(@RequestBody UnitDTO unitDTO) throws URISyntaxException {
        log.debug("REST request to save Unit : {}", unitDTO);
        if (unitDTO.getId() != null) {
            throw new BadRequestAlertException("A new unit cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Unit result = unitService.save(unitDTO);
        return ResponseEntity.created(new URI("/api/units/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /units} : Updates an existing unit.
     *
     * @param unitDTO the unitDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated unitDTO,
     * or with status {@code 400 (Bad Request)} if the unitDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the unitDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/units")
    @PreAuthorize("hasRole(\"" + AuthoritiesConstants.ADMIN + "\")")
    public ResponseEntity<Unit> updateUnit(@RequestBody UnitDTO unitDTO) throws URISyntaxException {
        log.debug("REST request to update Unit : {}", unitDTO);
        if (unitDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Unit result = unitService.save(unitDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, unitDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /units} : get all the units.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of units in body.
     */
    @GetMapping("/units")
    public ResponseEntity<List<Unit>> getAllUnits(UnitCriteria criteria, Pageable pageable) {
        log.debug("REST request to get Units by criteria: {}", criteria);
        Page<Unit> page = unitQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /units/count} : count all the units.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/units/count")
    public ResponseEntity<Long> countUnits(UnitCriteria criteria) {
        log.debug("REST request to count Units by criteria: {}", criteria);
        return ResponseEntity.ok().body(unitQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /units/:id} : get the "id" unit.
     *
     * @param id the id of the unitDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the unitDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/units/{id}")
    public ResponseEntity<Unit> getUnit(@PathVariable Long id) {
        log.debug("REST request to get Unit : {}", id);
        Optional<Unit> unitDTO = unitService.findOne(id);
        return ResponseUtil.wrapOrNotFound(unitDTO);
    }

    /**
     * {@code DELETE  /units/:id} : delete the "id" unit.
     *
     * @param id the id of the unitDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/units/{id}")
    @PreAuthorize("hasRole(\"" + AuthoritiesConstants.ADMIN + "\")")
    public ResponseEntity<Void> deleteUnit(@PathVariable Long id) {
        log.debug("REST request to delete Unit : {}", id);
        unitService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
