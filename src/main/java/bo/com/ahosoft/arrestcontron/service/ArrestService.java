package bo.com.ahosoft.arrestcontron.service;

import bo.com.ahosoft.arrestcontron.domain.Arrest;
import bo.com.ahosoft.arrestcontron.domain.Authority;
import bo.com.ahosoft.arrestcontron.domain.SystemParameter;
import bo.com.ahosoft.arrestcontron.domain.User;
import bo.com.ahosoft.arrestcontron.domain.enumeration.ArrestType;
import bo.com.ahosoft.arrestcontron.domain.enumeration.PaymentStatus;
import bo.com.ahosoft.arrestcontron.repository.ArrestRepository;
import bo.com.ahosoft.arrestcontron.repository.SystemParameterRepository;
import bo.com.ahosoft.arrestcontron.repository.UserRepository;
import bo.com.ahosoft.arrestcontron.security.AuthoritiesConstants;
import bo.com.ahosoft.arrestcontron.security.SecurityUtils;
import bo.com.ahosoft.arrestcontron.service.dto.ArrestDTO;
import bo.com.ahosoft.arrestcontron.service.errors.*;
import bo.com.ahosoft.arrestcontron.service.mapper.ArrestMapper;
import bo.com.ahosoft.arrestcontron.service.util.SystemParameterConstants;
import bo.com.ahosoft.arrestcontron.web.rest.vm.ArrestNoFineVM;
import bo.com.ahosoft.arrestcontron.web.rest.vm.ArrestPaidOutVM;
import bo.com.ahosoft.arrestcontron.web.rest.vm.ArrestPendingVM;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.Optional;

/**
 * Service Implementation for managing {@link Arrest}.
 */
@RequiredArgsConstructor
@Service
@Transactional
public class ArrestService {

    private final Logger log = LoggerFactory.getLogger(ArrestService.class);

    private final ArrestRepository arrestRepository;

    private final ArrestMapper arrestMapper;

    private final UserRepository userRepository;

    private final SystemParameterRepository systemParameterRepository;

    /**
     * Save a arrest.
     *
     * @param arrestDTO the entity to save.
     * @return the persisted entity.
     */
    public Arrest save(ArrestDTO arrestDTO) throws UserNotFoundException, ArrestNotFoundException, UserNotValidException, DateArrestOutRangeException {

        Optional<User> userOptional = SecurityUtils.getCurrentUserLogin()
            .flatMap(userRepository::findOneWithAuthoritiesByLogin);

        if (!userOptional.isPresent())
            throw new UserNotFoundException();

        boolean admin = false;
        User currentUser = userOptional.get();
        for (Authority authority : currentUser.getAuthorities()) {
            if (authority.getName().equals(AuthoritiesConstants.ADMIN)) {
                admin = true;
                break;
            }
        }
        arrestDTO.setArrestDate(arrestDTO.getArrestDate() != null ? arrestDTO.getArrestDate().withZoneSameInstant(ZonedDateTime.now().getZone()) : ZonedDateTime.now());
        log.debug("Request to save Arrest : {}", arrestDTO);
        if (arrestDTO.getId() == null) {
            Arrest arrestNew = arrestMapper.toEntity(arrestDTO);
            arrestNew.setUser(userOptional.get());
            arrestNew.setOffice(userOptional.get().getOffice());
            arrestNew.setStatus(PaymentStatus.PENDING);
            LocalDate localDateArrest = arrestNew.getArrestDate().toLocalDate();
            LocalDate localDateCurrent = LocalDate.now();
            if (arrestNew.getType().equals(ArrestType.DRIVER)) {
                if (arrestNew.getWithDriver() == null)
                    arrestNew.setWithDriver(true);
            } else {
                arrestNew.setWithDriver(null);
            }
            log.debug("DATE localDateArrest {}", localDateArrest );
            log.debug("DATE localDateCurrent {}", localDateCurrent );
            if (!admin) {
                if (!localDateCurrent.isEqual(localDateArrest)) {
                    if (localDateCurrent.isBefore(localDateArrest)) {
                        throw new DateArrestOutRangeException();
                    }

                    if (localDateCurrent.isAfter(localDateArrest)) {
                        Optional<SystemParameter> systemParameterOptional = systemParameterRepository.findById(SystemParameterConstants.MAXIMUM_RECORD_TIME);
                        if (systemParameterOptional.isPresent()) {
                            SystemParameter systemParameter = systemParameterOptional.get();
                            if (systemParameter.getValue() != null && !systemParameter.getValue().isEmpty()) {
                                LocalDate localDateArrestPlus = localDateArrest.plusDays(1);
                                if (!localDateCurrent.isEqual(localDateArrestPlus))
                                    throw new DateArrestOutRangeException();

                                LocalDateTime currentLocalDateTime = LocalDateTime.now();
                                LocalDateTime maxLocalDate = localDateCurrent.atStartOfDay().plusHours(Long.parseLong(systemParameter.getValue()));
                                if (currentLocalDateTime.isAfter(maxLocalDate))
                                    throw new DateArrestOutRangeException();
                            }
                        }
                    }
                }
            }

            return arrestRepository.save(arrestNew);
        } else {
            Optional<Arrest> arrestOptional = arrestRepository.findById(arrestDTO.getId());
            if (!arrestOptional.isPresent())
                throw new ArrestNotFoundException();

            Arrest arrestUpdate = arrestOptional.get();

            if (!admin) {
                if (!arrestUpdate.getOffice().getUnit().equals(currentUser.getOffice().getUnit()))
                    throw new UserNotValidException();
            }
            arrestUpdate.setDocumentNumber(arrestDTO.getDocumentNumber());
            arrestUpdate.setFullName(arrestDTO.getFullName());
            arrestUpdate.setDescription(arrestDTO.getDescription());
            arrestUpdate.setType(arrestDTO.getType());
            arrestUpdate.setVehicleType(arrestDTO.getVehicleType());
            arrestUpdate.setPlate(arrestDTO.getPlate());

            if (arrestDTO.getType().equals(ArrestType.DRIVER)) {
                if (arrestDTO.getWithDriver() == null)
                    arrestUpdate.setWithDriver(true);
                else
                    arrestUpdate.setWithDriver(arrestDTO.getWithDriver());
            } else {
                arrestUpdate.setWithDriver(null);
            }
            if (admin)
                arrestUpdate.setArrestDate(arrestDTO.getArrestDate());

            return arrestRepository.save(arrestUpdate);
        }
    }

    public Arrest updateArrestNoFine(ArrestNoFineVM arrestNoFineVM) throws ArrestNotFoundException, UserNotFoundException, UserNotValidException, StatusNotValidException {
        Optional<User> userOptional = SecurityUtils.getCurrentUserLogin()
            .flatMap(userRepository::findOneWithAuthoritiesByLogin);

        if (!userOptional.isPresent())
            throw new UserNotFoundException();

        boolean admin = false;
        User currentUser = userOptional.get();
        for (Authority authority : currentUser.getAuthorities()) {
            if (authority.getName().equals(AuthoritiesConstants.ADMIN)) {
                admin = true;
                break;
            }
        }

        Optional<Arrest> arrestOptional = arrestRepository.findById(arrestNoFineVM.getId());
        if (!arrestOptional.isPresent())
            throw new ArrestNotFoundException();

        Arrest arrest = arrestOptional.get();

        if (!admin) {
            if (!arrest.getOffice().getUnit().equals(currentUser.getOffice().getUnit()))
                throw new UserNotValidException();
        }

        if (!arrest.getStatus().equals(PaymentStatus.PENDING))
            throw new StatusNotValidException();

        arrest.setStatus(PaymentStatus.NO_FINE);
        arrest.setStateDescription(arrestNoFineVM.getStateDescription());
        arrest.setUserStatus(currentUser);

        arrestRepository.save(arrest);
        return arrest;
    }

    public Arrest updateArrestPaidOut(ArrestPaidOutVM arrestPaidOutVM) throws StatusNotValidException, UserNotValidException, ArrestNotFoundException, UserNotFoundException {
        Optional<User> userOptional = SecurityUtils.getCurrentUserLogin()
            .flatMap(userRepository::findOneWithAuthoritiesByLogin);

        if (!userOptional.isPresent())
            throw new UserNotFoundException();

        boolean admin = false;
        User currentUser = userOptional.get();
        for (Authority authority : currentUser.getAuthorities()) {
            if (authority.getName().equals(AuthoritiesConstants.ADMIN)) {
                admin = true;
                break;
            }
        }

        Optional<Arrest> arrestOptional = arrestRepository.findById(arrestPaidOutVM.getId());
        if (!arrestOptional.isPresent())
            throw new ArrestNotFoundException();

        Arrest arrest = arrestOptional.get();

        if (!admin) {
            if (!arrest.getOffice().getUnit().equals(currentUser.getOffice().getUnit()))
                throw new UserNotValidException();
        }

        if (!arrest.getStatus().equals(PaymentStatus.PENDING))
            throw new StatusNotValidException();

        arrest.setStatus(PaymentStatus.PAID_OUT);
        arrest.setDepositNumber(arrestPaidOutVM.getDepositNumber());
        arrest.setStateDescription(arrestPaidOutVM.getStateDescription());
        arrest.setUserStatus(currentUser);

        arrestRepository.save(arrest);
        return arrest;
    }

    public Arrest updateArrestPending(ArrestPendingVM arrestPendingVM) throws StatusNotValidException, UserNotValidException, ArrestNotFoundException, UserNotFoundException {
        Optional<User> userOptional = SecurityUtils.getCurrentUserLogin()
            .flatMap(userRepository::findOneWithAuthoritiesByLogin);

        if (!userOptional.isPresent())
            throw new UserNotFoundException();

        boolean admin = false;
        User currentUser = userOptional.get();
        for (Authority authority : currentUser.getAuthorities()) {
            if (authority.getName().equals(AuthoritiesConstants.ADMIN)) {
                admin = true;
                break;
            }
        }

        Optional<Arrest> arrestOptional = arrestRepository.findById(arrestPendingVM.getId());
        if (!arrestOptional.isPresent())
            throw new ArrestNotFoundException();

        Arrest arrest = arrestOptional.get();

        if (!admin) {
            throw new UserNotValidException();
        }

        if (arrest.getStatus().equals(PaymentStatus.PENDING))
            throw new StatusNotValidException();

        arrest.setStatus(PaymentStatus.PENDING);
        arrest.setDepositNumber(null);
        arrest.setStateDescription(null);
        arrest.setUserStatus(null);

        arrestRepository.save(arrest);
        return arrest;
    }

    /**
     * Get all the arrests.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<Arrest> findAll(Pageable pageable) {
        log.debug("Request to get all Arrests");
        return arrestRepository.findAll(pageable);
    }

    /**
     * Get one arrest by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<Arrest> findOne(Long id) {
        log.debug("Request to get Arrest : {}", id);
        return arrestRepository.findById(id);
    }

    /**
     * Delete the arrest by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Arrest : {}", id);
        arrestRepository.deleteById(id);
    }
}
