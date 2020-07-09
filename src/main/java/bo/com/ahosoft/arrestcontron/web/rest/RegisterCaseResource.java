package bo.com.ahosoft.arrestcontron.web.rest;

import bo.com.ahosoft.arrestcontron.domain.RegisterCase;
import bo.com.ahosoft.arrestcontron.security.AuthoritiesConstants;
import bo.com.ahosoft.arrestcontron.service.RegisterCaseQueryService;
import bo.com.ahosoft.arrestcontron.service.RegisterCaseService;
import bo.com.ahosoft.arrestcontron.service.dto.*;
import bo.com.ahosoft.arrestcontron.service.errors.*;
import bo.com.ahosoft.arrestcontron.web.rest.errors.BadRequestAlertException;
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
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link RegisterCase}.
 */
@RestController
@RequestMapping("/api")
public class RegisterCaseResource {

    private final Logger log = LoggerFactory.getLogger(RegisterCaseResource.class);

    private static final String ENTITY_NAME = "registerCase";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final RegisterCaseService registerCaseService;

    private final RegisterCaseQueryService registerCaseQueryService;

    public RegisterCaseResource(RegisterCaseService registerCaseService, RegisterCaseQueryService registerCaseQueryService) {
        this.registerCaseService = registerCaseService;
        this.registerCaseQueryService = registerCaseQueryService;
    }

    /**
     * {@code POST  /register-cases} : Create a new registerCase.
     *
     * @param createCaseDTO the registerCaseDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new registerCaseDTO, or with status {@code 400 (Bad Request)} if the registerCase has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PreAuthorize("hasRole(\"" + AuthoritiesConstants.ADMIN + "\") or hasRole(\"" + AuthoritiesConstants.RECEPTIONIST + "\") or hasRole(\"" + AuthoritiesConstants.DISPATCHER + "\")")
    @PostMapping("/register-cases")
    public ResponseEntity<RegisterCase> createRegisterCase(@Valid @RequestBody CreateCaseDTO createCaseDTO) throws URISyntaxException {
        log.debug("REST request to save RegisterCase : {}", createCaseDTO);
        if (createCaseDTO.getId() != null) {
            throw new BadRequestAlertException("A new registerCase cannot already have an ID", ENTITY_NAME, "idexists");
        }

        try {
            RegisterCase result = registerCaseService.save(createCaseDTO);
            return ResponseEntity.created(new URI("/api/register-cases/" + result.getId()))
                .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
                .body(result);
        } catch (UserNotFoundException e) {
            throw new BadRequestException(e.getCode(), e.getMessage());
        } catch (NotValidException e) {
            throw new BadRequestException(e.getCode(), e.getMessage());
        } catch (RegisterCaseNotFoundException e) {
            throw new BadRequestException(e.getCode(), e.getMessage());
        } catch (UnitNotFoundException e) {
            throw new BadRequestException(e.getCode(), e.getMessage());
        }
    }

    /**
     * {@code PUT  /register-cases} : Updates an existing registerCase.
     *
     * @param createCaseDTO the registerCaseDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated registerCaseDTO,
     * or with status {@code 400 (Bad Request)} if the registerCaseDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the registerCaseDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PreAuthorize("hasRole(\"" + AuthoritiesConstants.ADMIN + "\") or hasRole(\"" + AuthoritiesConstants.RECEPTIONIST + "\") or hasRole(\"" + AuthoritiesConstants.DISPATCHER + "\")")
    @PutMapping("/register-cases")
    public ResponseEntity<RegisterCase> updateRegisterCase(@Valid @RequestBody CreateCaseDTO createCaseDTO) throws URISyntaxException {
        log.debug("REST request to update RegisterCase : {}", createCaseDTO);
        if (createCaseDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        try {
            RegisterCase result = registerCaseService.save(createCaseDTO);
            return ResponseEntity.ok()
                .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, createCaseDTO.getId().toString()))
                .body(result);
        } catch (UserNotFoundException e) {
            throw new BadRequestException(e.getCode(), e.getMessage());
        } catch (NotValidException e) {
            throw new BadRequestException(e.getCode(), e.getMessage());
        } catch (RegisterCaseNotFoundException e) {
            throw new BadRequestException(e.getCode(), e.getMessage());
        } catch (UnitNotFoundException e) {
            throw new BadRequestException(e.getCode(), e.getMessage());
        }
    }

    @PreAuthorize("hasRole(\"" + AuthoritiesConstants.ADMIN + "\") or hasRole(\"" + AuthoritiesConstants.DISPATCHER + "\")")
    @PutMapping("/register-cases/dispatch")
    public ResponseEntity<RegisterCase> dispatchRegisterCase(@Valid @RequestBody DispatchCaseDTO createCaseDTO) throws URISyntaxException {
        log.debug("REST request to update RegisterCase : {}", createCaseDTO);
        if (createCaseDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        try {
            RegisterCase result = registerCaseService.dispatch(createCaseDTO);
            return ResponseEntity.ok()
                .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, createCaseDTO.getId().toString()))
                .body(result);
        } catch (UserNotFoundException e) {
            throw new BadRequestException(e.getCode(), e.getMessage());
        } catch (StatusNotValidException e) {
            throw new BadRequestException(e.getCode(), e.getMessage());
        } catch (RegisterCaseNotFoundException e) {
            throw new BadRequestException(e.getCode(), e.getMessage());
        } catch (UnitNotFoundException e) {
            throw new BadRequestException(e.getCode(), e.getMessage());
        }
    }

    @PreAuthorize("hasRole(\"" + AuthoritiesConstants.ADMIN + "\") or hasRole(\"" + AuthoritiesConstants.DISPATCHER + "\")")
    @PutMapping("/register-cases/finalize")
    public ResponseEntity<RegisterCase> finalizeRegisterCase(@Valid @RequestBody FinalizeCaseDTO createCaseDTO) throws URISyntaxException {
        log.debug("REST request to update RegisterCase : {}", createCaseDTO);
        if (createCaseDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        try {
            RegisterCase result = registerCaseService.finalizeCase(createCaseDTO);
            return ResponseEntity.ok()
                .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, createCaseDTO.getId().toString()))
                .body(result);
        } catch (UserNotFoundException e) {
            throw new BadRequestException(e.getCode(), e.getMessage());
        } catch (StatusNotValidException e) {
            throw new BadRequestException(e.getCode(), e.getMessage());
        } catch (RegisterCaseNotFoundException e) {
            throw new BadRequestException(e.getCode(), e.getMessage());
        } catch (UnitNotFoundException e) {
            throw new BadRequestException(e.getCode(), e.getMessage());
        }
    }

    @PreAuthorize("hasRole(\"" + AuthoritiesConstants.ADMIN + "\") or hasRole(\"" + AuthoritiesConstants.DISPATCHER + "\")")
    @PutMapping("/register-cases/check")
    public ResponseEntity<RegisterCase> checkRegisterCase(@Valid @RequestBody CheckCaseDTO checkCaseDTO) throws URISyntaxException {
        log.debug("REST request to update checkRegisterCase : {}", checkCaseDTO);
        if (checkCaseDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        try {
            RegisterCase result = registerCaseService.checkCase(checkCaseDTO);
            return ResponseEntity.ok()
                .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, checkCaseDTO.getId().toString()))
                .body(result);
        } catch (UserNotFoundException e) {
            throw new BadRequestException(e.getCode(), e.getMessage());
        } catch (StatusNotValidException e) {
            throw new BadRequestException(e.getCode(), e.getMessage());
        } catch (RegisterCaseNotFoundException e) {
            throw new BadRequestException(e.getCode(), e.getMessage());
        } catch (UnitNotFoundException e) {
            throw new BadRequestException(e.getCode(), e.getMessage());
        } catch (CheckException e) {
            throw new BadRequestException(e.getCode(), e.getMessage());
        }
    }

    /**
     * {@code GET  /register-cases} : get all the registerCases.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of registerCases in body.
     */
    @GetMapping("/register-cases")
    public ResponseEntity<List<RegisterCase>> getAllRegisterCases(RegisterCaseCriteria criteria, Pageable pageable) {
        log.debug("REST request to get RegisterCases by criteria: {}", criteria);
        Page<RegisterCase> page = registerCaseQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /register-cases/count} : count all the registerCases.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/register-cases/count")
    public ResponseEntity<Long> countRegisterCases(RegisterCaseCriteria criteria) {
        log.debug("REST request to count RegisterCases by criteria: {}", criteria);
        return ResponseEntity.ok().body(registerCaseQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /register-cases/:id} : get the "id" registerCase.
     *
     * @param id the id of the registerCaseDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the registerCaseDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/register-cases/{id}")
    public ResponseEntity<RegisterCase> getRegisterCase(@PathVariable Long id) {
        log.debug("REST request to get RegisterCase : {}", id);
        Optional<RegisterCase> registerCaseDTO = registerCaseService.findOne(id);
        return ResponseUtil.wrapOrNotFound(registerCaseDTO);
    }

    /**
     * {@code DELETE  /register-cases/:id} : delete the "id" registerCase.
     *
     * @param id the id of the registerCaseDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/register-cases/{id}")
    public ResponseEntity<Void> deleteRegisterCase(@PathVariable Long id) {
        log.debug("REST request to delete RegisterCase : {}", id);
        registerCaseService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
