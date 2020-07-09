package bo.com.ahosoft.arrestcontron.web.rest;

import bo.com.ahosoft.arrestcontron.domain.SystemParameter;
import bo.com.ahosoft.arrestcontron.domain.enumeration.TypeSystemParameter;
import bo.com.ahosoft.arrestcontron.repository.SystemParameterRepository;
import bo.com.ahosoft.arrestcontron.service.dto.SystemParameterDTO;
import bo.com.ahosoft.arrestcontron.web.rest.errors.BadRequestAlertException;
import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link bo.com.ahosoft.arrestcontron.domain.SystemParameter}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class SystemParameterResource {

    private final Logger log = LoggerFactory.getLogger(SystemParameterResource.class);

    private static final String ENTITY_NAME = "systemParameter";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final SystemParameterRepository systemParameterRepository;

    public SystemParameterResource(SystemParameterRepository systemParameterRepository) {
        this.systemParameterRepository = systemParameterRepository;
    }


    @PutMapping("/system-parameters")
    public ResponseEntity<SystemParameter> updateSystemParameter(@Valid @RequestBody SystemParameterDTO systemParameterDTO) throws URISyntaxException {
        log.debug("REST request to update SystemParameter : {}", systemParameterDTO);
        if (systemParameterDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Optional<SystemParameter> systemParameterOptional = systemParameterRepository.findById(systemParameterDTO.getId());
        if (!systemParameterOptional.isPresent())
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        SystemParameter result = systemParameterOptional.get();
        result.setValue(systemParameterDTO.getValue() == null || systemParameterDTO.getValue().isEmpty() ? null : systemParameterDTO.getValue());
        if (systemParameterDTO.getValue() != null && !systemParameterDTO.getValue().isEmpty()) {
            if (result.getType().equals(TypeSystemParameter.NUMBER))
                try {
                    Double.valueOf(systemParameterDTO.getValue());
                } catch (Exception e) {
                    throw new BadRequestAlertException("Invalid value", ENTITY_NAME, "idnull");
                }
        }
        systemParameterRepository.save(result);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, result.getId()))
            .body(result);
    }

    /**
     * {@code GET  /system-parameters} : get all the systemParameters.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of systemParameters in body.
     */
    @GetMapping("/system-parameters")
    public List<SystemParameter> getAllSystemParameters() {
        log.debug("REST request to get all SystemParameters");
        return systemParameterRepository.findAll();
    }

    /**
     * {@code GET  /system-parameters/:id} : get the "id" systemParameter.
     *
     * @param id the id of the systemParameter to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the systemParameter, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/system-parameters/{id}")
    public ResponseEntity<SystemParameter> getSystemParameter(@PathVariable String id) {
        log.debug("REST request to get SystemParameter : {}", id);
        Optional<SystemParameter> systemParameter = systemParameterRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(systemParameter);
    }
}
