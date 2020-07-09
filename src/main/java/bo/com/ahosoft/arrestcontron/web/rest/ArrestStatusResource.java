package bo.com.ahosoft.arrestcontron.web.rest;

import bo.com.ahosoft.arrestcontron.domain.Arrest;
import bo.com.ahosoft.arrestcontron.security.AuthoritiesConstants;
import bo.com.ahosoft.arrestcontron.service.ArrestQueryService;
import bo.com.ahosoft.arrestcontron.service.ArrestService;
import bo.com.ahosoft.arrestcontron.service.errors.*;
import bo.com.ahosoft.arrestcontron.web.rest.errors.BadRequestAlertException;
import bo.com.ahosoft.arrestcontron.web.rest.errors.my.BadRequestException;
import bo.com.ahosoft.arrestcontron.web.rest.vm.ArrestNoFineVM;
import bo.com.ahosoft.arrestcontron.web.rest.vm.ArrestPaidOutVM;
import bo.com.ahosoft.arrestcontron.web.rest.vm.ArrestPendingVM;
import io.github.jhipster.web.util.HeaderUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URISyntaxException;

/**
 * REST controller for managing {@link Arrest}.
 */
@RestController
@RequestMapping("/api")
public class ArrestStatusResource {

    private final Logger log = LoggerFactory.getLogger(ArrestStatusResource.class);

    private static final String ENTITY_NAME = "arrest";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ArrestService arrestService;

    private final ArrestQueryService arrestQueryService;

    public ArrestStatusResource(ArrestService arrestService, ArrestQueryService arrestQueryService) {
        this.arrestService = arrestService;
        this.arrestQueryService = arrestQueryService;
    }

    @PutMapping("/arrests/no-fine")
    @PreAuthorize("hasRole(\"" + AuthoritiesConstants.ADMIN + "\") or hasRole(\"" + AuthoritiesConstants.UNIT + "\") or hasRole(\"" + AuthoritiesConstants.USER + "\")")
    public ResponseEntity<Arrest> updateArrestNoFine(@Valid @RequestBody ArrestNoFineVM arrestNoFineVM) throws URISyntaxException {
        log.debug("REST request to update Arrest No Fine : {}", arrestNoFineVM);
        if (arrestNoFineVM.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }

        try {
            Arrest result = arrestService.updateArrestNoFine(arrestNoFineVM);
            return ResponseEntity.ok()
                .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, arrestNoFineVM.getId().toString()))
                .body(result);
        } catch (UserNotFoundException e) {
            throw new BadRequestException(e.getCode(), e.getMessage());
        } catch (ArrestNotFoundException e) {
            throw new BadRequestException(e.getCode(), e.getMessage());
        } catch (UserNotValidException e) {
            throw new BadRequestException(e.getCode(), e.getMessage());
        } catch (StatusNotValidException e) {
            throw new BadRequestAlertException(e.getMessage(), ENTITY_NAME, e.getCode());
        }

    }

    @PutMapping("/arrests/paid-out")
    @PreAuthorize("hasRole(\"" + AuthoritiesConstants.ADMIN + "\") or hasRole(\"" + AuthoritiesConstants.UNIT + "\") or hasRole(\"" + AuthoritiesConstants.USER + "\")")
    public ResponseEntity<Arrest> updateArrestPaidOut(@Valid @RequestBody ArrestPaidOutVM arrestPaidOutVM) throws URISyntaxException {
        log.debug("REST request to update Arrest Paid Out : {}", arrestPaidOutVM);
        if (arrestPaidOutVM.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }

        try {
            Arrest result = arrestService.updateArrestPaidOut(arrestPaidOutVM);
            return ResponseEntity.ok()
                .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, arrestPaidOutVM.getId().toString()))
                .body(result);
        } catch (UserNotFoundException e) {
            throw new BadRequestException(e.getCode(), e.getMessage());
        } catch (ArrestNotFoundException e) {
            throw new BadRequestException(e.getCode(), e.getMessage());
        } catch (UserNotValidException e) {
            throw new BadRequestException(e.getCode(), e.getMessage());
        } catch (StatusNotValidException e) {
            throw new BadRequestAlertException(e.getMessage(), ENTITY_NAME, e.getCode());
        }

    }

    @PutMapping("/arrests/pending")
    @PreAuthorize("hasRole(\"" + AuthoritiesConstants.ADMIN + "\")")
    public ResponseEntity<Arrest> updateArrestPending(@Valid @RequestBody ArrestPendingVM arrestPendingVM) throws URISyntaxException {
        log.debug("REST request to update Arrest Pending : {}", arrestPendingVM);
        if (arrestPendingVM.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }

        try {
            Arrest result = arrestService.updateArrestPending(arrestPendingVM);
            return ResponseEntity.ok()
                .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, arrestPendingVM.getId().toString()))
                .body(result);
        } catch (UserNotFoundException e) {
            throw new BadRequestException(e.getCode(), e.getMessage());
        } catch (ArrestNotFoundException e) {
            throw new BadRequestException(e.getCode(), e.getMessage());
        } catch (UserNotValidException e) {
            throw new BadRequestException(e.getCode(), e.getMessage());
        } catch (StatusNotValidException e) {
            throw new BadRequestAlertException(e.getMessage(), ENTITY_NAME, e.getCode());
        }

    }

}
