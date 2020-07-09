package bo.com.ahosoft.arrestcontron.web.rest;

import bo.com.ahosoft.arrestcontron.domain.Arrest;
import bo.com.ahosoft.arrestcontron.security.AuthoritiesConstants;
import bo.com.ahosoft.arrestcontron.service.ArrestService;
import bo.com.ahosoft.arrestcontron.service.dto.TotalArrest;
import bo.com.ahosoft.arrestcontron.service.errors.ArrestNotFoundException;
import bo.com.ahosoft.arrestcontron.service.errors.DateArrestOutRangeException;
import bo.com.ahosoft.arrestcontron.service.errors.UserNotFoundException;
import bo.com.ahosoft.arrestcontron.service.errors.UserNotValidException;
import bo.com.ahosoft.arrestcontron.web.rest.errors.BadRequestAlertException;
import bo.com.ahosoft.arrestcontron.service.dto.ArrestDTO;
import bo.com.ahosoft.arrestcontron.service.dto.ArrestCriteria;
import bo.com.ahosoft.arrestcontron.service.ArrestQueryService;

import bo.com.ahosoft.arrestcontron.web.rest.errors.my.BadRequestException;
import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
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
 * REST controller for managing {@link bo.com.ahosoft.arrestcontron.domain.Arrest}.
 */
@RestController
@RequestMapping("/api")
public class ArrestResource {

    private final Logger log = LoggerFactory.getLogger(ArrestResource.class);

    private static final String ENTITY_NAME = "arrest";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ArrestService arrestService;

    private final ArrestQueryService arrestQueryService;

    public ArrestResource(ArrestService arrestService, ArrestQueryService arrestQueryService) {
        this.arrestService = arrestService;
        this.arrestQueryService = arrestQueryService;
    }

    /**
     * {@code POST  /arrests} : Create a new arrest.
     *
     * @param arrestDTO the arrestDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new arrestDTO, or with status {@code 400 (Bad Request)} if the arrest has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/arrests")
    public ResponseEntity<Arrest> createArrest(@Valid @RequestBody ArrestDTO arrestDTO) throws URISyntaxException {
        log.debug("REST request to save Arrest : {}", arrestDTO);
        if (arrestDTO.getId() != null) {
            throw new BadRequestAlertException("A new arrest cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Arrest result = null;
        try {
            result = arrestService.save(arrestDTO);
        } catch (UserNotFoundException e) {
            throw new BadRequestException(e.getCode(), e.getMessage());
        } catch (ArrestNotFoundException e) {
            throw new BadRequestException(e.getCode(), e.getMessage());
        } catch (UserNotValidException e) {
            throw new BadRequestException(e.getCode(), e.getMessage());
        } catch (DateArrestOutRangeException e) {
            throw new BadRequestAlertException(e.getMessage(), ENTITY_NAME, e.getCode());
        }
        return ResponseEntity.created(new URI("/api/arrests/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /arrests} : Updates an existing arrest.
     *
     * @param arrestDTO the arrestDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated arrestDTO,
     * or with status {@code 400 (Bad Request)} if the arrestDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the arrestDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/arrests")
    public ResponseEntity<Arrest> updateArrest(@Valid @RequestBody ArrestDTO arrestDTO) throws URISyntaxException {
        log.debug("REST request to update Arrest : {}", arrestDTO);
        if (arrestDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Arrest result = null;
        try {
            result = arrestService.save(arrestDTO);
        } catch (UserNotFoundException e) {
            throw new BadRequestException(e.getCode(), e.getMessage());
        } catch (ArrestNotFoundException e) {
            throw new BadRequestException(e.getCode(), e.getMessage());
        } catch (UserNotValidException e) {
            throw new BadRequestException(e.getCode(), e.getMessage());
        } catch (DateArrestOutRangeException e) {
            throw new BadRequestAlertException(e.getMessage(), ENTITY_NAME, e.getCode());
        }
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, arrestDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /arrests} : get all the arrests.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of arrests in body.
     */
    @GetMapping("/arrests")
    public ResponseEntity<List<Arrest>> getAllArrests(ArrestCriteria criteria, Pageable pageable) {
        log.debug("REST request to get Arrests by criteria: {}", criteria);
        Page<Arrest> page = arrestQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    @GetMapping("/arrests/list")
    public ResponseEntity<List<Arrest>> getAllArrestsList(ArrestCriteria criteria) {
        log.debug("REST request to get Arrests by criteria: {}", criteria);
        List<Arrest> arrestList = arrestQueryService.findByCriteria(criteria);
        return ResponseEntity.ok().body(arrestList);
    }

    /**
     * {@code GET  /arrests/count} : count all the arrests.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/arrests/count")
    public ResponseEntity<Long> countArrests(ArrestCriteria criteria) {
        log.debug("REST request to count Arrests by criteria: {}", criteria);
        return ResponseEntity.ok().body(arrestQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /arrests/:id} : get the "id" arrest.
     *
     * @param id the id of the arrestDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the arrestDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/arrests/{id}")
    public ResponseEntity<Arrest> getArrest(@PathVariable Long id) {
        log.debug("REST request to get Arrest : {}", id);
        Optional<Arrest> arrestDTO = arrestService.findOne(id);
        return ResponseUtil.wrapOrNotFound(arrestDTO);
    }

    /**
     * {@code DELETE  /arrests/:id} : delete the "id" arrest.
     *
     * @param id the id of the arrestDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/arrests/{id}")
    @PreAuthorize("hasRole(\"" + AuthoritiesConstants.ADMIN + "\")")
    public ResponseEntity<Void> deleteArrest(@PathVariable Long id) {
        log.debug("REST request to delete Arrest : {}", id);
        arrestService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }


    @GetMapping("/arrests/total")
    @PreAuthorize("hasRole(\"" + AuthoritiesConstants.ADMIN + "\") or hasRole(\"" + AuthoritiesConstants.UNIT + "\") or hasRole(\""+ AuthoritiesConstants.REPORT + "\")")
    public ResponseEntity<List<TotalArrest>> getAllArrests(ArrestCriteria criteria) {
        log.debug("REST request to get Arrests by criteria: {}", criteria);
        try {
            List<TotalArrest> totalArrestList = arrestQueryService.findTotalByCriteria(criteria);
            return ResponseEntity.ok().body(totalArrestList);
        } catch (UserNotFoundException e) {
            throw new BadRequestAlertException(e.getMessage(), ENTITY_NAME, e.getCode());
        }
    }
}
