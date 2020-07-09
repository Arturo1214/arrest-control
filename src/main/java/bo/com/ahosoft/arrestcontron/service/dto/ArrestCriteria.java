package bo.com.ahosoft.arrestcontron.service.dto;

import java.io.Serializable;
import java.util.Objects;

import bo.com.ahosoft.arrestcontron.domain.enumeration.ArrestType;
import bo.com.ahosoft.arrestcontron.domain.enumeration.PaymentStatus;
import bo.com.ahosoft.arrestcontron.domain.enumeration.VehicleType;
import io.github.jhipster.service.Criteria;
import io.github.jhipster.service.filter.*;

/**
 * Criteria class for the {@link bo.com.ahosoft.arrestcontron.domain.Arrest} entity. This class is used
 * in {@link bo.com.ahosoft.arrestcontron.web.rest.ArrestResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /arrests?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class ArrestCriteria implements Serializable, Criteria {
    /**
     * Class for filtering ArrestType
     */
    public static class ArrestTypeFilter extends Filter<ArrestType> {

        public ArrestTypeFilter() {
        }

        public ArrestTypeFilter(ArrestTypeFilter filter) {
            super(filter);
        }

        @Override
        public ArrestTypeFilter copy() {
            return new ArrestTypeFilter(this);
        }

    }
    /**
     * Class for filtering VehicleType
     */
    public static class VehicleTypeFilter extends Filter<VehicleType> {

        public VehicleTypeFilter() {
        }

        public VehicleTypeFilter(VehicleTypeFilter filter) {
            super(filter);
        }

        @Override
        public VehicleTypeFilter copy() {
            return new VehicleTypeFilter(this);
        }

    }
    /**
     * Class for filtering PaymentStatus
     */
    public static class PaymentStatusFilter extends Filter<PaymentStatus> {

        public PaymentStatusFilter() {
        }

        public PaymentStatusFilter(PaymentStatusFilter filter) {
            super(filter);
        }

        @Override
        public PaymentStatusFilter copy() {
            return new PaymentStatusFilter(this);
        }

    }

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter documentNumber;

    private StringFilter fullName;

    private StringFilter description;

    private ArrestTypeFilter type;

    private VehicleTypeFilter vehicleType;

    private StringFilter plate;

    private ZonedDateTimeFilter arrestDate;

    private PaymentStatusFilter status;

    private StringFilter stateDescription;

    private StringFilter depositNumber;

    private LongFilter userId;

    private StringFilter userLogin;

    private StringFilter userFullName;

    private LongFilter unitId;

    private LongFilter officeId;

    private LongFilter userStatusId;

    public ArrestCriteria() {
    }

    public ArrestCriteria(ArrestCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.documentNumber = other.documentNumber == null ? null : other.documentNumber.copy();
        this.fullName = other.fullName == null ? null : other.fullName.copy();
        this.description = other.description == null ? null : other.description.copy();
        this.type = other.type == null ? null : other.type.copy();
        this.vehicleType = other.vehicleType == null ? null : other.vehicleType.copy();
        this.plate = other.plate == null ? null : other.plate.copy();
        this.arrestDate = other.arrestDate == null ? null : other.arrestDate.copy();
        this.status = other.status == null ? null : other.status.copy();
        this.stateDescription = other.stateDescription == null ? null : other.stateDescription.copy();
        this.depositNumber = other.depositNumber == null ? null : other.depositNumber.copy();
        this.userId = other.userId == null ? null : other.userId.copy();
        this.userLogin = other.userLogin == null ? null : other.userLogin.copy();
        this.userFullName = other.userFullName == null ? null : other.userFullName.copy();
        this.unitId = other.unitId == null ? null : other.unitId.copy();
        this.officeId = other.officeId == null ? null : other.officeId.copy();
        this.userStatusId = other.userStatusId == null ? null : other.userStatusId.copy();
    }

    @Override
    public ArrestCriteria copy() {
        return new ArrestCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getDocumentNumber() {
        return documentNumber;
    }

    public void setDocumentNumber(StringFilter documentNumber) {
        this.documentNumber = documentNumber;
    }

    public StringFilter getFullName() {
        return fullName;
    }

    public void setFullName(StringFilter fullName) {
        this.fullName = fullName;
    }

    public StringFilter getDescription() {
        return description;
    }

    public void setDescription(StringFilter description) {
        this.description = description;
    }

    public ArrestTypeFilter getType() {
        return type;
    }

    public void setType(ArrestTypeFilter type) {
        this.type = type;
    }

    public VehicleTypeFilter getVehicleType() {
        return vehicleType;
    }

    public void setVehicleType(VehicleTypeFilter vehicleType) {
        this.vehicleType = vehicleType;
    }

    public StringFilter getPlate() {
        return plate;
    }

    public void setPlate(StringFilter plate) {
        this.plate = plate;
    }

    public ZonedDateTimeFilter getArrestDate() {
        return arrestDate;
    }

    public void setArrestDate(ZonedDateTimeFilter arrestDate) {
        this.arrestDate = arrestDate;
    }

    public PaymentStatusFilter getStatus() {
        return status;
    }

    public void setStatus(PaymentStatusFilter status) {
        this.status = status;
    }

    public StringFilter getStateDescription() {
        return stateDescription;
    }

    public void setStateDescription(StringFilter stateDescription) {
        this.stateDescription = stateDescription;
    }

    public StringFilter getDepositNumber() {
        return depositNumber;
    }

    public void setDepositNumber(StringFilter depositNumber) {
        this.depositNumber = depositNumber;
    }

    public LongFilter getUserId() {
        return userId;
    }

    public void setUserId(LongFilter userId) {
        this.userId = userId;
    }

    public StringFilter getUserLogin() {
        return userLogin;
    }

    public void setUserLogin(StringFilter userLogin) {
        this.userLogin = userLogin;
    }

    public StringFilter getUserFullName() {
        return userFullName;
    }

    public void setUserFullName(StringFilter userFullName) {
        this.userFullName = userFullName;
    }

    public LongFilter getUnitId() {
        return unitId;
    }

    public void setUnitId(LongFilter unitId) {
        this.unitId = unitId;
    }

    public LongFilter getOfficeId() {
        return officeId;
    }

    public void setOfficeId(LongFilter officeId) {
        this.officeId = officeId;
    }

    public LongFilter getUserStatusId() {
        return userStatusId;
    }

    public void setUserStatusId(LongFilter userStatusId) {
        this.userStatusId = userStatusId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ArrestCriteria that = (ArrestCriteria) o;
        return Objects.equals(id, that.id) &&
            Objects.equals(documentNumber, that.documentNumber) &&
            Objects.equals(fullName, that.fullName) &&
            Objects.equals(description, that.description) &&
            Objects.equals(type, that.type) &&
            Objects.equals(vehicleType, that.vehicleType) &&
            Objects.equals(plate, that.plate) &&
            Objects.equals(arrestDate, that.arrestDate) &&
            Objects.equals(status, that.status) &&
            Objects.equals(stateDescription, that.stateDescription) &&
            Objects.equals(depositNumber, that.depositNumber) &&
            Objects.equals(userId, that.userId) &&
            Objects.equals(userLogin, that.userLogin) &&
            Objects.equals(userFullName, that.userFullName) &&
            Objects.equals(unitId, that.unitId) &&
            Objects.equals(officeId, that.officeId) &&
            Objects.equals(userStatusId, that.userStatusId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, documentNumber, fullName, description, type, vehicleType, plate, arrestDate, status, stateDescription, depositNumber, userId, userLogin, userFullName, unitId, officeId, userStatusId);
    }

    @Override
    public String toString() {
        return "ArrestCriteria{" +
            "id=" + id +
            ", documentNumber=" + documentNumber +
            ", fullName=" + fullName +
            ", description=" + description +
            ", type=" + type +
            ", vehicleType=" + vehicleType +
            ", plate=" + plate +
            ", arrestDate=" + arrestDate +
            ", status=" + status +
            ", stateDescription=" + stateDescription +
            ", depositNumber=" + depositNumber +
            ", userId=" + userId +
            ", userLogin=" + userLogin +
            ", userFullName=" + userFullName +
            ", unitId=" + unitId +
            ", officeId=" + officeId +
            ", userStatusId=" + userStatusId +
            '}';
    }
}
