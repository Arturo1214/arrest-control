package bo.com.ahosoft.arrestcontron.web.rest;

import bo.com.ahosoft.arrestcontron.domain.Office;
import bo.com.ahosoft.arrestcontron.security.AuthoritiesConstants;
import bo.com.ahosoft.arrestcontron.service.OfficeService;
import bo.com.ahosoft.arrestcontron.web.rest.errors.BadRequestAlertException;
import bo.com.ahosoft.arrestcontron.service.dto.OfficeDTO;
import bo.com.ahosoft.arrestcontron.service.dto.OfficeCriteria;
import bo.com.ahosoft.arrestcontron.service.OfficeQueryService;

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

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link bo.com.ahosoft.arrestcontron.domain.Office}.
 */
@RestController
@RequestMapping("/api")
public class OfficeResource {

    private final Logger log = LoggerFactory.getLogger(OfficeResource.class);

    private static final String ENTITY_NAME = "office";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final OfficeService officeService;

    private final OfficeQueryService officeQueryService;

    public OfficeResource(OfficeService officeService, OfficeQueryService officeQueryService) {
        this.officeService = officeService;
        this.officeQueryService = officeQueryService;
    }

    /**
     * {@code POST  /offices} : Create a new office.
     *
     * @param officeDTO the officeDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new officeDTO, or with status {@code 400 (Bad Request)} if the office has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/offices")
    @PreAuthorize("hasRole(\"" + AuthoritiesConstants.ADMIN + "\") or hasRole(\"" + AuthoritiesConstants.UNIT + "\")")
    public ResponseEntity<Office> createOffice(@Valid @RequestBody OfficeDTO officeDTO) throws URISyntaxException {
        log.debug("REST request to save Office : {}", officeDTO);
        if (officeDTO.getId() != null) {
            throw new BadRequestAlertException("A new office cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Office result = officeService.save(officeDTO);
        return ResponseEntity.created(new URI("/api/offices/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /offices} : Updates an existing office.
     *
     * @param officeDTO the officeDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated officeDTO,
     * or with status {@code 400 (Bad Request)} if the officeDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the officeDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/offices")
    @PreAuthorize("hasRole(\"" + AuthoritiesConstants.ADMIN + "\") or hasRole(\"" + AuthoritiesConstants.UNIT + "\")")
    public ResponseEntity<Office> updateOffice(@Valid @RequestBody OfficeDTO officeDTO) throws URISyntaxException {
        log.debug("REST request to update Office : {}", officeDTO);
        if (officeDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Office result = officeService.save(officeDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, officeDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /offices} : get all the offices.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of offices in body.
     */
    @GetMapping("/offices")
    public ResponseEntity<List<Office>> getAllOffices(OfficeCriteria criteria, Pageable pageable) {
        log.debug("REST request to get Offices by criteria: {}", criteria);
        Page<Office> page = officeQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /offices/count} : count all the offices.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/offices/count")
    public ResponseEntity<Long> countOffices(OfficeCriteria criteria) {
        log.debug("REST request to count Offices by criteria: {}", criteria);
        return ResponseEntity.ok().body(officeQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /offices/:id} : get the "id" office.
     *
     * @param id the id of the officeDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the officeDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/offices/{id}")
    public ResponseEntity<Office> getOffice(@PathVariable Long id) {
        log.debug("REST request to get Office : {}", id);
        Optional<Office> officeDTO = officeService.findOne(id);
        return ResponseUtil.wrapOrNotFound(officeDTO);
    }

    /**
     * {@code DELETE  /offices/:id} : delete the "id" office.
     *
     * @param id the id of the officeDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/offices/{id}")
    @PreAuthorize("hasRole(\"" + AuthoritiesConstants.ADMIN + "\")")
    public ResponseEntity<Void> deleteOffice(@PathVariable Long id) {
        log.debug("REST request to delete Office : {}", id);
        officeService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
