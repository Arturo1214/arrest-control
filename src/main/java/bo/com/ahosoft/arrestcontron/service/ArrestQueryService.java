package bo.com.ahosoft.arrestcontron.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.*;

import javax.persistence.criteria.JoinType;

import bo.com.ahosoft.arrestcontron.domain.enumeration.ArrestType;
import bo.com.ahosoft.arrestcontron.domain.enumeration.VehicleType;
import bo.com.ahosoft.arrestcontron.repository.OfficeRepository;
import bo.com.ahosoft.arrestcontron.repository.UnitRepository;
import bo.com.ahosoft.arrestcontron.repository.UserRepository;
import bo.com.ahosoft.arrestcontron.security.AuthoritiesConstants;
import bo.com.ahosoft.arrestcontron.security.SecurityUtils;
import bo.com.ahosoft.arrestcontron.service.dto.TotalArrest;
import bo.com.ahosoft.arrestcontron.service.dto.TotalArrestCriteria;
import bo.com.ahosoft.arrestcontron.service.errors.UserNotFoundException;
import io.github.jhipster.service.filter.LongFilter;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.github.jhipster.service.QueryService;

import bo.com.ahosoft.arrestcontron.domain.Arrest;
import bo.com.ahosoft.arrestcontron.domain.*; // for static metamodels
import bo.com.ahosoft.arrestcontron.repository.ArrestRepository;
import bo.com.ahosoft.arrestcontron.service.dto.ArrestCriteria;
import bo.com.ahosoft.arrestcontron.service.dto.ArrestDTO;
import bo.com.ahosoft.arrestcontron.service.mapper.ArrestMapper;

/**
 * Service for executing complex queries for {@link Arrest} entities in the database.
 * The main input is a {@link ArrestCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link ArrestDTO} or a {@link Page} of {@link ArrestDTO} which fulfills the criteria.
 */
@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class ArrestQueryService extends QueryService<Arrest> {

    private final Logger log = LoggerFactory.getLogger(ArrestQueryService.class);

    private final ArrestRepository arrestRepository;

    private final UnitRepository unitRepository;

    private final OfficeRepository officeRepository;

    private final UserRepository userRepository;


    /**
     * Return a {@link List} of {@link ArrestDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<Arrest> findByCriteria(ArrestCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Arrest> specification = createSpecification(criteria);
        return arrestRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link ArrestDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<Arrest> findByCriteria(ArrestCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Arrest> specification = createSpecification(criteria);
        return arrestRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(ArrestCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Arrest> specification = createSpecification(criteria);
        return arrestRepository.count(specification);
    }

    @Transactional(readOnly = true)
    public List<TotalArrest> findTotalByCriteria(TotalArrestCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        ArrestCriteria arrestCriteria = new ArrestCriteria();
        if (criteria != null)
            arrestCriteria.setArrestDate(criteria.getArrestDate());
        List<TotalArrest> totalArrestList = new ArrayList<>();
        List<Unit> unitList = unitRepository.findAll();
        for (Unit unit : unitList) {
            TotalArrest totalArrest = new TotalArrest();
            totalArrest.setUnit(unit);
            arrestCriteria.setOfficeId(null);
            arrestCriteria.setType(null);
            arrestCriteria.setVehicleType(null);

            LongFilter unitFilter = new LongFilter();
            unitFilter.setEquals(unit.getId());

            arrestCriteria.setUnitId(unitFilter);
            totalArrest.setTotalArrested(countByCriteria(arrestCriteria));

            ArrestCriteria.ArrestTypeFilter arrestTypeFilter = new ArrestCriteria.ArrestTypeFilter();
            arrestTypeFilter.setEquals(ArrestType.PEDESTRIAN);
            arrestCriteria.setType(arrestTypeFilter);

            totalArrest.setTotalPedestrian(countByCriteria(arrestCriteria));

            arrestTypeFilter.setEquals(ArrestType.PASSENGER);
            arrestCriteria.setType(arrestTypeFilter);

            totalArrest.setTotalPassenger(countByCriteria(arrestCriteria));

            arrestTypeFilter.setEquals(ArrestType.DRIVER);
            arrestCriteria.setType(arrestTypeFilter);

            totalArrest.setTotalDriver(countByCriteria(arrestCriteria));

            ArrestCriteria.VehicleTypeFilter vehicleTypeFilter = new ArrestCriteria.VehicleTypeFilter();
            vehicleTypeFilter.setEquals(VehicleType.VEHICLE);
            arrestCriteria.setVehicleType(vehicleTypeFilter);

            totalArrest.setTotalVehicle(countByCriteria(arrestCriteria));

            vehicleTypeFilter.setEquals(VehicleType.MOTORCYCLE);
            arrestCriteria.setVehicleType(vehicleTypeFilter);

            totalArrest.setTotalMotorcycle(countByCriteria(arrestCriteria));

            List<Office> officeList = officeRepository.findAllByUnitId(unit.getId());
            for (Office office : officeList) {
                LongFilter officeFilter = new LongFilter();
                officeFilter.setEquals(office.getId());

                arrestCriteria.setOfficeId(officeFilter);
                arrestCriteria.setType(null);
                arrestCriteria.setVehicleType(null);

                TotalArrest.DetailTotal detailTotal = new TotalArrest.DetailTotal();
                detailTotal.setOffice(office);
                detailTotal.setTotalArrested(countByCriteria(arrestCriteria));

                arrestTypeFilter = new ArrestCriteria.ArrestTypeFilter();
                arrestTypeFilter.setEquals(ArrestType.PEDESTRIAN);
                arrestCriteria.setType(arrestTypeFilter);

                detailTotal.setTotalPedestrian(countByCriteria(arrestCriteria));

                arrestTypeFilter.setEquals(ArrestType.PASSENGER);
                arrestCriteria.setType(arrestTypeFilter);

                detailTotal.setTotalPassenger(countByCriteria(arrestCriteria));

                arrestTypeFilter.setEquals(ArrestType.DRIVER);
                arrestCriteria.setType(arrestTypeFilter);

                detailTotal.setTotalDriver(countByCriteria(arrestCriteria));

                vehicleTypeFilter = new ArrestCriteria.VehicleTypeFilter();
                vehicleTypeFilter.setEquals(VehicleType.VEHICLE);
                arrestCriteria.setVehicleType(vehicleTypeFilter);

                detailTotal.setTotalVehicle(countByCriteria(arrestCriteria));

                vehicleTypeFilter.setEquals(VehicleType.MOTORCYCLE);
                arrestCriteria.setVehicleType(vehicleTypeFilter);

                detailTotal.setTotalMotorcycle(countByCriteria(arrestCriteria));

                totalArrest.getDetailTotals().add(detailTotal);
            }

            totalArrestList.add(totalArrest);
        }

        return totalArrestList;
    }

    @Transactional(readOnly = true)
    public List<TotalArrest> findTotalByCriteria(ArrestCriteria criteria) throws UserNotFoundException {

        Optional<User> userOptional = SecurityUtils.getCurrentUserLogin()
            .flatMap(userRepository::findOneWithAuthoritiesByLogin);

        if (!userOptional.isPresent())
            throw new UserNotFoundException();

        boolean admin = false;
        User currentUser = userOptional.get();
        for (Authority authority : currentUser.getAuthorities()) {
            if (authority.getName().equals(AuthoritiesConstants.ADMIN) ||
                authority.getName().equals(AuthoritiesConstants.REPORT)) {
                admin = true;
                break;
            }
        }

        List<Arrest> arrestList = findByCriteria(criteria);
        log.debug("Total arrest {}", arrestList.size());
        List<TotalArrest> totalArrestList = new ArrayList<>();
        List<Unit> unitList;
        if (admin)
            unitList = unitRepository.findAll();
        else
            unitList = Collections.singletonList(currentUser.getOffice().getUnit());
        for (Unit unit : unitList) {
            TotalArrest totalArrest = new TotalArrest();
            totalArrest.setUnit(unit);


            totalArrest.setTotalPedestrian(arrestList.stream().filter(arrest -> arrest.getOffice().getUnit().equals(unit) &&
                arrest.getType().equals(ArrestType.PEDESTRIAN)).count());

            totalArrest.setTotalPassenger(arrestList.stream().filter(arrest -> arrest.getOffice().getUnit().equals(unit) &&
                arrest.getType().equals(ArrestType.PASSENGER)).count());

            totalArrest.setTotalDriver(arrestList.stream().filter(arrest -> arrest.getOffice().getUnit().equals(unit) &&
                arrest.getType().equals(ArrestType.DRIVER) && arrest.getWithDriver()).count());

            totalArrest.setTotalArrested(totalArrest.getTotalPedestrian() + totalArrest.getTotalPassenger() + totalArrest.getTotalDriver());

            totalArrest.setTotalMotorized(arrestList.stream().filter(arrest -> arrest.getOffice().getUnit().equals(unit) &&
                arrest.getType().equals(ArrestType.DRIVER)).count());

            totalArrest.setTotalVehicle(arrestList.stream().filter(arrest -> arrest.getOffice().getUnit().equals(unit) &&
                arrest.getType().equals(ArrestType.DRIVER) && arrest.getVehicleType().equals(VehicleType.VEHICLE)).count());

            totalArrest.setTotalMotorcycle(arrestList.stream().filter(arrest -> arrest.getOffice().getUnit().equals(unit) &&
                arrest.getType().equals(ArrestType.DRIVER) && arrest.getVehicleType().equals(VehicleType.MOTORCYCLE)).count());

            List<Office> officeList = officeRepository.findAllByUnitId(unit.getId());
            for (Office office : officeList) {
                TotalArrest.DetailTotal detailTotal = new TotalArrest.DetailTotal();
                detailTotal.setOffice(office);



                detailTotal.setTotalPedestrian(arrestList.stream().filter(arrest -> arrest.getOffice().equals(office) &&
                    arrest.getType().equals(ArrestType.PEDESTRIAN)).count());

                detailTotal.setTotalPassenger(arrestList.stream().filter(arrest -> arrest.getOffice().equals(office) &&
                    arrest.getType().equals(ArrestType.PASSENGER)).count());

                detailTotal.setTotalDriver(arrestList.stream().filter(arrest -> arrest.getOffice().equals(office) &&
                    arrest.getType().equals(ArrestType.DRIVER) && arrest.getWithDriver()).count());

                detailTotal.setTotalArrested(detailTotal.getTotalPedestrian() + detailTotal.getTotalPassenger() + detailTotal.getTotalDriver());

                detailTotal.setTotalMotorized(arrestList.stream().filter(arrest -> arrest.getOffice().equals(office) &&
                    arrest.getType().equals(ArrestType.DRIVER)).count());

                detailTotal.setTotalVehicle(arrestList.stream().filter(arrest -> arrest.getOffice().equals(office) &&
                    arrest.getType().equals(ArrestType.DRIVER) && arrest.getVehicleType().equals(VehicleType.VEHICLE)).count());

                detailTotal.setTotalMotorcycle
                    (arrestList.stream().filter(arrest -> arrest.getOffice().equals(office) &&
                    arrest.getType().equals(ArrestType.DRIVER) && arrest.getVehicleType().equals(VehicleType.MOTORCYCLE)).count());

                totalArrest.getDetailTotals().add(detailTotal);
            }
            totalArrestList.add(totalArrest);
        }
        return totalArrestList;
    }

    /**
     * Function to convert {@link ArrestCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Arrest> createSpecification(ArrestCriteria criteria) {
        Specification<Arrest> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Arrest_.id));
            }
            if (criteria.getDocumentNumber() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDocumentNumber(), Arrest_.documentNumber));
            }
            if (criteria.getFullName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getFullName(), Arrest_.fullName));
            }
            if (criteria.getDescription() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDescription(), Arrest_.description));
            }
            if (criteria.getType() != null) {
                specification = specification.and(buildSpecification(criteria.getType(), Arrest_.type));
            }
            if (criteria.getVehicleType() != null) {
                specification = specification.and(buildSpecification(criteria.getVehicleType(), Arrest_.vehicleType));
            }
            if (criteria.getPlate() != null) {
                specification = specification.and(buildStringSpecification(criteria.getPlate(), Arrest_.plate));
            }
            if (criteria.getArrestDate() != null) {
                if (criteria.getArrestDate().getGreaterThanOrEqual() != null) {
                    LocalDateTime localDateTime = criteria.getArrestDate().getGreaterThanOrEqual().toLocalDateTime();
                    criteria.getArrestDate().setGreaterThanOrEqual(localDateTime.atZone(ZonedDateTime.now().getZone()));
                    log.debug("getGreaterThanOrEqual {}", criteria.getArrestDate().getGreaterThanOrEqual());
                }
                if (criteria.getArrestDate().getLessThanOrEqual() != null) {
                    LocalDateTime localDateTime = criteria.getArrestDate().getLessThanOrEqual().toLocalDateTime();
                    criteria.getArrestDate().setLessThanOrEqual(localDateTime.atZone(ZonedDateTime.now().getZone()));
                    log.debug("getLessThanOrEqual {}", criteria.getArrestDate().getLessThanOrEqual());
                }
                specification = specification.and(buildRangeSpecification(criteria.getArrestDate(), Arrest_.arrestDate));
            }
            if (criteria.getStatus() != null) {
                specification = specification.and(buildSpecification(criteria.getStatus(), Arrest_.status));
            }
            if (criteria.getStateDescription() != null) {
                specification = specification.and(buildStringSpecification(criteria.getStateDescription(), Arrest_.stateDescription));
            }
            if (criteria.getDepositNumber() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDepositNumber(), Arrest_.depositNumber));
            }

            if (criteria.getUserId() != null) {
                specification = specification.and(buildSpecification(criteria.getUserId(),
                    root -> root.join(Arrest_.user, JoinType.LEFT).get(User_.id)));
            }
            if (criteria.getUserLogin() != null) {
                specification = specification.and(buildSpecification(criteria.getUserLogin(),
                    root -> root.join(Arrest_.user, JoinType.LEFT).get(User_.login)));
            }
            if (criteria.getUserFullName() != null) {
                specification = specification.and(buildSpecification(criteria.getUserFullName(),
                    root -> root.join(Arrest_.user, JoinType.LEFT).get(User_.fullName)));
            }

            if (criteria.getUnitId() != null) {
                specification = specification.and(buildSpecification(criteria.getUnitId(),
                    root -> root.join(Arrest_.office, JoinType.LEFT)
                        .join(Office_.unit, JoinType.INNER)
                        .get(Unit_.id)));
            }
            if (criteria.getOfficeId() != null) {
                specification = specification.and(buildSpecification(criteria.getOfficeId(),
                    root -> root.join(Arrest_.office, JoinType.LEFT)
                        .get(Office_.id)));
            }

            if (criteria.getUserStatusId() != null) {
                specification = specification.and(buildSpecification(criteria.getUserStatusId(),
                    root -> root.join(Arrest_.userStatus, JoinType.LEFT).get(User_.id)));
            }

        }
        return specification;
    }
}
