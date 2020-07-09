package bo.com.ahosoft.arrestcontron.service.dto;

import bo.com.ahosoft.arrestcontron.domain.RegisterCase;
import bo.com.ahosoft.arrestcontron.domain.enumeration.StateCase;
import bo.com.ahosoft.arrestcontron.domain.enumeration.StateRegister;
import bo.com.ahosoft.arrestcontron.web.rest.RegisterCaseResource;
import io.github.jhipster.service.Criteria;
import io.github.jhipster.service.filter.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * Criteria class for the {@link RegisterCase} entity. This class is used
 * in {@link RegisterCaseResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /register-cases?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class RegisterCaseCriteria implements Serializable, Criteria {
    /**
     * Class for filtering StateRegister
     */
    public static class StateRegisterFilter extends Filter<StateRegister> {

        public StateRegisterFilter() {
        }

        public StateRegisterFilter(StateRegisterFilter filter) {
            super(filter);
        }

        @Override
        public StateRegisterFilter copy() {
            return new StateRegisterFilter(this);
        }

    }
    /**
     * Class for filtering StateCase
     */
    public static class StateCaseFilter extends Filter<StateCase> {

        public StateCaseFilter() {
        }

        public StateCaseFilter(StateCaseFilter filter) {
            super(filter);
        }

        @Override
        public StateCaseFilter copy() {
            return new StateCaseFilter(this);
        }

    }

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private ZonedDateTimeFilter registrationDate;

    private StringFilter address;

    private StringFilter informer;

    private StringFilter phone;

    private StringFilter description;

    private StateRegisterFilter state;

    private StringFilter zone;

    private StringFilter acronymPatrol;

    private StringFilter patrolLeader;

    private StateCaseFilter stateCase;

    private StringFilter descriptionCompletion;

    private BigDecimalFilter latitude;

    private BigDecimalFilter longitude;

    private LongFilter receptionistId;

    private LongFilter unitId;

    private LongFilter dispatcherId;

    private LongFilter typeCaseId;

    public RegisterCaseCriteria() {
    }

    public RegisterCaseCriteria(RegisterCaseCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.registrationDate = other.registrationDate == null ? null : other.registrationDate.copy();
        this.address = other.address == null ? null : other.address.copy();
        this.informer = other.informer == null ? null : other.informer.copy();
        this.phone = other.phone == null ? null : other.phone.copy();
        this.description = other.description == null ? null : other.description.copy();
        this.state = other.state == null ? null : other.state.copy();
        this.zone = other.zone == null ? null : other.zone.copy();
        this.acronymPatrol = other.acronymPatrol == null ? null : other.acronymPatrol.copy();
        this.patrolLeader = other.patrolLeader == null ? null : other.patrolLeader.copy();
        this.stateCase = other.stateCase == null ? null : other.stateCase.copy();
        this.descriptionCompletion = other.descriptionCompletion == null ? null : other.descriptionCompletion.copy();
        this.latitude = other.latitude == null ? null : other.latitude.copy();
        this.longitude = other.longitude == null ? null : other.longitude.copy();
        this.receptionistId = other.receptionistId == null ? null : other.receptionistId.copy();
        this.unitId = other.unitId == null ? null : other.unitId.copy();
        this.dispatcherId = other.dispatcherId == null ? null : other.dispatcherId.copy();
        this.typeCaseId = other.typeCaseId == null ? null : other.typeCaseId.copy();
    }

    @Override
    public RegisterCaseCriteria copy() {
        return new RegisterCaseCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public ZonedDateTimeFilter getRegistrationDate() {
        return registrationDate;
    }

    public void setRegistrationDate(ZonedDateTimeFilter registrationDate) {
        this.registrationDate = registrationDate;
    }

    public StringFilter getAddress() {
        return address;
    }

    public void setAddress(StringFilter address) {
        this.address = address;
    }

    public StringFilter getInformer() {
        return informer;
    }

    public void setInformer(StringFilter informer) {
        this.informer = informer;
    }

    public StringFilter getPhone() {
        return phone;
    }

    public void setPhone(StringFilter phone) {
        this.phone = phone;
    }

    public StringFilter getDescription() {
        return description;
    }

    public void setDescription(StringFilter description) {
        this.description = description;
    }

    public StateRegisterFilter getState() {
        return state;
    }

    public void setState(StateRegisterFilter state) {
        this.state = state;
    }

    public StringFilter getZone() {
        return zone;
    }

    public void setZone(StringFilter zone) {
        this.zone = zone;
    }

    public StringFilter getAcronymPatrol() {
        return acronymPatrol;
    }

    public void setAcronymPatrol(StringFilter acronymPatrol) {
        this.acronymPatrol = acronymPatrol;
    }

    public StringFilter getPatrolLeader() {
        return patrolLeader;
    }

    public void setPatrolLeader(StringFilter patrolLeader) {
        this.patrolLeader = patrolLeader;
    }

    public StateCaseFilter getStateCase() {
        return stateCase;
    }

    public void setStateCase(StateCaseFilter stateCase) {
        this.stateCase = stateCase;
    }

    public StringFilter getDescriptionCompletion() {
        return descriptionCompletion;
    }

    public void setDescriptionCompletion(StringFilter descriptionCompletion) {
        this.descriptionCompletion = descriptionCompletion;
    }

    public BigDecimalFilter getLatitude() {
        return latitude;
    }

    public void setLatitude(BigDecimalFilter latitude) {
        this.latitude = latitude;
    }

    public BigDecimalFilter getLongitude() {
        return longitude;
    }

    public void setLongitude(BigDecimalFilter longitude) {
        this.longitude = longitude;
    }

    public LongFilter getReceptionistId() {
        return receptionistId;
    }

    public void setReceptionistId(LongFilter receptionistId) {
        this.receptionistId = receptionistId;
    }

    public LongFilter getUnitId() {
        return unitId;
    }

    public void setUnitId(LongFilter unitId) {
        this.unitId = unitId;
    }

    public LongFilter getDispatcherId() {
        return dispatcherId;
    }

    public void setDispatcherId(LongFilter dispatcherId) {
        this.dispatcherId = dispatcherId;
    }

    public LongFilter getTypeCaseId() {
        return typeCaseId;
    }

    public void setTypeCaseId(LongFilter typeCaseId) {
        this.typeCaseId = typeCaseId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final RegisterCaseCriteria that = (RegisterCaseCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(registrationDate, that.registrationDate) &&
            Objects.equals(address, that.address) &&
            Objects.equals(informer, that.informer) &&
            Objects.equals(phone, that.phone) &&
            Objects.equals(description, that.description) &&
            Objects.equals(state, that.state) &&
            Objects.equals(zone, that.zone) &&
            Objects.equals(acronymPatrol, that.acronymPatrol) &&
            Objects.equals(patrolLeader, that.patrolLeader) &&
            Objects.equals(stateCase, that.stateCase) &&
            Objects.equals(descriptionCompletion, that.descriptionCompletion) &&
            Objects.equals(latitude, that.latitude) &&
            Objects.equals(longitude, that.longitude) &&
            Objects.equals(receptionistId, that.receptionistId) &&
            Objects.equals(unitId, that.unitId) &&
            Objects.equals(dispatcherId, that.dispatcherId) &&
            Objects.equals(typeCaseId, that.typeCaseId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        registrationDate,
        address,
        informer,
        phone,
        description,
        state,
        zone,
        acronymPatrol,
        patrolLeader,
        stateCase,
        descriptionCompletion,
        latitude,
        longitude,
        receptionistId,
        unitId,
        dispatcherId,
        typeCaseId
        );
    }

    @Override
    public String toString() {
        return "RegisterCaseCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (registrationDate != null ? "registrationDate=" + registrationDate + ", " : "") +
                (address != null ? "address=" + address + ", " : "") +
                (informer != null ? "informer=" + informer + ", " : "") +
                (phone != null ? "phone=" + phone + ", " : "") +
                (description != null ? "description=" + description + ", " : "") +
                (state != null ? "state=" + state + ", " : "") +
                (zone != null ? "zone=" + zone + ", " : "") +
                (acronymPatrol != null ? "acronymPatrol=" + acronymPatrol + ", " : "") +
                (patrolLeader != null ? "patrolLeader=" + patrolLeader + ", " : "") +
                (stateCase != null ? "stateCase=" + stateCase + ", " : "") +
                (descriptionCompletion != null ? "descriptionCompletion=" + descriptionCompletion + ", " : "") +
                (latitude != null ? "latitude=" + latitude + ", " : "") +
                (longitude != null ? "longitude=" + longitude + ", " : "") +
                (receptionistId != null ? "receptionistId=" + receptionistId + ", " : "") +
                (unitId != null ? "unitId=" + unitId + ", " : "") +
                (dispatcherId != null ? "dispatcherId=" + dispatcherId + ", " : "") +
                (typeCaseId != null ? "typeCaseId=" + typeCaseId + ", " : "") +
            "}";
    }

}
