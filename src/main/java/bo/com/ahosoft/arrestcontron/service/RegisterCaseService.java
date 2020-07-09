package bo.com.ahosoft.arrestcontron.service;


import bo.com.ahosoft.arrestcontron.domain.Authority;
import bo.com.ahosoft.arrestcontron.domain.RegisterCase;
import bo.com.ahosoft.arrestcontron.domain.Unit;
import bo.com.ahosoft.arrestcontron.domain.User;
import bo.com.ahosoft.arrestcontron.domain.enumeration.StateCase;
import bo.com.ahosoft.arrestcontron.domain.enumeration.StateRegister;
import bo.com.ahosoft.arrestcontron.repository.RegisterCaseRepository;
import bo.com.ahosoft.arrestcontron.repository.UnitRepository;
import bo.com.ahosoft.arrestcontron.security.AuthoritiesConstants;
import bo.com.ahosoft.arrestcontron.service.dto.*;
import bo.com.ahosoft.arrestcontron.service.errors.*;
import bo.com.ahosoft.arrestcontron.service.mapper.CreateCaseMapper;
import bo.com.ahosoft.arrestcontron.service.mapper.RegisterCaseMapper;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.constraints.Size;
import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.Optional;

/**
 * Service Implementation for managing {@link RegisterCase}.
 */
@RequiredArgsConstructor
@Service
@Transactional
public class RegisterCaseService {

    private final Logger log = LoggerFactory.getLogger(RegisterCaseService.class);

    private final RegisterCaseRepository registerCaseRepository;
    private final CreateCaseMapper createCaseMapper;
    private final UserService userService;
    private final UnitRepository unitRepository;

    /**
     * Save a registerCase.
     *
     * @param createCaseDTO the entity to save.
     * @return the persisted entity.
     */
    public RegisterCase save(CreateCaseDTO createCaseDTO) throws UserNotFoundException, RegisterCaseNotFoundException, UnitNotFoundException, NotValidException {
        log.debug("Request to save RegisterCase : {}", createCaseDTO);
        RegisterCase registerCase = createCaseMapper.toEntity(createCaseDTO);
        Optional<User> userOptional = userService.getUserWithAuthorities();
        if (!userOptional.isPresent())
            throw new UserNotFoundException();

        User user = userOptional.get();
        boolean dispatcher = false;
        for (Authority authority : user.getAuthorities()) {
            if (authority.getName().equals(AuthoritiesConstants.DISPATCHER)) {
                dispatcher = true;
                break;
            }
        }

        if (dispatcher) {
            if (registerCase.getId() == null) {
                if (registerCase.getZone() == null || registerCase.getAcronymPatrol() == null
                    || registerCase.getPatrolLeader() == null || registerCase.getUnit() == null)
                    throw new NotValidException();

                Optional<Unit> optionalUnit = unitRepository.findById(createCaseDTO.getUnitId());
                if (!optionalUnit.isPresent())
                    throw new UnitNotFoundException();
                registerCase.setUnit(optionalUnit.get());
                registerCase.setState(StateRegister.PENDING);
                registerCase.setReceptionist(userOptional.get());
                registerCase.setDispatcher(userOptional.get());
                return registerCaseRepository.save(registerCase);
            } else {
                Optional<RegisterCase> registerCaseOptional = registerCaseRepository.findById(registerCase.getId());
                if (!registerCaseOptional.isPresent())
                    throw new RegisterCaseNotFoundException();

                if (registerCase.getZone() == null || registerCase.getAcronymPatrol() == null
                    || registerCase.getPatrolLeader() == null || registerCase.getUnit() == null)
                    throw new NotValidException();

                RegisterCase update = registerCaseOptional.get();
                update.setRegistrationDate(registerCase.getRegistrationDate());
                update.setAddress(registerCase.getAddress());
                update.setInformer(registerCase.getInformer());
                update.setPhone(registerCase.getPhone());
                update.setDescription(registerCase.getDescription());
                update.setTypeCase(registerCase.getTypeCase());
                update.setLatitude(registerCase.getLatitude());
                update.setLongitude(registerCase.getLongitude());
                update.setZone(registerCase.getZone());
                update.setAcronymPatrol(registerCase.getAcronymPatrol());
                update.setPatrolLeader(registerCase.getPatrolLeader());
                update.setSupportPatrol(registerCase.getSupportPatrol());
                Optional<Unit> optionalUnit = unitRepository.findById(createCaseDTO.getUnitId());
                if (!optionalUnit.isPresent())
                    throw new UnitNotFoundException();
                update.setUnit(optionalUnit.get());

                return registerCaseRepository.save(update);
            }
        } else {
            if (registerCase.getId() == null) {
                registerCase.setZone(null);
                registerCase.setAcronymPatrol(null);
                registerCase.setPatrolLeader(null);
                registerCase.setSupportPatrol(null);
                registerCase.setUnit(null);
                registerCase.setState(StateRegister.PENDING);
                registerCase.setReceptionist(userOptional.get());
                registerCase.setDispatcher(userOptional.get());
                return registerCaseRepository.save(registerCase);
            } else {
                Optional<RegisterCase> registerCaseOptional = registerCaseRepository.findById(registerCase.getId());
                if (!registerCaseOptional.isPresent())
                    throw new RegisterCaseNotFoundException();

                RegisterCase update = registerCaseOptional.get();
                update.setRegistrationDate(registerCase.getRegistrationDate());
                update.setAddress(registerCase.getAddress());
                update.setInformer(registerCase.getInformer());
                update.setPhone(registerCase.getPhone());
                update.setDescription(registerCase.getDescription());
                update.setTypeCase(registerCase.getTypeCase());
                update.setLatitude(registerCase.getLatitude());
                update.setLongitude(registerCase.getLongitude());
                return registerCaseRepository.save(update);
            }
        }

    }

    public RegisterCase dispatch(DispatchCaseDTO createCaseDTO) throws RegisterCaseNotFoundException, StatusNotValidException, UnitNotFoundException, UserNotFoundException {
        log.debug("Request to save RegisterCase : {}", createCaseDTO);
        Optional<User> userOptional = userService.getUserWithAuthorities();
        if (!userOptional.isPresent())
            throw new UserNotFoundException();
        Optional<RegisterCase> optionalRegisterCase = findOne(createCaseDTO.getId());
        if (!optionalRegisterCase.isPresent())
            throw new RegisterCaseNotFoundException();
        RegisterCase registerCase = optionalRegisterCase.get();
        if (!registerCase.getState().equals(StateRegister.NOT_ASSIGNED))
            throw new StatusNotValidException();

        Optional<Unit> optionalUnit = unitRepository.findById(createCaseDTO.getUnitId());
        if (!optionalUnit.isPresent())
            throw new UnitNotFoundException();

        registerCase.setState(StateRegister.PENDING);
        registerCase.setZone(createCaseDTO.getZone());
        registerCase.setAcronymPatrol(createCaseDTO.getAcronymPatrol());
        registerCase.setPatrolLeader(createCaseDTO.getPatrolLeader());
        registerCase.setUnit(optionalUnit.get());
        registerCase.setDispatcher(userOptional.get());
        registerCaseRepository.save(registerCase);
        return registerCase;
    }

    public RegisterCase finalizeCase(FinalizeCaseDTO createCaseDTO) throws RegisterCaseNotFoundException, StatusNotValidException, UnitNotFoundException, UserNotFoundException {
        log.debug("Request to save RegisterCase : {}", createCaseDTO);
        Optional<User> userOptional = userService.getUserWithAuthorities();
        if (!userOptional.isPresent())
            throw new UserNotFoundException();
        Optional<RegisterCase> optionalRegisterCase = findOne(createCaseDTO.getId());
        if (!optionalRegisterCase.isPresent())
            throw new RegisterCaseNotFoundException();
        RegisterCase registerCase = optionalRegisterCase.get();
        if (!registerCase.getState().equals(StateRegister.PENDING))
            throw new StatusNotValidException();

        registerCase.setState(StateRegister.FINALIZED);
        registerCase.setStateCase(createCaseDTO.getStateCase());
        registerCase.setDescriptionCompletion(createCaseDTO.getDescriptionCompletion());

        registerCaseRepository.save(registerCase);
        return registerCase;
    }

    public RegisterCase checkCase(CheckCaseDTO checkCaseDTO) throws RegisterCaseNotFoundException, StatusNotValidException, UnitNotFoundException, UserNotFoundException, CheckException {
        log.debug("Request to save RegisterCase : {}", checkCaseDTO);
        Optional<User> userOptional = userService.getUserWithAuthorities();
        if (!userOptional.isPresent())
            throw new UserNotFoundException();
        Optional<RegisterCase> optionalRegisterCase = findOne(checkCaseDTO.getId());
        if (!optionalRegisterCase.isPresent())
            throw new RegisterCaseNotFoundException();
        RegisterCase registerCase = optionalRegisterCase.get();
        if (!registerCase.getState().equals(StateRegister.PENDING))
            throw new StatusNotValidException();
        if (registerCase.getCheckDate() != null)
            throw new CheckException();

        registerCase.setCheckDate(ZonedDateTime.now());

        registerCaseRepository.save(registerCase);
        return registerCase;
    }

    /**
     * Get all the registerCases.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<RegisterCase> findAll(Pageable pageable) {
        log.debug("Request to get all RegisterCases");
        return registerCaseRepository.findAll(pageable);
    }

    /**
     * Get one registerCase by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<RegisterCase> findOne(Long id) {
        log.debug("Request to get RegisterCase : {}", id);
        return registerCaseRepository.findById(id);
    }

    /**
     * Delete the registerCase by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete RegisterCase : {}", id);
        registerCaseRepository.deleteById(id);
    }

    private BigDecimal roundScale(BigDecimal value) {
        if (value != null)
            return value.setScale(10, BigDecimal.ROUND_CEILING);
        return null;
    }
}
